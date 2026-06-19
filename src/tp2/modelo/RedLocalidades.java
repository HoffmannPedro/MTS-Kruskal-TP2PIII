package tp2.modelo;

import java.util.ArrayList;
import java.util.List;

public class RedLocalidades {

    private final List<Localidad> localidades;

    public RedLocalidades(List<Localidad> localidades) {
        this.localidades = new ArrayList<>(localidades);
    }

    public void agregar(Localidad localidad) {
        localidades.add(localidad);
    }

    public void eliminar(int indice) {
        localidades.remove(indice);
    }

    public void eliminar(Localidad localidad) {
        localidades.remove(localidad);
    }

    public void eliminarTodas() {
        localidades.clear();
    }

    public List<Localidad> obtenerTodas() {
        return List.copyOf(localidades);
    }

    public int cantidad() {
        return localidades.size();
    }

    public boolean isEmpty() {
        return localidades.isEmpty();
    }

    public List<Localidad> filtrar(String nombre, String provincia) {
    return FiltroLocalidades.aplicarFiltros(
        localidades,
        nombre,
        provincia
        );
    }
    public List<String> obtenerProvincias() {
        return FiltroLocalidades.obtenerProvincias(localidades);
    }

}
