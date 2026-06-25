package tp2.modelo;

/** Lógica de negocio para valuar conexiones según reglas de costo */

public class ValuadorConexiones {
    
    private static final double UMBRAL_DISTANCIA_KM = 300.0;

    double costoPorKm ;
    double porcentajeAumentoExceso;
    double costoFijoInterprovincial;


    public double getCostoPorKm() {
        return costoPorKm;
    }

    public double getPorcentajeAumentoExceso() {
        return porcentajeAumentoExceso;
    }

    public double getCostoFijoInterprovincial() {
        return costoFijoInterprovincial;
    }

    public ValuadorConexiones(double costoPorKm, double porcentajeAumentoExceso, double costoFijoInterprovincial) {

         if (costoPorKm < 0)
            throw new IllegalArgumentException("El costo por km no puede ser negativo");
        if (porcentajeAumentoExceso < 0)
            throw new IllegalArgumentException("El porcentaje de aumento no puede ser negativo");
        if (costoFijoInterprovincial < 0)
            throw new IllegalArgumentException("El costo fijo no puede ser negativo");
        
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
