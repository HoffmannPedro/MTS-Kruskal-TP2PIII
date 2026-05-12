package tp2.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Clase encargada de planificar la red óptima usando el algoritmo de Kruskal */

public class PlanificadorRed {

    /** Calcula el MST(Árbol generador mínimo) de la red para las localidades dadas */
    public SolucionRed planificar(List<Localidad> localidades, ParametrosCosto parametros) {
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

    /** Genera un grafo completo (todas las conexiones posibles entre las localidades) */
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
