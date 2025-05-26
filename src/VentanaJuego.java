import database.JugadoresDAO;
import database.JugadoresDTO;
import java.awt.*;
import javax.swing.*;
import movimientos.Movimiento;

public class VentanaJuego implements Mensaje {
    private JFrame ventana;
    private JPanel pantalla;
    private JPanel centro;
    private JLabel movimientoLabel;
    private JButton confirmar;
    private PanelOpciones panelOpciones;
    private Cliente cliente;

    public VentanaJuego(Cliente cliente, String titulo) {
        this.cliente = cliente;
        ventana = new JFrame(titulo);
        ventana.getContentPane().setBackground(new Color(22, 132, 75));
        pantalla = new JPanel(); pantalla.setOpaque(false);
        centro = new JPanel(new BorderLayout()); centro.setOpaque(false);
        movimientoLabel = new JLabel("", SwingConstants.CENTER);

        configurarVentana();
        configurarConfirmar();
        configurarCentro();
        configurarBotones();
        conexionBaseDatos();
    }

    private void configurarVentana() {
        ventana.setSize(800, 900);
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

        confirmar.addActionListener(e -> {
            Movimiento movSel = panelOpciones.getMovimientoSeleccionado();
            if (movSel != null) {
                MensajeMovimiento mm = new MensajeMovimiento(movSel.getNombre());
                cliente.enviarMensaje(mm);
                confirmar.setBackground(Color.GREEN);
                confirmar.setText("¡Confirmado!");
                panelOpciones.setBotonesHabilitados(false);
            } else {
                JOptionPane.showMessageDialog(ventana, "Selecciona primero un movimiento.");
            }
        });
    }

    private void configurarCentro() {
        centro.add(confirmar, BorderLayout.NORTH);
        centro.add(movimientoLabel, BorderLayout.CENTER);
        ventana.add(centro, BorderLayout.CENTER);
    }

    private void configurarBotones() {
        panelOpciones = new PanelOpciones(movimientoLabel);
        ventana.add(panelOpciones.getPanel(), BorderLayout.SOUTH);
    }

    private void conexionBaseDatos(){
        try{
            JugadoresDTO nuevo = new JugadoresDTO();
            nuevo.setNombre("AAA");
            nuevo.setPuntos(0);
            nuevo.setIp(new IPAddress().getIp());
            new JugadoresDAO().append(nuevo);
        } catch (Exception e){ e.printStackTrace(); }
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> ventana.setVisible(true));
    }

    @Override
    public void mensaje_entrante(String msj) {
        // Mostrar mensajes del servidor en un diálogo
        SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(ventana, msj)
        );
    }
}
