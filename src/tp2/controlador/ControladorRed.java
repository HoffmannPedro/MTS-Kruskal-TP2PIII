package tp2.controlador;

import tp2.modelo.Localidad;
import tp2.modelo.ParametrosCosto;
import tp2.modelo.PlanificadorRed;
import tp2.modelo.SolucionRed;
import tp2.modelo.RepositorioLocalidades;
import tp2.vista.PanelFormularioLocalidad;
import tp2.vista.PanelParametrosCosto;
import tp2.vista.VentanaPrincipal;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/** Controlador que dirige la comunicación entre la Vista y el Modelo.*/

public class ControladorRed {
    private final VentanaPrincipal vista;
    private final PlanificadorRed modelo;
    private final RepositorioLocalidades repositorio;
    private final List<Localidad> localidades;

    public ControladorRed(VentanaPrincipal vista, PlanificadorRed modelo, RepositorioLocalidades repositorio) {
        this.vista = vista;
        this.modelo = modelo;
        this.repositorio = repositorio;
        
        this.localidades = repositorio.cargar();
        inicializarVista();
        suscribirEventos();
    }

    private void inicializarVista() {
        this.vista.getPnlListado().actualizar(localidades);
    }

    private void suscribirEventos() {
        this.vista.getPnlFormulario().getBtnAgregar().addActionListener(e -> agregarLocalidad());
        this.vista.agregarAccionCalcular(e -> solicitarPlanificacion());
        this.vista.getPnlListado().getBtnEliminar().addActionListener(e -> eliminarSeleccionada());
        
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

        localidades.remove(indice);
        repositorio.guardar(localidades);
        vista.getPnlListado().actualizar(localidades);
        
        vista.getPnlMapa().limpiar();
        vista.getPnlResultados().limpiar();
    }

    private void agregarLocalidad() {
        PanelFormularioLocalidad formulario = vista.getPnlFormulario();
        try {
            Localidad nueva = extraerLocalidadDesdeFormulario(formulario);
            localidades.add(nueva);
            
            repositorio.guardar(localidades);
            vista.getPnlListado().actualizar(localidades);
            formulario.limpiar();
            
        } catch (NumberFormatException ex) {
            vista.mostrarError("La latitud y longitud deben ser números válidos.");
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
        if (localidades.size() < 2) {
            vista.mostrarError("Se necesitan al menos 2 localidades para planificar la red.");
            return;
        }

        try {
            ParametrosCosto parametros = extraerParametrosCosto();
            ejecutarPlanificacionAsincronica(parametros);
        } catch (NumberFormatException ex) {
            vista.mostrarError("Los parámetros de costo deben ser números válidos.");
        }
    }

    private ParametrosCosto extraerParametrosCosto() {
        PanelParametrosCosto panel = vista.getPnlParametros();
        return new ParametrosCosto(
            Double.parseDouble(panel.getCostoKm()),
            Double.parseDouble(panel.getPorcentajeAumento()),
            Double.parseDouble(panel.getCostoProvincia())
        );
    }

    private void ejecutarPlanificacionAsincronica(ParametrosCosto parametros) {
        SwingWorker<SolucionRed, Void> trabajador = new SwingWorker<>() {
            @Override
            protected SolucionRed doInBackground() {
                return modelo.planificar(localidades, parametros);
            }

            @Override
            protected void done() {
                try {
                    SolucionRed resultado = get();
                    actualizarResultadosEnVista(resultado);
                } catch (Exception e) {
                    vista.mostrarError("Error en el cálculo: " + e.getMessage());
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
        vista.getPnlMapa().dibujarSolucion(localidades, resultado.conexiones());
    }
}
