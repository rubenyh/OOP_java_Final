//Este archivo es unicamente para probar que la base de datos funcione, debe de salir todo OK
//en caso de que esté todo correcto
//25-mayo todo dio OK

import java.util.List;

import database.JugadoresDAO;
import database.JugadoresDTO;

public class TestJugadoresDAO {
    public static void main(String[] args) {
        try {
            JugadoresDAO dao = new JugadoresDAO();

            JugadoresDTO nuevo = new JugadoresDTO();
            nuevo.setNombre("Prueba");
            nuevo.setPuntos(42);
            nuevo.setIp("192.168.0.1");
            dao.append(nuevo);
            System.out.println("→ Insertado OK");

            List<JugadoresDTO> todos = dao.readAll();
            System.out.println("→ Total registros: " + todos.size());
            todos.forEach(j -> System.out.println(j.getId() + ": " + j.getNombre()));

            JugadoresDTO ultimo = todos.get(todos.size() - 1);
            ultimo.setPuntos(99);
            dao.update(ultimo);
            System.out.println("→ Actualizado OK (ID=" + ultimo.getId() + ")");


            dao.delete(ultimo);
            System.out.println("→ Borrado OK (ID=" + ultimo.getId() + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
