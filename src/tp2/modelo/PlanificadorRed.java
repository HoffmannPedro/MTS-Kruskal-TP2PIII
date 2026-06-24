package tp2.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Clase encargada de mantener las localidades y planificar la red optima usando Kruskal. */

public class PlanificadorRed {
    private final RedLocalidades localidades;
    private final RepositorioLocalidades repositorio;

    public PlanificadorRed() {
        this.localidades = new RedLocalidades(List.of());
        this.repositorio = null;
    }

    public PlanificadorRed(RepositorioLocalidades repositorio) {
        this.repositorio = repositorio;
        this.localidades = new RedLocalidades(repositorio.cargar());
    }

    public void agregarLocalidad(Localidad localidad) {
        localidades.agregar(localidad);
        guardarCambios();
    }

    public void eliminarLocalidad(Localidad localidad) {
        localidades.eliminar(localidad);
        guardarCambios();
    }

    public void eliminarTodasLasLocalidades() {
        localidades.eliminarTodas();
        guardarCambios();
    }

    public List<Localidad> obtenerLocalidades() {
        return localidades.obtenerTodas();
    }

    public int cantidadLocalidades() {
        return localidades.cantidad();
    }

    public boolean sinLocalidades() {
        return localidades.isEmpty();
    }

    public List<Localidad> filtrarLocalidades(String nombre, String provincia) {
        return localidades.filtrar(nombre, provincia);
    }

    public List<String> obtenerProvincias() {
        return localidades.obtenerProvincias();
    }

    public SolucionRed planificar(ParametrosCosto parametros) {
        return calcularMST(localidades.obtenerTodas(), parametros);
    }

    /** Calcula el MST de la red para las localidades dadas. */
    SolucionRed planificar(List<Localidad> localidades, ParametrosCosto parametros) {
        return calcularMST(localidades, parametros);
    }

    private void guardarCambios() {
        if (repositorio != null) {
            repositorio.guardar(localidades.obtenerTodas());
        }
    }

    private SolucionRed calcularMST(List<Localidad> localidades, ParametrosCosto parametros) {
        if (localidades == null || localidades.size() < 2) {
            return new SolucionRed(new ArrayList<>(), 0.0);
        }

        Grafo<Localidad> grafoCompleto = generarGrafoCompleto(localidades, parametros);
        Grafo<Localidad> arbolExpansionMinimo = aplicarKruskal(grafoCompleto);

        return crearSolucionRed(arbolExpansionMinimo, parametros);
    }

    private Grafo<Localidad> aplicarKruskal(Grafo<Localidad> grafo) {
        List<Arista<Localidad>> aristasOrdenadas = new ArrayList<>(grafo.aristas());
        aristasOrdenadas.sort(Comparator.comparingDouble(Arista::peso));

        Map<Localidad, Integer> indices = indexarVertices(grafo.vertices());
        UnionFind componentes = new UnionFind(grafo.cantidadVertices());
        Grafo<Localidad> arbolExpansionMinimo = new Grafo<>();

        for (Localidad localidad : grafo.vertices()) {
            arbolExpansionMinimo.agregarVertice(localidad);
        }

        for (Arista<Localidad> arista : aristasOrdenadas) {
            int indice1 = indices.get(arista.vertice1());
            int indice2 = indices.get(arista.vertice2());

            if (!componentes.find(indice1, indice2)) {
                componentes.union(indice1, indice2);
                arbolExpansionMinimo.agregarArista(arista);
            }

            if (arbolExpansionMinimo.cantidadAristas() == grafo.cantidadVertices() - 1) {
                break;
            }
        }

        return arbolExpansionMinimo;
    }

    private Map<Localidad, Integer> indexarVertices(List<Localidad> vertices) {
        Map<Localidad, Integer> indices = new HashMap<>();

        for (int i = 0; i < vertices.size(); i++) {
            indices.put(vertices.get(i), i);
        }

        return indices;
    }

    /** Genera un grafo completo con todas las conexiones posibles entre localidades. */
    private Grafo<Localidad> generarGrafoCompleto(List<Localidad> localidades, ParametrosCosto parametros) {
        Grafo<Localidad> grafo = new Grafo<>();

        for (Localidad localidad : localidades) {
            grafo.agregarVertice(localidad);
        }

        for (int i = 0; i < localidades.size(); i++) {
            for (int j = i + 1; j < localidades.size(); j++) {
                Localidad l1 = localidades.get(i);
                Localidad l2 = localidades.get(j);
                Conexion conexion = ValuadorConexiones.crearConexion(l1, l2, parametros);
                grafo.agregarArista(new Arista<>(l1, l2, conexion.costo()));
            }
        }

        return grafo;
    }

    private SolucionRed crearSolucionRed(Grafo<Localidad> arbolExpansionMinimo, ParametrosCosto parametros) {
        List<Conexion> conexiones = new ArrayList<>();
        double costoTotal = 0.0;

        for (Arista<Localidad> arista : arbolExpansionMinimo.aristas()) {
            Conexion conexion = ValuadorConexiones.crearConexion(arista.vertice1(), arista.vertice2(), parametros);
            conexiones.add(conexion);
            costoTotal += conexion.costo();
        }

        return new SolucionRed(conexiones, costoTotal);
    }
}
