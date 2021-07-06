package sample.Tools;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    public static Connection conDb(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/ideal","root","root");
            return con;
        }
        catch (ClassNotFoundException  | SQLException exception) {
            return null;
        }
    }
}
