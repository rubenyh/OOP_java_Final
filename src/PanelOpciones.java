import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelOpciones {
    private JPanel panel;
    private List<JButton> botones;
    private JLabel movimiento;

    private final String[] opciones = {"PIEDRA", "PAPEL", "TIJERAS", "LAGARTO", "SPOCK"};
    private final String[] rutasimg = {"img/rock.png", "img/paper.png", "img/scissors.png", "img/lizzard.png", "img/spock.png"};

    public PanelOpciones(JLabel movimiento) {
        this.movimiento = movimiento;
        panel = new JPanel(new FlowLayout());
            panel.setBackground(new Color(229, 210, 154)); 
        botones = new ArrayList<>();
        crearBotones();
    }

    private void crearBotones() {
        for (int i = 0; i < opciones.length; i++) {
            String opcion = opciones[i];
            ImageIcon iconoOriginal = new ImageIcon(rutasimg[i]);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(121, 190, Image.SCALE_SMOOTH);
            Image iconoMostrar = iconoOriginal.getImage().getScaledInstance(230, 400, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
            ImageIcon iconoxMostrar = new ImageIcon(iconoMostrar);

            JButton boton = new JButton(opcion, iconoEscalado);
            boton.setPreferredSize(new Dimension(121, 193));
            boton.addActionListener((ActionEvent e) -> movimiento.setIcon(iconoxMostrar));
            panel.add(boton);
            botones.add(boton);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public List<JButton> getBotones() {
        return botones;
    }

    public void setBotonesHabilitados(boolean estado) {
        for (JButton boton : botones) {
            boton.setEnabled(estado);
        }
    }
}