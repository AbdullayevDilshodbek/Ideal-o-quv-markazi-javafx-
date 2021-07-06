package sample.admin.guruh;

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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.Tools.MysqlConnection;
import sample.Tools.SqlQuerys;
import sample.admin.fan.FanModel;
import sample.admin.teacher.TeacherModul;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Guruh implements Initializable {

    SqlQuerys q = new SqlQuerys();
    Connection conn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            conn = MysqlConnection.conDb();
            updateGroupTB();
        panel_insert.setVisible(false);
        panel_edit_group.setVisible(false);
        updateGroupTB();

    }
    @FXML
    AnchorPane panel_insert,panel_edit_group;
    @FXML
    ComboBox<String> cBox_insert_fan;
    @FXML
    ComboBox<String> cBox_insert_teacher;

    public void open_insert_panel(ActionEvent actionEvent) {
        panel_insert.setVisible(true);
        List<String> listFan = new ArrayList<>();
        for (FanModel fm:q.getAllFan()) {
            listFan.add(fm.getName());
        }
        cBox_insert_fan.setItems(FXCollections.observableList(listFan));
    }
    public void getSpecialTeacher(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm:q.searchFan(cBox_insert_fan.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listSpecialTeacher = new ArrayList<>();
        for (TeacherModul tm:q.searchGroupTeacher(fan_id)) {
            listSpecialTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cBox_insert_teacher.setItems(FXCollections.observableList(listSpecialTeacher));
    }
    @FXML
    Label lb_teacher_id;
    public void selectTeacher(ActionEvent actionEvent) {
        if (cBox_insert_teacher.getSelectionModel().getSelectedItem() != null){
        String fullName = cBox_insert_teacher.getSelectionModel().getSelectedItem();
        int findSymbol = fullName.indexOf(" ");
        String name = fullName.substring(0,findSymbol);
        String familya = fullName.substring(findSymbol+1);
        lb_teacher_id.setText(name + " " + familya);
        for (TeacherModul tm:q.getTeacherId(name.trim(),familya.trim())) {
            lb_teacher_id.setText(tm.getId());
        }
        }
    }
    @FXML
    TextField edt_insert_group_name;
    @FXML
    TextField edt_tolov;
    public void insert_group(ActionEvent actionEvent) {
        int allow = 0;
        String groupName = edt_insert_group_name.getText();
        String tolovMiqdori = edt_tolov.getText().trim();
        if ( !groupName.equals("") && !tolovMiqdori.equals("") && cBox_insert_fan.getSelectionModel().getSelectedItem() != null && cBox_insert_teacher.getSelectionModel().getSelectedItem() != null
        && q.checkSameGroup(groupName,lb_teacher_id.getText()) == 0 ){
            String fanId = "";
            for (FanModel fm:q.searchFan(cBox_insert_fan.getSelectionModel().getSelectedItem())) {
                fanId = fm.getId();
            }
           q.insertGroup(groupName,fanId,lb_teacher_id.getText(),tolovMiqdori);
            allow = 1;
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Guruh muoffaqqiyatli qo'shildi...");
            a.show();
        }
        if (tolovMiqdori.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Iltimos to'lov miqdorini kiriting ? ");
            alert.show();
        }
        if (allow == 0 && !groupName.equals("") && cBox_insert_fan.getSelectionModel().getSelectedItem() != null
                && cBox_insert_teacher.getSelectionModel().getSelectedItem() != null && !edt_tolov.getText().trim().equals("")){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Shu nomidagi guruh shu o'qtuvchida avvaldan mavjud !!!");
            a.show();
        }
        if ( groupName.equals("") || cBox_insert_teacher.getSelectionModel().getSelectedItem() == null || cBox_insert_fan.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Ma'lumotlar yetarlicha emas !!!");
            a.show();
        }
        updateGroupTB();
    }
    public void hide_insert_panel(ActionEvent actionEvent) {
        panel_insert.setVisible(false);
    }
    @FXML
    TableView<GroupModul> tb_group;
    @FXML
    TableColumn<GroupModul,String> id;
    @FXML
    TableColumn<GroupModul,String> guruh_name;
    @FXML
    TableColumn<GroupModul,String> fan_id;
    @FXML
    TableColumn<GroupModul,String> teacher_id;
    @FXML
    TableColumn<GroupModul,String> narx;

    public void updateGroupTB(){
        ObservableList<GroupModul> view = FXCollections.observableList(q.getAllGroup());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        guruh_name.setCellValueFactory(new PropertyValueFactory<>("guruh_name"));
        fan_id.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        teacher_id.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        narx.setCellValueFactory(new PropertyValueFactory<>("narx"));
        tb_group.setItems(view);
    }
    @FXML
    TextField edt_search;
    public void search(KeyEvent keyEvent) {
        ObservableList<GroupModul> view = FXCollections.observableList(q.searchGroup(edt_search.getText()));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        guruh_name.setCellValueFactory(new PropertyValueFactory<>("guruh_name"));
        fan_id.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        teacher_id.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        tb_group.setItems(view);
    }
    public void open_edit_panel(ActionEvent actionEvent) {
        panel_edit_group.setVisible(true);
        List<String> listFan = new ArrayList<>();
        for (FanModel fm: q.getAllFan()) {
            listFan.add(fm.getName());
        }
        cBox_edit_fan.setItems(FXCollections.observableList(listFan));
    }
    @FXML
    Label lb_temp_guruhID,lb_first_teacherID;
    @FXML
    Label lb_frist_fanID;
    @FXML
    Label lb_first_gurhName;
    @FXML
    Label lb_new_amount;
    @FXML
    TextField edt_new_amount;
    public void click_table(MouseEvent mouseEvent) {
        GroupModul gm = tb_group.getSelectionModel().getSelectedItem();
        edt_editGroup_name.setText(gm.getGuruh_name());
        lb_new_fan.setText(gm.getFan_id());
        lb_new_teacher.setText(gm.getTeacher_id());
        lb_temp_guruhID.setText(gm.getId());
        lb_new_amount.setText(gm.getNarx());
        lb_first_amount.setText(gm.getNarx());
//        --------------------------------   ----------------
       lb_first_gurhName.setText(gm.getGuruh_name());

        for (FanModel fm:q.searchFan(gm.getFan_id())) {
            lb_frist_fanID.setText(fm.getId());
        }

        String fullName = gm.getTeacher_id();
        if (!fullName.equals("")){
        int findSymbol = fullName.indexOf(" ");
        String name = fullName.substring(0,findSymbol);
        String familya = fullName.substring(findSymbol+1);
        for (TeacherModul tm:q.getTeacherId(name.trim(),familya.trim())) {
            lb_first_teacherID.setText(tm.getId());
        }
        }
    }
    public void hide_edit_panel(ActionEvent actionEvent) {
        panel_edit_group.setVisible(false);
    }
    @FXML
    ComboBox<String> cBox_edit_teacher;
    @FXML
    ComboBox<String> cBox_edit_fan;
    @FXML
    Label lb_new_fan;
    @FXML
    Label lb_new_teacher;
    @FXML
    TextField edt_editGroup_name;
    public void edit_new_fan(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm:q.searchFan(cBox_edit_fan.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listSpecialTeacher = new ArrayList<>();
        for (TeacherModul tm:q.searchGroupTeacher(fan_id)) {
            listSpecialTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cBox_edit_teacher.setItems(FXCollections.observableList(listSpecialTeacher));
        lb_new_fan.setText(cBox_edit_fan.getSelectionModel().getSelectedItem());
    }
    @FXML
    Label lb_edit_teacherID;
    @FXML
    Label lb_edit_info;
    @FXML
    Label lb_first_amount;
    public void edit_new_teacher(ActionEvent actionEvent) {
        String teacherID = "";
        if (cBox_edit_teacher.getSelectionModel().getSelectedItem() != null){
            String fullName = cBox_edit_teacher.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(" ");
            String name = fullName.substring(0,findSymbol);
            String familya = fullName.substring(findSymbol+1);
            for (TeacherModul tm:q.getTeacherId(name,familya)) {
                teacherID = tm.getId();
            }
            lb_first_teacherID.setText(teacherID);
        }
    }
    public void update_group(ActionEvent actionEvent) {
        String gName = edt_editGroup_name.getText();
        String fID = "";
        String teacherID = "";
        String narx = edt_new_amount.getText();
        String gID = lb_temp_guruhID.getText();
        for (FanModel fm:q.searchFan(cBox_edit_fan.getSelectionModel().getSelectedItem())) {
            fID = (fm.getId());
        }
//        if (cBox_edit_teacher.getSelectionModel().getSelectedItem() != null){
//        String fullName = cBox_edit_teacher.getSelectionModel().getSelectedItem();
//        int findSymbol = fullName.indexOf(" ");
//        String name = fullName.substring(0,findSymbol);
//        String familya = fullName.substring(findSymbol+1);
//        for (TeacherModul tm:q.getTeacherId(name.trim(),familya.trim())) {
//            teacherID = tm.getId();
//        }
//        }
            teacherID = lb_first_teacherID.getText();
        if (!gName.equals("") && !fID.equals("") && !teacherID.equals("") && !narx.equals("") && !gID.equals("")){
            q.updateGroup(gName,fID,teacherID,narx,gID);
            if (q.checkSameGroup(gName,fID,teacherID) == 1){
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Guruhga o'zgartirish amalga oshirildi...");
                a.show();
            }
            if (q.checkSameGroup(gName,fID,teacherID) > 1){
                String lg_name = lb_first_gurhName.getText();
                String lfID = lb_frist_fanID.getText();
                String ltID = lb_first_teacherID.getText();
                String lAmount = lb_first_amount.getText();
                q.updateGroup(lg_name,lfID,ltID,lAmount,gID);
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Shu nomidagi guruh shu o'qtuvchida avvaldan mavjud !!!");
                a.show();
            }
        }
        else {
//            (edt_editGroup_name.getText().equals("") || cBox_edit_fan.getSelectionModel().getSelectedItem() == null || cBox_edit_teacher.getSelectionModel().getSelectedItem() == null){
            lb_edit_info.setText("Ma'lumotlar yetarlicha emas...");
        }
        updateGroupTB();
    }
}
