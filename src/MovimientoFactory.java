import movimientos.*;

public class MovimientoFactory {
    public static Movimiento create(String nombre) {
        return switch (nombre) {
            case "Piedra" -> new Piedra();
            case "Papel"  -> new Papel();
            case "Tijera" -> new Tijera();
            case "Lagarto"-> new Lagarto();
            case "Spock"  -> new Spock();
            default -> throw new IllegalArgumentException("Movimiento desconocido: " + nombre);
        };
    }
}

