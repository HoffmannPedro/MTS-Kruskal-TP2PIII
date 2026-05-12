package tp2.vista;

import tp2.modelo.Localidad;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Panel que muestra el listado de localidades en una tabla */

public class PanelListado extends JPanel {
    private JTable tablaLocalidades;
    private DefaultTableModel modeloTabla;
    private JButton botonEliminar;

    public PanelListado() {
        configurarPanel();
        inicializarComponentes();
        construirLayout();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Localidades Registradas"));
    }

    private void inicializarComponentes() {
        String[] nombresColumnas = {"Nombre", "Provincia", "Latitud", "Longitud"};
        modeloTabla = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        
        tablaLocalidades = new JTable(modeloTabla);
        tablaLocalidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLocalidades.setRowHeight(25);

        botonEliminar = new JButton("Eliminar Seleccionada");
        botonEliminar.setBackground(new Color(187, 45, 59));
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private void construirLayout() {
        add(new JScrollPane(tablaLocalidades), BorderLayout.CENTER);
        
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.add(botonEliminar);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    public void actualizar(List<Localidad> localidades) {
        modeloTabla.setRowCount(0);
        for (Localidad localidad : localidades) {
            modeloTabla.addRow(new Object[]{
                localidad.nombre(), 
                localidad.provincia(), 
                localidad.latitud(), 
                localidad.longitud()
            });
        }
    }

    public int getIndiceSeleccionado() {
        return tablaLocalidades.getSelectedRow();
    }

    public JButton getBtnEliminar() {
        return botonEliminar;
    }
}
