package tp2.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnionFindTest {

    @Test
    public void testUnionSimple() {
        UnionFind uf = new UnionFind(5);
        assertTrue(uf.union(0, 1));
        assertFalse(uf.union(0, 1)); // Ya están unidos
        assertEquals(uf.find(0), uf.find(1));
    }

    @Test
    public void testDeteccionCiclo() {
        UnionFind uf = new UnionFind(3);
        uf.union(0, 1);
        uf.union(1, 2);
        
        // 0-1 y 1-2 ya conectan 0 con 2. Unir 0 y 2 formaría un ciclo.
        assertFalse(uf.union(0, 2), "Unir 0 y 2 debería detectar que ya están en el mismo conjunto");
    }

    @Test
    public void testMultiplesComponentes() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        uf.union(2, 3);
        assertNotEquals(uf.find(1), uf.find(3));
        
        uf.union(1, 2);
        assertEquals(uf.find(0), uf.find(3));
    }

    @Test
    public void testFindPathCompression() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(2, 3);

        int rootBefore = uf.find(3);
        int rootAfter = uf.find(0);

        assertEquals(rootBefore, rootAfter);
        assertEquals(uf.find(1), uf.find(2));
    }

    @Test
    public void testComponentesSeparados() {
        UnionFind uf = new UnionFind(6);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);

        assertNotEquals(uf.find(0), uf.find(2));
        assertNotEquals(uf.find(0), uf.find(4));
        assertNotEquals(uf.find(2), uf.find(4));
    }
}
