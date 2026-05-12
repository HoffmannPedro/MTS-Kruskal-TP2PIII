package tp2.modelo;

/** Lógica de negocio para valuar conexiones según reglas de costo */

public class ValuadorConexiones {
    private static final double UMBRAL_DISTANCIA_KM = 300.0;

    public static Conexion crearConexion(Localidad localidad1, Localidad localidad2, ParametrosCosto parametros) {
        double distancia = CalculadoraDistancia.calcularHaversine(localidad1, localidad2);
        double costoBase = distancia * parametros.costoPorKm();

        if (distancia > UMBRAL_DISTANCIA_KM) {
            costoBase += (costoBase * parametros.porcentajeAumentoExceso());
        }

        if (!localidad1.provincia().equalsIgnoreCase(localidad2.provincia())) {
            costoBase += parametros.costoFijoInterprovincial();
        }

        return new Conexion(localidad1, localidad2, distancia, costoBase);
    }
}
