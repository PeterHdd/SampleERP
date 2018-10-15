package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connections {
    public static Connection connection;

    public static Connection getConnections() {
        String user="peter";
        String password="admin";
        String dbName = "erp";
        try {
            connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}