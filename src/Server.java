import database.JugadoresDAO;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import movimientos.Movimiento;


public class Server {
    private final int port;
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final Map<String,String> pendingMoves = new ConcurrentHashMap<>();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);
        JugadoresDAO dao = new JugadoresDAO();

        while (true) {
            Socket clientSock = serverSocket.accept();
            String clientIp = clientSock.getInetAddress().getHostAddress();

            // database logic: increment or append
            // try {
            //     JugadoresDTO existe = dao.readByIp(clientIp);
            //     if (existe != null) {
            //         dao.incrementarPuntoPorIp(clientIp, existe.getPuntos());
            //     } else {
            //         JugadoresDTO nuevo = new JugadoresDTO();
            //         nuevo.setNombre("AAA");
            //         nuevo.setPuntos(1);
            //         nuevo.setIp(clientIp);
            //         dao.append(nuevo);
            //     }
            // } catch (SQLException e) {
            //     e.printStackTrace();
            // }

            ClientHandler handler = new ClientHandler(clientSock, this);
            clients.add(handler);
            new Thread(handler).start();
            System.out.println("Client connected: " + clientIp);
        }
    }

    public synchronized void handleMove(ClientHandler from, String moveName) {
        pendingMoves.put(from.getClientIp(), moveName);
        if (pendingMoves.size() == 2) {
            Iterator<Map.Entry<String,String>> it = pendingMoves.entrySet().iterator();
            var e1 = it.next();
            var e2 = it.next();

            Movimiento m1 = MovimientoFactory.create(e1.getValue());
            Movimiento m2 = MovimientoFactory.create(e2.getValue());
            ResultadoJuego r1 = Logica.jugar(m1, m2);
            ResultadoJuego r2 = (r1 == ResultadoJuego.GANASTE)
                               ? ResultadoJuego.PERDISTE
                               : (r1 == ResultadoJuego.PERDISTE)
                                 ? ResultadoJuego.GANASTE
                                 : ResultadoJuego.EMPATE;

            for (ClientHandler c : clients) {
                String ip = c.getClientIp();
                if (ip.equals(e1.getKey())) {
                    c.send(new MensajeResultado(e1.getValue(), e2.getValue(), r1));
                } else if (ip.equals(e2.getKey())) {
                    c.send(new MensajeResultado(e2.getValue(), e1.getValue(), r2));
                }
            }
            pendingMoves.clear();
        }
    }

    public void removeClient(ClientHandler c) {
        clients.remove(c);
        System.out.println("Client disconnected: " + c.getClientIp());
    }

    public static void main(String[] args) throws Exception {
        new Server(9000).start();
    }
}

