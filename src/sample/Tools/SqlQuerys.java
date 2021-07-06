package sample.Tools;

import com.mysql.jdbc.Connection;
import sample.admin.fan.FanModel;
import sample.admin.guruh.GroupModul;
import sample.admin.teacher.TeacherModul;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlQuerys {

    public SqlQuerys() {
        conn = MysqlConnection.conDb();
    }
    PreparedStatement prs;
    ResultSet rs;
    Connection conn;

    public List<TeacherModule> checkPassword(String login, String password) {
        List<TeacherModule> dataTeacher = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where name = ? and parol = ? ");
            prs.setString(1, login);
            prs.setString(2, password);
            rs = prs.executeQuery();
            while (rs.next()) {
                dataTeacher.add(new TeacherModule(rs.getString(2), rs.getString(5), rs.getString(6)));
            }
            prs.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataTeacher;
    }

    public List<FanModel> getAllFan() {
        List<FanModel> fanData = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.clientPrepareStatement("select * from fan");
            rs = prs.executeQuery();
            while (rs.next()) {
                fanData.add(new FanModel(rs.getString(1), rs.getString(2)));
            }
            prs.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fanData;
    }

    public List<TeacherModul> getAllTeacherForTable() {
        List<TeacherModul> techData = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select teacher.id, teacher.name, teacher.familya, fan.name,teacher.parol,teacher.amount from teacher inner join fan on teacher.fan_id=fan.id");
            rs = prs.executeQuery();
            while (rs.next()) {
                techData.add(new TeacherModul(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5),rs.getString(6)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return techData;
    }

    public void changeAdminPasssword(String par){
        String adm = "1";
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("update teacher set parol = ? where admin = ?");
            prs.setString(1,par);
            prs.setString(2,adm);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<TeacherModul> get(){
        String adm = "1";
        List<TeacherModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where admin = ?");
            prs.setString(1,adm);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new TeacherModul(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TeacherModul> searchTeacher(String detail) throws SQLException {
        List<TeacherModul> teacherData = new ArrayList<>();
        try (Connection conn = MysqlConnection.conDb()) {
            assert conn != null;
            prs = conn.prepareStatement("select teacher.id, teacher.name, teacher.familya, fan.name, teacher.parol, teacher.amount from teacher inner " +
                    " join fan on teacher.fan_id=fan.id where teacher.name like '%' ? '%' or teacher.familya like '%' ? '%' or fan.name like '%' ? '%' ");
            prs.setString(1, detail);
            prs.setString(2, detail);
            prs.setString(3, detail);
            rs = prs.executeQuery();
            while (rs.next()) {
                teacherData.add(new TeacherModul(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherData;
    }

    public List<TeacherModul> searchGroupTeacher(String fan_id){
        List<TeacherModul> listSpecialTeacher = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where fan_id = ?");
            prs.setString(1,fan_id);
            rs = prs.executeQuery();
            while (rs.next()){
                listSpecialTeacher.add(new TeacherModul(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            conn.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSpecialTeacher;
    }
    public List<TeacherModul> getTeacherId(String name,String familya){
        List<TeacherModul> teacherID = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where name = ? and familya = ?");
            prs.setString(1,name);
            prs.setString(2,familya);
            rs = prs.executeQuery();
            while (rs.next()){
                teacherID.add(new TeacherModul(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherID;
    }

    public void insertTeacher(String name, String familya, String fan_id, String password,String miqdor) {
        String admin = "0";
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("INSERT INTO teacher(name,familya,fan_id,parol,admin,amount) VALUES(?,?,?,?,?,?)");
            prs.setString(1, name);
            prs.setString(2, familya);
            prs.setString(3, fan_id);
            prs.setString(4, password);
            prs.setString(5, admin);
            prs.setString(6, miqdor);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeacher(String name, String familya, String fan_id,String amount, String id) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("update teacher set name = ?, familya = ?, fan_id = ?, amount = ? where id = ? ");
            prs.setString(1, name);
            prs.setString(2, familya);
            prs.setString(3, fan_id);
            prs.setString(4, amount);
            prs.setString(5, id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeacherPassword(String new_password, String id) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("UPDATE teacher SET parol = ? where id = ? ");
            prs.setString(1, new_password);
            prs.setString(2, id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int checkSameTeacher(String name, String familya) {
        int check = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where name = ? and  familya = ?");
            prs.setString(1, name);
            prs.setString(2, familya);
            rs = prs.executeQuery();
            while (rs.next()) {
                check++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public int checkSamePasswordTeacher(String password) {
        int count = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where parol = ?");
            prs.setString(1, password);
            rs = prs.executeQuery();
            while (rs.next()) {
                count++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int checkSameFan(String name){
        int count = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select *from fan where name = ?");
            rs = prs.executeQuery();
            while (rs.next()){
                count++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<FanModel> searchFan(String name) {
//        fanlarni qidirish uchun
        List<FanModel> data = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from fan where name like '%" + name + "%'");
            rs = prs.executeQuery();
            while (rs.next()) {
                data.add(new FanModel(rs.getString(1), rs.getString(2)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void insertFan(String name) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("insert into fan(name) values(?)");
            prs.setString(1, name);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFan(String id, String new_name) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("UPDATE fan SET name = ? WHERE id = ? ");
            prs.setString(1, new_name);
            prs.setString(2, id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //"select teacher.id, teacher.name, teacher.familya, fan.name,teacher.parol from teacher inner join fan on teacher.fan_id=fan.id");
    public List<GroupModul> getAllGroup() {
        List<GroupModul> dataGroup = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select guruh.id, guruh.guruh_name, fan.name, teacher.name, teacher.familya, guruh.narx from guruh " +
                    "inner join fan on guruh.fan_id=fan.id inner join teacher on guruh.teacher_id=teacher.id ");
            rs = prs.executeQuery();
            while (rs.next()) {
                dataGroup.add(new GroupModul(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) +
                        " " + rs.getString(5),rs.getString(6)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataGroup;
    }

    public List<GroupModul> searchGroup(String detail) {
        List<GroupModul> dataGroup = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select guruh.id, guruh.guruh_name, fan.name, teacher.name, teacher.familya, guruh.narx from guruh " +
            "inner join fan on guruh.fan_id=fan.id inner join teacher on guruh.teacher_id=teacher.id where " +
                    " guruh.guruh_name like '%' ? '%' or fan.name like '%,..p ' ? '%' or teacher.name like '%' ? '%' or guruh.narx like '%' ? '%'");
            prs.setString(1, detail);
            prs.setString(2, detail);
            prs.setString(3, detail);
            prs.setString(4, detail);
            rs = prs.executeQuery();
            while (rs.next()) {
                dataGroup.add(new GroupModul(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) +
                        " " + rs.getString(5),rs.getString(6)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataGroup;
    }
    public void insertGroup(String name,String fan_id,String teacher_id,String tolovMiqdori){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("insert into guruh(guruh_name,fan_id,teacher_id,narx) values(?,?,?,?)");
            prs.setString(1,name);
            prs.setString(2,fan_id);
            prs.setString(3,teacher_id);
            prs.setString(4,tolovMiqdori);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int checkSameGroup(String groupName,String teacherID){
        int count = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from guruh where guruh_name = ? and teacher_id = ?");
            prs.setString(1,groupName);
            prs.setString(2,teacherID);
            rs = prs.executeQuery();
            while (rs.next()){
                count++;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateGroupDirect(String newFan_ID,String teacherID){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("update guruh set fan_id = ? where teacher_id = ?");
            prs.setString(1,newFan_ID);
            prs.setString(2,teacherID);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateGroup(String g_name,String fan_id,String teacher_id,String narx,String g_id){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("update guruh set guruh_name = ?, fan_id = ?, teacher_id = ?, narx = ? where id = ?");
            prs.setString(1,g_name);
            prs.setString(2,fan_id);
            prs.setString(3,teacher_id);
            prs.setString(4,narx);
            prs.setString(5,g_id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int checkSameGroup(String g_name,String fan_id,String teacher_id){
        int countGroup = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from guruh where guruh_name = ? and fan_id = ? and teacher_id = ?");
            prs.setString(1,g_name);
            prs.setString(2,fan_id);
            prs.setString(3,teacher_id);
            rs = prs.executeQuery();
            while (rs.next()){
                countGroup++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countGroup;
    }

}
