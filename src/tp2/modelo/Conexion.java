package tp2.modelo;

/** Representa una conexión (arista) entre dos localidades */

public record Conexion(
        Localidad localidad1,
        Localidad localidad2,
        double distancia,
        double costo) {
    public Conexion {
        if (localidad1 == null || localidad2 == null)
            throw new IllegalArgumentException("Las localidades no pueden ser nulas");
        if (localidad1.equals(localidad2))
            throw new IllegalArgumentException("No se puede conectar una localidad consigo misma");
        if (distancia < 0)
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        if (costo < 0)
            throw new IllegalArgumentException("El costo no puede ser negativo");
    }
}
