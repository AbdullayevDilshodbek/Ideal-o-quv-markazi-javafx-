package sample.admin.davomad;

import com.mysql.jdbc.Connection;
import sample.Tools.MysqlConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDavomadQuery {
    PreparedStatement prs;
    ResultSet rs;
    Connection conn;

    public List<AdminDavomadModul> getAllDavomad() throws SQLException {
        List<AdminDavomadModul> list = new ArrayList<>();
        int tr = 1;
        String status = "";
        Connection conn = MysqlConnection.conDb();
        try {
            assert conn != null;
            prs = conn.prepareStatement("select davomad.id, oquvchilar.fish, teacher.name, teacher.familya, guruh.guruh_name, davomad.sana, davomad.status" +
                    " from davomad inner join oquvchilar on davomad.oquvchi_id=oquvchilar.id inner join teacher on davomad.teacher_id=teacher.id" +
                    " inner join guruh on davomad.guruh_id=guruh.id");
            rs = prs.executeQuery();
            while (rs.next()){
                if (rs.getString(7).equals("1")){
                    status = "+";
                } else {
                    status = "---";
                }
                list.add(new AdminDavomadModul(rs.getString(1),tr,rs.getString(2)
                ,rs.getString(3) + " " + rs.getString(4),rs.getString(5)
                ,rs.getString(6),status));
                tr++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
        rs.close();
        return list;
    }


}
