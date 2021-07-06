package sample.admin.oquvchi;

import com.mysql.jdbc.Connection;
import javafx.fxml.Initializable;
import sample.Tools.MysqlConnection;
import sample.admin.guruh.GroupModul;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OquvchiQuerys implements Initializable {
    PreparedStatement prs;
    ResultSet rs;
    Connection conn;

    public OquvchiQuerys() {
       conn = MysqlConnection.conDb();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public List<OquvGuruhModul> getAllPuipleForPay(){
        List<OquvGuruhModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchi_guruh");
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new OquvGuruhModul(rs.getString(1),1,rs.getString(2),rs.getString(3)
                ,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)
                ,rs.getString(9)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OquvchiModul> getAllpupils(){
        List<OquvchiModul> listOquvchi = new ArrayList<>();
        int tr = 1;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchilar ");
            rs = prs.executeQuery();
            while (rs.next()){
                listOquvchi.add(new OquvchiModul(tr,rs.getString(1),rs.getString(2)
                        ,rs.getString(3),rs.getString(4)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOquvchi;
    }

    public List<OquvchiModul> searchPupils(String detail){
        List<OquvchiModul> listResult = new ArrayList<>();
        int tr = 1;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchilar where fish like ? '%' or phone_number like ? '%' ");
            prs.setString(1,detail);
            prs.setString(2,detail);
            rs = prs.executeQuery();
            while (rs.next()){
                listResult.add(new OquvchiModul(tr,rs.getString(1),rs.getString(2),rs.getString(3)
                ,rs.getString(4)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listResult;
    }
    public int checkSamePupil(String fish){
        int count = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchilar where fish = ?");
            prs.setString(1,fish);
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
    public List<GroupModul> searchGroupOfTeacher(String teacherID){
        List<GroupModul> listGroup = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from guruh where teacher_id = ?");
            prs.setString(1,teacherID);
            rs = prs.executeQuery();
            while (rs.next()){
                listGroup.add(new GroupModul(rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listGroup;
    }
    public void insertPuipleToOquvchilarTable(String fish,String phone_number,String qoshilgan_sana){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("insert into oquvchilar(fish,phone_number,qoshilgan_sana) values(?,?,?)");
            prs.setString(1,fish);
            prs.setString(2,phone_number);
            prs.setString(3,qoshilgan_sana);
            prs.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<OquvchiModul> getOquvchiID(String fish){
        List<OquvchiModul> listOquvchi = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchilar where fish = ?");
            prs.setString(1,fish);
            rs = prs.executeQuery();
            while (rs.next()){
                listOquvchi.add(new OquvchiModul(rs.getString(1),rs.getString(2),rs.getString(3)) );
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOquvchi;
    }
    public List<GroupModul> getGroupID(String guruh_name,String fan_id,String teacher_id){
        List<GroupModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from guruh where guruh_name = ? and fan_id = ? and teacher_id = ?");
            prs.setString(1,guruh_name);
            prs.setString(2,fan_id);
            prs.setString(3,teacher_id);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new GroupModul(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OquvGuruhModul> queryForSocketToOquvchiguruh(){
        int tr = 1;
        String count = "";
        List<OquvGuruhModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_guruh.id, oquvchilar.fish, fan.name, teacher.name,teacher.familya, guruh.guruh_name," +
                    " oquvchi_guruh.qarz,oquvchi_guruh.chegirma, oquvchi_guruh.qayd_sanasi, oquvchi_guruh.update_date from oquvchi_guruh " +
                    "inner join oquvchilar on oquvchi_guruh.oquvchi_id=oquvchilar.id inner join fan on " +
                    "oquvchi_guruh.fan_id=fan.id inner join teacher on oquvchi_guruh.teacher_id=teacher.id inner join guruh on " +
                    "oquvchi_guruh.guruh_id=guruh.id");
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new OquvGuruhModul(rs.getString(1),tr,rs.getString(2),rs.getString(3),
                        rs.getString(4)+ " " + rs.getString(5), rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)));
                tr++;
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void insertPuipleToOquvchi_gutuhTable(String oquvchi_id,String fan_id,String teacher_id,String guruh_id
            ,String qarz,String chegirma,String qayd_sanasi){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("insert into oquvchi_guruh(oquvchi_id,fan_id,teacher_id,guruh_id,qarz,chegirma,qayd_sanasi,update_date) values(?,?,?,?,?,?,?,?)");
            prs.setString(1,oquvchi_id);
            prs.setString(2,fan_id);
            prs.setString(3,teacher_id);
            prs.setString(4,guruh_id);
            prs.setString(5,qarz);
            prs.setString(6,chegirma);
            prs.setString(7,qayd_sanasi);
            prs.setString(8,qayd_sanasi);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int checkSamePuipleGroup(String oquvchi_id,String fan_id,String teacher_id,String guruh_id){
        int count = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchi_guruh where oquvchi_id = ? and fan_id = ? and teacher_id = ? and guruh_id = ?");
            prs.setString(1,oquvchi_id);
            prs.setString(2,fan_id);
            prs.setString(3,teacher_id);
            prs.setString(4,guruh_id);
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
    public List<OquvGuruhModul> getSelectPuiple(String puipleID){
        List<OquvGuruhModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_guruh.id, oquvchilar.fish, fan.name, teacher.name,teacher.familya," +
                    " guruh.guruh_name, oquvchi_guruh.qarz,oquvchi_guruh.chegirma, oquvchi_guruh.qayd_sanasi from oquvchi_guruh " +
                    "inner join oquvchilar on oquvchi_guruh.oquvchi_id=oquvchilar.id inner join fan on " +
                    "oquvchi_guruh.fan_id=fan.id inner join teacher on oquvchi_guruh.teacher_id=teacher.id inner join guruh on " +
                    "oquvchi_guruh.guruh_id=guruh.id where oquvchi_guruh.oquvchi_id = ?");
            prs.setString(1,puipleID);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new OquvGuruhModul(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4) +
                        " " +rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OquvGuruhModul> getSelectPuipleWithoquvchGuruhId(String oquvchiGuruhID){
        List<OquvGuruhModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchi_guruh where id = ?");
            prs.setString(1,oquvchiGuruhID);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new OquvGuruhModul(rs.getString(1),rs.getString(6),rs.getString(7),rs.getString(9)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertPuipleFromOquvguruhToKarzinka(String fish,String fan_id,String teacher_id,String qarz,String ochirilgan_sana){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("insert into karzinka(fish,fan_id,teacher_id,qarz,ochrilgan_sana) values(?,?,?,?,?)");
            prs.setString(1,fish);
            prs.setString(2,fan_id);
            prs.setString(3,teacher_id);
            prs.setString(4,qarz);
            prs.setString(5,ochirilgan_sana);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteOquvchiGuruh(String id){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("delete from oquvchi_guruh where id = ?");
            prs.setString(1,id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getPuipleOfGroupsCount(String fishID){
        int count = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from oquvchi_guruh where oquvchi_id = ?");
            prs.setString(1,fishID);
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
    public void deletePuipleFromOquvchilar(String id){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("delete from oquvchilar where id = ?");
            prs.setString(1,id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updatePuipleFishAndNumber(String fish,String phone_number,String id){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("UPDATE oquvchilar SET fish = ? , phone_number = ? WHERE id = ?");
            prs.setString(1,fish);
            prs.setString(2,phone_number);
            prs.setString(3,id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    korish kerak qaytamiz......???????????????????????????????????? hazil
    public void updatePuipleFanTeacherAndGroup(String fan_id,String teacher_id,String group_Id,String qarz,String chegirma,String update_date,String id){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement(" update oquvchi_guruh set fan_id = ?, teacher_id = ?, guruh_id = ?, qarz = ?, chegirma = ?, update_date = ? where id = ?");
            prs.setString(1,fan_id);
            prs.setString(2,teacher_id);
            prs.setString(3,group_Id);
            prs.setString(4,qarz);
            prs.setString(5,chegirma);
            prs.setString(6,update_date);
            prs.setString(7,id);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateUpdateDateOfALLForNewMonth(String newUpdateDate,String oquvchi_guruhID){
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("update oquvchi_guruh set update_date = ? where id = ?");
            prs.setString(1,newUpdateDate);
            prs.setString(2,oquvchi_guruhID);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<GroupModul> searchGetGroupOfNarx(String guruhID){
        List<GroupModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from guruh where id = ?");
            prs.setString(1,guruhID);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new GroupModul(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
            conn.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
