package tp2.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocalidadParametrosCostosTest {

    @Test
    public void testLocalidadValidaCreaInstancia() {
        Localidad localidad = new Localidad("Ezeiza", "Buenos Aires", -34.8222, -58.5358);
        assertEquals("Ezeiza", localidad.nombre());
        assertEquals("Buenos Aires", localidad.provincia());
        assertEquals(-34.8222, localidad.latitud(), 0.0001);
        assertEquals(-58.5358, localidad.longitud(), 0.0001);
    }

    @Test
    public void testLocalidadNombreVacioLanzaError() {
        assertThrows(IllegalArgumentException.class, () ->
                new Localidad("", "P1", -34.0, -58.0));
    }

    @Test
    public void testLocalidadLongitudFueraDeRangoLanzaError() {
        assertThrows(IllegalArgumentException.class, () ->
                new Localidad("A", "P1", -34.0, 200.0));
    }

    @Test
    public void testParametrosCostoNegativoLanzaError() {
        assertThrows(IllegalArgumentException.class, () ->
                new ParametrosCosto(-1.0, 0.1, 100.0));
    }

    @Test
    public void testParametrosCostoConValoresIntermedios() {
        ParametrosCosto parametros = new ParametrosCosto(50.0, 0.25, 300.0);
        assertEquals(50.0, parametros.costoPorKm(), 0.0001);
        assertEquals(0.25, parametros.porcentajeAumentoExceso(), 0.0001);
        assertEquals(300.0, parametros.costoFijoInterprovincial(), 0.0001);
    }
}
