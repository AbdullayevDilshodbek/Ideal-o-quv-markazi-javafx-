package sample.teacher;

public class TeacherRoomModul {
    private String id;
    private int tr;
    private String fish;
    private String guruh;
    private String qarz;
    private String sana;

    public TeacherRoomModul(String id, int tr, String fish, String guruh, String qarz, String sana) {
        this.id = id;
        this.tr = tr;
        this.fish = fish;
        this.guruh = guruh;
        this.qarz = qarz;
        this.sana = sana;
    }

    public String getId() {
        return id;
    }

    public int getTr() {
        return tr;
    }

    public String getFish() {
        return fish;
    }

    public String getGuruh() {
        return guruh;
    }

    public String getQarz() {
        return qarz;
    }

    public String getSana() {
        return sana;
    }
}
