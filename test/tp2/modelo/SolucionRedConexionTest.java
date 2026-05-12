package tp2.modelo;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolucionRedConexionTest {

    @Test
    public void testSolucionRedListaNulaLanzaError() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolucionRed(null, 0.0));
    }

    @Test
    public void testSolucionRedCostoNegativoLanzaError() {
        assertThrows(IllegalArgumentException.class, () ->
                new SolucionRed(List.of(new Conexion(
                        new Localidad("A", "P1", -34.0, -58.0),
                        new Localidad("B", "P1", -34.1, -58.1),
                        15.0, 15000.0)), -100.0));
    }

    @Test
    public void testConexionMismaLocalidadLanzaError() {
        Localidad lugar = new Localidad("A", "P1", -34.0, -58.0);
        assertThrows(IllegalArgumentException.class, () ->
                new Conexion(lugar, lugar, 0.0, 0.0));
    }
}
