import java.awt.*;
import javax.swing.*;
import movimientos.Lagarto;
import movimientos.Movimiento;
import movimientos.Papel;
import movimientos.Piedra;
import movimientos.Spock;
import movimientos.Tijera;

public class VentanaJuego {
    private JFrame ventana;
    private JLabel estadoConexion;
    private JTextField marcadorJugador1;
    private JTextField marcadorJugador2;
    private JPanel pantalla;
    private JPanel centro;
    private JPanel confirmarPanel;
    private JLabel movimientoJugador1;
    private JLabel movimientoJugador2;
    private JLabel resultado;
    private JButton confirmar;
    private PanelOpciones panelOpciones;

    public VentanaJuego() {
        ventana = new JFrame();
        ventana.setBackground(new Color(22, 132, 75));
        pantalla = new JPanel();
        pantalla.setOpaque(false);
        centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        confirmarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


        configurarVentana();
        configurarConfirmar();
        configurarCentro();
        configurarBotones();

        ventana.setVisible(true);
    }

    private void configurarVentana() {
        ventana.setSize(800, 900);
        ventana.getContentPane().setBackground(new Color(22, 132, 75));
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());
        ventana.add(pantalla, BorderLayout.CENTER);
    
        // Panel superior con estado de conexión y marcadores
    JPanel panelSuperior = new JPanel(new BorderLayout());
    panelSuperior.setBackground(new Color(22, 132, 75));
    panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Estado de conexión (esquina superior izquierda)
    estadoConexion = new JLabel("Offline");
    estadoConexion.setForeground(Color.RED);
    estadoConexion.setFont(new Font("Arial", Font.BOLD, 16));
    panelSuperior.add(estadoConexion, BorderLayout.WEST);JPanel marcadorPanel = new JPanel(new GridLayout(1, 2, 20, 0));
    marcadorPanel.setOpaque(false);

    marcadorJugador1 = new JTextField("0");
    marcadorJugador1.setEditable(false);
    marcadorJugador1.setHorizontalAlignment(JTextField.CENTER);
    marcadorJugador1.setFont(new Font("Arial", Font.BOLD, 20));

    marcadorJugador2 = new JTextField("0");
    marcadorJugador2.setEditable(false);
    marcadorJugador2.setHorizontalAlignment(JTextField.CENTER);
    marcadorJugador2.setFont(new Font("Arial", Font.BOLD, 20));

    marcadorPanel.add(marcadorJugador2);
    marcadorPanel.add(marcadorJugador1);

    panelSuperior.add(marcadorPanel, BorderLayout.CENTER);

    ventana.add(panelSuperior, BorderLayout.NORTH); 
    ventana.add(pantalla, BorderLayout.CENTER);
}


    private void configurarConfirmar() {
    confirmar = new JButton("Confirmar");
    confirmar.setBackground(Color.LIGHT_GRAY);
    confirmar.setOpaque(true);
    confirmar.setBorderPainted(false);
    confirmar.setFont(new Font("Arial", Font.BOLD, 14));
    confirmarPanel.add(confirmar);

    final boolean[] confirmadoArray = { false };

    confirmar.addActionListener(e -> {
        if (!confirmadoArray[0]) {
            confirmar.setBackground(Color.GREEN);
            confirmar.setText("¡Confirmado!");
            confirmadoArray[0] = true;

            // Desactivar botones
            for (JButton boton : panelOpciones.getBotones()) {
                boton.setEnabled(false);
            }

            // Jugador 1
            Movimiento jugador1 = panelOpciones.getMovimientoSeleccionado();

            // Mostrar movimiento del jugador 1
            java.net.URL rutaJugador1 = getClass().getResource(jugador1.getRutaImagen());
            if (rutaJugador1 != null) {
                ImageIcon icono = new ImageIcon(rutaJugador1);
                Image img = icono.getImage().getScaledInstance(230, 400, Image.SCALE_SMOOTH);
                movimientoJugador1.setIcon(new ImageIcon(img));
            }

            //  Jugador 2 aleatorio
            Movimiento[] movimientos = {
                new Piedra(), new Papel(), new Tijera(), new Lagarto(), new Spock()
            };
            Movimiento jugador2 = movimientos[new java.util.Random().nextInt(movimientos.length)];

            // Mostrar movimiento del jugador 2
            java.net.URL rutaJugador2 = getClass().getResource(jugador2.getRutaImagen());
            if (rutaJugador2 != null) {
                ImageIcon icono = new ImageIcon(rutaJugador2);
                Image img = icono.getImage().getScaledInstance(230, 400, Image.SCALE_SMOOTH);
                movimientoJugador2.setIcon(new ImageIcon(img));
            }

            // Determinar resultado
           ResultadoJuego resultadoJuego = Logica.jugar(jugador1, jugador2);
String rutaResultado = "/img/tie.gif";

switch (resultadoJuego) {
    case GANASTE:
        rutaResultado = "/img/you-win-winner.gif";
        break;
    case PERDISTE:
        rutaResultado = "/img/you-loose.gif";
        break;
    case EMPATE:
        rutaResultado = "/img/tie.gif";
        break;
}

java.net.URL rutaImagenResultado = getClass().getResource(rutaResultado);
if (rutaImagenResultado != null) {
    resultado.setIcon(new ImageIcon(rutaImagenResultado));
    resultado.setVisible(true);
}

        } else {
            confirmar.setBackground(Color.LIGHT_GRAY);
            confirmar.setText("Confirmar");
            confirmadoArray[0] = false;

            // Limpiar interfaz
            for (JButton boton : panelOpciones.getBotones()) {
                boton.setEnabled(true);
            }
            movimientoJugador1.setIcon(null);
            movimientoJugador2.setIcon(null);
            resultado.setVisible(false);
        }
    });
}
private void configurarCentro() {
    // Panel donde se apilan el resultado (GIF) y el centro del juego
    JPanel overlayPanel = new JPanel();
    overlayPanel.setLayout(new OverlayLayout(overlayPanel));
    overlayPanel.setOpaque(false);

    // Panel principal con cartas y botón confirmar
    JPanel panelCentralTotal = new JPanel(new BorderLayout());
    panelCentralTotal.setOpaque(false);

    // Centro con las cartas
    centro.setLayout(new GridLayout(1, 2));
    movimientoJugador2 = new JLabel("", SwingConstants.CENTER);
    movimientoJugador1 = new JLabel("", SwingConstants.CENTER);
    centro.add(movimientoJugador2);
    centro.add(movimientoJugador1);

    panelCentralTotal.add(centro, BorderLayout.CENTER);
    panelCentralTotal.add(confirmarPanel, BorderLayout.SOUTH); // Usamos el panel de botón

    // Resultado encima
    resultado = new JLabel("", SwingConstants.CENTER);
    resultado.setOpaque(false);
    resultado.setAlignmentX(0.5f);
    resultado.setAlignmentY(0.5f);
    resultado.setVisible(false);

    overlayPanel.add(resultado);
    overlayPanel.add(panelCentralTotal);

    pantalla.setLayout(new BorderLayout());
    pantalla.add(overlayPanel, BorderLayout.CENTER);
}

    private void configurarBotones() {
        panelOpciones = new PanelOpciones(movimientoJugador1);
        ventana.add(panelOpciones.getPanel(), BorderLayout.SOUTH);
    }

    public void mostrar() {
        ventana.setVisible(true);
    }
}
