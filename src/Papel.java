public class Papel implements Movimiento {
    @Override
    public String getNombre() {
        return "Papel";
    }

    @Override
    public boolean ganaContra(Movimiento otro) {
        return otro.getNombre().equals("Piedra") || otro.getNombre().equals("Spock");
    }

}
