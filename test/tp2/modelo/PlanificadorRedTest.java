package tp2.modelo;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PlanificadorRedTest {

    @Test
    public void testMSTTresLocalidadesEnLinea() {
        // Localidades en una línea imaginaria (A --- B --- C)
        Localidad a = new Localidad("A", "P1", -34.0, -58.0);
        Localidad b = new Localidad("B", "P1", -34.1, -58.1); // ~14km de A
        Localidad c = new Localidad("C", "P1", -34.2, -58.2); // ~14km de B, ~28km de A

        List<Localidad> localidades = Arrays.asList(a, b, c);
        ParametrosCosto params = new ParametrosCosto(1000.0, 0.40, 20000.0);

        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(localidades, params);

        // En un MST de 3 nodos, siempre debe haber 2 aristas
        assertEquals(2, solucion.conexiones().size());
        
        // El costo total debería ser la suma de A-B y B-C (las más baratas)
        double distAB = CalculadoraDistancia.calcularHaversine(a, b);
        double distBC = CalculadoraDistancia.calcularHaversine(b, c);
        double costoEsperado = (distAB * 1000.0) + (distBC * 1000.0);

        assertEquals(costoEsperado, solucion.costoTotal(), 1.0);
    }

    @Test
    public void testListaVacia() {
        PlanificadorRed planificador = new PlanificadorRed();
        SolucionRed solucion = planificador.planificar(null, null);
        assertTrue(solucion.conexiones().isEmpty());
        assertEquals(0.0, solucion.costoTotal());
    }
}
