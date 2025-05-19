public class Main {
    public static void main(String[] args) throws Exception {
      
      
        Juego juego1 = new Juego("juego1", 9000, "127.0.0.1", 9000);

        Juego juego2 = new Juego("juego2", 9001, "127.0.0.1", 9000);

        juego1.setVisible(true);
        juego2.setVisible(true);
    }
}
