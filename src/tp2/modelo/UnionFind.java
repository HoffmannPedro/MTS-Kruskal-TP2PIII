package tp2.modelo;

/** Estructura de datos Disjoint Set Union (DSU) */

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

    public int find(int nodo) {
        if (padres[nodo] == nodo) {
            return nodo;
        }
        return padres[nodo] = find(padres[nodo]);
    }

    public boolean union(int nodo1, int nodo2) {
        int raiz1 = find(nodo1);
        int raiz2 = find(nodo2);

        if (raiz1 != raiz2) {
            if (rangos[raiz1] < rangos[raiz2]) {
                padres[raiz1] = raiz2;
            } else if (rangos[raiz1] > rangos[raiz2]) {
                padres[raiz2] = raiz1;
            } else {
                padres[raiz1] = raiz2;
                rangos[raiz2]++;
            }
            return true;
        }
        return false;
    }
}
