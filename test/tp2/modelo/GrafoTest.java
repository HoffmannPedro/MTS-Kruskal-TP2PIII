package tp2.modelo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GrafoTest {

    @Test
    public void testGrafoVacio() {
        Grafo<String> grafo = new Grafo<>();

        assertEquals(0, grafo.cantidadVertices());
        assertEquals(0, grafo.cantidadAristas());
        assertTrue(grafo.vertices().isEmpty());
        assertTrue(grafo.aristas().isEmpty());
    }

    @Test
    public void testAgregarVertice() {
        Grafo<String> grafo = new Grafo<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");

        assertEquals(2, grafo.cantidadVertices());
        assertTrue(grafo.vertices().contains("A"));
        assertTrue(grafo.vertices().contains("B"));
    }

    @Test
    public void testAgregarVerticeDuplicadoNoLoRepite() {
        Grafo<String> grafo = new Grafo<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("A");

        assertEquals(1, grafo.cantidadVertices());
    }

    @Test
    public void testAgregarVerticeNuloLanzaError() {
        Grafo<String> grafo = new Grafo<>();
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarVertice(null));
    }

    @Test
    public void testAgregarAristaValida() {
        Grafo<String> grafo = new Grafo<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista(new Arista<>("A", "B", 3.0));

        assertEquals(1, grafo.cantidadAristas());
        assertEquals(3.0, grafo.aristas().get(0).peso(), 0.0001);
    }

    @Test
    public void testAgregarAristaNulaLanzaError() {
        Grafo<String> grafo = new Grafo<>();
        grafo.agregarVertice("A");
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(null));
    }

    @Test
    public void testAgregarAristaConVerticeInexistenteLanzaError() {
        Grafo<String> grafo = new Grafo<>();
        grafo.agregarVertice("A");

        assertThrows(IllegalArgumentException.class,
                () -> grafo.agregarArista(new Arista<>("A", "B", 1.0)));
    }

    @Test
    public void testConstructorConVerticesYAristas() {
        List<String> vertices = Arrays.asList("A", "B", "C");
        List<Arista<String>> aristas = Arrays.asList(
                new Arista<>("A", "B", 1.0),
                new Arista<>("B", "C", 2.0)
        );

        Grafo<String> grafo = new Grafo<>(vertices, aristas);

        assertEquals(3, grafo.cantidadVertices());
        assertEquals(2, grafo.cantidadAristas());
    }

    @Test
    public void testConstructorConListasNulasLanzaError() {
        assertThrows(IllegalArgumentException.class, () -> new Grafo<>(null, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Grafo<>(List.of("A"), null));
    }

    @Test
    public void testVerticesYAristasRetornanCopiasInmutables() {
        Grafo<String> grafo = new Grafo<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista(new Arista<>("A", "B", 1.0));

        assertThrows(UnsupportedOperationException.class, () -> grafo.vertices().add("C"));
        assertThrows(UnsupportedOperationException.class, () -> grafo.aristas().add(new Arista<>("A", "B", 2.0)));
    }
}
