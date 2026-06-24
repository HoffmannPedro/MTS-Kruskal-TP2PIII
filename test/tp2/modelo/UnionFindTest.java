package tp2.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnionFindTest {

    @Test
    public void testUnionSimple() {
        UnionFind uf = new UnionFind(5);
        assertFalse(uf.find(0, 1));

        uf.union(0, 1);

        assertTrue(uf.find(0, 1));
        assertEquals(uf.root(0), uf.root(1));
    }

    @Test
    public void testDeteccionCiclo() {
        UnionFind uf = new UnionFind(3);
        uf.union(0, 1);
        uf.union(1, 2);

        assertTrue(uf.find(0, 2), "0 y 2 ya deberian estar en el mismo conjunto");
    }

    @Test
    public void testMultiplesComponentes() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        uf.union(2, 3);
        assertFalse(uf.find(1, 3));

        uf.union(1, 2);
        assertTrue(uf.find(0, 3));
    }

    @Test
    public void testRootPathCompression() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(2, 3);

        int rootBefore = uf.root(3);
        int rootAfter = uf.root(0);

        assertEquals(rootBefore, rootAfter);
        assertTrue(uf.find(1, 2));
    }

    @Test
    public void testComponentesSeparados() {
        UnionFind uf = new UnionFind(6);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);

        assertFalse(uf.find(0, 2));
        assertFalse(uf.find(0, 4));
        assertFalse(uf.find(2, 4));
    }
}
