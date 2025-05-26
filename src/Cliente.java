import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private String hostIp;
    private int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private VentanaJuego juego;

    public Cliente(String hostIp, int port, String juegoTitulo) throws Exception {
        this.hostIp = hostIp;           
        this.port = port;
        conectar();
        this.juego = new VentanaJuego(this, juegoTitulo);
        juego.mostrar();
        mensajeServidor();
    }

    private void conectar() throws Exception {
        socket = new Socket(hostIp, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void enviarMensaje(Object mensaje) {
        try {
            oos.writeObject(mensaje);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviar(int puerto, String mensaje) {
        try (Socket cliente = new Socket(hostIp, puerto);
             ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream())) {
            oos.writeObject(mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void mensajeServidor() {
        new Thread(() -> {
            try {
                Object msg;
                while ((msg = ois.readObject()) != null) {
                    if (msg instanceof String) {
                        juego.mensaje_entrante((String) msg);
                    }
                }
            } catch (Exception e) {
                System.err.println("Conexión al servidor cerrada.");
            }
        }).start();
    }



}
