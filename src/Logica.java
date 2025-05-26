import movimientos.Movimiento;

public class Logica {
    public static ResultadoJuego jugar(Movimiento jugador1, Movimiento jugador2) {
        if (jugador1.ganaContra(jugador2)) {
            return ResultadoJuego.GANASTE;
        } else if (jugador2.ganaContra(jugador1)) {
            return ResultadoJuego.PERDISTE;
        } else {
            return ResultadoJuego.EMPATE;
        }
    }
}
