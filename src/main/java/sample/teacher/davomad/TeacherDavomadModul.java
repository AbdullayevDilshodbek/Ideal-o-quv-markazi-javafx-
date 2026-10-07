package sample.teacher.davomad;

import com.jfoenix.controls.JFXCheckBox;

public class TeacherDavomadModul {
    private String id;
    private int tr;
    private String fish;
    private String guruh;
    private String qarz;
    private String sana;
    private JFXCheckBox checkBox;
    private String kun1;
    private String kun2;
    private String kun3;
    private String kun4;
    private String kun5;

    public TeacherDavomadModul(String id, int tr, String fish, String guruh, String qarz, String sana, String kun1,String kun2,
                               String kun3,String  kun4, String kun5) {
        this.id = id;
        this.tr = tr;
        this.fish = fish;
        this.guruh = guruh;
        this.qarz = qarz;
        this.sana = sana;
        this.checkBox = new JFXCheckBox();
        checkBox.setText("Keldimi ?");
        this.kun1 = kun1;
        this.kun2 = kun2;
        this.kun3 = kun3;
        this.kun4 = kun4;
        this.kun5 = kun5;
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

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public String getKun1() {
        return kun1;
    }

    public String getKun2() {
        return kun2;
    }

    public String getKun3() {
        return kun3;
    }

    public String getKun4() {
        return kun4;
    }

    public String getKun5() {
        return kun5;
    }
}
