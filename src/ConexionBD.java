import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConexionBD {
    protected Connection conexion;

    public ConexionBD() {
        //Parametros para configurar la conexion de la base de datos
        String usr = "root";
        String pwd = "root";
        String url = "jdbc:mysql://localhost:3306/movil";
        String driver = "com.mysql.jdbc.Driver";
        try {
            //Carga el driver
            Class.forName(driver);
            //Crea la conexion con la base de datos por medio del driver
            conexion = DriverManager.getConnection(url, usr, pwd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void cerrar(PreparedStatement ps) throws Exception {
        if (ps != null) {
            ps.close();
        }
    }

    protected void cerrar(ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }
    }
}