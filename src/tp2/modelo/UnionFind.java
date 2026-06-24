package tp2.modelo;

/** Estructura de datos Disjoint Set Union (DSU). */
public class UnionFind {
    private final int[] padres;
    private final int[] rangos;

    public UnionFind(int cantidadElementos) {
        padres = new int[cantidadElementos];
        rangos = new int[cantidadElementos];

        for (int i = 0; i < cantidadElementos; i++) {
            padres[i] = i;
            rangos[i] = 0;
        }
    }

    public int root(int nodo) {
        if (padres[nodo] == nodo) {
            return nodo;
        }
        return padres[nodo] = root(padres[nodo]);
    }

    public boolean find(int nodo1, int nodo2) {
        return root(nodo1) == root(nodo2);
    }

    public void union(int nodo1, int nodo2) {
        int raiz1 = root(nodo1);
        int raiz2 = root(nodo2);

        if (raiz1 == raiz2) {
            return;
        }

        if (rangos[raiz1] < rangos[raiz2]) {
            padres[raiz1] = raiz2;
        } else if (rangos[raiz1] > rangos[raiz2]) {
            padres[raiz2] = raiz1;
        } else {
            padres[raiz1] = raiz2;
            rangos[raiz2]++;
        }
    }
}
