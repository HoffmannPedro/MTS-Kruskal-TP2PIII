package tp2.vista;

import tp2.modelo.Conexion;
import tp2.modelo.SolucionRed;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Panel para mostrar el reporte detallado del MST(Árbol generador mínimo) calculado */

public class PanelResultados extends JPanel {
    private JTable tablaConexiones;
    private DefaultTableModel modeloTabla;
    private JLabel etiquetaCostoTotal;

    public PanelResultados() {
        configurarPanel();
        inicializarComponentes();
        construirLayout();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Reporte de Red Óptima (MST)"));
    }

    private void inicializarComponentes() {
        String[] nombresColumnas = {"Origen", "Destino", "Distancia (km)", "Costo ($)"};
        modeloTabla = new DefaultTableModel(nombresColumnas, 0);
        tablaConexiones = new JTable(modeloTabla);
        tablaConexiones.setRowHeight(25);
        
        etiquetaCostoTotal = new JLabel("Costo Total: $0.00");
        etiquetaCostoTotal.setFont(new Font("SansSerif", Font.BOLD, 22));
        etiquetaCostoTotal.setForeground(new Color(102, 204, 255));
        etiquetaCostoTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        etiquetaCostoTotal.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 15));
    }

    private void construirLayout() {
        add(new JScrollPane(tablaConexiones), BorderLayout.CENTER);
        add(etiquetaCostoTotal, BorderLayout.SOUTH);
    }

    public void mostrarSolucion(SolucionRed solucion) {
        modeloTabla.setRowCount(0);
        for (Conexion conexion : solucion.conexiones()) {
            modeloTabla.addRow(new Object[]{
                conexion.localidad1().nombre(),
                conexion.localidad2().nombre(),
                String.format("%.2f", conexion.distancia()),
                String.format("%.2f", conexion.costo())
            });
        }
        etiquetaCostoTotal.setText("Costo Total: $" + String.format("%.2f", solucion.costoTotal()));
    }

    public void limpiar() {
        modeloTabla.setRowCount(0);
        etiquetaCostoTotal.setText("Costo Total: $0.00");
    }
}
