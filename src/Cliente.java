import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private String hostIp;
    private int port;
    private Juego juego;

    public Cliente(String hostIp, int port, String juegoTitulo) throws Exception {
        this.hostIp = hostIp;
        this.port = port;
        Socket socket = new Socket(hostIp, port);
        this.juego = new Juego(juegoTitulo);
    }


    public void connectServer(String hostIp){
        
    }
    public void enviar(int puerto, String mensaje) {
        try (Socket cliente = new Socket(hostIp, puerto);
             ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream())) {
            oos.writeObject(mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
