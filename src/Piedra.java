public class Piedra implements Movimiento {
    @Override
    public String getNombre() {
        return "Piedra";
    }

    @Override
    public boolean ganaContra(Movimiento otro) {
        return otro.getNombre().equals("Tijera") || otro.getNombre().equals("Lagarto");
    }

    @Override
    public String getRutaImagen() {
        return "img/rock.png";
    }
}
