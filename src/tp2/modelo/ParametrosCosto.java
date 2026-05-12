package tp2.modelo;

/** Parámetros de costo configurables por el usuario */

public record ParametrosCosto(
        double costoPorKm,
        double porcentajeAumentoExceso,
        double costoFijoInterprovincial) {
    public ParametrosCosto {
        if (costoPorKm < 0)
            throw new IllegalArgumentException("El costo por km no puede ser negativo");
        if (porcentajeAumentoExceso < 0)
            throw new IllegalArgumentException("El porcentaje de aumento no puede ser negativo");
        if (costoFijoInterprovincial < 0)
            throw new IllegalArgumentException("El costo fijo no puede ser negativo");
    }
}
