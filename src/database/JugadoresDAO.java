package database;
import java.sql.*;
import java.util.*;

@SuppressWarnings("unused")
public class JugadoresDAO extends ConexionBD {
    private static final String NOMBRE = "nombre";
    private static final String PUNTOS = "puntos"; //Se debe de cambiar a int
    private static final String IP = "ip";

    private static final String SQL_SELECT_ALL = "SELECT * FROM jugadores";

    private static final String SQL_INSERT = "INSERT INTO jugadores" +
            "(" + NOMBRE +
            "," + PUNTOS +
            "," + IP +
            ") VALUES (?,?,?)";

    private static final String SQL_READ = "SELECT * FROM jugadores WHERE " +
            IP + " = ?";

    private static final String SQL_DELETE = "DELETE FROM jugadores WHERE " +
            IP + " = ?";

    private static final String SQL_UPDATE = "UPDATE jugadores SET " +
            NOMBRE + " = ?," +
            PUNTOS + " = ? " +
            "WHERE " + IP + " = ?";

    public JugadoresDAO() {
        super();
    }

    public List<JugadoresDTO> readAll() throws SQLException {
    List<JugadoresDTO> result = new ArrayList<>();
    String sql = "SELECT * FROM jugadores";
    try (PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            JugadoresDTO dto = new JugadoresDTO();
            dto.setId(rs.getInt("id"));
            dto.setNombre(rs.getString("nombre"));
            dto.setPuntos(rs.getInt("puntos"));
            dto.setIp(rs.getString("ip"));
            result.add(dto);
        }
    }
    return result;
    }

    public void append(JugadoresDTO dto) throws Exception {
        PreparedStatement ps = null;
        // Objeto sobre el cual se almacena la consulta SQL previamente creada
        ps = conexion.prepareStatement(SQL_INSERT);
        // ps.setString sustituye cada uno de los símbolos de interrogación en la sentencia SQL por los valores deseados
        ps.setString(1, dto.getNombre());
        ps.setInt(2, dto.getPuntos());
        ps.setString(3, dto.getIp());
        // Ejecuta la actualización
        ps.executeUpdate();
        cerrar(ps);
    }

    public void update(JugadoresDTO dto) throws Exception {
        PreparedStatement ps = null;
        ps = conexion.prepareStatement(SQL_UPDATE);
        ps.setString(1, dto.getNombre());
        ps.setInt(2, dto.getPuntos());
        ps.setString(3, dto.getIp());
        ps.executeUpdate();
        cerrar(ps);
    }

    public void delete(JugadoresDTO dto) throws Exception {
        PreparedStatement ps = null;
        ps = conexion.prepareStatement(SQL_DELETE);
        ps.setString(1, dto.getIp());
        ps.executeUpdate();
        cerrar(ps);
    }
}