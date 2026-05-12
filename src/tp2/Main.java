package tp2;

import com.formdev.flatlaf.FlatDarkLaf;
import tp2.controlador.ControladorRed;
import tp2.modelo.PlanificadorRed;
import tp2.modelo.RepositorioLocalidades;
import tp2.modelo.RepositorioLocalidadesCSV;
import tp2.vista.VentanaPrincipal;

import javax.swing.*;

/** Clase principal de inicio de la aplicación */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FlatDarkLaf.setup();

                PlanificadorRed modelo = new PlanificadorRed();
                RepositorioLocalidades repositorio = new RepositorioLocalidadesCSV("localidades.csv");
                VentanaPrincipal vista = new VentanaPrincipal();

                new ControladorRed(vista, modelo, repositorio);

                vista.setVisible(true);

            } catch (Exception e) {
                System.err.println("Error crítico al iniciar la aplicación: " + e.getMessage());
            }
        });
    }
}
