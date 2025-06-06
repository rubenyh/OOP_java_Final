package movimientos;
public class Spock implements Movimiento {
    @Override
    public String getNombre() {
        return "Spock";
    }

    @Override
    public boolean ganaContra(Movimiento otro) {
        return otro.getNombre().equals("Tijera") || otro.getNombre().equals("Piedra");
    }

    @Override
    public String getRutaImagen() {
        return "img/spock.png";
    }
}
