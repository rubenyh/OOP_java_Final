
import java.sql.*;
import java.util.*;

@SuppressWarnings("unused")
public class DispositivoDAO extends ConexionBD {
    private static final String DIRECCION_MAC = "direccion_mac";
    private static final String USUARIO = "usuario";
    private static final String ESTADO = "estado";
    private static final String NOMBRE_DISP = "nombre_disp";
    private static final String TIPO = "tipo";

    private static final String SQL_SELECT_ALL = "SELECT * FROM DISPOSITIVO";

    private static final String SQL_INSERT = "INSERT INTO DISPOSITIVO" +
            "(" + DIRECCION_MAC +
            "," + USUARIO +
            "," + ESTADO +
            "," + NOMBRE_DISP +
            "," + TIPO +
            ") VALUES (?,?,?,?,?)";

    private static final String SQL_READ = "SELECT * FROM DISPOSITIVO WHERE " +
            DIRECCION_MAC + " = ?";

    private static final String SQL_DELETE = "DELETE FROM DISPOSITIVO WHERE " +
            DIRECCION_MAC + " = ?";

    private static final String SQL_UPDATE = "UPDATE DISPOSITIVO SET " +
            USUARIO + " = ?," +
            ESTADO + " = ?," +
            NOMBRE_DISP + " = ?," +
            TIPO + " = ? " +
            "WHERE " + DIRECCION_MAC + " = ?";

    public DispositivoDAO() {
        super();
    }

    public List readAll() throws Exception {
        // Encargado de almacenar el resultado de la consulta a la base de datos
        ResultSet rs = null;
        List result = new ArrayList();
        // Coloca la sentencia SQL en el PreparedStatement
        PreparedStatement ps = conexion.prepareStatement(SQL_SELECT_ALL);
        // Ejecuta la consulta en la base de datos, y almacena el resultado en el ResultSet
        rs = ps.executeQuery();
        while (rs.next()) {
            // Obtiene uno a uno los objetos del rs para almacenarlos en la lista que será regresada
            result.add(rs.getObject(String.valueOf(rs)));
        }
        // Cierra los flujos de datos
        cerrar(ps);
        cerrar(rs);
        return result;
    }

    public void append(DispositivoDTO dto) throws Exception {
        PreparedStatement ps = null;
        // Objeto sobre el cual se almacena la consulta SQL previamente creada
        ps = conexion.prepareStatement(SQL_INSERT);
        // ps.setString sustituye cada uno de los símbolos de interrogación en la sentencia SQL por los valores deseados
        ps.setString(1, dto.getDireccionMac());
        ps.setString(2, dto.getUsuario());
        ps.setString(3, dto.getEstado());
        ps.setString(4, dto.getNombreDisp());
        ps.setString(5, dto.getTipo());
        // Ejecuta la actualización
        ps.executeUpdate();
        cerrar(ps);
    }

    public void update(DispositivoDTO dto) throws Exception {
        PreparedStatement ps = null;
        ps = conexion.prepareStatement(SQL_UPDATE);
        ps.setString(1, dto.getUsuario());
        ps.setString(2, dto.getEstado());
        ps.setString(3, dto.getNombreDisp());
        ps.setString(4, dto.getTipo());
        ps.setString(5, dto.getDireccionMac());
        ps.executeUpdate();
        cerrar(ps);
    }

    public void delete(DispositivoDTO dto) throws Exception {
        PreparedStatement ps = null;
        ps = conexion.prepareStatement(SQL_DELETE);
        ps.setString(1, dto.getDireccionMac());
        ps.executeUpdate();
        cerrar(ps);
    }
}