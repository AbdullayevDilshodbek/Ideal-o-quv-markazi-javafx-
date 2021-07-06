package sample.admin.davomad;

import org.apache.poi.ss.formula.functions.T;

public class AdminDavomadModul {
    private String id;
    private int tr;
    private String oquvchi;
    private String teacher;
    private String guruh;
    private String sana;
    private String status;

    public AdminDavomadModul(String id, int tr, String oquvchi, String teacher, String guruh, String sana, String status) {
        this.id = id;
        this.tr = tr;
        this.oquvchi = oquvchi;
        this.teacher = teacher;
        this.guruh = guruh;
        this.sana = sana;
        this.status = status;
    }

    public AdminDavomadModul(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public int getTr() {
        return tr;
    }

    public String getOquvchi() {
        return oquvchi;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getGuruh() {
        return guruh;
    }

    public String getSana() {
        return sana;
    }

    public String getStatus() {
        return status;
    }
}
