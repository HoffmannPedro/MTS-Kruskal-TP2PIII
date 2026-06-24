package tp2.modelo;

import java.util.ArrayList;
import java.util.List;

public class Grafo<T> {
    private final List<T> vertices;
    private final List<Arista<T>> aristas;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.aristas = new ArrayList<>();
    }

    public Grafo(List<T> vertices, List<Arista<T>> aristas) {
        if (vertices == null || aristas == null) {
            throw new IllegalArgumentException("Los vertices y aristas no pueden ser nulos");
        }

        this.vertices = new ArrayList<>();
        this.aristas = new ArrayList<>();
        for (T vertice : vertices) {
            agregarVertice(vertice);
        }
        for (Arista<T> arista : aristas) {
            agregarArista(arista);
        }
    }

    public void agregarVertice(T vertice) {
        if (vertice == null) {
            throw new IllegalArgumentException("El vertice no puede ser nulo");
        }
        if (!vertices.contains(vertice)) {
            vertices.add(vertice);
        }
    }

    public void agregarArista(Arista<T> arista) {
        if (arista == null) {
            throw new IllegalArgumentException("La arista no puede ser nula");
        }
        if (!vertices.contains(arista.vertice1()) || !vertices.contains(arista.vertice2())) {
            throw new IllegalArgumentException("La arista contiene vertices que no pertenecen al grafo");
        }
        aristas.add(arista);
    }

    public List<T> vertices() {
        return List.copyOf(vertices);
    }

    public List<Arista<T>> aristas() {
        return List.copyOf(aristas);
    }

    public int cantidadVertices() {
        return vertices.size();
    }

    public int cantidadAristas() {
        return aristas.size();
    }
}
