package sample.admin.tolov;

import com.mysql.jdbc.Connection;
import javafx.fxml.Initializable;
import sample.Tools.MysqlConnection;
import sample.admin.oquvchi.OquvGuruhModul;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TolovQuery {
    PreparedStatement prs;
    ResultSet rs;
    Connection conn;

    public TolovQuery() {
        conn = MysqlConnection.conDb();
    }

    public List<OquvGuruhModul> searchOquvchiFanTeacher(String detail) {
        List<OquvGuruhModul> list = new ArrayList<>();
        int count = 1;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_guruh.id, oquvchilar.fish, fan.name, teacher.name,teacher.familya," +
                    " guruh.guruh_name, oquvchi_guruh.qarz,oquvchi_guruh.chegirma, oquvchi_guruh.qayd_sanasi, oquvchi_guruh.update_date from oquvchi_guruh " +
                    "inner join oquvchilar on oquvchi_guruh.oquvchi_id=oquvchilar.id inner join fan on " +
                    "oquvchi_guruh.fan_id=fan.id inner join teacher on oquvchi_guruh.teacher_id=teacher.id inner join guruh on " +
                    "oquvchi_guruh.guruh_id=guruh.id where oquvchilar.fish like ? '%' or fan.name like ? '%' or teacher.name like ? '%' or teacher.familya like ? '%'");
            prs.setString(1, detail);
            prs.setString(2, detail);
            prs.setString(3, detail);
            prs.setString(4, detail);
            rs = prs.executeQuery();
            while (rs.next()) {
                list.add(new OquvGuruhModul(rs.getString(1), (count), rs.getString(2), rs.getString(3),
                        rs.getString(4) + " " + rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10)));
                count++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OquvGuruhModul> getSpecialGroup0(String fan, String teacherName, String teacherFamilya) {
        List<OquvGuruhModul> list = new ArrayList<>();
        int count = 1;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_guruh.id, oquvchilar.fish, fan.name, teacher.name,teacher.familya," +
                    " guruh.guruh_name, oquvchi_guruh.qarz,oquvchi_guruh.chegirma, oquvchi_guruh.qayd_sanasi, oquvchi_guruh.update_date from oquvchi_guruh " +
                    "inner join oquvchilar on oquvchi_guruh.oquvchi_id=oquvchilar.id inner join fan on " +
                    "oquvchi_guruh.fan_id=fan.id inner join teacher on oquvchi_guruh.teacher_id=teacher.id inner join guruh on " +
                    "oquvchi_guruh.guruh_id=guruh.id where fan.name = ? and teacher.name = ? and teacher.familya = ?");
            prs.setString(1, fan);
            prs.setString(2, teacherName);
            prs.setString(3, teacherFamilya);
            rs = prs.executeQuery();
            while (rs.next()) {
                list.add(new OquvGuruhModul(rs.getString(1), (count), rs.getString(2), rs.getString(3),
                        rs.getString(4) + " " + rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10)));
                count++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OquvGuruhModul> getSpecialGroup(String fan, String teacherName, String teacherFamilya, String group) {
        List<OquvGuruhModul> list = new ArrayList<>();
        int count = 1;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_guruh.id, oquvchilar.fish, fan.name, teacher.name,teacher.familya," +
                    " guruh.guruh_name, oquvchi_guruh.qarz,oquvchi_guruh.chegirma, oquvchi_guruh.qayd_sanasi,oquvchi_guruh.update_date from oquvchi_guruh " +
                    "inner join oquvchilar on oquvchi_guruh.oquvchi_id=oquvchilar.id inner join fan on " +
                    "oquvchi_guruh.fan_id=fan.id inner join teacher on oquvchi_guruh.teacher_id=teacher.id inner join guruh on " +
                    "oquvchi_guruh.guruh_id=guruh.id where fan.name = ? and teacher.name = ? and teacher.familya = ? and guruh.guruh_name = ?");
            prs.setString(1, fan);
            prs.setString(2, teacherName);
            prs.setString(3, teacherFamilya);
            prs.setString(4, group);
            rs = prs.executeQuery();
            while (rs.next()) {
                list.add(new OquvGuruhModul(rs.getString(1), (count), rs.getString(2), rs.getString(3),
                        rs.getString(4) + " " + rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10)));
                count++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertTolov(String oquvchi_ID, String fan_ID, String teacher_ID, String guruh_ID, String tolov, String sana) {
        String status = "1";
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("insert into tolov(oquvchi_id,fan_id,teacher_id,guruh_id,tolov,sana,status) values(?,?,?,?,?,?,?)");
            prs.setString(1, oquvchi_ID);
            prs.setString(2, fan_ID);
            prs.setString(3, teacher_ID);
            prs.setString(4, guruh_ID);
            prs.setString(5, tolov);
            prs.setString(6, sana);
            prs.setString(7, status);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOquvchiQarzFromOquvchiGuruh(String qarz, String id) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("update oquvchi_guruh set qarz = ? where id = ?");
            prs.setString(1, qarz);
            prs.setString(2, id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TolovModul> getTolovHistory(String oquvchiID, String guruhID) {
        List<TolovModul> list = new ArrayList<>();
        int tr = 1;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                    "guruh.guruh_name,tolov.tolov,tolov.sana from tolov inner join oquvchilar on tolov.oquvchi_id=oquvchilar.id " +
                    "inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id " +
                    "inner join guruh on tolov.guruh_id=guruh.id where oquvchi_id = ? and guruh_id = ?");
            prs.setString(1, oquvchiID);
            prs.setString(2, guruhID);
            rs = prs.executeQuery();
            while (rs.next()) {
                list.add(new TolovModul(rs.getString(1), tr, rs.getString(2), rs.getString(3), rs.getString(4) +
                        " " + rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateTolov(String newAmount, String ID) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("update tolov set tolov = ? where id = ?");
            prs.setString(1, newAmount);
            prs.setString(2, ID);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
