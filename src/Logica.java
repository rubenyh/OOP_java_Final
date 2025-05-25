import movimientos.Movimiento;

public class Logica {

    public static void jugar(Movimiento jugador1, Movimiento jugador2) {
        if (jugador1.ganaContra(jugador2)) {
            System.out.println("Gana: " + jugador1.getNombre());
        } else if (jugador2.ganaContra(jugador1)) {
            System.out.println("Gana: " + jugador2.getNombre());
        } else {
            System.out.println("Empate");
        }
    }
}
