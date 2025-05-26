import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.SwingUtilities;

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
                    if (msg instanceof String) {
                        String s = (String) msg;
                        if (s.startsWith("STATE:ONLINE")) {
                            SwingUtilities.invokeLater(() ->
                                juego.actualizarEstadoConexion(true)
                            );
                        } else if (s.startsWith("STATE:OFFLINE")) {
                            SwingUtilities.invokeLater(() ->
                                juego.actualizarEstadoConexion(false)
                            );
                        } else {
                            SwingUtilities.invokeLater(() ->
                                juego.mensaje_entrante(s)
                            );
                        }
                    } else {
                        // Handle non-String messages correctly
                        Object resultMsg = msg;
                        SwingUtilities.invokeLater(() ->
                            juego.mensaje_entrante(resultMsg)
                        );
                    }
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() ->
                    juego.actualizarEstadoConexion(false)
                );
            }
        }).start();
}
}
