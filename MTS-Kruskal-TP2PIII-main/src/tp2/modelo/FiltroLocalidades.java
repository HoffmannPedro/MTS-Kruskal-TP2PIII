package tp2.modelo;

import java.util.List;
import java.util.stream.Collectors;

/** Utilidad para búsqueda y filtrado de localidades */

public class FiltroLocalidades {

    /**
     * Filtra localidades por nombre (búsqueda parcial, case-insensitive)
     */
    public static List<Localidad> buscarPorNombre(List<Localidad> localidades, String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return localidades;
        }
        String busqueda = nombre.toLowerCase();
        return localidades.stream()
                .filter(l -> l.nombre().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
    }

    /**
     * Filtra localidades por provincia (búsqueda exacta, case-insensitive)
     */
    public static List<Localidad> filtrarPorProvincia(List<Localidad> localidades, String provincia) {
        if (provincia == null || provincia.isBlank() || provincia.equalsIgnoreCase("Todas")) {
            return localidades;
        }
        return localidades.stream()
                .filter(l -> l.provincia().equalsIgnoreCase(provincia))
                .collect(Collectors.toList());
    }

    /**
     * Aplica búsqueda y filtrado simultáneamente
     */
    public static List<Localidad> aplicarFiltros(List<Localidad> localidades, String nombre, String provincia) {
        List<Localidad> resultado = buscarPorNombre(localidades, nombre);
        return filtrarPorProvincia(resultado, provincia);
    }

    /**
     * Obtiene la lista de provincias únicas disponibles
     */
    public static List<String> obtenerProvincias(List<Localidad> localidades) {
        return localidades.stream()
                .map(Localidad::provincia)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
