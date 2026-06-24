package tp2.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValuadorConexionesTest {

    private final ParametrosCosto paramsStandard = new ParametrosCosto(1000.0, 0.40, 20000.0);

    @Test
    public void testCostoCortoIntraprovincial() {
        // Distancia < 300km, misma provincia
        Localidad l1 = new Localidad("A", "P1", -34.0, -58.0);
        Localidad l2 = new Localidad("B", "P1", -34.1, -58.1); // ~14km

        ValuadorConexiones valuador = new ValuadorConexiones(paramsStandard.costoPorKm(), paramsStandard.porcentajeAumentoExceso(), paramsStandard.costoFijoInterprovincial());
        Conexion c = valuador.crearConexion(l1, l2);

        assertTrue(c.distancia() < 300.0);
        // Costo debe ser distancia * 1000
        assertEquals(c.distancia() * 1000.0, c.costo(), 0.001);
    }

    @Test
    public void testCostoLargoInterprovincial() {
        // Distancia > 300km (BSAS a Córdoba), distinta provincia
        Localidad bsAs = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba = new Localidad("Córdoba", "Córdoba", -31.4135, -64.1811);
        ValuadorConexiones valuador = new ValuadorConexiones(paramsStandard.costoPorKm(), paramsStandard.porcentajeAumentoExceso(), paramsStandard.costoFijoInterprovincial());
        Conexion c = valuador.crearConexion(bsAs, cordoba);

        assertTrue(c.distancia() > 300.0);
        assertNotEquals("Buenos Aires", "Córdoba");

        // Cálculo esperado:
        double d = c.distancia();
        double esperado = (d * 1000.0) * 1.40 + 20000.0;

        assertEquals(esperado, c.costo(), 0.001);
    }

    @Test
    public void testCostoExactamenteInferiorA300kmNoAumento() {
        Localidad l1 = new Localidad("A", "P1", -34.0, -58.0);
        Localidad l2 = new Localidad("B", "P1", -36.3, -58.0);

        ValuadorConexiones valuador = new ValuadorConexiones(paramsStandard.costoPorKm(), paramsStandard.porcentajeAumentoExceso(), paramsStandard.costoFijoInterprovincial());
        Conexion c = valuador.crearConexion(l1, l2);

        assertTrue(c.distancia() < 300.0);
        assertEquals(c.distancia() * 1000.0, c.costo(), 0.001);
    }

    @Test
    public void testCostoLargoMismaProvinciaSoloAumento() {
        Localidad l1 = new Localidad("A", "P1", -34.0, -58.0);
        Localidad l2 = new Localidad("B", "P1", -37.7, -58.0);

        ValuadorConexiones valuador = new ValuadorConexiones(paramsStandard.costoPorKm(), paramsStandard.porcentajeAumentoExceso(), paramsStandard.costoFijoInterprovincial());
        Conexion c = valuador.crearConexion(l1, l2);

        assertTrue(c.distancia() > 300.0);
        double esperado = (c.distancia() * 1000.0) * 1.40;
        assertEquals(esperado, c.costo(), 0.001);
    }
}
