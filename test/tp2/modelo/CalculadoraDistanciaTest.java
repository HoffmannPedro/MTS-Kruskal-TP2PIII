package tp2.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraDistanciaTest {

    @Test
    public void testHaversineBsAsCordoba() {
        // Coordenadas aproximadas
        Localidad bsAs = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba = new Localidad("Córdoba", "Córdoba", -31.4135, -64.1811);

        double distancia = CalculadoraDistancia.calcularHaversine(bsAs, cordoba);

        // La distancia debería estar cerca de los 648km
        assertEquals(648.0, distancia, 10.0, "La distancia entre BSAS y Córdoba debería ser aprox 648km");
    }

    @Test
    public void testMismaLocalidad() {
        Localidad l1 = new Localidad("Test", "Test", -34.0, -58.0);
        double distancia = CalculadoraDistancia.calcularHaversine(l1, l1);
        assertEquals(0.0, distancia, 0.001);
    }
}
