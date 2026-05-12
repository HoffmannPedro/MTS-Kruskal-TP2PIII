package tp2.modelo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepositorioLocalidadesCSVTest {

    @Test
    public void testGuardarYCargarLocalidades() throws IOException {
        Path archivoTemp = Files.createTempFile("localidades", ".csv");
        try {
            RepositorioLocalidadesCSV repositorio = new RepositorioLocalidadesCSV(archivoTemp.toString());
            List<Localidad> esperadas = List.of(
                    new Localidad("A", "P1", -34.0, -58.0),
                    new Localidad("B", "P2", -31.4, -64.2)
            );

            repositorio.guardar(esperadas);
            List<Localidad> cargadas = repositorio.cargar();

            assertEquals(esperadas.size(), cargadas.size());
            assertEquals(esperadas.get(0), cargadas.get(0));
            assertEquals(esperadas.get(1), cargadas.get(1));
        } finally {
            Files.deleteIfExists(archivoTemp);
        }
    }

    @Test
    public void testCargarArchivoInexistenteDevuelveListaVacia() {
        Path archivoTemp = Path.of(System.getProperty("java.io.tmpdir"), "archivo_no_existente_12345.csv");
        RepositorioLocalidadesCSV repositorio = new RepositorioLocalidadesCSV(archivoTemp.toString());

        List<Localidad> cargadas = repositorio.cargar();

        assertTrue(cargadas.isEmpty(), "La carga de un archivo inexistente debe devolver lista vacía");
    }

    @Test
    public void testIgnoraLineasInvalidasAlCargar() throws IOException {
        Path archivoTemp = Files.createTempFile("localidades", ".csv");
        try {
            String contenido = "A;P1;-34.0;-58.0\n" +
                    "INCOMPLETO;P2;-31.4\n" +
                    "B;P2;-31.4;-64.2\n" +
                    "X;Y;no_numero;no_numero\n";
            Files.writeString(archivoTemp, contenido);

            RepositorioLocalidadesCSV repositorio = new RepositorioLocalidadesCSV(archivoTemp.toString());
            List<Localidad> cargadas = repositorio.cargar();

            assertEquals(2, cargadas.size());
            assertEquals(new Localidad("A", "P1", -34.0, -58.0), cargadas.get(0));
            assertEquals(new Localidad("B", "P2", -31.4, -64.2), cargadas.get(1));
        } finally {
            Files.deleteIfExists(archivoTemp);
        }
    }

    @Test
    public void testGuardarListaVaciaYRecuperaListaVacia() throws IOException {
        Path archivoTemp = Files.createTempFile("localidades", ".csv");
        try {
            RepositorioLocalidades repositorio = new RepositorioLocalidadesCSV(archivoTemp.toString());
            repositorio.guardar(List.of());

            List<Localidad> cargadas = repositorio.cargar();
            assertNotNull(cargadas);
            assertTrue(cargadas.isEmpty(), "Guardar lista vacía debe producir archivo válido y cargar lista vacía");
        } finally {
            Files.deleteIfExists(archivoTemp);
        }
    }

    @Test
    public void testRepositorioLocalidadesInterfaceUsaImplementacionCSV() throws IOException {
        Path archivoTemp = Files.createTempFile("localidades", ".csv");
        try {
            RepositorioLocalidades repositorio = new RepositorioLocalidadesCSV(archivoTemp.toString());
            List<Localidad> esperadas = List.of(
                    new Localidad("A", "P1", -34.0, -58.0)
            );

            repositorio.guardar(esperadas);
            List<Localidad> cargadas = repositorio.cargar();

            assertEquals(1, cargadas.size());
            assertEquals(esperadas.get(0), cargadas.get(0));
        } finally {
            Files.deleteIfExists(archivoTemp);
        }
    }
}
