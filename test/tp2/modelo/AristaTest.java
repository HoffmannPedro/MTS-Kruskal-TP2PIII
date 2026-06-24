package tp2.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AristaTest {

    @Test
    public void testCrearAristaValida() {
        Arista<String> arista = new Arista<>("A", "B", 10.5);

        assertEquals("A", arista.vertice1());
        assertEquals("B", arista.vertice2());
        assertEquals(10.5, arista.peso(), 0.0001);
    }

    @Test
    public void testVerticeNuloLanzaError() {
        assertThrows(IllegalArgumentException.class, () -> new Arista<>(null, "B", 1.0));
        assertThrows(IllegalArgumentException.class, () -> new Arista<>("A", null, 1.0));
    }

    @Test
    public void testAristaConMismoVerticeLanzaError() {
        assertThrows(IllegalArgumentException.class, () -> new Arista<>("A", "A", 1.0));
    }

    @Test
    public void testPesoNegativoLanzaError() {
        assertThrows(IllegalArgumentException.class, () -> new Arista<>("A", "B", -0.01));
    }

    @Test
    public void testPesoCeroEsValido() {
        Arista<String> arista = new Arista<>("A", "B", 0.0);
        assertEquals(0.0, arista.peso(), 0.0001);
    }

    @Test
    public void testEqualsConsideraVerticesYPeso() {
        Arista<String> arista1 = new Arista<>("A", "B", 5.0);
        Arista<String> arista2 = new Arista<>("A", "B", 5.0);
        Arista<String> aristaDistintoPeso = new Arista<>("A", "B", 6.0);
        Arista<String> aristaDistintosVertices = new Arista<>("A", "C", 5.0);

        assertEquals(arista1, arista2);
        assertNotEquals(arista1, aristaDistintoPeso);
        assertNotEquals(arista1, aristaDistintosVertices);
    }

    @Test
    public void testHashCodeConsistenteConEquals() {
        Arista<String> arista1 = new Arista<>("A", "B", 5.0);
        Arista<String> arista2 = new Arista<>("A", "B", 5.0);

        assertEquals(arista1.hashCode(), arista2.hashCode());
    }
}
