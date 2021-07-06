package sample.admin.guruh;

public class GroupModul {
    private String id;
    private String guruh_name;
    private String fan_id;
    private String teacher_id;
    private String narx;

    public GroupModul(String id, String guruh_name, String fan_id, String teacher_id,String narx) {
        this.id = id;
        this.guruh_name = guruh_name;
        this.fan_id = fan_id;
        this.teacher_id = teacher_id;
        this.narx = narx;
    }


    public GroupModul(String narx) {
        this.narx = narx;
    }

    public String getId() {
        return id;
    }

    public String getGuruh_name() {
        return guruh_name;
    }

    public String getFan_id() {
        return fan_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }
    public String getNarx() {
        return narx;
    }
}
