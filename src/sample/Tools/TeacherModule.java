package sample.Tools;

public class TeacherModule {
    private String id;
    private String name;
    private String familya;
    private String fan_name;
    private String parol;
    private String admin;

    public TeacherModule(String id, String name, String familya, String fan_name, String parol, String admin) {
        this.id = id;
        this.name = name;
        this.familya = familya;
        this.fan_name = fan_name;
        this.parol = parol;
        this.admin = admin;
    }

    public TeacherModule(String name, String parol, String admin) {
        this.name = name;
        this.parol = parol;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilya() {
        return familya;
    }

    public void setFamilya(String familya) {
        this.familya = familya;
    }

    public String getFan_name() {
        return fan_name;
    }

    public void setFan_name(String fan_name) {
        this.fan_name = fan_name;
    }

    public String getParol() {
        return parol;
    }

    public void setParol(String parol) {
        this.parol = parol;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
