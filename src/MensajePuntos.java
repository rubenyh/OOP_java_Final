import java.io.Serializable;

public class MensajePuntos implements Serializable {
    private final int puntos;
    private final int puntosOponente;

    public MensajePuntos(int puntos, int puntosOponente) {
        this.puntos      = puntos;
        this.puntosOponente  = puntosOponente;
    }
    public int getPuntos() { 
        return puntos; 
    }
    public int getPuntosOponente() { 
        return puntosOponente;
    }
}
