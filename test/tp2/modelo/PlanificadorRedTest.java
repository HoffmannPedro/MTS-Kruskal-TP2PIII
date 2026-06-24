package tp2.modelo;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PlanificadorRedTest {

    private final ParametrosCosto paramsStandard = new ParametrosCosto(1000.0, 0.40, 20000.0);

    private double costoEsperado(Localidad l1, Localidad l2) {
        ValuadorConexiones valuador = new ValuadorConexiones(paramsStandard.costoPorKm(), paramsStandard.porcentajeAumentoExceso(), paramsStandard.costoFijoInterprovincial());
        return valuador.crearConexion(l1, l2).costo();
    }

    private boolean conecta(SolucionRed solucion, Localidad a, Localidad b) {
        return solucion.conexiones().stream().anyMatch(c ->
                (c.localidad1().equals(a) && c.localidad2().equals(b))
                        || (c.localidad1().equals(b) && c.localidad2().equals(a)));
    }

    @Test
    public void testMSTTresLocalidadesEnLinea() {
        // Localidades en una línea imaginaria (A --- B --- C)
        Localidad a = new Localidad("A", "P1", -34.0, -58.0);
        Localidad b = new Localidad("B", "P1", -34.1, -58.1); // ~14km de A
        Localidad c = new Localidad("C", "P1", -34.2, -58.2); // ~14km de B, ~28km de A

        List<Localidad> localidades = Arrays.asList(a, b, c);

        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(localidades, paramsStandard);

        // En un MST de 3 nodos, siempre debe haber 2 aristas
        assertEquals(2, solucion.conexiones().size());
        
        // El costo total debería ser la suma de A-B y B-C (las más baratas)
        double distAB = CalculadoraDistancia.calcularHaversine(a, b);
        double distBC = CalculadoraDistancia.calcularHaversine(b, c);
        double costoEsperado = (distAB * 1000.0) + (distBC * 1000.0);

        assertEquals(costoEsperado, solucion.costoTotal(), 1.0);
    }

    @Test
    public void testMSTCuatroLocalidadesEnLinea() {
        Localidad a = new Localidad("A", "P1", -34.0, -58.0);
        Localidad b = new Localidad("B", "P1", -34.1, -58.1);
        Localidad c = new Localidad("C", "P1", -34.2, -58.2);
        Localidad d = new Localidad("D", "P1", -34.3, -58.3);

        List<Localidad> localidades = Arrays.asList(a, b, c, d);
        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(localidades, paramsStandard);

        assertEquals(3, solucion.conexiones().size());
        assertTrue(conecta(solucion, a, b));
        assertTrue(conecta(solucion, b, c));
        assertTrue(conecta(solucion, c, d));

        double costoEsperado = costoEsperado(a, b) + costoEsperado(b, c) + costoEsperado(c, d);
        assertEquals(costoEsperado, solucion.costoTotal(), 1.0);
    }

    @Test
    public void testMSTCincoLocalidadesEnLinea() {
        Localidad a = new Localidad("A", "P1", -34.0, -58.0);
        Localidad b = new Localidad("B", "P1", -34.1, -58.1);
        Localidad c = new Localidad("C", "P1", -34.2, -58.2);
        Localidad d = new Localidad("D", "P1", -34.3, -58.3);
        Localidad e = new Localidad("E", "P1", -34.4, -58.4);

        List<Localidad> localidades = Arrays.asList(a, b, c, d, e);
        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(localidades, paramsStandard);

        assertEquals(4, solucion.conexiones().size());
        assertTrue(conecta(solucion, a, b));
        assertTrue(conecta(solucion, b, c));
        assertTrue(conecta(solucion, c, d));
        assertTrue(conecta(solucion, d, e));

        double costoEsperado = costoEsperado(a, b) + costoEsperado(b, c)
                + costoEsperado(c, d) + costoEsperado(d, e);
        assertEquals(costoEsperado, solucion.costoTotal(), 1.0);
    }

    @Test
    public void testMSTCuatroLocalidadesConCentro() {
        Localidad centro = new Localidad("Centro", "P1", -34.0, -58.0);
        Localidad norte = new Localidad("Norte", "P1", -33.99, -58.0);
        Localidad sur = new Localidad("Sur", "P1", -34.01, -58.0);
        Localidad este = new Localidad("Este", "P1", -34.0, -57.99);
        Localidad oeste = new Localidad("Oeste", "P1", -34.0, -58.01);

        List<Localidad> localidades = Arrays.asList(centro, norte, sur, este, oeste);
        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(localidades, paramsStandard);

        assertEquals(4, solucion.conexiones().size());
        assertTrue(conecta(solucion, centro, norte));
        assertTrue(conecta(solucion, centro, sur));
        assertTrue(conecta(solucion, centro, este));
        assertTrue(conecta(solucion, centro, oeste));

        double costoEsperado = costoEsperado(centro, norte) + costoEsperado(centro, sur)
                + costoEsperado(centro, este) + costoEsperado(centro, oeste);
        assertEquals(costoEsperado, solucion.costoTotal(), 1.0);
    }

    @Test
    public void testMSTSeisLocalidadesEnLineaConDosProvincias() {
        Localidad a = new Localidad("A", "P1", -34.0, -58.0);
        Localidad b = new Localidad("B", "P1", -34.1, -58.1);
        Localidad c = new Localidad("C", "P1", -34.2, -58.2);
        Localidad d = new Localidad("D", "P1", -34.3, -58.3);
        Localidad e = new Localidad("E", "P2", -34.4, -58.4);
        Localidad f = new Localidad("F", "P2", -34.5, -58.5);

        List<Localidad> localidades = Arrays.asList(a, b, c, d, e, f);
        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(localidades, paramsStandard);

        assertEquals(5, solucion.conexiones().size());
        assertTrue(conecta(solucion, a, b));
        assertTrue(conecta(solucion, b, c));
        assertTrue(conecta(solucion, c, d));
        assertTrue(conecta(solucion, d, e));
        assertTrue(conecta(solucion, e, f));

        double costoEnCadena = costoEsperado(a, b) + costoEsperado(b, c) + costoEsperado(c, d)
                + costoEsperado(d, e) + costoEsperado(e, f);
        assertEquals(costoEnCadena, solucion.costoTotal(), 5.0);
    }

    @Test
    public void testListaVacia() {
        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(null, null);
        assertTrue(solucion.conexiones().isEmpty());
        assertEquals(0.0, solucion.costoTotal());
    }

    @Test
    public void testUnSoloNodo() {
        Localidad a = new Localidad("A", "P1", -34.0, -58.0);
        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(Arrays.asList(a), new ParametrosCosto(1000.0, 0.40, 20000.0));

        assertTrue(solucion.conexiones().isEmpty());
        assertEquals(0.0, solucion.costoTotal());
    }

    @Test
    public void testLocalidadesDuplicadasDiferentesNombre() {
        Localidad a = new Localidad("A", "P1", -34.0, -58.0);
        Localidad b = new Localidad("B", "P1", -34.0, -58.0);

        List<Localidad> localidades = Arrays.asList(a, b);

        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(localidades, paramsStandard);

        assertEquals(1, solucion.conexiones().size());
        assertEquals(0.0, solucion.costoTotal(), 0.001);
        assertEquals(a, solucion.conexiones().get(0).localidad1());
        assertEquals(b, solucion.conexiones().get(0).localidad2());
    }
}
