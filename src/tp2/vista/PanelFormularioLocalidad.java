package tp2.vista;

import javax.swing.*;
import java.awt.*;

/** Panel para el ingreso de datos de una nueva localidad */

public class PanelFormularioLocalidad extends JPanel {
    private JTextField campoNombre;
    private JTextField campoProvincia;
    private JTextField campoLatitud;
    private JTextField campoLongitud;
    private JButton botonAgregar;

    public PanelFormularioLocalidad() {
        configurarPanel();
        inicializarComponentes();
        construirLayout();
    }

    private void configurarPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Nueva Localidad"));
    }

    private void inicializarComponentes() {
        campoNombre = new JTextField(15);
        campoProvincia = new JTextField(15);
        campoLatitud = new JTextField(10);
        campoLongitud = new JTextField(10);
        
        campoLatitud.setToolTipText("Ej: -34.60 (Grados decimales)");
        campoLongitud.setToolTipText("Ej: -58.38 (Grados decimales)");
        
        botonAgregar = new JButton("Agregar Localidad");
        botonAgregar.setFont(new Font("SansSerif", Font.BOLD, 12));
        botonAgregar.setBackground(new Color(13, 110, 253));
        botonAgregar.setForeground(Color.WHITE);
    }

    private void construirLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; add(campoNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Provincia:"), gbc);
        gbc.gridx = 1; add(campoProvincia, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Latitud (dec):"), gbc);
        gbc.gridx = 1; add(campoLatitud, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Longitud (dec):"), gbc);
        gbc.gridx = 1; add(campoLongitud, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(botonAgregar, gbc);
    }

    public void setCoordenadas(double latitud, double longitud) {
        campoLatitud.setText(String.format("%.6f", latitud).replace(",", "."));
        campoLongitud.setText(String.format("%.6f", longitud).replace(",", "."));
    }

    public void limpiar() {
        campoNombre.setText("");
        campoProvincia.setText("");
        campoLatitud.setText("");
        campoLongitud.setText("");
    }

    public String getNombre() { return campoNombre.getText(); }
    public String getProvincia() { return campoProvincia.getText(); }
    public String getLatitud() { return campoLatitud.getText(); }
    public String getLongitud() { return campoLongitud.getText(); }
    public JButton getBtnAgregar() { return botonAgregar; }
}
