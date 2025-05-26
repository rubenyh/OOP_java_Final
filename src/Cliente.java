import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private final String hostIp;
    private final int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final VentanaJuego juego;

    public Cliente(String hostIp, int port, String titulo) throws Exception {
        this.hostIp = hostIp;
        this.port   = port;
        conectar();
        juego = new VentanaJuego(this, titulo);
        juego.mostrar();
        mensajeServidor();
    }

    private void conectar() throws Exception {
        socket = new Socket(hostIp, port);
        oos    = new ObjectOutputStream(socket.getOutputStream());
        ois    = new ObjectInputStream(socket.getInputStream());
    }

    public void enviarMensaje(Object mensaje) {
        try {
            oos.writeObject(mensaje);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mensajeServidor() {
        new Thread(() -> {
            try {
                Object msg;
                while ((msg = ois.readObject()) != null) {
                    juego.mensaje_entrante(msg);
                }
            } catch (Exception e) {
                System.err.println("Conexión al servidor cerrada.");
            }
        }).start();
    }
}
