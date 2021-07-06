package sample.teacher.davomad;

import com.mysql.jdbc.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Tools.Controller;
import sample.Tools.MysqlConnection;
import sample.Tools.SqlQuerys;
import sample.admin.davomad.AdminDavomadModul;
import sample.admin.guruh.GroupModul;
import sample.admin.teacher.TeacherModul;
import sample.admin.oquvchi.OquvchiModul;
import sample.admin.oquvchi.OquvchiQuerys;
import sample.teacher.TeacherRoomQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherDavomad implements Initializable {
    TeacherRoomQuery q = new TeacherRoomQuery();
    SqlQuerys sq = new SqlQuerys();
    OquvchiQuerys oq = new OquvchiQuerys();
    Controller c = new Controller();
    Connection conn;
    TeacherDavomadQuery tdq = new TeacherDavomadQuery();
    private String parol = "";
    private String teacherID = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            conn = MysqlConnection.conDb();
        try {
            FileReader reader = new FileReader("text.css");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                parol = line.trim();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        String teacherID = "";
        for (TeacherModul tm : q.getTeacherID(String.valueOf(String.valueOf(parol.hashCode()).hashCode()))) {
            teacherID = tm.getId();
            lb_teacherID.setText(teacherID);
            lb_fanID.setText(tm.getFan_id());
        }
        List<String> guruhlist = new ArrayList<>();
        for (GroupModul gm : q.getGuruh(teacherID)) {
            guruhlist.add(gm.getGuruh_name());
        }
        cbox_guruh.setItems(FXCollections.observableList(guruhlist));
        updateTable1();
    }

    @FXML
    Label lb_fanID;
    @FXML
    Label
    lb_teacherID;

    @FXML
    TableColumn<TeacherDavomadModul, String> tb_id;
    @FXML
    TableColumn<TeacherDavomadModul, String> tb_tr;
    @FXML
    TableColumn<TeacherDavomadModul, String> tb_fish;
    @FXML
    TableColumn<TeacherDavomadModul, String> tb_guruh;
    @FXML
    TableColumn<TeacherDavomadModul, String> tb_qarz;
    @FXML
    TableColumn<TeacherDavomadModul, String> tb_sana;
    @FXML
    TableColumn<TeacherDavomadModul, String> kun1;
    @FXML
    TableColumn<TeacherDavomadModul, String> kun2;
    @FXML
    TableColumn<TeacherDavomadModul, String> kun3;
    @FXML
    TableColumn<TeacherDavomadModul, String> kun4;
    @FXML
    TableColumn<TeacherDavomadModul, String> kun5;
    @FXML
    TableColumn<TeacherDavomadModul, String> tb_belgilash;
    @FXML
    TableView<TeacherDavomadModul> tb_view;

    @FXML
    ComboBox<String> cbox_guruh;


    public void updateTable1() {
        ObservableList<TeacherDavomadModul> view = FXCollections.observableList(tdq.getAllPuiple(teacherID));
      tableItems();
        tb_view.setItems(view);
    }

    public void tableItems(){
        tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tb_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        tb_fish.setCellValueFactory(new PropertyValueFactory<>("fish"));
        tb_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh"));
        tb_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
        tb_sana.setCellValueFactory(new PropertyValueFactory<>("sana"));
        kun1.setCellValueFactory(new PropertyValueFactory<>("kun1"));
        kun2.setCellValueFactory(new PropertyValueFactory<>("kun2"));
        kun3.setCellValueFactory(new PropertyValueFactory<>("kun3"));
        kun4.setCellValueFactory(new PropertyValueFactory<>("kun4"));
        kun5.setCellValueFactory(new PropertyValueFactory<>("kun5"));
        tb_belgilash.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
    }
    public void sort_guruh(ActionEvent actionEvent) {
        String teacherID = "";
        for (TeacherModul tm : q.getTeacherID(String.valueOf(String.valueOf(parol.hashCode()).hashCode()))) {
            teacherID = tm.getId();
        }
        ObservableList<TeacherDavomadModul> view = FXCollections.observableList(tdq.sotrGuruh(teacherID, cbox_guruh.getSelectionModel().getSelectedItem()));
      tableItems();
        tb_view.setItems(view);
    }


    public void davomadniKiritish(ActionEvent actionEvent) {
        if (!cbox_guruh.getSelectionModel().isEmpty()){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String sana = now.format(off);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Davomad to'liq qilindimi ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
//            tb_view.requestFocus();
            for (int i = 0; i < tb_view.getItems().size(); i++) {
                tb_view.getSelectionModel().select(i);
                TeacherDavomadModul btn = tb_view.getSelectionModel().getSelectedItem();
                if (btn.getCheckBox().isSelected()){
                    String oquvchiID = "";
                    String teacherID = lb_teacherID.getText();
                    String guruhID = "";
                    String status = "1";

                    for (OquvchiModul om:q.getOquvchi(btn.getFish())) {
                        oquvchiID = om.getId();
                    }
                    for (GroupModul gm:q.getGuruhID(teacherID,btn.getGuruh())) {
                        guruhID = gm.getId();
                    }
                     if (tdq.checkDavomad(oquvchiID,teacherID,guruhID,sana) == 0){
                    tdq.insertDavomad(oquvchiID,teacherID,guruhID,sana,status);
                     } else if (tdq.checkDavomad(oquvchiID,teacherID,guruhID,sana) != 0) {
                         tdq.updateDavomad(oquvchiID,teacherID,guruhID,sana,status);
                     }
                } else if (!btn.getCheckBox().isSelected()){
                    String oquvchiID = "";
                    String teacherID = lb_teacherID.getText();
                    String guruhID = "";
                    String status = "0";

                    for (OquvchiModul om:q.getOquvchi(btn.getFish())) {
                        oquvchiID = om.getId();
                    }
                    for (GroupModul gm:q.getGuruhID(teacherID,btn.getGuruh())) {
                        guruhID = gm.getId();
                    }
                    if (tdq.checkDavomad(oquvchiID,teacherID,guruhID,sana) == 0){
                        tdq.insertDavomad(oquvchiID,teacherID,guruhID,sana,status);
                    } else if (tdq.checkDavomad(oquvchiID,teacherID,guruhID,sana) != 0) {
                        tdq.updateDavomad(oquvchiID,teacherID,guruhID,sana,status);
                    }
                }
            }
            Alert as = new Alert(Alert.AlertType.CONFIRMATION);
            as.setContentText("Bazaga kiritildi");
        } else if (result.get() == ButtonType.CANCEL){

            }
        }
    }

    public void test1(ActionEvent actionEvent) {

        for (StatusModul adm: tdq.getLastFiveDaysDavomadOfPuiple("17")) {
            System.out.println("id " + adm.getId() + " status " + adm.getStatuses());
        }
    }
}
