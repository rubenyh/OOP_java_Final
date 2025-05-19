public class Tijera implements Movimiento {
    @Override
    public String getNombre() {
        return "Tijera";
    }

    @Override
    public boolean ganaContra(Movimiento otro) {
        return otro.getNombre().equals("Papel") || otro.getNombre().equals("Lagarto");
    }
}
