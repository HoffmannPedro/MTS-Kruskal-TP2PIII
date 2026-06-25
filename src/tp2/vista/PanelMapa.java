package tp2.vista;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

/** Panel que integra JMapViewer para visualizar la red sobre un mapa real */

public class PanelMapa extends JPanel {
    private final JMapViewer mapa;

    public PanelMapa() {
        setLayout(new BorderLayout());
        mapa = new JMapViewer();
        mapa.setBackground(new Color(33, 37, 41));
        
        mapa.setDisplayPosition(new Coordinate(-38.4161, -63.6167), 4);
        
        add(mapa, BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder("Visualización Geográfica (Click para capturar coordenadas)"));
    }

    public void alHacerClick(Consumer<Coordinate> callback) {
        mapa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Coordinate coordenada = (Coordinate) mapa.getPosition(e.getPoint());
                    callback.accept(coordenada);
                }
            }
        });
    }

    /**
     * Dibuja la solucion en el mapa.
     * @param marcadores Lista de {latitud, longitud} de cada localidad
     * @param conexiones Lista de pares {{lat1, lon1}, {lat2, lon2}} de cada conexion del AGM
     */
    public void dibujarSolucion(List<double[]> marcadores, List<double[][]> conexiones) {
        limpiar();

        for (double[] m : marcadores) {
            mapa.addMapMarker(new MapMarkerDot(new Coordinate(m[0], m[1])));
        }

        for (double[][] par : conexiones) {
            Coordinate coord1 = new Coordinate(par[0][0], par[0][1]);
            Coordinate coord2 = new Coordinate(par[1][0], par[1][1]);
            MapPolygonImpl linea = new MapPolygonImpl(List.of(coord1, coord2, coord1));
            linea.setColor(new Color(0, 255, 255));
            linea.setStroke(new BasicStroke(2.5f));
            mapa.addMapPolygon(linea);
        }

        if (!marcadores.isEmpty()) {
            mapa.setDisplayToFitMapMarkers();
        }
    }

    public void limpiar() {
        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();
    }

    /** Agrega un marcador al mapa con los datos primitivos de la localidad */
    public void agregarMarcador(String nombre, double latitud, double longitud) {
        mapa.addMapMarker(new MapMarkerDot(nombre, new Coordinate(latitud, longitud)));
    }
}
