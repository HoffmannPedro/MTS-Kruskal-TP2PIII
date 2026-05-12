package tp2.vista;

import javax.swing.*;
import java.awt.*;

/** Panel para configurar los parámetros de costo */

public class PanelParametrosCosto extends JPanel {
    private JTextField campoCostoKm;
    private JTextField campoPorcentajeAumento;
    private JTextField campoCostoInterprovincial;

    public PanelParametrosCosto() {
        configurarPanel();
        inicializarComponentes();
        construirLayout();
    }

    private void configurarPanel() {
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Configuración de Costos"));
    }

    private void inicializarComponentes() {
        campoCostoKm = new JTextField("1000.0");
        campoPorcentajeAumento = new JTextField("0.40");
        campoCostoInterprovincial = new JTextField("20000.0");
    }

    private void construirLayout() {
        add(new JLabel("Costo por Km ($):"));
        add(campoCostoKm);
        add(new JLabel("Aumento (>300km) [0-1]:"));
        add(campoPorcentajeAumento);
        add(new JLabel("Costo Interprovincial ($):"));
        add(campoCostoInterprovincial);
    }

    public String getCostoKm() { return campoCostoKm.getText(); }
    public String getPorcentajeAumento() { return campoPorcentajeAumento.getText(); }
    public String getCostoProvincia() { return campoCostoInterprovincial.getText(); }
}
