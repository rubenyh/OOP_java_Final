public class Lagarto implements Movimiento {
    @Override
    public String getNombre() {
        return "Lagarto";
    }

    @Override
    public boolean ganaContra(Movimiento otro) {
        return otro.getNombre().equals("Papel") || otro.getNombre().equals("Spock");
    }

    @Override
    public String getRutaImagen() {
        return "img/lizzard.png";
    }
}
