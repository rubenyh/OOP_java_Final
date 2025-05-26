import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private final String clientIp;
    private final ObjectOutputStream oos;

    public ClientHandler(Socket sock, Server server) throws Exception {
        this.socket   = sock;
        this.server   = server;
        this.clientIp = sock.getInetAddress().getHostAddress();
        this.oos      = new ObjectOutputStream(sock.getOutputStream());
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            Object obj;
            while ((obj = ois.readObject()) != null) {
                if (obj instanceof MensajeMovimiento) {
                    MensajeMovimiento mm = (MensajeMovimiento) obj;
                    System.out.println("Received from " + clientIp + ": " + mm.getMovimiento());
                    server.handleMove(this, mm.getMovimiento());
                }
            }
        } catch (Exception ignored) {
        } finally {
            server.removeClient(this);
            try { socket.close(); } catch (Exception e) {}
        }
    }

    public void send(Object msg) {
        try {
            oos.writeObject(msg);
            oos.flush();
        } catch (Exception e) {
            server.removeClient(this);
        }
    }

    public String getClientIp() { return clientIp; }
}
