package tp2.vista;

import tp2.modelo.Localidad;
import tp2.modelo.FiltroLocalidades;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Panel que muestra el listado de localidades en una tabla */

public class PanelListado extends JPanel {
    private JTable tablaLocalidades;
    private DefaultTableModel modeloTabla;
    private JButton botonEliminar;
    private JButton botonEliminarTodo;
    private JTextField campoBusqueda;
    private JComboBox<String> comboProvincia;
    private JButton botonLimpiarFiltros;
    private List<Localidad> localidadesCompletas;

    public PanelListado() {
        this.localidadesCompletas = new ArrayList<>();
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

        campoBusqueda = new JTextField(15);
        campoBusqueda.setToolTipText("Buscar por nombre de localidad");

        comboProvincia = new JComboBox<>();
        comboProvincia.addItem("Todas");

        botonLimpiarFiltros = new JButton("Limpiar Filtros");
        botonLimpiarFiltros.setBackground(new Color(100, 100, 100));
        botonLimpiarFiltros.setForeground(Color.WHITE);
        botonLimpiarFiltros.setFont(new Font("SansSerif", Font.PLAIN, 11));

        botonEliminar = new JButton("Eliminar Seleccionada");
        botonEliminar.setBackground(new Color(187, 45, 59));
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setFont(new Font("SansSerif", Font.BOLD, 12));

        botonEliminarTodo = new JButton("Eliminar Todo");
        botonEliminarTodo.setBackground(new Color(139, 0, 0));
        botonEliminarTodo.setForeground(Color.WHITE);
        botonEliminarTodo.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private void construirLayout() {
        add(new JScrollPane(tablaLocalidades), BorderLayout.CENTER);
        
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(campoBusqueda);
        panelFiltros.add(new JLabel("Provincia:"));
        panelFiltros.add(comboProvincia);
        panelFiltros.add(botonLimpiarFiltros);
        
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.add(botonEliminar);
        panelAcciones.add(botonEliminarTodo);
        
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelFiltros, BorderLayout.NORTH);
        panelSur.add(panelAcciones, BorderLayout.SOUTH);
        
        add(panelSur, BorderLayout.SOUTH);
    }

    public void actualizar(List<Localidad> localidades) {
        this.localidadesCompletas = new ArrayList<>(localidades);
        actualizarTabla(localidades);
        actualizarComboProvincas();
    }

    private void actualizarTabla(List<Localidad> localidades) {
        modeloTabla.setRowCount(0);
        for (Localidad localidad : localidades) {
            modeloTabla.addRow(new Object[]{
                localidad.nombre(), 
                localidad.provincia(), 
                String.format("%.4f", localidad.latitud()), 
                String.format("%.4f", localidad.longitud())
            });
        }
    }

    private void actualizarComboProvincas() {
        String provinciaSeleccionada = (String) comboProvincia.getSelectedItem();
        comboProvincia.removeAllItems();
        comboProvincia.addItem("Todas");

        List<String> provincias = FiltroLocalidades.obtenerProvincias(localidadesCompletas);
        for (String provincia : provincias) {
            comboProvincia.addItem(provincia);
        }

        if (provinciaSeleccionada != null) {
            comboProvincia.setSelectedItem(provinciaSeleccionada);
        }
    }

    public void aplicarFiltros() {
        String nombre = campoBusqueda.getText();
        String provincia = (String) comboProvincia.getSelectedItem();
        
        List<Localidad> filtradas = FiltroLocalidades.aplicarFiltros(localidadesCompletas, nombre, provincia);
        actualizarTabla(filtradas);
    }

    public void limpiarFiltros() {
        campoBusqueda.setText("");
        comboProvincia.setSelectedItem("Todas");
        actualizarTabla(localidadesCompletas);
    }

    public int getIndiceSeleccionadoEnCompletas() {
        int indiceTabla = tablaLocalidades.getSelectedRow();
        if (indiceTabla == -1) return -1;

        String nombre = (String) modeloTabla.getValueAt(indiceTabla, 0);
        String provincia = (String) modeloTabla.getValueAt(indiceTabla, 1);

        for (int i = 0; i < localidadesCompletas.size(); i++) {
            Localidad l = localidadesCompletas.get(i);
            if (l.nombre().equals(nombre) && l.provincia().equals(provincia)) {
                return i;
            }
        }
        return -1;
    }

    public int getIndiceSeleccionado() {
        return tablaLocalidades.getSelectedRow();
    }

    public JButton getBtnEliminar() {
        return botonEliminar;
    }

    public JButton getBtnEliminarTodo() {
        return botonEliminarTodo;
    }

    public JTextField getCampoBusqueda() {
        return campoBusqueda;
    }

    public JComboBox<String> getComboProvincia() {
        return comboProvincia;
    }

    public JButton getBotonLimpiarFiltros() {
        return botonLimpiarFiltros;
    }
}
