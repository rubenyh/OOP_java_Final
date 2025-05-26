import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
//Imports for the database
import database.JugadoresDAO;
import database.JugadoresDTO;

public class Server {
    private final int port;
    private String hostIp;
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    public Server(String hostip, int port){
        this.hostIp = hostip;
        this.port = port;
    }


    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);
        JugadoresDAO dao = new JugadoresDAO();   
        
        while (true) {
            Socket clientSock = serverSocket.accept();
            String clientIp = clientSock.getInetAddress().getHostAddress();
            
            
            try {
            // Si ya existe, suma 1; si no, crea con 1 punto
            JugadoresDTO existe = dao.readByIp(clientIp);
            if (existe != null) {
                int i = existe.getPuntos();
                dao.incrementarPuntoPorIp(clientIp, i);
                System.out.println("Sumado 1 punto a " + clientIp);
            } else {
                JugadoresDTO nuevo = new JugadoresDTO();
                nuevo.setNombre("BBB");
                nuevo.setPuntos(1);
                nuevo.setIp(clientIp);
                dao.append(nuevo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

            ClientHandler handler = new ClientHandler(clientSock, this);
            clients.add(handler);
            new Thread(handler).start();
            notifyClientConnected(handler);
        }
    }

    public void broadcast(String msg) {
        for (ClientHandler c : clients) {
            c.send(msg);
        }
    }


    public void removeClient(ClientHandler c) {
        clients.remove(c);
        System.out.println("Client " + c.getClientIp() + " disconnected");
    }

    public void notifyClientConnected(ClientHandler c) {
        System.out.println("Client " + c.getClientIp() + " connected");
    }

    public static void main(String[] args) {
        int port = 9000;
        try {
            new Server(port).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
