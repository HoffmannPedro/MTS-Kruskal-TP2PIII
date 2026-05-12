package tp2.modelo;

import java.util.List;

/** Interfaz para la persistencia de localidades */
public interface RepositorioLocalidades {
    void guardar(List<Localidad> localidades);
    List<Localidad> cargar();
}
