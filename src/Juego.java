
import java.io.IOException;
import javax.swing.*;

public class Juego extends JFrame {
    private JTextArea txtEscribir, txtMensajes;
    private JButton btnEnviar, btnLimpiar;
    private Cliente cliente;
    private Server server;
    private String ip;
    private int puerto2;


    public Juego(String nombre, int puerto1, String ip, int puerto2) throws IOException {
        
        super(nombre);
        this.ip = ip;
        this.puerto2 = puerto2;
        this.cliente = new Cliente();
        try {
            this.server = new Server(puerto1);
        } catch (Exception e) {
            throw new IOException("No pude iniciar el servidor en el puerto " + puerto1, e);
        }
        frames();
        new Thread(() -> {
            while (true) {
                String msg = server.recibir();
                if (msg != null) {
                    txtMensajes.append(msg + "\n");
                }
            }
        }, "Hilo-Servidor-" + puerto1).start();
    }

    private void frames() {
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }
}
