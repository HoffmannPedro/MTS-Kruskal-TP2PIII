package tp2.modelo;

/** Clase encargada de cálculos geográficos */

public class CalculadoraDistancia {
    private static final double RADIO_TIERRA_KM = 6371.0;

    public static double calcularHaversine(Localidad origen, Localidad destino) {
        double diferenciaLatitud = Math.toRadians(destino.latitud() - origen.latitud());
        double diferenciaLongitud = Math.toRadians(destino.longitud() - origen.longitud());

        double coeficienteHaversine = Math.sin(diferenciaLatitud / 2) * Math.sin(diferenciaLatitud / 2) +
                Math.cos(Math.toRadians(origen.latitud())) * Math.cos(Math.toRadians(destino.latitud())) *
                        Math.sin(diferenciaLongitud / 2) * Math.sin(diferenciaLongitud / 2);

        double distanciaAngular = 2 * Math.atan2(Math.sqrt(coeficienteHaversine), Math.sqrt(1 - coeficienteHaversine));

        return RADIO_TIERRA_KM * distanciaAngular;
    }
}
