package tp2.modelo;

/** Lógica de negocio para valuar conexiones según reglas de costo */

public class ValuadorConexiones {
    
    private static final double UMBRAL_DISTANCIA_KM = 300.0;

    double costoPorKm ;
    double porcentajeAumentoExceso;
    double costoFijoInterprovincial;


    public ValuadorConexiones(double costoPorKm, double porcentajeAumentoExceso, double costoFijoInterprovincial) {
        this.costoPorKm = costoPorKm;
        this.porcentajeAumentoExceso = porcentajeAumentoExceso;
        this.costoFijoInterprovincial = costoFijoInterprovincial;
    }

    public Conexion crearConexion(Localidad localidad1, Localidad localidad2) {
        double distancia = CalculadoraDistancia.calcularHaversine(localidad1, localidad2);
        double costoBase = distancia * costoPorKm;

        if (distancia > UMBRAL_DISTANCIA_KM) {
            costoBase += (costoBase * porcentajeAumentoExceso);
        }

        if (!localidad1.provincia().equalsIgnoreCase(localidad2.provincia())) {
            costoBase += costoFijoInterprovincial;
        }

        return new Conexion(localidad1, localidad2, distancia, costoBase);
    }
}
