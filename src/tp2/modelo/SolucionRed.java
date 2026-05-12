package tp2.modelo;

import java.util.List;

/** Representa el resultado final de la planificación de la red */

public record SolucionRed(
    List<Conexion> conexiones,
    double costoTotal
) {
    public SolucionRed {
        if (conexiones == null) throw new IllegalArgumentException("La lista de conexiones no puede ser nula");
        if (costoTotal < 0) throw new IllegalArgumentException("El costo total no puede ser negativo");
    }
}
