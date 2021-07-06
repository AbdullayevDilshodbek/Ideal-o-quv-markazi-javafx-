package sample.teacher.davomad;

import com.mysql.jdbc.Connection;
import sample.Tools.MysqlConnection;
import sample.admin.davomad.AdminDavomadModul;
import sample.admin.oquvchi.OquvGuruhModul;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDavomadQuery {
    PreparedStatement prs;
    ResultSet rs;
    Connection conn;

    public TeacherDavomadQuery() {
        conn = MysqlConnection.conDb();
    }

    public List<TeacherDavomadModul> getAllPuiple(String teacherID) {
        int tr = 1;
        List<TeacherDavomadModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select og.id, o.fish, g.guruh_name, og.qarz, og.qayd_sanasi," +
                    " GROUP_CONCAT(status) as 'status' from davomad d " +
                    " join oquvchi_guruh og on d.oquvchi_id = og.oquvchi_id" +
                    " join oquvchilar o on o.id = d.oquvchi_id" +
                    " join guruh g on og.guruh_id = g.id  where og.teacher_id = ?" +
                    " GROUP BY d.oquvchi_id");
            prs.setString(1, teacherID);
//            SELECT og.id, o.fish, g.guruh_name, og.qarz, og.qayd_sanasi,
//                    GROUP_CONCAT(status) AS 'status',
//                    GROUP_CONCAT(d.sana) AS 'data'
//            FROM davomad d
//            JOIN oquvchi_guruh og
//            on d.oquvchi_id = og.oquvchi_id
//            JOIN oquvchilar o
//            on o.id = d.oquvchi_id
//            INNER JOIN guruh g
//            on og.guruh_id = g.id
//            GROUP BY d.oquvchi_id
            rs = prs.executeQuery();
                while (rs.next()) {
                    String[] kun = new String[5];
                    kun[0] = "-";
                    kun[1] = "-";
                    kun[2] = "-";
                    kun[3] = "-";
                    kun[4] = "-";
                    String statues = rs.getString(6);
                    int k = 0;
                    if (statues.length() > 1){
                        while (statues.length() > 1){
                            if (statues.substring(0,1).equals("0")){
                            kun[k] = "YO'Q";
                            } else if (statues.substring(0,1).equals("1")){
                                kun[k] = "BOR";
                            }
                            statues = statues.substring(2);
                            k++;
                        }
                        if (statues.equals("0")){
                        kun[k] = "YO'Q";
                        } else if (statues.equals("1")){
                            kun[k] = "BOR";
                        }
                    } else if (statues.length() == 1){
                        if (statues.equals("0")){
                            kun[k] = "YO'Q";
                        } else if (statues.equals("1")){
                            kun[k] = "BOR";
                        }
                    }
                    list.add(new TeacherDavomadModul(rs.getString(1), tr, rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getString(5),
                            kun[0], kun[1], kun[2], kun[3], kun[4]));
                    tr++;
                }
//            }
        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }

    public List<StatusModul> getLastFiveDaysDavomadOfPuiple(String teacherID) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        List<StatusModul> list = new ArrayList<>();
        String id = "";
        for (OquvGuruhModul idd : getAllOquvchiIdTheGroup(teacherID)) {
            id = idd.getOquvchi_id();
            try {
                prs = conn.prepareStatement("select status from davomad where teacher_id = ? and oquvchi_id = ?" +
                        " order by id desc limit 5");
                prs.setString(1, teacherID);
                prs.setString(2, id);
                rs = prs.executeQuery();
                while (rs.next()) {
                    list.add(new StatusModul(id, rs.getString(1)));
                    System.out.println(id + "-->" + rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for(StatusModul item: list){
            System.out.println("ID:"+item.getId());
            System.out.println("Status:"+item.getStatuses());
        }
        return list;
    }

    public List<OquvGuruhModul> getAllOquvchiIdTheGroup(String teacherId) {
        List<OquvGuruhModul> oquvchiIdListofGroup = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select oquvchi_id from oquvchi_guruh where teacher_id = ?");
            prs.setString(1, teacherId);
            rs = prs.executeQuery();
            while (rs.next()) {
                oquvchiIdListofGroup.add(new OquvGuruhModul(rs.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oquvchiIdListofGroup;
    }

    public String getOquvchiId(String fish) {
        String id = "";
        try {
            prs = conn.prepareStatement("select * from oquvchilar where fish = ?");
            prs.setString(1, fish);
            rs = prs.executeQuery();
            id = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<TeacherDavomadModul> sotrGuruh(String teacherID, String guruh) {
        int tr = 1;
        List<TeacherDavomadModul> list = new ArrayList<>();
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select og.id, o.fish, g.guruh_name, og.qarz, og.qayd_sanasi," +
                    " GROUP_CONCAT(status) as 'status' from davomad d " +
                    " join oquvchi_guruh og on d.oquvchi_id = og.oquvchi_id" +
                    " join oquvchilar o on o.id = d.oquvchi_id" +
                    " join guruh g on og.guruh_id = g.id  where og.teacher_id = ? and g.guruh_name = ?" +
                    " GROUP BY d.oquvchi_id");
            prs.setString(1, teacherID);
            prs.setString(2, guruh);
            rs = prs.executeQuery();
            while (rs.next()) {
                String[] kun = new String[5];
                kun[0] = "-";
                kun[1] = "-";
                kun[2] = "-";
                kun[3] = "-";
                kun[4] = "-";
                String statues = rs.getString(6);
                int k = 0;
                if (statues.length() > 1){
                    while (statues.length() > 1){
                        if (statues.substring(0,1).equals("0")){
                            kun[k] = "YO'Q";
                        } else if (statues.substring(0,1).equals("1")){
                            kun[k] = "BOR";
                        }
                        statues = statues.substring(2);
                        k++;
                    }
                    if (statues.equals("0")){
                        kun[k] = "YO'Q";
                    } else if (statues.equals("1")){
                        kun[k] = "BOR";
                    }
                } else if (statues.length() == 1){
                    if (statues.equals("0")){
                        kun[k] = "YO'Q";
                    } else if (statues.equals("1")){
                        kun[k] = "BOR";
                    }
                }
                list.add(new TeacherDavomadModul(rs.getString(1), tr, rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        kun[0], kun[1], kun[2], kun[3], kun[4]));
                tr++;
            }
//            }
        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }

    public void insertDavomad(String oquvchiID, String teacherID, String guruhID, String sana, String satus) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("insert into davomad(oquvchi_id,teacher_id,guruh_id,sana,status) values(?,?,?,?,?)");
            prs.setString(1, oquvchiID);
            prs.setString(2, teacherID);
            prs.setString(3, guruhID);
            prs.setString(4, sana);
            prs.setString(5, satus);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int checkDavomad(String oquvchiID, String teacherID, String guruhID, String sana) {
        int count = 0;
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("select * from davomad where oquvchi_id = ? and teacher_id = ? and guruh_id = ? and sana = ?");
            prs.setString(1, oquvchiID);
            prs.setString(2, teacherID);
            prs.setString(3, guruhID);
            prs.setString(4, sana);
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

    public void updateDavomad(String oquvchiID, String teacherID, String guruhID, String sana, String satus) {
        conn = MysqlConnection.conDb();
        assert conn != null;
        try {
            prs = conn.prepareStatement("update davomad set status = ? where oquvchi_id = ? and teacher_id = ? and guruh_id = ? and sana = ?");
            prs.setString(1, satus);
            prs.setString(2, oquvchiID);
            prs.setString(3, teacherID);
            prs.setString(4, guruhID);
            prs.setString(5, sana);
            prs.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void borYoq(String k1,String k2,String k3,String k4,String k5,int ol){
//        if (ol == 0) {
//            if (sm.getStatuses().equals("0")) {
//                k1 = "YO'Q";
//                ol++;
//            } else {
//                k1 = "BOR";
//                ol++;
//            }
//        }
//        if (ol == 1) {
//            if (sm.getStatuses().equals("0")) {
//                k2 = "YO'Q";
//                ol++;
//            } else {
//                k2 = "BOR";
//                ol++;
//            }
//        }
//        if (ol == 2) {
//            if (sm.getStatuses().equals("0")) {
//                k3 = "YO'Q";
//                ol++;
//            } else {
//                k3 = "BOR";
//                ol++;
//            }
//        }
//        if (ol == 3) {
//            if (sm.getStatuses().equals("0")) {
//                k4 = "YO'Q";
//                ol++;
//            } else {
//                k4 = "BOR";
//                ol++;
//            }
//        }
//        if (ol == 4) {
//            if (sm.getStatuses().equals("0")) {
//                k5 = "YO'Q";
//                ol++;
//            } else {
//                k5 = "BOR";
//                ol++;
//            }
//        }
//    }

}
