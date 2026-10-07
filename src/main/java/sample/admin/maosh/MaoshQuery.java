package sample.admin.maosh;

import com.mysql.jdbc.Connection;
import sample.Tools.MysqlConnection;
import sample.admin.teacher.TeacherModul;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaoshQuery {

        PreparedStatement prs;
        ResultSet rs;
        Connection conn;

    public MaoshQuery() {
        conn = MysqlConnection.conDb();
    }

    public List<MaoshModul> getAllTolovs(String status){
        List<MaoshModul> list = new ArrayList<>();
        int tr = 1;
        if (status.equals("0")){
            conn = MysqlConnection.conDb();
            assert conn != null;
        try {
            prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                    " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                    " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                    " inner join guruh on tolov.guruh_id=guruh.id");
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                        ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                        ,rs.getString(8),rs.getString(9)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        } else {
            conn = MysqlConnection.conDb();
            assert conn != null;
            try {
                prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                        " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                        " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                        " inner join guruh on tolov.guruh_id=guruh.id where tolov.status = ?");
                prs.setString(1,status);
                rs = prs.executeQuery();
                while (rs.next()){
                    list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                            ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                            ,rs.getString(8),rs.getString(9)));
                    tr++;
                }
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public List<MaoshModul> searchPuiple(String fish,String status){
        int tr = 1;
        List<MaoshModul> list = new ArrayList<>();
        if (status.equals("0")){
            conn = MysqlConnection.conDb();
            assert conn != null;
        try {
            prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                    " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                    " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                    " inner join guruh on tolov.guruh_id=guruh.id where oquvchilar.fish like '%' ? '%'");
            prs.setString(1,fish);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                        ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                        ,rs.getString(8),rs.getString(9)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        } else {
            conn = MysqlConnection.conDb();
            assert conn != null;
            try {
                prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                        " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                        " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                        " inner join guruh on tolov.guruh_id=guruh.id where oquvchilar.fish like '%' ? '%' and tolov.status = ? ");
                prs.setString(1,fish);
                prs.setString(2,status);
                rs = prs.executeQuery();
                while (rs.next()){
                    list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                            ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                            ,rs.getString(8),rs.getString(9)));
                    tr++;
                }
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<MaoshModul> sortFromFan(String fan,String status){
        List<MaoshModul> list = new ArrayList<>();
        int tr = 1;
        if (status.equals("0")){
            conn = MysqlConnection.conDb();
            assert conn != null;
        try {
            prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                    " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                    " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                    " inner join guruh on tolov.guruh_id=guruh.id where fan.name = ?");
            prs.setString(1,fan);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                        ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                        ,rs.getString(8),rs.getString(9)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        } else {
            conn = MysqlConnection.conDb();
            assert conn != null;
            try {
                prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                        " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                        " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                        " inner join guruh on tolov.guruh_id=guruh.id where fan.name = ? and tolov.status = ?");
                prs.setString(1,fan);
                prs.setString(2,status);
                rs = prs.executeQuery();
                while (rs.next()){
                    list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                            ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                            ,rs.getString(8),rs.getString(9)));
                    tr++;
                }
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<MaoshModul> sortFromFanAndTeacher(String fan,String teacherN,String teacherF,String status){
        List<MaoshModul> list = new ArrayList<>();
        int tr = 1;
        if (status.equals("0")){
            conn = MysqlConnection.conDb();
            assert conn != null;
            try {
                prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                        " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                        " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                        " inner join guruh on tolov.guruh_id=guruh.id where fan.name = ? and teacher.name = ? and teacher.familya = ?");
                prs.setString(1,fan);
                prs.setString(2,teacherN);
                prs.setString(3,teacherF);
                rs = prs.executeQuery();
                while (rs.next()){
                    list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                            ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                            ,rs.getString(8),rs.getString(9)));
                    tr++;
                }
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            conn = MysqlConnection.conDb();
            assert conn != null;
            try {
                prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                        " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                        " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                        " inner join guruh on tolov.guruh_id=guruh.id where fan.name = ? and teacher.name = ? and teacher.familya = ? and tolov.status = ?");
                prs.setString(1,fan);
                prs.setString(2,teacherN);
                prs.setString(3,teacherF);
                prs.setString(4,status);
                rs = prs.executeQuery();
                while (rs.next()){
                    list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                            ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                            ,rs.getString(8),rs.getString(9)));
                    tr++;
                }
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<MaoshModul> sortFromFanTeacherAndGroup(String fan,String teacherN,String teacherF,String group,String status){
        List<MaoshModul> list = new ArrayList<>();
        int tr = 1;
        if (status.equals("0")){
            conn = MysqlConnection.conDb();
            assert conn != null;
            try {
                prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                        " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                        " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                        " inner join guruh on tolov.guruh_id=guruh.id where fan.name = ? and teacher.name = ? and teacher.familya = ? and guruh.guruh_name = ?");
                prs.setString(1,fan);
                prs.setString(2,teacherN);
                prs.setString(3,teacherF);
                prs.setString(4,group);
                rs = prs.executeQuery();
                while (rs.next()){
                    list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                            ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                            ,rs.getString(8),rs.getString(9)));
                    tr++;
                }
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            conn = MysqlConnection.conDb();
            assert conn != null;
            try {
                prs = conn.prepareStatement("select tolov.id,oquvchilar.fish,fan.name,teacher.name,teacher.familya," +
                        " guruh.guruh_name,tolov.tolov,tolov.sana,tolov.status from tolov inner join  oquvchilar on tolov.oquvchi_id=oquvchilar.id" +
                        " inner join fan on tolov.fan_id=fan.id inner join teacher on tolov.teacher_id=teacher.id" +
                        " inner join guruh on tolov.guruh_id=guruh.id where fan.name = ? and teacher.name = ? and teacher.familya = ?" +
                        " and guruh.guruh_name = ? and tolov.status = ?");
                prs.setString(1,fan);
                prs.setString(2,teacherN);
                prs.setString(3,teacherF);
                prs.setString(4,group);
                prs.setString(5,status);
                rs = prs.executeQuery();
                while (rs.next()){
                    list.add(new MaoshModul(rs.getString(1),tr,rs.getString(2),rs.getString(3)
                            ,rs.getString(4) + " " + rs.getString(5),rs.getString(6),rs.getString(7)
                            ,rs.getString(8),rs.getString(9)));
                    tr++;
                }
                conn.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<TeacherModul> getTeacherAmount(String teacherID){
        List<TeacherModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from teacher where id = ?");
            prs.setString(1,teacherID);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new TeacherModul(rs.getString(1),rs.getString(2),rs.getString(3)
                ,rs.getString(4),rs.getString(5),rs.getString(7)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void maoshBerish(String teacherID){
        String status = "0";
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
//            UPDATE oquvchilar SET fish = ? , phone_number = ? WHERE id = ?
            prs = conn.prepareStatement("update tolov set status = ? where teacher_id = ?");
            prs.setString(1,status);
            prs.setString(2,teacherID);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTolovHistory(String teacherID, String totalSumma, String miqdor, String olganSumma,String sana){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("insert into maosh_history(teacher_id,total_summa,miqdor,olgan_summa,sana)" +
                    " values(?,?,?,?,?)");
            prs.setString(1,teacherID);
            prs.setString(2,totalSumma);
            prs.setString(3,miqdor);
            prs.setString(4,olganSumma);
            prs.setString(5,sana);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
