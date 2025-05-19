import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private String hostIp;
    private String clientIp;
    private Juego juego;

    public Cliente(String hostIp, String clientIp, String juegoTitulo) throws Exception {
        this.hostIp = hostIp;
        this.clientIp = clientIp;
        this.juego = new Juego(juegoTitulo);
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
