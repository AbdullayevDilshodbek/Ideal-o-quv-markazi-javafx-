package sample.admin.oquvchi;

import javafx.beans.Observable;

public class OquvchiModul {
    private int tr;
    private String id;
    private String fish;
    private String phone_number;
    private String qoshilgan_sana;
    private String status;

    public OquvchiModul(int tr, String id, String fish, String phone_number, String qoshilgan_sana) {
        this.tr = tr;
        this.id = id;
        this.fish = fish;
        this.phone_number = phone_number;
        this.qoshilgan_sana = qoshilgan_sana;
    }
    public OquvchiModul(String id, String fish,String phone_number) {
        this.id = id;
        this.fish = fish;
        this.phone_number = phone_number;
    }


    public int getTr() {
        return tr;
    }

    public String getId() {
        return id;
    }

    public String getFish() {
        return fish;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getQoshilgan_sana() {
        return qoshilgan_sana;
    }

    public String getStatus() {
        return status;
    }

}
