import database.JugadoresDAO;
import database.JugadoresDTO;
import java.awt.*;
import javax.swing.*;
import movimientos.Lagarto;
import movimientos.Movimiento;
import movimientos.Papel;
import movimientos.Piedra;
import movimientos.Spock;
import movimientos.Tijera;

public class VentanaJuego implements Mensaje {
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
    private Cliente cliente;
    private boolean confirmado = false;

    public VentanaJuego(Cliente cliente, String titulo) {
        this.cliente = cliente;
        ventana = new JFrame(titulo);
        ventana.getContentPane().setBackground(new Color(22, 132, 75));
        pantalla = new JPanel();
        pantalla.setOpaque(false);
        centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        confirmarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        configurarVentana();
        configurarSuperior();
        configurarConfirmar();
        configurarCentro();
        configurarBotones();
        conexionBaseDatos();

        ventana.setVisible(true);
    }

    private void configurarVentana() {
        ventana.setSize(800, 900);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());
        ventana.add(pantalla, BorderLayout.CENTER);
    }

    private void configurarSuperior() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(22, 132, 75));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        estadoConexion = new JLabel("Offline");
        estadoConexion.setForeground(Color.RED);
        estadoConexion.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(estadoConexion, BorderLayout.WEST);

        JPanel marcadorPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        marcadorPanel.setOpaque(false);

        marcadorJugador1 = new JTextField("0");
        marcadorJugador1.setEditable(false);
        marcadorJugador1.setHorizontalAlignment(JTextField.CENTER);
        marcadorJugador1.setFont(new Font("Arial", Font.BOLD, 20));

        marcadorJugador2 = new JTextField("0");
        marcadorJugador2.setEditable(false);
        marcadorJugador2.setHorizontalAlignment(JTextField.CENTER);
        marcadorJugador2.setFont(new Font("Arial", Font.BOLD, 20));

        marcadorPanel.add(marcadorJugador1);
        marcadorPanel.add(marcadorJugador2);

        panelSuperior.add(marcadorPanel, BorderLayout.CENTER);
        ventana.add(panelSuperior, BorderLayout.NORTH);
    }

    private void configurarConfirmar() {
        confirmar.addActionListener(e -> {
            if (!confirmado) {
                Movimiento movSel = panelOpciones.getMovimientoSeleccionado();
                if (movSel == null) {
                    JOptionPane.showMessageDialog(ventana, "Selecciona primero un movimiento.");
                    return;
                }
                cliente.enviarMensaje(new MensajeMovimiento(movSel.getNombre()));
                setMovimientoIcon(movimientoJugador1, movSel.getRutaImagen());
                panelOpciones.setBotonesHabilitados(false);
                confirmar.setText("¡Enviado!");
                confirmar.setBackground(Color.GREEN);
                confirmado = true;
            }
        });
    }

    private void configurarCentro() {
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));
        overlayPanel.setOpaque(false);

        JPanel panelCentralTotal = new JPanel(new BorderLayout());
        panelCentralTotal.setOpaque(false);

        centro.setLayout(new GridLayout(1, 2));
        movimientoJugador2 = new JLabel("", SwingConstants.CENTER);
        movimientoJugador1 = new JLabel("", SwingConstants.CENTER);
        centro.add(movimientoJugador2);
        centro.add(movimientoJugador1);

        panelCentralTotal.add(centro, BorderLayout.CENTER);
        panelCentralTotal.add(confirmarPanel, BorderLayout.SOUTH);

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

    private void conexionBaseDatos() {
        try {
            JugadoresDTO nuevo = new JugadoresDTO();
            nuevo.setNombre("AAA");
            nuevo.setPuntos(0);
            nuevo.setIp(new IPAddress().getIp());
            new JugadoresDAO().append(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarEstadoConexion(boolean online) {
        if (online) {
            estadoConexion.setText("Online");
            estadoConexion.setForeground(Color.GREEN);
        } else {
            estadoConexion.setText("Offline");
            estadoConexion.setForeground(Color.RED);
        }
    }

    private void setMovimientoIcon(JLabel label, String ruta) {
        java.net.URL rutaImg = getClass().getResource(ruta);
        if (rutaImg != null) {
            ImageIcon icono = new ImageIcon(rutaImg);
            Image img = icono.getImage().getScaledInstance(230, 400, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(img));
        }
    }

    private void mostrarResultado(ResultadoJuego rj) {
        String rutaResultado = "/img/tie.gif";
        switch (rj) {
            case GANASTE:
                rutaResultado = "/img/you-win-winner.gif";
                // actualizar marcador
                marcadorJugador1.setText(
                    String.valueOf(Integer.parseInt(marcadorJugador1.getText()) + 1));
                break;
            case PERDISTE:
                rutaResultado = "/img/you-loose.gif";
                marcadorJugador2.setText(
                    String.valueOf(Integer.parseInt(marcadorJugador2.getText()) + 1));
                break;
            case EMPATE:
                rutaResultado = "/img/tie.gif";
                break;
        }
        java.net.URL ruta = getClass().getResource(rutaResultado);
        if (ruta != null) {
            resultado.setIcon(new ImageIcon(ruta));
            resultado.setVisible(true);
        }
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> ventana.setVisible(true));
    }

    @Override
    public void mensaje_entrante(Object msg) {
        if (!(msg instanceof MensajeResultado)) return;
        MensajeResultado mr = (MensajeResultado) msg;

        setMovimientoIcon(movimientoJugador2,
            MovimientoFactory.create(mr.getSuMovimiento()).getRutaImagen());
        mostrarResultado(mr.getResultadoParaTi());

        confirmado = false;
        panelOpciones.setBotonesHabilitados(true);
        confirmar.setText("Confirmar");
        confirmar.setBackground(Color.LIGHT_GRAY);
    }
    

}