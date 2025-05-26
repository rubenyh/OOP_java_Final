import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private String hostIp;
    private int port;
    private VentanaJuego juego;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream  ois;

    public Cliente(String hostIp, int port, String titulo) throws Exception {
        this.hostIp = hostIp;
        this.port = port;
        // 1) Abre el socket y los streams
        socket = new Socket(hostIp, port);
        oos    = new ObjectOutputStream(socket.getOutputStream());
        ois    = new ObjectInputStream(socket.getInputStream());

        // 2) Crea la ventana y pasa esta instancia de Cliente
        this.juego = new VentanaJuego(this, titulo);
        this.juego.mostrar();

        // 3) Arranca un hilo que lee en bucle los mensajes entrantes
        new Thread(() -> {
            try {
                Object obj;
                while ((obj = ois.readObject()) != null) {
                    if (obj instanceof String) {
                        String msj = (String) obj;
                        // Si es un aviso de estado, actualiza el label
                        if (msj.equals("STATE:ONLINE")) {
                            juego.actualizarEstadoConexion(true);
                        } else if (msj.equals("STATE:OFFLINE")) {
                            juego.actualizarEstadoConexion(false);
                        } else {
                            // otros mensajes (jugadas, chat…)
                            juego.mensaje_entrante(msj);
                        }
                    }
                }
            } catch (Exception e) {
                // Al romper el bucle, marcamos offline
                juego.actualizarEstadoConexion(false);
            }
        }).start();
    }

    // Método que usa VentanaJuego para enviar cualquier mensaje
    public void enviarMensaje(Object mensaje) {
        try {
            oos.writeObject(mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
