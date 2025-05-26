package database;
import java.io.Serializable;

public class JugadoresDTO implements Serializable{
    private int id;
    private String nombre;
    private int puntos;
    private String ip;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getPuntos() {
        return puntos;
    }
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Nombre: ").append(nombre);
        return sb.toString();
    }
}