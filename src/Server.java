import database.JugadoresDAO;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import movimientos.Movimiento;
import database.JugadoresDAO;
import database.JugadoresDTO;


public class Server {
    private final int port;
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final Map<String,String> pendingMoves = new ConcurrentHashMap<>();
    JugadoresDAO dao = new JugadoresDAO();

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

            try {
                // Si ya existe, suma 1; si no, crea con 1 punto
                JugadoresDTO existe = dao.readByIp(clientIp);
                if (existe != null) {
                    int i = existe.getPuntos();
                    dao.incrementarPuntoPorIp(clientIp, i);
                } else {
                    JugadoresDTO nuevo = new JugadoresDTO();
                    nuevo.setNombre("BBB");
                    nuevo.setPuntos(0);
                    nuevo.setIp(clientIp);
                    dao.append(nuevo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
}
            
            broadcast("STATE:ONLINE:" + clientIp);

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
            try {
                String ip1 = e1.getKey();
                String ip2 = e2.getKey();
                JugadoresDTO dto1 = dao.readByIp(ip1);
                JugadoresDTO dto2 = dao.readByIp(ip2);

                int puntos1 = dto1.getPuntos();
                int puntos2 = dto2.getPuntos();

                if (r1 == ResultadoJuego.GANASTE) {
                    dao.incrementarPuntoPorIp(ip1, puntos1);
                } else if (r2 == ResultadoJuego.GANASTE) {
                    dao.incrementarPuntoPorIp(ip2, puntos2);
                }


                for (ClientHandler c : clients) {
                    if (c.getClientIp().equals(ip1)) {
                        c.send(new MensajePuntos(puntos1, puntos2));
                    } else if (c.getClientIp().equals(ip2)) {
                        c.send(new MensajePuntos(puntos2, puntos1));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pendingMoves.clear();
        }
    }

    public void removeClient(ClientHandler c) {
        clients.remove(c);
        System.out.println("Client disconnected: " + c.getClientIp());
        broadcast("STATE:OFFLINE:");
    }

    public void notifyClientConnected(ClientHandler c) {
        System.out.println("Client " + c.getClientIp() + " connected");
        broadcast("STATE:ONLINE:");
    }

    public static void main(String[] args) throws Exception {
        new Server(9000).start();
    }
}

