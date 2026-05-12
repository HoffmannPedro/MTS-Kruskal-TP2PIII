package tp2.vista;

import tp2.modelo.Conexion;
import tp2.modelo.SolucionRed;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/** Vista principal de la aplicación */

public class VentanaPrincipal extends JFrame {
    private PanelFormularioLocalidad panelFormulario;
    private PanelParametrosCosto panelParametros;
    private PanelListado panelListado;
    private PanelResultados panelResultados;
    private PanelMapa panelMapa;
    private JButton botonCalcular;
    private JButton botonSalir;
    private JTextArea areaResumen;

    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
        construirLayout();
    }

    private void configurarVentana() {
        setTitle("Planificador de Red de Fibra Óptica");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        panelFormulario = new PanelFormularioLocalidad();
        panelParametros = new PanelParametrosCosto();
        panelListado = new PanelListado();
        panelResultados = new PanelResultados();
        panelMapa = new PanelMapa();

        botonCalcular = new JButton("Calcular Red Óptima");
        botonCalcular.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonCalcular.setBackground(new Color(25, 135, 84));
        botonCalcular.setForeground(Color.WHITE);
        botonCalcular.setPreferredSize(new Dimension(200, 45));

        botonSalir = new JButton("Salir");
        botonSalir.setFont(new Font("SansSerif", Font.BOLD, 12));
        botonSalir.setBackground(new Color(108, 117, 125));
        botonSalir.setForeground(Color.WHITE);
        botonSalir.setPreferredSize(new Dimension(200, 35));
        botonSalir.addActionListener(e -> System.exit(0));

        areaResumen = new JTextArea(10, 0);
        areaResumen.setEditable(false);
        areaResumen.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    private void construirLayout() {
        JPanel panelIzquierdo = crearPanelIzquierdo();
        JPanel panelCentral = crearPanelCentral();

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(panelFormulario);
        panel.add(Box.createVerticalStrut(10));
        panel.add(panelParametros);
        panel.add(Box.createVerticalStrut(20));

        JPanel envoltorioBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        envoltorioBoton.add(botonCalcular);
        panel.add(envoltorioBoton);

        JPanel envoltorioSalir = new JPanel(new FlowLayout(FlowLayout.CENTER));
        envoltorioSalir.add(botonSalir);
        panel.add(Box.createVerticalStrut(10));
        panel.add(envoltorioSalir);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Listado", panelListado);
        pestañas.addTab("Mapa Visual", panelMapa);
        pestañas.addTab("Reporte MST", panelResultados);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(pestañas, BorderLayout.CENTER);

        JScrollPane scrollResumen = new JScrollPane(areaResumen);
        scrollResumen.setBorder(BorderFactory.createTitledBorder("Resumen de la Planificación"));
        panel.add(scrollResumen, BorderLayout.SOUTH);

        return panel;
    }

    public void agregarAccionCalcular(ActionListener listener) {
        botonCalcular.addActionListener(listener);
    }

    public void setEstadoCargando(boolean cargando) {
        botonCalcular.setEnabled(!cargando);
        panelFormulario.getBtnAgregar().setEnabled(!cargando);
        if (cargando) {
            areaResumen.setText("Calculando red óptima... por favor espere.\n");
        }
    }

    public void mostrarResultado(SolucionRed solucion) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- RESULTADO DE LA PLANIFICACIÓN ---\n");
        sb.append("Cantidad de conexiones: ").append(solucion.conexiones().size()).append("\n");
        sb.append("Costo total estimado: $").append(String.format("%.2f", solucion.costoTotal())).append("\n\n");

        for (Conexion conexion : solucion.conexiones()) {
            sb.append("- ")
                    .append(conexion.localidad1().nombre())
                    .append(" <-> ")
                    .append(conexion.localidad2().nombre())
                    .append("\n");
        }
        areaResumen.setText(sb.toString());
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }

    public PanelFormularioLocalidad getPnlFormulario() {
        return panelFormulario;
    }

    public PanelParametrosCosto getPnlParametros() {
        return panelParametros;
    }

    public PanelListado getPnlListado() {
        return panelListado;
    }

    public PanelResultados getPnlResultados() {
        return panelResultados;
    }

    public PanelMapa getPnlMapa() {
        return panelMapa;
    }
}
