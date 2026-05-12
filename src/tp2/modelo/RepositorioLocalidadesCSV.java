package tp2.modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** Implementación de repositorio que persiste en un archivo CSV */

public class RepositorioLocalidadesCSV implements RepositorioLocalidades {
    private final String nombreArchivo;
    private static final String SEPARADOR = ";";

    public RepositorioLocalidadesCSV(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public void guardar(List<Localidad> localidades) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Localidad localidad : localidades) {
                escritor.println(serializarLocalidad(localidad));
            }
        } catch (IOException e) {
            manejarError("guardar", e);
        }
    }

    @Override
    public List<Localidad> cargar() {
        List<Localidad> localidades = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return localidades;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                deserializarLocalidad(linea).ifPresent(localidades::add);
            }
        } catch (IOException e) {
            manejarError("cargar", e);
        }
        return localidades;
    }

    private String serializarLocalidad(Localidad l) {
        return String.join(SEPARADOR, 
            l.nombre(), l.provincia(), 
            String.valueOf(l.latitud()), String.valueOf(l.longitud()));
    }

    private java.util.Optional<Localidad> deserializarLocalidad(String linea) {
        try {
            String[] datos = linea.split(SEPARADOR);
            if (datos.length == 4) {
                return java.util.Optional.of(new Localidad(
                    datos[0], datos[1], 
                    Double.parseDouble(datos[2]), Double.parseDouble(datos[3])));
            }
        } catch (NumberFormatException ignored) {}
        return java.util.Optional.empty();
    }

    private void manejarError(String operacion, Exception e) {
        System.err.printf("Error al %s localidades: %s%n", operacion, e.getMessage());
    }
}
