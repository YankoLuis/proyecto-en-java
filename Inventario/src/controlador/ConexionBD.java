
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    Connection con;
    String driver = "com.mysql.jdbc.Driver";
    String dbName = "productos";
    String url = "jdbc:mysql://localhost:3306/"+dbName+"?useSSL=false&serverTimezone=UTC"; // Url de base de datos
    String usuario = "root"; // Variable para usuario de la base de datos
    String password = ""; // Variable para usuario de la base de datos 
    
     public Connection conectarBaseDatos() {
       try{
           Class.forName(driver);
           con = (Connection) DriverManager.getConnection(url, usuario, password);
           System.out.println("Conexión Correcta");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error en la conexión:" + e);
        }
        return con;
    }
}

