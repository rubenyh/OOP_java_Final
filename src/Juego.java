import javax.swing.*;

public class Juego extends JFrame implements Mensaje {

    public Juego(String title) throws Exception {
        super(title);
        JPanel panel = new JPanel();
        add(panel);
        setSize(800, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void mensaje_entrante(Object msj) {

    }
}
