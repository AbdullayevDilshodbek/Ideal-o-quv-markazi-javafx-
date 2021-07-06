package sample.admin.tolov;

import com.mysql.jdbc.Connection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import sample.Tools.MysqlConnection;
import sample.Tools.SqlQuerys;
import sample.admin.fan.FanModel;
import sample.admin.guruh.GroupModul;
import sample.admin.teacher.TeacherModul;
import sample.admin.oquvchi.OquvGuruhModul;
import sample.admin.oquvchi.OquvchiModul;
import sample.admin.oquvchi.OquvchiQuerys;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Tolov implements Initializable {
    Connection conn;
    TolovQuery q = new TolovQuery();
    OquvchiQuerys oq = new OquvchiQuerys();
    SqlQuerys sq = new SqlQuerys();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            conn = MysqlConnection.conDb();
        updateTB1();
        List<String> fanList = new ArrayList<>();
        for (FanModel fm: sq.getAllFan()) {
            fanList.add(fm.getName());
        }
        cbox_fan.setItems(FXCollections.observableList(fanList));
    }

    @FXML
    TableColumn<OquvGuruhModul, String> t1_id;
    @FXML
    TableColumn<OquvGuruhModul, String> t1_tr;
    @FXML
    TableColumn<OquvGuruhModul, String> t1_oquvchi;
    @FXML
    TableColumn<OquvGuruhModul, String> t1_fan;
    @FXML
    TableColumn<OquvGuruhModul, String> t1_oqituvchi;
    @FXML
    TableColumn<OquvGuruhModul, String> t1_guruh;
    @FXML
    TableColumn<OquvGuruhModul, String> t1_qarz;
    @FXML
    TableColumn<OquvGuruhModul, String> t1_chegirma;
    @FXML
    TableColumn<OquvGuruhModul, String> t1_sana;
    @FXML
    TableView<OquvGuruhModul> tb_view1;

    public void updateTB1() {
        ObservableList<OquvGuruhModul> view = FXCollections.observableList(oq.queryForSocketToOquvchiguruh());
        t1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        t1_oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
        t1_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        t1_oqituvchi.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        t1_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
        t1_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
        t1_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
        t1_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
        tb_view1.setItems(view);
    }

    @FXML
    TableColumn<OquvGuruhModul, String> t_id;
    @FXML
    TableColumn<OquvGuruhModul, String> t_tr;
    @FXML
    TableColumn<OquvGuruhModul, String> t_oquvchi;
    @FXML
    TableColumn<OquvGuruhModul, String> t_fan;
    @FXML
    TableColumn<OquvGuruhModul, String> t_oqituvchi;
    @FXML
    TableColumn<OquvGuruhModul, String> t_guruh;
    @FXML
    TableColumn<OquvGuruhModul, String> t_qarz;
    @FXML
    TableColumn<OquvGuruhModul, String> t_chegirma;
    @FXML
    TableColumn<OquvGuruhModul, String> t_sana;
    @FXML
    TableView<OquvGuruhModul> tb_view;
    @FXML
    Label lb_fish;
    @FXML
    Label lb_teacher;
    @FXML
    Label lb_guruh;
    @FXML
    Label lb_fan;
    @FXML
    Label oquvchi_guruhID;
    @FXML
    Label lb_qarz;
    @FXML
    Label lb_oquvchiID;
    @FXML
    Label lb_guruhID;
    @FXML
    Label lb_lastQarz;
    @FXML
    Label lb_lastOquvGuruhID;
    @FXML
    TextField edt_tolovMiqdori;

    @FXML
    Button insert_tolov;
    @FXML
    Button update_tolov;


    public void put_date_tb2(MouseEvent mouseEvent) {
        update_tolov.setDisable(true);
        OquvGuruhModul om = tb_view1.getSelectionModel().getSelectedItem();
//        ----------------------------          -----------------    ---------------
        try {
        lb_lastQarz.setText(om.getQarz());
        lb_lastOquvGuruhID.setText(om.getId());
        lb_fish.setText(om.getOquvchi_id());
        lb_teacher.setText(om.getTeacher_id());
        lb_fan.setText(om.getFan_id());
        lb_guruh.setText(om.getGuruh_id());
        oquvchi_guruhID.setText(om.getId());
        lb_qarz.setText(om.getQarz());
        } catch (Exception e){
            e.getMessage();
        }

        for (OquvchiModul om1:oq.getOquvchiID(om.getOquvchi_id())) {
            lb_oquvchiID.setText(om1.getId());
        }
        String group = om.getGuruh_id();
        String fanID = "";
        for (FanModel fm:sq.searchFan(om.getFan_id())) {
            fanID = fm.getId();
        }

        String fullName = om.getTeacher_id();
        int findSymbol = fullName.indexOf(" ");
        String name = fullName.substring(0,findSymbol);
        String familya = fullName.substring(findSymbol+1);
        String teacherID = "";
        for (TeacherModul tm: sq.getTeacherId(name,familya)) {
            teacherID = tm.getId();
        }
        String guruhID = "";
        for (GroupModul gm : oq.getGroupID(group, fanID, teacherID)) {
            guruhID = gm.getId();
        }
        lb_guruhID.setText(guruhID);
        double guruxNarxi = 0.0;
        for (GroupModul gm:oq.searchGetGroupOfNarx(guruhID)) {
            guruxNarxi = Double.parseDouble(gm.getNarx());
        }
        double SpecialAmount = guruxNarxi * ( 1 - Double.parseDouble(om.getChegirma())/100 );
        edt_tolovMiqdori.setText(om.getQarz());
        update_table();
        TbTolov();

    }

    public void update_table() {
        OquvGuruhModul om = tb_view1.getSelectionModel().getSelectedItem();

        lb_lastQarz.setText(om.getQarz());

        String fish = om.getOquvchi_id();
        String puipleID = "";
        for (OquvchiModul om1 : oq.getOquvchiID(fish)) {
            puipleID = om1.getId();
        }
        ObservableList<OquvGuruhModul> view = FXCollections.observableList(oq.getSelectPuiple(puipleID));
        t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t_oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
        t_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        t_oqituvchi.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        t_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
        t_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
        t_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
        t_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
        tb_view.setItems(view);
    }

    public void put_date_tb(MouseEvent mouseEvent) {

        update_tolov.setDisable(true);

        OquvGuruhModul om = tb_view.getSelectionModel().getSelectedItem();

        lb_lastOquvGuruhID.setText(om.getId());

        if (om.getOquvchi_id() != null ){
        lb_fish.setText(om.getOquvchi_id());
        lb_teacher.setText(om.getTeacher_id());
        lb_fan.setText(om.getFan_id());
        lb_guruh.setText(om.getGuruh_id());
        oquvchi_guruhID.setText(om.getId());
        lb_qarz.setText(om.getQarz());
        }
        for (OquvchiModul om1:oq.getOquvchiID(om.getOquvchi_id())) {
            lb_oquvchiID.setText(om1.getId());
        }
        String group = om.getGuruh_id();
        String fanID = "";
        for (FanModel fm:sq.searchFan(om.getFan_id())) {
            fanID = fm.getId();
        }
        String fullName = om.getTeacher_id();
        int findSymbol = fullName.indexOf(" ");
        String name = fullName.substring(0,findSymbol);
        String familya = fullName.substring(findSymbol+1);
        String teacherID = "";
        for (TeacherModul tm: sq.getTeacherId(name,familya)) {
            teacherID = tm.getId();
        }
        String guruhID = "";
        for (GroupModul gm : oq.getGroupID(group, fanID, teacherID)) {
            guruhID = gm.getId();
        }
        lb_guruhID.setText(guruhID);
        int guruxNarxi = 0;
        for (GroupModul gm:oq.searchGetGroupOfNarx(guruhID)) {
            guruxNarxi = Integer.parseInt(gm.getNarx());
        }
        double SpecialAmount = guruxNarxi * ( 1 - Double.parseDouble(om.getChegirma())/100 );
        edt_tolovMiqdori.setText(om.getQarz());
        TbTolov();
    }

    @FXML
    TextField edt_search;

    public void search(KeyEvent keyEvent) {
        String detail = edt_search.getText();
        ObservableList<OquvGuruhModul> view = FXCollections.observableList(q.searchOquvchiFanTeacher(detail));
        t1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        t1_oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
        t1_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        t1_oqituvchi.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        t1_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
        t1_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
        t1_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
        t1_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
        tb_view1.setItems(view);
    }

    @FXML
    ComboBox<String> cbox_fan;
    @FXML
    ComboBox<String> cbox_teacher;
    @FXML
    ComboBox<String> cbox_guruh;

    public void put_teachers(ActionEvent actionEvent) {
        String fan = cbox_fan.getSelectionModel().getSelectedItem();
        if (cbox_fan.getSelectionModel().getSelectedItem() != null){
            String fanID = "";
            for (FanModel fm:sq.searchFan(cbox_fan.getSelectionModel().getSelectedItem().toString())) {
                fanID = fm.getId();
            }
            List<String> listTeacher = new ArrayList<>();
            for (TeacherModul tm: sq.searchGroupTeacher(fanID)) {
                listTeacher.add(tm.getName() + " " + tm.getFamilya());
            }
            cbox_teacher.setItems(FXCollections.observableList(listTeacher));
        }
    }

    public void put_group(ActionEvent actionEvent) {

        String TeacherName = cbox_teacher.getSelectionModel().getSelectedItem();
        if (cbox_teacher.getSelectionModel().getSelectedItem() != null){
            String fullName = cbox_teacher.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(" ");
            String name = fullName.substring(0,findSymbol);
            String familya = fullName.substring(findSymbol+1);
            String teacherID = "";
            for (TeacherModul tm: sq.getTeacherId(name,familya)) {
                teacherID = tm.getId();
            }
            List<String> listGroup = new ArrayList<>();
            for (GroupModul gm:oq.searchGroupOfTeacher(teacherID)) {
                listGroup.add(gm.getGuruh_name());
            }
            cbox_guruh.setItems(FXCollections.observableList(listGroup));

            if (cbox_fan.getSelectionModel().getSelectedItem() != null && cbox_teacher.getSelectionModel().getSelectedItem() != null){
                String fan = cbox_fan.getSelectionModel().getSelectedItem();

                ObservableList<OquvGuruhModul> view = FXCollections.observableList(q.getSpecialGroup0(fan,name,familya));
                t1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
                t1_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
                t1_oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
                t1_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
                t1_oqituvchi.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
                t1_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
                t1_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
                t1_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
                t1_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
                tb_view1.setItems(view);
            }
        }
    }
    public void saralash(ActionEvent actionEvent) {
        if (cbox_fan.getSelectionModel().getSelectedItem() != null && cbox_guruh.getSelectionModel().getSelectedItem()
                != null && cbox_teacher.getSelectionModel().getSelectedItem() != null){
            String fan = cbox_fan.getSelectionModel().getSelectedItem();
            String fullName = cbox_teacher.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(" ");
            String name = fullName.substring(0,findSymbol);
            String familya = fullName.substring(findSymbol+1);
            String group = cbox_guruh.getSelectionModel().getSelectedItem();

            ObservableList<OquvGuruhModul> view = FXCollections.observableList(q.getSpecialGroup(fan,name,familya,group));
            t1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            t1_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
            t1_oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
            t1_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
            t1_oqituvchi.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
            t1_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
            t1_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
            t1_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
            t1_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
            tb_view1.setItems(view);
        }
    }

    public void insert_tolov(ActionEvent actionEvent) {
        if (!edt_tolovMiqdori.getText().equals("")){
            if (Integer.parseInt(edt_tolovMiqdori.getText()) > 0){
            String oquvchiID = "";
            for (OquvchiModul om:oq.getOquvchiID(lb_fish.getText())) {
                oquvchiID = om.getId();
            }
            String teacherID = "";
            String fullName = lb_teacher.getText();
            int findSymbol = fullName.indexOf(" ");
            String name = fullName.substring(0,findSymbol);
            String familya = fullName.substring(findSymbol+1);
            for (TeacherModul tm:sq.getTeacherId(name,familya)) {
                teacherID = tm.getId();
            }
            String fanID = "";
            for (FanModel fm: sq.searchFan(lb_fan.getText())) {
                fanID = fm.getId();
            }
            String guruhID = "";
            for (GroupModul gm:oq.getGroupID(lb_guruh.getText(),fanID,teacherID)) {
                guruhID = gm.getId();
            }
            Double tol = Double.parseDouble(edt_tolovMiqdori.getText());
            int tolI = (int) (tol + 1 - 1);
            String tolov = String.valueOf(tolI);
            DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String sana = off.format(now);

            if (!lb_qarz.getText().equals("lb_qarz")){
            q.insertTolov(oquvchiID,fanID,teacherID,guruhID,tolov,sana);

            Double qarz = Double.parseDouble(lb_qarz.getText()) - Double.parseDouble(tolov);
            q.updateOquvchiQarzFromOquvchiGuruh(String.valueOf(qarz),oquvchi_guruhID.getText());
            lb_qarz.setText("lb_qarz");
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("To'lov amalga oshrildi...");
            a.show();
            update_table();
            updateTB1();
            TbTolov();
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Iltimos o'quvchini qaytatdan tanlang");
                a.show();
            }
            }
        }
    }

    @FXML
    TableColumn<TolovModul, String> t3_id;
    @FXML
    TableColumn<TolovModul, String> t3_tr;
    @FXML
    TableColumn<TolovModul, String> t3_oquvchi;
    @FXML
    TableColumn<TolovModul, String> t3_fan;
    @FXML
    TableColumn<TolovModul, String> t3_oqituvchi;
    @FXML
    TableColumn<TolovModul, String> t3_guruh;
    @FXML
    TableColumn<TolovModul, String> t3_tolov;
    @FXML
    TableColumn<TolovModul, String> t3_sana;
    @FXML
    TableView<TolovModul> tb_tolov;

    public void TbTolov(){
        String oquvchiID = lb_oquvchiID.getText();
        String guruhID = lb_guruhID.getText();
        ObservableList<TolovModul> view = FXCollections.observableList(q.getTolovHistory(oquvchiID,guruhID));
        updateTableTolov();
        tb_tolov.setItems(view);
    }
    @FXML
    Label up_fish;
    @FXML
    Label up_teacher;
    @FXML
    Label up_fan;
    @FXML
    Label up_guruh;
    @FXML
    Label up_tolovID;
    @FXML
    Label lb_lastTolov;
    @FXML
    TextField up_tolovMiqdori;
    public void put_updateTolov(MouseEvent mouseEvent) {

        insert_tolov.setDisable(false);
        update_tolov.setDisable(false);

        TolovModul tm = tb_tolov.getSelectionModel().getSelectedItem();
        up_fish.setText(tm.getOquvchi());
        up_teacher.setText(tm.getOqituvchi());
        up_fan.setText(tm.getFan());
        up_guruh.setText(tm.getGuruh());
        up_tolovMiqdori.setText(tm.getTolov());
        lb_lastTolov.setText(tm.getTolov());
        up_tolovID.setText(tm.getId());
    }

    public void updateTolov(ActionEvent actionEvent) {
        if (!up_tolovID.getText().equals("ID")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(up_fish.getText() + " ni tolov miqdorini yangilamoqchmisiz ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                q.updateTolov(up_tolovMiqdori.getText(),up_tolovID.getText());

                Double qarz = Double.parseDouble(lb_lastQarz.getText()) + Double.parseDouble(lb_lastTolov.getText()) - Double.parseDouble(up_tolovMiqdori.getText());
                q.updateOquvchiQarzFromOquvchiGuruh(String.valueOf(qarz),oquvchi_guruhID.getText());

                updateTB1();

                String fish = up_fish.getText();
                String puipleID = "";
                for (OquvchiModul om1 : oq.getOquvchiID(fish)) {
                    puipleID = om1.getId();
                }
//                uptable2
                ObservableList<OquvGuruhModul> view = FXCollections.observableList(oq.getSelectPuiple(puipleID));
                t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
                t_oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
                t_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
                t_oqituvchi.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
                t_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
                t_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
                t_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
                t_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
                tb_view.setItems(view);


                String oquvchiID = lb_oquvchiID.getText();
                String guruhID = lb_guruhID.getText();
//              updatetable3
                ObservableList<TolovModul> view2 = FXCollections.observableList(q.getTolovHistory(oquvchiID,guruhID));
                updateTableTolov();
                tb_tolov.setItems(view2);

            } else if (result.get() == ButtonType.CANCEL) {

            }
        }
    }
    public void updateTableTolov(){
        t3_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t3_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        t3_oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi"));
        t3_fan.setCellValueFactory(new PropertyValueFactory<>("fan"));
        t3_oqituvchi.setCellValueFactory(new PropertyValueFactory<>("oqituvchi"));
        t3_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh"));
        t3_tolov.setCellValueFactory(new PropertyValueFactory<>("tolov"));
        t3_sana.setCellValueFactory(new PropertyValueFactory<>("sana"));
    }

}
