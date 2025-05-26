package movimientos;
public interface Movimiento {
    String getNombre();

    boolean ganaContra(Movimiento otro);

    String getRutaImagen();
}
