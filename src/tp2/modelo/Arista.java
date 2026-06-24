package tp2.modelo;

import java.util.Objects;

public class Arista<T> {
    private final T vertice1;
    private final T vertice2;
    private final double peso;

    public Arista(T vertice1, T vertice2, double peso) {
        if (vertice1 == null || vertice2 == null) {
            throw new IllegalArgumentException("Los vertices no pueden ser nulos");
        }
        if (vertice1.equals(vertice2)) {
            throw new IllegalArgumentException("Una arista no puede conectar un vertice consigo mismo");
        }
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }

        this.vertice1 = vertice1;
        this.vertice2 = vertice2;
        this.peso = peso;
    }

    public T vertice1() {
        return vertice1;
    }

    public T vertice2() {
        return vertice2;
    }

    public double peso() {
        return peso;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Arista<?>)) {
            return false;
        }
        Arista<?> other = (Arista<?>) obj;
        return Double.compare(peso, other.peso) == 0
                && Objects.equals(vertice1, other.vertice1)
                && Objects.equals(vertice2, other.vertice2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertice1, vertice2, peso);
    }
}
