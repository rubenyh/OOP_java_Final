import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.SwingUtilities;

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
                        String s = (String) msg;
                        if (s.startsWith("STATE:ONLINE")) {
                            // Llamada directa para poner Online
                            SwingUtilities.invokeLater(() ->
                                juego.actualizarEstadoConexion(true)
                            );
                        } else if (s.startsWith("STATE:OFFLINE")) {
                            // Llamada directa para poner Offline
                            SwingUtilities.invokeLater(() ->
                                juego.actualizarEstadoConexion(false)
                            );
                        } else {
                            // Cualquier otro mensaje lo muestra en un diálogo
                            SwingUtilities.invokeLater(() ->
                                juego.mensaje_entrante(s)
                            );
                        }
                    }
                }
            } catch (Exception e) {
                // Si el socket se cierra, marcamos Offline también
                SwingUtilities.invokeLater(() ->
                    juego.actualizarEstadoConexion(false)
                );
            }
        }).start();
}




}
