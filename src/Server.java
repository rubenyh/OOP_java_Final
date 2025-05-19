
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket servidor;

    public Server(int puerto) throws Exception {
        servidor = new ServerSocket(puerto);
    }

    public String recibir() {
        try (Socket socket = servidor.accept();
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            return (String) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
