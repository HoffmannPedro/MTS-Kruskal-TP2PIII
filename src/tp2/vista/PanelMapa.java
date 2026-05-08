package tp2.vista;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import tp2.modelo.Conexion;
import tp2.modelo.Localidad;

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

    public void dibujarSolucion(List<Localidad> localidades, List<Conexion> mst) {
        limpiar();

        for (Localidad localidad : localidades) {
            mapa.addMapMarker(new MapMarkerDot(localidad.nombre(), new Coordinate(localidad.latitud(), localidad.longitud())));
        }

        for (Conexion conexion : mst) {
            Coordinate coord1 = new Coordinate(conexion.localidad1().latitud(), conexion.localidad1().longitud());
            Coordinate coord2 = new Coordinate(conexion.localidad2().latitud(), conexion.localidad2().longitud());
            
            MapPolygonImpl linea = new MapPolygonImpl(List.of(coord1, coord2, coord1));
            linea.setColor(new Color(0, 255, 255));
            linea.setStroke(new BasicStroke(2.5f));
            mapa.addMapPolygon(linea);
        }

        if (!localidades.isEmpty()) {
            mapa.setDisplayToFitMapMarkers();
        }
    }

    public void limpiar() {
        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();
    }
}
