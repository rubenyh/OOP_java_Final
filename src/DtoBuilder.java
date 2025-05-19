import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DtoBuilder {

    public static Map<String, String> buildDtos(String jdbcUrl, String user, String password){
        Map<String, String> dtos = new HashMap<>();
        try(Connection connection = DriverManager.getConnection(jdbcUrl, user, password)){
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables =
                metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            while(tables.next()){
                String tableName = tables.getString("TABLE_NAME");
                String dtoCode = buildDtoForTable(tableName, metaData);
                dtos.put(tableName, dtoCode);
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return dtos;
        }
    
    public static String buildDtoForTable(String tableName, DatabaseMetaData metaData) throws SQLException  {
        StringBuilder dtoCode = new StringBuilder();
        ResultSet columns = metaData.getColumns(null, null, tableName, null);
        String className = Character.toUpperCase(tableName.charAt(0)) + tableName.substring(1)
            + "Dto";
        dtoCode.append("public class").append(className).append(" {\n");
        while (columns.next()){
            String columnName = columns.getString("COLUMN_NAME");
            String columnType = columns.getString("TYPE_NAME");
            
            String javaType = mapSqlTypeToJava(columnType);
            dtoCode.append("    private ").append(javaType).append(" ").append(columnName).append(";\n");
            dtoCode.append("    public ").append(javaType).append(" get").append(capitalize(columnName))
                .append("(){return ").append(columnName).append("; }\n");
            dtoCode.append("    public void set").append(capitalize(columnName)).append("(").append(javaType)
                .append(" ").append(columnName).append(") {this.").append(columnName).append(" = ")
                .append(columnName).append(";}\n");
        }
        dtoCode.append("}\n");
        return dtoCode.toString();
    }

    public static String mapSqlTypeToJava(String sqlType){
        switch(sqlType.toUpperCase()){
            case "VARCHAR": case "CHAR": case "TEXT": return "String";
            case "INT": case "INTEGER": return "int";
            case "BIGINT": return "long";
            case "DOUBLE": return "float";
            case "FLOAT": return "float";
            case "BOOLEAN": return "boolean";
            case "DATE": return "java.sql.Date";
            case "TIMESTAMP": return "java.sql.TimeStamp";
            default: return "Object";
        }
    }
    
    public static String capitalize(String str){
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    public static void main(String[] args) {
        Map<String, String> dtos = buildDtos("jdbc:mysql://localhost:3306/dispositivo",
        "root", "password");
        dtos.forEach((table, dto) -> {
            System.out.println("DTO para tabla: " + table);
            System.out.println(dto);
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter(table+"DTO.java"));
                writer.write(dto);
                writer.close();
                }catch(IOException e){

            }
        });
    }
}
