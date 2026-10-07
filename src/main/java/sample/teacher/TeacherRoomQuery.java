package sample.teacher;

import com.mysql.jdbc.Connection;
import sample.Tools.MysqlConnection;
import sample.admin.guruh.GroupModul;
import sample.admin.teacher.TeacherModul;
import sample.admin.oquvchi.OquvchiModul;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherRoomQuery {
    PreparedStatement prs;
    ResultSet rs;
    Connection conn;

    public TeacherRoomQuery() {
        conn = MysqlConnection.conDb();
    }

    public List<TeacherRoomModul> getAllPuiple(String teacherID){
        int tr = 1;
        List<TeacherRoomModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_guruh.id, oquvchilar.fish, guruh.guruh_name, oquvchi_guruh.qarz," +
                    " oquvchi_guruh.qayd_sanasi from oquvchi_guruh inner join oquvchilar on oquvchi_guruh.oquvchi_id=oquvchilar.id" +
                    " inner join guruh on oquvchi_guruh.guruh_id=guruh.id where oquvchi_guruh.teacher_id = ?");
            prs.setString(1,teacherID);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new TeacherRoomModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                ,rs.getString(4),rs.getString(5)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<TeacherRoomModul> searchPuiple(String teacherID,String fish){
        int tr = 1;
        List<TeacherRoomModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_guruh.id, oquvchilar.fish, guruh.guruh_name, oquvchi_guruh.qarz," +
                    " oquvchi_guruh.qayd_sanasi from oquvchi_guruh inner join oquvchilar on oquvchi_guruh.oquvchi_id=oquvchilar.id" +
                    " inner join guruh on oquvchi_guruh.guruh_id=guruh.id where oquvchi_guruh.teacher_id = ? and oquvchilar.fish like '%' ? '%' ");
            prs.setString(1,teacherID);
            prs.setString(2,fish);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new TeacherRoomModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TeacherModul> getTeacherID(String password){
        List<TeacherModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where parol = ?");
            prs.setString(1,password);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new TeacherModul(rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<GroupModul> getGuruh(String teacherID){
        List<GroupModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from guruh where teacher_id = ?");
            prs.setString(1,teacherID);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new GroupModul(rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<GroupModul> getGuruhID(String teacherID, String guruhName){
        List<GroupModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from guruh where teacher_id = ? and guruh_name = ?");
            prs.setString(1,teacherID);
            prs.setString(2,guruhName);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new GroupModul(rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TeacherRoomModul> sotrGuruh(String teacherID,String guruh){
        int tr = 1;
        List<TeacherRoomModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_guruh.id, oquvchilar.fish, guruh.guruh_name, oquvchi_guruh.qarz," +
                    " oquvchi_guruh.qayd_sanasi from oquvchi_guruh inner join oquvchilar on oquvchi_guruh.oquvchi_id=oquvchilar.id" +
                    " inner join guruh on oquvchi_guruh.guruh_id=guruh.id where oquvchi_guruh.teacher_id = ? and guruh.guruh_name = ?");
            prs.setString(1,teacherID);
            prs.setString(2,guruh);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new TeacherRoomModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<OquvchiModul> getOquvchi(String fish){
        List<OquvchiModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchilar where fish = ?");
            prs.setString(1,fish);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new OquvchiModul(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
