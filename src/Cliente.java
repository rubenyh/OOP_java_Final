import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

    public void enviar(String ip, int puerto, String mensaje){


        try (Socket cliente = new Socket(ip, puerto);
        
             ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream())) {
            oos.writeObject(mensaje);
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
    }
}
