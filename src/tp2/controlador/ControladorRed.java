package tp2.controlador;

import tp2.modelo.*;
import tp2.vista.PanelFormularioLocalidad;
import tp2.vista.PanelParametrosCosto;
import tp2.vista.VentanaPrincipal;

import javax.swing.*;
import java.util.List;


/** Controlador que coordina la comunicacion entre la Vista y el Modelo. */

public class ControladorRed {
    private final VentanaPrincipal vista;
    private final PlanificadorRed modelo;


    public ControladorRed(VentanaPrincipal vista, PlanificadorRed modelo) {
        this.vista = vista;
        this.modelo = modelo;
        inicializarVista();
        suscribirEventos();
    }

    private void inicializarVista() {
        actualizarProvincias();
        actualizarListado();
    }

    private void suscribirEventos() {
        this.vista.getPnlFormulario().getBtnAgregar().addActionListener(e -> agregarLocalidad());
        this.vista.agregarAccionCalcular(e -> solicitarPlanificacion());
        this.vista.getPnlListado().getBtnEliminar().addActionListener(e -> eliminarSeleccionada());
        this.vista.getPnlListado().getBtnEliminarTodo().addActionListener(e -> eliminarTodas());
        
        this.vista.getPnlListado().getCampoBusqueda().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                actualizarListado();
            }
        });

        this.vista.getPnlListado().getComboProvincia().addActionListener(e -> actualizarListado());

        this.vista.getPnlListado().getBotonLimpiarFiltros().addActionListener(e -> {
            vista.getPnlListado().limpiarFiltros();
            actualizarListado();
        });
        
        this.vista.getPnlMapa().alHacerClick(coordenada -> {
            this.vista.getPnlFormulario().setCoordenadas(coordenada.getLat(), coordenada.getLon());
        });
    }

    private void eliminarSeleccionada() {
        int indice = vista.getPnlListado().getIndiceSeleccionado();
        if (indice == -1) {
            vista.mostrarError("Seleccione una localidad en la tabla para eliminar.");
            return;
        }

        List<Localidad> localidadesVisibles = obtenerLocalidadesVisibles();
        modelo.eliminarLocalidad(localidadesVisibles.get(indice));
        vista.getPnlListado().limpiarFiltros();
        actualizarProvincias();
        actualizarListado();
        
        vista.getPnlMapa().limpiar();
        vista.getPnlResultados().limpiar();
    }

    private void eliminarTodas() {
        if (modelo.sinLocalidades()) {
            vista.mostrarError("No hay localidades para eliminar.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
            vista,
            "Estas seguro de que deseas eliminar todas las localidades?\nEsta accion no se puede deshacer.",
            "Confirmar eliminacion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            modelo.eliminarTodasLasLocalidades();
            vista.getPnlListado().limpiarFiltros();
            actualizarProvincias();
            actualizarListado();
            
            vista.getPnlMapa().limpiar();
            vista.getPnlResultados().limpiar();
            vista.mostrarError("Todas las localidades han sido eliminadas.");
        }
    }

    private void agregarLocalidad() {
        PanelFormularioLocalidad formulario = vista.getPnlFormulario();
        try {
            Localidad nueva = extraerLocalidadDesdeFormulario(formulario);
            modelo.agregarLocalidad(nueva);
            
            actualizarProvincias();
            actualizarListado();
            vista.getPnlMapa().agregarMarcador(nueva.nombre(), nueva.latitud(), nueva.longitud());
            formulario.limpiar();
            
        } catch (NumberFormatException ex) {
            vista.mostrarError("La latitud y longitud deben ser numeros validos.");
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private Localidad extraerLocalidadDesdeFormulario(PanelFormularioLocalidad form) {
        String nombre = form.getNombre();
        String provincia = form.getProvincia();
        double latitud = Double.parseDouble(form.getLatitud());
        double longitud = Double.parseDouble(form.getLongitud());
        return new Localidad(nombre, provincia, latitud, longitud);
    }

    private void solicitarPlanificacion() {
        if (modelo.cantidadLocalidades() < 2) {
            vista.mostrarError("Se necesitan al menos 2 localidades para planificar la red.");
            return;
        }

        try {
            ValuadorConexiones parametros = extraerParametrosCosto();
            ejecutarPlanificacionAsincronica(parametros);
        } catch (NumberFormatException ex) {
            vista.mostrarError("Los parametros de costo deben ser numeros validos.");
        }
    }

    private ValuadorConexiones extraerParametrosCosto() {
        PanelParametrosCosto panel = vista.getPnlParametros();
        return new ValuadorConexiones(
            Double.parseDouble(panel.getCostoKm()),
            Double.parseDouble(panel.getPorcentajeAumento()),
            Double.parseDouble(panel.getCostoProvincia())
        );
    }

    private void ejecutarPlanificacionAsincronica(ValuadorConexiones parametros) {
        SwingWorker<SolucionRed, Void> trabajador = new SwingWorker<>() {
            @Override
            protected SolucionRed doInBackground() {
                return modelo.planificar(parametros);
            }

            @Override
            protected void done() {
                try {
                    SolucionRed resultado = get();
                    actualizarResultadosEnVista(resultado);
                } catch (Exception e) {
                    vista.mostrarError("Error en el calculo: " + e.getMessage());
                } finally {
                    vista.setEstadoCargando(false);
                }
            }
        };

        vista.setEstadoCargando(true);
        trabajador.execute();
    }

    private void actualizarResultadosEnVista(SolucionRed resultado) {
        vista.mostrarResultado(resultado);
        vista.getPnlResultados().mostrarSolucion(resultado);

        List<double[]> marcadores = modelo.obtenerLocalidades().stream()
            .map(l -> new double[]{l.latitud(), l.longitud()})
            .toList();

        List<double[][]> conexiones = resultado.conexiones().stream()
            .map(c -> new double[][]{
                {c.localidad1().latitud(), c.localidad1().longitud()},
                {c.localidad2().latitud(), c.localidad2().longitud()}
            })
            .toList();

        vista.getPnlMapa().dibujarSolucion(marcadores, conexiones);
    }

    private void actualizarListado() {
        List<String[]> filas = obtenerLocalidadesVisibles().stream()
            .map(l -> new String[]{
                l.nombre(),
                l.provincia(),
                String.format("%.4f", l.latitud()),
                String.format("%.4f", l.longitud())
            })
            .toList();
        vista.getPnlListado().actualizar(filas);
    }

    private void actualizarProvincias() {
        vista.getPnlListado().actualizarProvincias(modelo.obtenerProvincias());
    }

    private List<Localidad> obtenerLocalidadesVisibles() {
        return modelo.filtrarLocalidades(
            vista.getPnlListado().getTextoBusqueda(),
            vista.getPnlListado().getProvinciaSeleccionada()
        );
    }
}
