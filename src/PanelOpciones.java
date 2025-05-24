import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelOpciones {
    private JPanel panel;
    private List<JButton> botones;
    private JLabel movimientoLabel;
    private Movimiento movimientoSeleccionado;

    private final Movimiento[] movimientos = {
            new Piedra(),
            new Papel(),
            new Tijera(),
            new Lagarto(),
            new Spock()
    };

    public PanelOpciones(JLabel movimientoLabel) {
        this.movimientoLabel = movimientoLabel;
        this.panel = new JPanel(new FlowLayout());
        this.panel.setBackground(new Color(229, 210, 154));
        this.botones = new ArrayList<>();
        crearBotones();
    }

    private void crearBotones() {
        for (Movimiento mov : movimientos) {
            // Asegúrate que getRutaImagen() retorna una ruta válida (ej. "/img/paper.png")
            java.net.URL ruta = getClass().getResource(mov.getRutaImagen());
            if (ruta == null) {
                System.err.println("No se encontró la imagen para: " + mov.getNombre());
                continue; // Salta si no se encontró la imagen
            }

            ImageIcon iconoOriginal = new ImageIcon(ruta);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(121, 190, Image.SCALE_SMOOTH);
            Image iconoMostrar = iconoOriginal.getImage().getScaledInstance(230, 400, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
            ImageIcon iconoGrande = new ImageIcon(iconoMostrar);

            JButton boton = new JButton(mov.getNombre(), iconoEscalado);
            boton.setPreferredSize(new Dimension(121, 193));

            boton.addActionListener((ActionEvent e) -> {
                movimientoLabel.setIcon(iconoGrande);
                movimientoSeleccionado = mov;
            });

            panel.add(boton);
            botones.add(boton);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public Movimiento getMovimientoSeleccionado() {
        return movimientoSeleccionado;
    }

    public void setBotonesHabilitados(boolean estado) {
        for (JButton boton : botones) {
            boton.setEnabled(estado);
        }
    }

    public List<JButton> getBotones() {
        return botones;
    }

}
