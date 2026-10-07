package sample.admin.maosh;

public class MaoshModul {
    private String id;
    private int tr;
    private String oquvchi;
    private String fan;
    private String oqituvchi;
    private String guruh;
    private String tolov;
    private String sana;
    private String status;


    public MaoshModul(String id, int tr, String oquvchi, String fan, String oqituvchi, String guruh, String tolov, String sana, String status) {
        this.id = id;
        this.tr = tr;
        this.oquvchi = oquvchi;
        this.fan = fan;
        this.oqituvchi = oqituvchi;
        this.guruh = guruh;
        this.tolov = tolov;
        this.sana = sana;
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

    public String getFan() {
        return fan;
    }

    public String getOqituvchi() {
        return oqituvchi;
    }

    public String getGuruh() {
        return guruh;
    }

    public String getTolov() {
        return tolov;
    }

    public String getSana() {
        return sana;
    }

    public String getStatus() {
        return status;
    }
}
