import javax.swing.*;
import java.awt.*;
//Imports for the database
import database.JugadoresDAO;
import database.JugadoresDTO;

public class VentanaJuego {
    private JFrame ventana;
    private JPanel pantalla;
    private JPanel centro;
    private JLabel movimiento;
    private JButton confirmar;
    private PanelOpciones panelOpciones;

    public VentanaJuego() {
        ventana = new JFrame();
        ventana.setBackground(new Color(22, 132, 75));
        pantalla = new JPanel();
        pantalla.setOpaque(false);
        centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        movimiento = new JLabel("", SwingConstants.CENTER);

        configurarVentana();
        configurarConfirmar();
        configurarCentro();
        configurarBotones();
        conexionBaseDatos();

        ventana.setVisible(true);
    }

    private void configurarVentana() {
        ventana.setSize(800, 900);
        ventana.getContentPane().setBackground(new Color(22, 132, 75));
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());
        ventana.add(pantalla, BorderLayout.CENTER);
    }

    private void configurarConfirmar() {
        confirmar = new JButton("Confirmar");
        confirmar.setBackground(Color.LIGHT_GRAY);
        confirmar.setOpaque(true);
        confirmar.setBorderPainted(false);
        confirmar.setFont(new Font("Arial", Font.BOLD, 14));
        confirmar.setBounds(290, 600, 200, 25);

        final boolean[] confirmadoArray = { false };

        confirmar.addActionListener(e -> {
            if (!confirmadoArray[0]) {
                confirmar.setBackground(Color.GREEN);
                confirmar.setText("¡Confirmado!");
                confirmadoArray[0] = true;
                for (JButton boton : panelOpciones.getBotones()) {
                    boton.setEnabled(false);
                }
            } else {
                confirmar.setBackground(Color.LIGHT_GRAY);
                confirmar.setText("Confirmar");
                confirmadoArray[0] = false;
                for (JButton boton : panelOpciones.getBotones()) {
                    boton.setEnabled(true);
                }
            }
        });
    }

    private void configurarCentro() {
        centro.add(confirmar, BorderLayout.CENTER);
        centro.add(movimiento, BorderLayout.CENTER);
        ventana.add(centro, BorderLayout.CENTER);

    }

    private void configurarBotones() {
        panelOpciones = new PanelOpciones(movimiento);
        ventana.add(panelOpciones.getPanel(), BorderLayout.SOUTH);
    }

    private void conexionBaseDatos(){
        try{
            JugadoresDTO nuevo = new JugadoresDTO();
            nuevo.setNombre("AAA");
            nuevo.setPuntos(0);
            nuevo.setIp(new IPAddress().getIp());
            new JugadoresDAO().append(nuevo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostrar() {
        ventana.setVisible(true);
    }
}
