
import java.io.Serializable;

public class DispositivoDTO implements Serializable{
    private String usuario;
    private String direccionMac;
    private String estado;
    private String nombreDisp;
    private String tipo;

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getDireccionMac() {
        return direccionMac;
    }
    public void setDireccionMac(String direccionMac) {
        this.direccionMac = direccionMac;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNombreDisp() {
        return nombreDisp;
    }
    public void setNombreDisp(String nombreDisp) {
        this.nombreDisp = nombreDisp;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Nombre: ").append(usuario);
        return sb.toString();
    }
}