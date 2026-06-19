package tp2.modelo;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FiltroLocalidadesTest {

    private final List<Localidad> localidadesMuestra = List.of(
            new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816),
            new Localidad("La Plata", "Buenos Aires", -34.9205, -57.9537),
            new Localidad("Córdoba", "Córdoba", -31.4135, -64.1811),
            new Localidad("Rosario", "Santa Fe", -32.9388, -60.6633),
            new Localidad("Santa Fe", "Santa Fe", -31.6109, -60.7129)
    );

    @Test
    public void testBuscarPorNombreExacto() {
        List<Localidad> resultados = FiltroLocalidades.buscarPorNombre(localidadesMuestra, "Buenos Aires");
        assertEquals(1, resultados.size());
        assertEquals("Buenos Aires", resultados.get(0).nombre());
    }

    @Test
    public void testBuscarPorNombreParcial() {
        List<Localidad> resultados = FiltroLocalidades.buscarPorNombre(localidadesMuestra, "Santa");
        assertEquals(1, resultados.size());
        assertTrue(resultados.stream().allMatch(l -> l.nombre().contains("Santa")));
    }

    @Test
    public void testBuscarPorNombreCaseInsensitive() {
        List<Localidad> resultados = FiltroLocalidades.buscarPorNombre(localidadesMuestra, "buenos");
        assertEquals(1, resultados.size());
        assertEquals("Buenos Aires", resultados.get(0).nombre());
    }

    @Test
    public void testBuscarPorNombreVacioDevuelveTodas() {
        List<Localidad> resultados = FiltroLocalidades.buscarPorNombre(localidadesMuestra, "");
        assertEquals(localidadesMuestra.size(), resultados.size());
    }

    @Test
    public void testBuscarPorNombreNuloDevuelveTodas() {
        List<Localidad> resultados = FiltroLocalidades.buscarPorNombre(localidadesMuestra, null);
        assertEquals(localidadesMuestra.size(), resultados.size());
    }

    @Test
    public void testFiltrarPorProvinciaExacta() {
        List<Localidad> resultados = FiltroLocalidades.filtrarPorProvincia(localidadesMuestra, "Buenos Aires");
        assertEquals(2, resultados.size());
        assertTrue(resultados.stream().allMatch(l -> l.provincia().equals("Buenos Aires")));
    }

    @Test
    public void testFiltrarPorProvinciaCaseInsensitive() {
        List<Localidad> resultados = FiltroLocalidades.filtrarPorProvincia(localidadesMuestra, "buenos aires");
        assertEquals(2, resultados.size());
    }

    @Test
    public void testFiltrarPorProvinciaTodasDevuelveTodas() {
        List<Localidad> resultados = FiltroLocalidades.filtrarPorProvincia(localidadesMuestra, "Todas");
        assertEquals(localidadesMuestra.size(), resultados.size());
    }

    @Test
    public void testFiltrarPorProvinciaVacioDevuelveTodas() {
        List<Localidad> resultados = FiltroLocalidades.filtrarPorProvincia(localidadesMuestra, "");
        assertEquals(localidadesMuestra.size(), resultados.size());
    }

    @Test
    public void testAplicarFiltrosCombinados() {
        List<Localidad> resultados = FiltroLocalidades.aplicarFiltros(localidadesMuestra, "a", "Buenos Aires");
        assertEquals(2, resultados.size());
        assertTrue(resultados.stream().allMatch(l -> l.provincia().equals("Buenos Aires")));
        assertTrue(resultados.stream().allMatch(l -> l.nombre().toLowerCase().contains("a")));
    }

    @Test
    public void testObtenerProvinciasUnicas() {
        List<String> provincias = FiltroLocalidades.obtenerProvincias(localidadesMuestra);
        assertEquals(3, provincias.size());
        assertTrue(provincias.contains("Buenos Aires"));
        assertTrue(provincias.contains("Córdoba"));
        assertTrue(provincias.contains("Santa Fe"));
    }

    @Test
    public void testObtenerProvinciasOrdenadas() {
        List<String> provincias = FiltroLocalidades.obtenerProvincias(localidadesMuestra);
        for (int i = 0; i < provincias.size() - 1; i++) {
            assertTrue(provincias.get(i).compareTo(provincias.get(i + 1)) <= 0);
        }
    }

    @Test
    public void testBuscarSinResultados() {
        List<Localidad> resultados = FiltroLocalidades.buscarPorNombre(localidadesMuestra, "XyZ");
        assertTrue(resultados.isEmpty());
    }
}
