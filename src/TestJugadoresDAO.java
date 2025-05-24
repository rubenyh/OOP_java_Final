//Este archivo es unicamente para probar que la base de datos funcione, debe de salir todo OK
//en caso de que esté todo correcto
//24-mayo todo dio OK

import java.util.List;

public class TestJugadoresDAO {
    public static void main(String[] args) {
        try {
            JugadoresDAO dao = new JugadoresDAO();

            // 1) Inserción
            JugadoresDTO nuevo = new JugadoresDTO();
            nuevo.setNombre("Prueba");
            nuevo.setPuntos(42);
            nuevo.setIp("192.168.0.1");
            dao.append(nuevo);
            System.out.println("→ Insertado OK");

            // 2) Lectura de todos
            List<JugadoresDTO> todos = dao.readAll();
            System.out.println("→ Total registros: " + todos.size());
            todos.forEach(j -> System.out.println(j.getId() + ": " + j.getNombre()));

            // 3) Actualización (al último ID)
            JugadoresDTO ultimo = todos.get(todos.size() - 1);
            ultimo.setPuntos(99);
            dao.update(ultimo);
            System.out.println("→ Actualizado OK (ID=" + ultimo.getId() + ")");

            // 4) Lectura por IP
            // (si hubieras implementado un método readByIp)
            // JugadoresDTO lee = dao.readByIp("192.168.0.1");
            // System.out.println("→ Leído por IP: " + lee);

            // 5) Borrado
            dao.delete(ultimo);
            System.out.println("→ Borrado OK (ID=" + ultimo.getId() + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
