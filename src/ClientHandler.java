import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private ObjectOutputStream oos;
    private final String clientIp;

    public ClientHandler(Socket socket, Server server) throws Exception {
        this.socket = socket;
        this.server = server;
        this.clientIp = socket.getInetAddress().getHostAddress();
        this.oos = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try (var ois = new ObjectInputStream(socket.getInputStream())) {
            Object obj;
            while ((obj = ois.readObject()) != null) {
                String msg = (String) obj;
                String tagged = clientIp + ": " + msg;
                System.out.println("[" + clientIp + "] " + msg);
                server.broadcast(tagged);
            }
        } catch (Exception ignored) {
            server.removeClient(this);
        } finally {
            server.removeClient(this);
            try { socket.close(); } catch (Exception ignored) {}
        }
    }

    public void send(String msg) {
        try {
            oos.writeObject(msg);
        } catch (Exception e) {
            server.removeClient(this);
        }
    }

    public String getClientIp() {
        return clientIp;
    }
}
