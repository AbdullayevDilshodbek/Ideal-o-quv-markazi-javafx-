package sample.admin.teacher;

public class TeacherModul {
    private String id;
    private String name;
    private String familya;
    private String fan_id;
    private String fan_name;
    private String parol;
    private String admin;
    private String amount;


    public TeacherModul(String id, String name, String familya, String fan_id, String fan_name, String parol, String admin) {
        this.id = id;
        this.name = name;
        this.familya = familya;
        this.fan_id = fan_id;
        this.fan_name = fan_name;
        this.parol = parol;
        this.admin = admin;
    }

    public TeacherModul(String id, String name, String familya, String fan_id,String parol) {
        this.id = id;
        this.name = name;
        this.familya = familya;
        this.fan_id = fan_id;
        this.parol = parol;
    }

    public TeacherModul(String id, String name, String familya, String fan_id, String parol, String amount) {
        this.id = id;
        this.name = name;
        this.familya = familya;
        this.fan_id = fan_id;
        this.parol = parol;
        this.amount = amount;
    }

    public TeacherModul(String id, String name, String familya) {
        this.id = id;
        this.name = name;
        this.familya = familya;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFamilya() {
        return familya;
    }

    public String getFan_id() {
        return fan_id;
    }

    public String getParol() {
        return parol;
    }

    public String getAdmin() {
        return admin;
    }

    public String getFan_name() {
        return fan_name;
    }

    public String getAmount() {
        return amount;
    }
}
