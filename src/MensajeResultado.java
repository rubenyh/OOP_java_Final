
import java.io.Serializable;

public class MensajeResultado implements Serializable {
    private final String tuMovimiento;
    private final String suMovimiento;
    private final ResultadoJuego resultadoParaTi;

    public MensajeResultado(String tu, String su, ResultadoJuego res) {
        this.tuMovimiento = tu;
        this.suMovimiento = su;
        this.resultadoParaTi = res;
    }

    public String getTuMovimiento() {
        return tuMovimiento;
    }

    public String getSuMovimiento() {
        return suMovimiento;
    }
    public ResultadoJuego getResultadoParaTi() {
        return resultadoParaTi;
    }
    
}
