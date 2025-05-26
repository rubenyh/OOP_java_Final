import java.io.Serializable;

public class MensajeMovimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String movimiento;

    public MensajeMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getMovimiento() {
        return movimiento;
    }

    @Override
    public String toString() {
        return movimiento;
    }
}
