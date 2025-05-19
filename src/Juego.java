import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;

public class Juego extends JFrame implements Mensaje {
    private final JTextArea txtEscribir  = new JTextArea(3, 30);
    private final JTextArea txtMensajes = new JTextArea(10, 30);
    private final JButton btnEnviar     = new JButton("Enviar");
    private ObjectOutputStream oos;
    private ObjectInputStream  ois;

    public Juego(String title, String host, int port) throws Exception {
        super(title);
        Socket socket = new Socket(host, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        txtMensajes.setEditable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(txtMensajes));
        panel.add(new JScrollPane(txtEscribir));
        panel.add(btnEnviar);

        btnEnviar.addActionListener(this::onEnviar);
        add(panel);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Listening thread
        new Thread(this::listen).start();
    }

    private void onEnviar(ActionEvent e) {
        String texto = txtEscribir.getText().trim();
        if (texto.isEmpty()) return;
        try {
            oos.writeObject(texto);
            txtEscribir.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error sending message: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listen() {
        try {
            Object obj;
            while ((obj = ois.readObject()) != null) {
                mensaje_entrante((String) obj);
            }
        } catch (Exception ex) {
            SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(this,
                    "Disconnected from server.",
                    "Connection lost", JOptionPane.WARNING_MESSAGE)
            );
        }
    }

    @Override
    public void mensaje_entrante(String msj) {
        SwingUtilities.invokeLater(() -> {
            txtMensajes.append(msj + "\n");
        });
    }

}
