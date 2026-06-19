package tp2.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        int totalLocalidades = localidades.size();
        List<Conexion> conexionesPosibles = generarGrafoCompleto(localidades, parametros);

        conexionesPosibles.sort(Comparator.comparingDouble(Conexion::costo));

        UnionFind componentes = new UnionFind(totalLocalidades);
        List<Conexion> arbolExpansionMinimo = new ArrayList<>();
        double costoTotal = 0.0;

        for (Conexion conexion : conexionesPosibles) {
            int indice1 = localidades.indexOf(conexion.localidad1());
            int indice2 = localidades.indexOf(conexion.localidad2());

            if (componentes.union(indice1, indice2)) {
                arbolExpansionMinimo.add(conexion);
                costoTotal += conexion.costo();
            }

            if (arbolExpansionMinimo.size() == totalLocalidades - 1) {
                break;
            }
        }

        return new SolucionRed(arbolExpansionMinimo, costoTotal);
    }

    /** Genera un grafo completo con todas las conexiones posibles entre localidades. */
    private List<Conexion> generarGrafoCompleto(List<Localidad> localidades, ParametrosCosto parametros) {
        List<Conexion> conexiones = new ArrayList<>();
        int cantidadLocalidades = localidades.size();

        for (int i = 0; i < cantidadLocalidades; i++) {
            for (int j = i + 1; j < cantidadLocalidades; j++) {
                Localidad l1 = localidades.get(i);
                Localidad l2 = localidades.get(j);
                conexiones.add(ValuadorConexiones.crearConexion(l1, l2, parametros));
            }
        }
        return conexiones;
    }
}
