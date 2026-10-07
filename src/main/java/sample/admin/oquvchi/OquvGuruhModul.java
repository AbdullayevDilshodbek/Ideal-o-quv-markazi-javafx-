package sample.admin.oquvchi;

public class OquvGuruhModul {
    private String id;
    private int tr;
    private String oquvchi_id;
    private String fan_id;
    private String teacher_id;
    private String guruh_id;
    private String qarz;
    private String chegirma;
    private String qayd_sanasi;
    private String update_date;

    public OquvGuruhModul(String id, String oquvchi_id, String fan_id, String teacher_id, String guruh_id,String qarz,String chegirma,String qayd_sanasi) {
        this.id = id;
        this.oquvchi_id = oquvchi_id;
        this.fan_id = fan_id;
        this.teacher_id = teacher_id;
        this.guruh_id = guruh_id;
        this.qarz = qarz;
        this.chegirma = chegirma;
        this.qayd_sanasi = qayd_sanasi;
    }

    public OquvGuruhModul(String id,int tr, String oquvchi_id, String fan_id, String teacher_id, String guruh_id, String qarz, String chegirma, String qayd_sanasi,String update_date) {
        this.id = id;
        this.tr = tr;
        this.oquvchi_id = oquvchi_id;
        this.fan_id = fan_id;
        this.teacher_id = teacher_id;
        this.guruh_id = guruh_id;
        this.qarz = qarz;
        this.chegirma = chegirma;
        this.qayd_sanasi = qayd_sanasi;
        this.update_date = update_date;

    }

    public OquvGuruhModul(String id, int tr, String oquvchi_id, String fan_id, String teacher_id, String guruh_id, String qarz, String chegirma, String update_date) {
        this.id = id;
        this.tr = tr;
        this.oquvchi_id = oquvchi_id;
        this.fan_id = fan_id;
        this.teacher_id = teacher_id;
        this.guruh_id = guruh_id;
        this.qarz = qarz;
        this.chegirma = chegirma;
        this.update_date = update_date;
    }

    public OquvGuruhModul(String id, String qarz, String chegirma, String update_date) {
        this.id = id;
        this.qarz = qarz;
        this.chegirma = chegirma;
        this.update_date = update_date;
    }

    public OquvGuruhModul(String oquvchi_id) {
        this.oquvchi_id = oquvchi_id;
    }

    public String getId() {
        return id;
    }

    public int getTr() {
        return tr;
    }

    public String getOquvchi_id() {
        return oquvchi_id;
    }

    public String getFan_id() {
        return fan_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public String getGuruh_id() {
        return guruh_id;
    }

    public String getQarz() {
        return qarz;
    }

    public String getChegirma() {
        return chegirma;
    }

    public String getQayd_sanasi() {
        return qayd_sanasi;
    }

    public String getUpdate_date() {
        return update_date;
    }
}
