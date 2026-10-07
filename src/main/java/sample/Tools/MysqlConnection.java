package sample.Tools;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    private static final String DB_URL = envOrDefault("DB_URL", "jdbc:mysql://localhost/ideal");
    private static final String DB_USER = envOrDefault("DB_USER", "root");
    private static final String DB_PASSWORD = envOrDefault("DB_PASSWORD", "root");

    public static Connection conDb(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return con;
        }
        catch (ClassNotFoundException  | SQLException exception) {
            return null;
        }
    }

    private static String envOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value != null ? value : defaultValue;
    }
}
