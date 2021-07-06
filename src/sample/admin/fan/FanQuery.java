package sample.admin.fan;

import com.mysql.jdbc.Connection;
import sample.Tools.MysqlConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FanQuery {

    public FanQuery() {
        conn = MysqlConnection.conDb();
    }

    PreparedStatement prs;
    ResultSet rs;
    Connection conn;

    public int CheckFanOfTeacher(String id) {
        int count = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where fan_id = ?");
            prs.setString(1, id);
            rs = prs.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void deleteFan(String id) {
        try {
            conn = MysqlConnection.conDb();
            assert conn != null;
            prs = conn.prepareStatement("delete from fan where id = ?");
            prs.setString(1, id);
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
