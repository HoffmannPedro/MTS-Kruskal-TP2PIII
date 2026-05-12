package tp2.modelo;

/**
 * Representa una localidad geográfica con sus coordenadas.
 * Inmutable al ser un Record de Java 21.
 */
public record Localidad(
        String nombre,
        String provincia,
        double latitud,
        double longitud) {
    public Localidad {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        if (provincia == null || provincia.isBlank())
            throw new IllegalArgumentException("La provincia no puede ser nula o vacía");
        if (latitud < -90 || latitud > 90)
            throw new IllegalArgumentException("Latitud fuera de rango (-90 a 90)");
        if (longitud < -180 || longitud > 180)
            throw new IllegalArgumentException("Longitud fuera de rango (-180 a 180)");
    }
}
