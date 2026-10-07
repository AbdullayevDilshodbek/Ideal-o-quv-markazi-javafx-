package sample.admin.oquvchi;

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
import sample.admin.guruh.GroupModul;
import sample.admin.teacher.TeacherModul;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Oquvchilar implements Initializable {
    Connection conn;
    @FXML
    AnchorPane panel_insert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = MysqlConnection.conDb();
        panel_view.setDisable(true);
        panel_edit.setDisable(true);
        lb_insert_info.setVisible(false);
        updateTable1();

    }

    OquvchiQuerys q = new OquvchiQuerys();
    SqlQuerys sq = new SqlQuerys();

    @FXML
    TableView<OquvchiModul> tb_view1;
    @FXML
    TableColumn<OquvchiModul, String> t1_tr;
    @FXML
    TableColumn<OquvchiModul, String> t1_id;
    @FXML
    TableColumn<OquvchiModul, String> t1_fish;
    @FXML
    TableColumn<OquvchiModul, String> t1_phoneNumber;
    @FXML
    TableColumn<OquvchiModul, String> t1_qoshilganSana;

    public void updateTable1() {
        ObservableList<OquvchiModul> view = FXCollections.observableList(q.getAllpupils());
        t1_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        t1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1_fish.setCellValueFactory(new PropertyValueFactory<>("fish"));
        t1_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        t1_qoshilganSana.setCellValueFactory(new PropertyValueFactory<>("qoshilgan_sana"));
        tb_view1.setItems(view);
    }

    @FXML
    TextField edt_search;

    public void search(KeyEvent keyEvent) {
        String detail = edt_search.getText();
        ObservableList<OquvchiModul> view = FXCollections.observableList(q.searchPupils(detail));
        t1_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        t1_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t1_fish.setCellValueFactory(new PropertyValueFactory<>("fish"));
        t1_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        t1_qoshilganSana.setCellValueFactory(new PropertyValueFactory<>("qoshilgan_sana"));
        tb_view1.setItems(view);
    }

    public void open_insert_panel(ActionEvent actionEvent) {
        panel_insert.setDisable(false);
    }

    public void hide_insert_panel(ActionEvent actionEvent) {
        panel_insert.setDisable(true);
    }

    @FXML
    TextField edt_insert_ism;
    @FXML
    TextField edt_insert_familya;
    @FXML
    TextField edt_insert_phone_number;
    @FXML
    Label lb_insert_info;
    @FXML
    ComboBox<String> cbox_insert_fan1;
    @FXML
    ComboBox<String> cbox_insert_teacher1;
    @FXML
    ComboBox<String> cbox_insert_group1;

    //   ----------- this chapter for insert new pupiels ----------------
    public void check_same_puiple(KeyEvent keyEvent) {
        String fish = edt_insert_ism.getText() + " " + edt_insert_familya.getText();
        if (!edt_insert_ism.getText().equals("") && !edt_insert_familya.getText().equals("")) {
            lb_insert_info.setVisible(false);
            lb_insert_info.setText("");
            if (q.checkSamePupil(fish) == 0) {
                lb_insert_info.setVisible(false);
                lb_insert_info.setText("");
                List<String> listFan = new ArrayList<>();
                for (FanModel fm : sq.getAllFan()) {
                    listFan.add(fm.getName());
                }
                cbox_insert_fan1.setItems(FXCollections.observableList(listFan));
            } else {
                edt_insert_phone_number.setText("+");
                lb_insert_info.setVisible(true);
                lb_insert_info.setText("BU ISM VA FAMILYADAGI O'QUVCHI MAVJUD,BIROZ O'ZGARTIRING !!!");
            }
        } else {
            lb_insert_info.setVisible(true);
            lb_insert_info.setText("AVVAL ISM VA FAMILYANI KIRITING ...");
            edt_insert_phone_number.setText("+");
        }
    }

    //----------- first subject choose ----------------
    public void put_teacher1_insert(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm : sq.searchFan(cbox_insert_fan1.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listTeacher = new ArrayList<>();
        for (TeacherModul tm : sq.searchGroupTeacher(fan_id)) {
            listTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cbox_insert_teacher1.setItems(FXCollections.observableList(listTeacher));
    }

    public void put_group1_insert(ActionEvent actionEvent) {
        String teacherID = "";
        if (cbox_insert_teacher1.getSelectionModel().getSelectedItem() != null) {
            String fullName = cbox_insert_teacher1.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(" ");
            String name = fullName.substring(0, findSymbol);
            String familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                teacherID = tm.getId();
            }
        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_insert_group1.setItems(FXCollections.observableList(listGroup));
    }
//    ------------ END first subject choose ---------------------

    //    ------------ Two subject choose ---------------------
    @FXML
    ComboBox<String> cbox_insert_fan2;
    @FXML
    ComboBox<String> cbox_insert_teacher2;
    @FXML
    ComboBox<String> cbox_insert_group2;

    public void put_fan2_insert(ActionEvent actionEvent) {
        List<String> listFan = new ArrayList<>();
        for (FanModel fm : sq.getAllFan()) {
            listFan.add(fm.getName());
        }
        cbox_insert_fan2.setItems(FXCollections.observableList(listFan));
    }

    public void put_teacher2_insert(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm : sq.searchFan(cbox_insert_fan2.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listTeacher = new ArrayList<>();
        for (TeacherModul tm : sq.searchGroupTeacher(fan_id)) {
            listTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cbox_insert_teacher2.setItems(FXCollections.observableList(listTeacher));
    }

    public void put_group2_insert(ActionEvent actionEvent) {
        String teacherID = "";
        if (cbox_insert_teacher2.getSelectionModel().getSelectedItem() != null) {
            String fullName = cbox_insert_teacher2.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(' ');
            String name = fullName.substring(0, findSymbol);
            String familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                teacherID = tm.getId();
            }
        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_insert_group2.setItems(FXCollections.observableList(listGroup));
    }
//    ------------ END Two subject choose ---------------------

    //    ------------ three subject choose ---------------------
    @FXML
    ComboBox<String> cbox_insert_fan3;
    @FXML
    ComboBox<String> cbox_insert_teacher3;
    @FXML
    ComboBox<String> cbox_insert_group3;

    public void put_fan3_insert(ActionEvent actionEvent) {
        List<String> listFan = new ArrayList<>();
        for (FanModel fm : sq.getAllFan()) {
            listFan.add(fm.getName());
        }
        cbox_insert_fan3.setItems(FXCollections.observableList(listFan));
    }

    public void put_teacher3_insert(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm : sq.searchFan(cbox_insert_fan3.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listTeacher = new ArrayList<>();
        for (TeacherModul tm : sq.searchGroupTeacher(fan_id)) {
            listTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cbox_insert_teacher3.setItems(FXCollections.observableList(listTeacher));
    }

    public void put_group3_insert(ActionEvent actionEvent) {
        String teacherID = "";
        if (cbox_insert_teacher3.getSelectionModel().getSelectedItem() != null) {
            String fullName = cbox_insert_teacher3.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(' ');
            String name = fullName.substring(0, findSymbol);
            String familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                teacherID = tm.getId();
            }
        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_insert_group3.setItems(FXCollections.observableList(listGroup));
    }
    //    ------------ END three subject choose ---------------------

    //    ------------ four subject choose ---------------------
    @FXML
    ComboBox<String> cbox_insert_fan4;
    @FXML
    ComboBox<String> cbox_insert_teacher4;
    @FXML
    ComboBox<String> cbox_insert_group4;

    public void put_fan4_insert(ActionEvent actionEvent) {
        List<String> listFan = new ArrayList<>();
        for (FanModel fm : sq.getAllFan()) {
            listFan.add(fm.getName());
        }
        cbox_insert_fan4.setItems(FXCollections.observableList(listFan));
    }

    public void put_teacher4_insert(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm : sq.searchFan(cbox_insert_fan4.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listTeacher = new ArrayList<>();
        for (TeacherModul tm : sq.searchGroupTeacher(fan_id)) {
            listTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cbox_insert_teacher4.setItems(FXCollections.observableList(listTeacher));
    }

    public void put_group4_insert(ActionEvent actionEvent) {
        String teacherID = "";
        if (cbox_insert_teacher4.getSelectionModel().getSelectedItem() != null) {
            String fullName = cbox_insert_teacher4.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(' ');
            String name = fullName.substring(0, findSymbol);
            String familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                teacherID = tm.getId();
            }
        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_insert_group4.setItems(FXCollections.observableList(listGroup));
    }
//    ------------ END four subject choose ---------------------

    //    ------------ five subject choose ---------------------
    @FXML
    ComboBox<String> cbox_insert_fan5;
    @FXML
    ComboBox<String> cbox_insert_teacher5;
    @FXML
    ComboBox<String> cbox_insert_group5;

    public void put_fan5_insert(ActionEvent actionEvent) {
        List<String> listFan = new ArrayList<>();
        for (FanModel fm : sq.getAllFan()) {
            listFan.add(fm.getName());
        }
        cbox_insert_fan5.setItems(FXCollections.observableList(listFan));
    }

    public void put_teacher5_insert(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm : sq.searchFan(cbox_insert_fan5.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listTeacher = new ArrayList<>();
        for (TeacherModul tm : sq.searchGroupTeacher(fan_id)) {
            listTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cbox_insert_teacher5.setItems(FXCollections.observableList(listTeacher));
    }

    public void put_group5_insert(ActionEvent actionEvent) {
        String teacherID = "";
        if (cbox_insert_teacher5.getSelectionModel().getSelectedItem() != null) {
            String fullName = cbox_insert_teacher5.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(' ');
            String name = fullName.substring(0, findSymbol);
            String familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                teacherID = tm.getId();
            }
        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_insert_group5.setItems(FXCollections.observableList(listGroup));
    }

    //    ------------ END five subject choose ---------------------
    @FXML
    DatePicker qayd_sana;
    @FXML
    DatePicker qayd_sana2;
    @FXML
    TextField edt_chegirma1_insert;
    @FXML
    TextField edt_chegirma2_insert;
    @FXML
    TextField edt_chegirma3_insert;
    @FXML
    TextField edt_chegirma4_insert;
    @FXML
    TextField edt_chegirma5_insert;

    public void insert_puipleAnd_subject(ActionEvent actionEvent) {
        String fan1 = cbox_insert_fan1.getSelectionModel().getSelectedItem();
        String teacher1 = cbox_insert_teacher1.getSelectionModel().getSelectedItem();
        String group1 = cbox_insert_group1.getSelectionModel().getSelectedItem();
        LocalDate sana = qayd_sana.getValue();
        if (sana != null) {
            DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String sanaFromat = qayd_sana.getValue().format(off);
            lb_insert_info.setVisible(false);
            lb_insert_info.setText("");
            if (cbox_insert_fan1.getSelectionModel().getSelectedItem() != null &&
                    cbox_insert_teacher1.getSelectionModel().getSelectedItem() != null
                    && cbox_insert_group1.getSelectionModel().getSelectedItem() != null) {
                String fish = edt_insert_ism.getText() + " " + edt_insert_familya.getText();
                String phone_number = edt_insert_phone_number.getText();
                String oquvchiID = "";
                String fanID = "";
                String teacherID = "";
                String guruhID = "";
                String qarz = "";
                if (!edt_insert_ism.getText().equals("") && !edt_insert_familya.getText().equals("") && !phone_number.equals("")) {
                    if (q.checkSamePupil(fish) == 0) {
                        q.insertPuipleToOquvchilarTable(fish, phone_number, sanaFromat);
                        for (OquvchiModul om : q.getOquvchiID(fish)) {
                            oquvchiID = om.getId();
                        }
                        for (FanModel fm : sq.searchFan(fan1)) {
                            fanID = fm.getId();
                        }
                        if (cbox_insert_teacher1.getSelectionModel().getSelectedItem() != null) {
                            String fullName = cbox_insert_teacher1.getSelectionModel().getSelectedItem();
                            int findSymbol = fullName.indexOf(' ');
                            String Tname = fullName.substring(0, findSymbol);
                            String Tfamilya = fullName.substring(findSymbol + 1);
                            for (TeacherModul tm : sq.getTeacherId(Tname, Tfamilya)) {
                                teacherID = tm.getId();
                            }
                        }
                        for (GroupModul gm : q.getGroupID(group1, fanID, teacherID)) {
                            guruhID = gm.getId();
                        }
                        if (q.checkSamePuipleGroup(oquvchiID, fanID, teacherID, guruhID) == 0) {
                            YearMonth yearMonthObject = YearMonth.of(sana.getYear(), sana.getMonth());
                            int daysInMonth = yearMonthObject.lengthOfMonth();
                            int qaydSana = sana.getDayOfMonth();
                            int oqiganSana = daysInMonth - qaydSana + 1;
                            String guruh_narxi = "";
                            for (GroupModul gm : q.searchGetGroupOfNarx(guruhID)) {
                                guruh_narxi = gm.getNarx();
                            }
                            String chegirma1 = edt_chegirma1_insert.getText();
                            Double intqarz = ((Double.parseDouble(guruh_narxi) * oqiganSana) / daysInMonth) * (100 - Double.parseDouble(chegirma1)) / 100;
                            qarz = String.valueOf(intqarz);
                            q.insertPuipleToOquvchi_gutuhTable(oquvchiID, fanID, teacherID, guruhID, qarz, chegirma1, sanaFromat);

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("O'quvchi bazaga kiritildi...");
                            alert.show();
                        }
// --------------- FAN 2 BO'ICHA INSERT -------------
                        if (cbox_insert_fan2.getSelectionModel().getSelectedItem() != null && cbox_insert_teacher2.getSelectionModel().getSelectedItem() != null
                                && cbox_insert_group2.getSelectionModel().getSelectedItem() != null) {
                            String fan2 = cbox_insert_fan2.getSelectionModel().getSelectedItem();
                            String group2 = cbox_insert_group2.getSelectionModel().getSelectedItem();
                            String fanID2 = "";
                            String teacherID2 = "";
                            String guruhID2 = "";

                            for (FanModel fm : sq.searchFan(fan2)) {
                                fanID2 = fm.getId();
                            }
                            String fullName2 = cbox_insert_teacher2.getSelectionModel().getSelectedItem();
                            int findSymbol2 = fullName2.indexOf(" ");
                            String Tname2 = fullName2.substring(0, findSymbol2);
                            String Tfamilya2 = fullName2.substring(findSymbol2 + 1);
                            for (TeacherModul tm : sq.getTeacherId(Tname2, Tfamilya2)) {
                                teacherID2 = tm.getId();
                            }
                            for (GroupModul gm : q.getGroupID(group2, fanID2, teacherID2)) {
                                guruhID2 = gm.getId();
                            }
                            if (q.checkSamePuipleGroup(oquvchiID, fanID2, teacherID2, guruhID2) == 0) {
                                YearMonth yearMonthObject = YearMonth.of(sana.getYear(), sana.getMonth());
                                int daysInMonth = yearMonthObject.lengthOfMonth();
                                int qaydSana = sana.getDayOfMonth();
                                int oqiganSana = daysInMonth - qaydSana + 1;
                                String guruh_narxi = "";
                                for (GroupModul gm : q.searchGetGroupOfNarx(guruhID2)) {
                                    guruh_narxi = gm.getNarx();
                                }
                                String chegirma2 = edt_chegirma2_insert.getText();
                                int intqarz = (Integer.parseInt(guruh_narxi) * oqiganSana) / daysInMonth * (100 - Integer.parseInt(chegirma2)) / 100;
                                qarz = String.valueOf(intqarz);
                                q.insertPuipleToOquvchi_gutuhTable(oquvchiID, fanID2, teacherID2, guruhID2, qarz, chegirma2, sanaFromat);
                            }
                        }
                        // --------------- FAN 3 BO'ICHA INSERT -------------
                        if (cbox_insert_fan3.getSelectionModel().getSelectedItem() != null && cbox_insert_teacher3.getSelectionModel().getSelectedItem() != null
                                && cbox_insert_group3.getSelectionModel().getSelectedItem() != null) {
                            String fan3 = cbox_insert_fan3.getSelectionModel().getSelectedItem();
                            String group3 = cbox_insert_group3.getSelectionModel().getSelectedItem();
                            String fanID3 = "";
                            String teacherID3 = "";
                            String guruhID3 = "";

                            for (FanModel fm : sq.searchFan(fan3)) {
                                fanID3 = fm.getId();
                            }
                            if (cbox_insert_teacher3.getSelectionModel().getSelectedItem() != null) {
                                String fullName3 = cbox_insert_teacher3.getSelectionModel().getSelectedItem();
                                int findSymbol3 = fullName3.indexOf(" ");
                                String Tname3 = fullName3.substring(0, findSymbol3);
                                String Tfamilya3 = fullName3.substring(findSymbol3 + 1);
                                for (TeacherModul tm : sq.getTeacherId(Tname3, Tfamilya3)) {
                                    teacherID3 = tm.getId();
                                }
                            }
                            for (GroupModul gm : q.getGroupID(group3, fanID3, teacherID3)) {
                                guruhID3 = gm.getId();
                            }
                            if (q.checkSamePuipleGroup(oquvchiID, fanID3, teacherID3, guruhID3) == 0) {
                                YearMonth yearMonthObject = YearMonth.of(sana.getYear(), sana.getMonth());
                                int daysInMonth = yearMonthObject.lengthOfMonth();
                                int qaydSana = sana.getDayOfMonth();
                                int oqiganSana = daysInMonth - qaydSana + 1;
                                String guruh_narxi = "";
                                for (GroupModul gm : q.searchGetGroupOfNarx(guruhID3)) {
                                    guruh_narxi = gm.getNarx();
                                }
                                String chegirma3 = edt_chegirma3_insert.getText();
                                int intqarz = (Integer.parseInt(guruh_narxi) * oqiganSana) / daysInMonth * (100 - Integer.parseInt(chegirma3)) / 100;
                                qarz = String.valueOf(intqarz);
                                q.insertPuipleToOquvchi_gutuhTable(oquvchiID, fanID3, teacherID3, guruhID3, qarz, chegirma3, sanaFromat);
                            }
                        }
                        // --------------- FAN 4 BO'ICHA INSERT -------------
                        if (cbox_insert_fan4.getSelectionModel().getSelectedItem() != null && cbox_insert_teacher4.getSelectionModel().getSelectedItem() != null
                                && cbox_insert_group4.getSelectionModel().getSelectedItem() != null) {
                            String fan4 = cbox_insert_fan4.getSelectionModel().getSelectedItem();
                            String group4 = cbox_insert_group4.getSelectionModel().getSelectedItem();
                            String fanID4 = "";
                            String teacherID4 = "";
                            String guruhID4 = "";

                            for (FanModel fm : sq.searchFan(fan4)) {
                                fanID4 = fm.getId();
                            }
                            if (cbox_insert_teacher4.getSelectionModel().getSelectedItem() != null) {
                                String fullName4 = cbox_insert_teacher4.getSelectionModel().getSelectedItem();
                                int findSymbol4 = fullName4.indexOf(" ");
                                String Tname4 = fullName4.substring(0, findSymbol4);
                                String Tfamilya4 = fullName4.substring(findSymbol4 + 1);
                                for (TeacherModul tm : sq.getTeacherId(Tname4, Tfamilya4)) {
                                    teacherID4 = tm.getId();
                                }
                            }
                            for (GroupModul gm : q.getGroupID(group4, fanID4, teacherID4)) {
                                guruhID4 = gm.getId();
                            }
                            if (q.checkSamePuipleGroup(oquvchiID, fanID4, teacherID4, guruhID4) == 0) {
                                YearMonth yearMonthObject = YearMonth.of(sana.getYear(), sana.getMonth());
                                int daysInMonth = yearMonthObject.lengthOfMonth();
                                int qaydSana = sana.getDayOfMonth();
                                int oqiganSana = daysInMonth - qaydSana + 1;
                                String guruh_narxi = "";
                                for (GroupModul gm : q.searchGetGroupOfNarx(guruhID4)) {
                                    guruh_narxi = gm.getNarx();
                                }
                                String chegirma4 = edt_chegirma4_insert.getText();
                                int intqarz = (Integer.parseInt(guruh_narxi) * oqiganSana) / daysInMonth * (100 - Integer.parseInt(chegirma4)) / 100;
                                qarz = String.valueOf(intqarz);
                                q.insertPuipleToOquvchi_gutuhTable(oquvchiID, fanID4, teacherID4, guruhID4, qarz, chegirma4, sanaFromat);
                            }
                        }
// --------------- FAN 5 BO'ICHA INSERT -------------
                        if (cbox_insert_fan5.getSelectionModel().getSelectedItem() != null && cbox_insert_teacher5.getSelectionModel().getSelectedItem() != null
                                && cbox_insert_group5.getSelectionModel().getSelectedItem() != null) {
                            String fan5 = cbox_insert_fan5.getSelectionModel().getSelectedItem();
                            String group5 = cbox_insert_group5.getSelectionModel().getSelectedItem();
                            String fanID5 = "";
                            String teacherID5 = "";
                            String guruhID5 = "";

                            for (FanModel fm : sq.searchFan(fan5)) {
                                fanID5 = fm.getId();
                            }
                            if (cbox_insert_teacher5.getSelectionModel().getSelectedItem() != null) {
                                String fullName5 = cbox_insert_teacher5.getSelectionModel().getSelectedItem();
                                int findSymbol5 = fullName5.indexOf(" ");
                                String Tname5 = fullName5.substring(0, findSymbol5);
                                String Tfamilya5 = fullName5.substring(findSymbol5 + 1);
                                for (TeacherModul tm : sq.getTeacherId(Tname5, Tfamilya5)) {
                                    teacherID5 = tm.getId();
                                }
                            }
                            for (GroupModul gm : q.getGroupID(group5, fanID5, teacherID5)) {
                                guruhID5 = gm.getId();
                            }
                            if (q.checkSamePuipleGroup(oquvchiID, fanID5, teacherID5, guruhID5) == 0) {
                                YearMonth yearMonthObject = YearMonth.of(sana.getYear(), sana.getMonth());
                                int daysInMonth = yearMonthObject.lengthOfMonth();
                                int qaydSana = sana.getDayOfMonth();
                                int oqiganSana = daysInMonth - qaydSana + 1;
                                String guruh_narxi = "";
                                for (GroupModul gm : q.searchGetGroupOfNarx(guruhID5)) {
                                    guruh_narxi = gm.getNarx();
                                }
                                String chegirma5 = edt_chegirma5_insert.getText();
                                int intqarz = (Integer.parseInt(guruh_narxi) * oqiganSana) / daysInMonth * (100 - Integer.parseInt(chegirma5)) / 100;
                                qarz = String.valueOf(intqarz);
                                q.insertPuipleToOquvchi_gutuhTable(oquvchiID, fanID5, teacherID5, guruhID5, qarz, chegirma5, sanaFromat);
                            }
                        }
                        edt_chegirma1_insert.setText("0");
                        edt_chegirma2_insert.setText("0");
                        edt_chegirma3_insert.setText("0");
                        edt_chegirma4_insert.setText("0");
                        edt_chegirma5_insert.setText("0");

                        cbox_insert_fan1.getSelectionModel().clearSelection();
                        cbox_insert_fan2.getSelectionModel().clearSelection();
                        cbox_insert_fan3.getSelectionModel().clearSelection();
                        cbox_insert_fan4.getSelectionModel().clearSelection();
                        cbox_insert_fan5.getSelectionModel().clearSelection();
                        cbox_insert_fan1.setPromptText("FANI TANLANG");
                        cbox_insert_fan2.setPromptText("FANI TANLANG");
                        cbox_insert_fan3.setPromptText("FANI TANLANG");
                        cbox_insert_fan4.setPromptText("FANI TANLANG");
                        cbox_insert_fan5.setPromptText("FANI TANLANG");

                        cbox_insert_teacher1.getSelectionModel().clearSelection();
                        cbox_insert_teacher2.getSelectionModel().clearSelection();
                        cbox_insert_teacher3.getSelectionModel().clearSelection();
                        cbox_insert_teacher4.getSelectionModel().clearSelection();
                        cbox_insert_teacher5.getSelectionModel().clearSelection();
                        cbox_insert_teacher1.setPromptText("O'QTUVCHINI TANLANG");
                        cbox_insert_teacher2.setPromptText("O'QTUVCHINI TANLANG");
                        cbox_insert_teacher3.setPromptText("O'QTUVCHINI TANLANG");
                        cbox_insert_teacher4.setPromptText("O'QTUVCHINI TANLANG");
                        cbox_insert_teacher5.setPromptText("O'QTUVCHINI TANLANG");

                        cbox_insert_group1.getSelectionModel().clearSelection();
                        cbox_insert_group2.getSelectionModel().clearSelection();
                        cbox_insert_group3.getSelectionModel().clearSelection();
                        cbox_insert_group4.getSelectionModel().clearSelection();
                        cbox_insert_group5.getSelectionModel().clearSelection();
                        cbox_insert_group1.setPromptText("GURUHNI TANLANG");
                        cbox_insert_group2.setPromptText("GURUHNI TANLANG");
                        cbox_insert_group3.setPromptText("GURUHNI TANLANG");
                        cbox_insert_group4.setPromptText("GURUHNI TANLANG");
                        cbox_insert_group5.setPromptText("GURUHNI TANLANG");

                        edt_insert_familya.setText("");
                        edt_insert_phone_number.setText("+");
//                ------------
                        updateTable1();
                    } else {
                        Alert aa = new Alert(Alert.AlertType.WARNING);
                        aa.setContentText("Bu o'quvchi bazada bor. Ehtimol siz saqlash tugmasini 2 martta bosdingiz !!!");
                        aa.show();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("Ism, familya yoki telefon raqam kiritilmagan !!!");
                    a.show();
                }
            }
        } else {
            lb_insert_info.setVisible(true);
            lb_insert_info.setText("Qayd sanasini tanlamadingiz");
        }
    }
//    ---------- END INSERT NEW PUPLIELS ---------------------------

    //    ----------- remov puiple from oquvchi_guruh table and oquvchilar table
    @FXML
    AnchorPane panel_view;
    @FXML
    TableView<OquvGuruhModul> tb_view2;
    @FXML
    TableColumn<OquvchiModul, String> t2_id;
    @FXML
    TableColumn<OquvchiModul, String> t2_fish;
    @FXML
    TableColumn<OquvchiModul, String> t2_fan;
    @FXML
    TableColumn<OquvchiModul, String> t2_teacher;
    @FXML
    TableColumn<OquvchiModul, String> t2_group;
    @FXML
    TableColumn<OquvchiModul, String> t2_qarz;
    @FXML
    TableColumn<OquvchiModul, String> t2_chegirma;
    @FXML
    TableColumn<OquvchiModul, String> t3_qarz;
    @FXML
    TableColumn<OquvchiModul, String> t3_chegirma;
    @FXML
    TableColumn<OquvchiModul, String> t2_sana;

    public void view_puipleGroup(ActionEvent actionEvent) {
        panel_view.setDisable(false);
    }

    public void put_puipleDataToTB2(MouseEvent mouseEvent) {
        lb_fan3.setText("FAN");
        lb_teacher3.setText("O'QITUVCHI");
        lb_group3.setText("GURUH");
        lb_chegirma3.setText("CHEGIRMA");
        btn_ozgarishni_saqlash.setDisable(true);
        List<String> empty = new ArrayList<>();
        cbox_edit_group.setItems(FXCollections.observableList(empty));
        cbox_edit_group.setPromptText("GURUHNITANLANG");
        update_table2();
        update_table3();
        panel_insert2.setDisable(true);
        OquvchiModul om = tb_view1.getSelectionModel().getSelectedItem();
        String fish = om.getFish();
        String number = om.getPhone_number();
        edt_edit_fish.setText(fish);
        for (OquvchiModul om2 : q.getOquvchiID(om.getFish())) {
            lb_id.setText(om2.getId());
            edt_edit_fish.setText(om2.getFish());
            edt_edit_phone_number.setText(om2.getPhone_number());
            lb_last_fish.setText(om2.getFish());
            lb_last_number.setText(om2.getPhone_number());
        }
        if (!om.getId().equals("")) {
            allow = 1;
        } else {
            allow = 0;
        }
    }

    public void update_table2() {
        OquvchiModul om = tb_view1.getSelectionModel().getSelectedItem();
        String puipleID = om.getId();
        ObservableList<OquvGuruhModul> view = FXCollections.observableList(q.getSelectPuiple(puipleID));
        t2_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t2_fish.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
        t2_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        t2_teacher.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        t2_group.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
        t2_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
        t2_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
        t2_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
        tb_view2.setItems(view);
    }

    public void update_table3() {
        OquvchiModul om = tb_view1.getSelectionModel().getSelectedItem();
        String puipleID = om.getId();
        ObservableList<OquvGuruhModul> view = FXCollections.observableList(q.getSelectPuiple(puipleID));
        t3_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t3_fish.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
        t3_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        t3_teacher.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        t3_group.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
        t3_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
        t3_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
        t3_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
        tb_view3.setItems(view);
    }

    public void hide_view_panel(ActionEvent actionEvent) {
        panel_view.setDisable(true);
    }

    public void remov_puipleFromGroup(ActionEvent actionEvent) {
        OquvGuruhModul om = tb_view2.getSelectionModel().getSelectedItem();
        String id = om.getId();
        String fish = om.getOquvchi_id();
        String fan = om.getFan_id();
        String teacher = om.getTeacher_id();
        String guruh = om.getGuruh_id();
        if (allowForRemove == 1) {
            String fishID = "";
            String fanID = "";
            String teacherID = "";
            String guruhID = "";
            String sana = "";

            for (OquvchiModul ogm : q.getOquvchiID(fish)) {
                fishID = ogm.getId();
            }
            for (FanModel fm : sq.searchFan(fan)) {
                fanID = fm.getId();
            }
            if (!teacher.equals("")) {
                String fullName = teacher;
                int findSymbol = fullName.indexOf(' ');
                String Tname = fullName.substring(0, findSymbol);
                String Tfamilya = fullName.substring(findSymbol + 1);
                for (TeacherModul tm : sq.getTeacherId(Tname, Tfamilya)) {
                    teacherID = tm.getId();
                }
            }
            for (GroupModul gm : q.getGroupID(guruh, fanID, teacherID)) {
                guruhID = gm.getId();
            }
            DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(off.format(now));
            sana = off.format(now);
//            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
//            String vaqt = date.toString();
//            sana = vaqt.substring(0, 11);
            if (!id.equals("")) {
                String qarz = "";
                for (OquvGuruhModul ogm : q.getSelectPuipleWithoquvchGuruhId(id)) {
                    qarz = ogm.getQarz();
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Siz chindan " + fish + " ni guruhdan o'chirmoqchisizmi ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    q.insertPuipleFromOquvguruhToKarzinka(fish, fanID, teacherID, qarz, sana);
                    q.deleteOquvchiGuruh(id);
                    test();
                } else if (result.get() == ButtonType.CANCEL) {
                    test();
                }
                if (q.getPuipleOfGroupsCount(fishID) == 0) {
                    q.deletePuipleFromOquvchilar(fishID);
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText(fish + " hech qaysi guruhda yo'qligi sababli u o'quvchilar bazasidan o'chirildi va karzinkaga saqlandi");
                    a.show();
                }
            }
            updateTable1();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Siz avval yuqoridagi jadvaldan tanlovni amalga oshiring !!!");
            a.show();
        }
    }
    //    ----------- END remov puiple from oquvchi_guruh table and oquvchilar table ---------------

    //--------------- Chapter EDIT ---------------
    @FXML
    AnchorPane panel_edit;

    public void open_edit_panel(ActionEvent actionEvent) {
        panel_edit.setDisable(false);
    }

    public void hide_edit_panel(ActionEvent actionEvent) {
        panel_edit.setDisable(true);
    }

    @FXML
    TextField edt_edit_fish;
    @FXML
    TextField edt_edit_phone_number;
    @FXML
    TableView<OquvGuruhModul> tb_view3;
    @FXML
    TableColumn<OquvGuruhModul, String> t3_id;
    @FXML
    TableColumn<OquvGuruhModul, String> t3_fish;
    @FXML
    TableColumn<OquvGuruhModul, String> t3_fan;
    @FXML
    TableColumn<OquvGuruhModul, String> t3_teacher;
    @FXML
    TableColumn<OquvGuruhModul, String> t3_group;
    @FXML
    TableColumn<OquvGuruhModul, String> t3_sana;
    @FXML
    Label lb_last_fish;
    @FXML
    Label lb_last_number;
    @FXML
    Label lb_fan3;
    @FXML
    Label lb_teacher3;
    @FXML
    Label lb_group3;
    @FXML
    Label lb_chegirma3;
    @FXML
    Label oquv_guruhID;
    @FXML
    Label last_fanID;
    @FXML
    Label last_groupID;
    @FXML
    Label last_teacherID;
    @FXML
    Label lb_qarz;
    @FXML
    Label cancle_updateDate;
    @FXML
    ComboBox<String> cbox_edit_fan;
    @FXML
    ComboBox<String> cbox_edit_teacher;
    @FXML
    ComboBox<String> cbox_edit_group;
    @FXML
    TextField edt_edit_chegirma;
    @FXML
    Button btn_ozgarishni_saqlash;

    public void put_puipleDate3(MouseEvent mouseEvent) {
        if (!lb_fan3.getText().equals("FAN")) {
        }
        btn_ozgarishni_saqlash.setDisable(false);
        OquvGuruhModul ogm = tb_view3.getSelectionModel().getSelectedItem();
        String fish = ogm.getOquvchi_id();
        lb_qarz.setText(ogm.getQarz());
        for (OquvGuruhModul ogm23 : q.getSelectPuipleWithoquvchGuruhId(ogm.getId())) {
            cancle_updateDate.setText(ogm23.getUpdate_date());
        }
        String number = "";
        for (OquvchiModul om : q.getOquvchiID(fish)) {
            number = om.getPhone_number();
        }

        String id = ogm.getId();
        String fan = ogm.getFan_id();
        String teacher = ogm.getTeacher_id();
        String group = ogm.getGuruh_id();
        String chegirma = ogm.getChegirma();
        String sana = ogm.toString();
        oquv_guruhID.setText(id);
        for (FanModel fm : sq.searchFan(fan)) {
            last_fanID.setText(fm.getId());
        }
        if (!teacher.equals("")) {
            String fullName = teacher;
            int findSymbol = fullName.indexOf(" ");
            String name = fullName.substring(0, findSymbol);
            String familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                last_teacherID.setText(tm.getId());
            }
        }
        for (GroupModul gm : q.getGroupID(group, last_fanID.getText(), last_teacherID.getText())) {
            last_groupID.setText(gm.getId());
        }

        edt_edit_fish.setText(fish);
        edt_edit_phone_number.setText(number);
        lb_last_fish.setText(fish);
        lb_last_number.setText(number);

        lb_fan3.setText(fan);
        lb_teacher3.setText(teacher);
        lb_group3.setText(group);
        lb_chegirma3.setText(chegirma);
        List<String> listFan = new ArrayList<>();
        for (FanModel fm : sq.getAllFan()) {
            listFan.add(fm.getName());
        }
        cbox_edit_fan.setItems(FXCollections.observableList(listFan));
//         ------------ Labelga Teacher name va fan name ni olib beradi ----
        String teacherID = "";
//        if (cbox_edit_teacher.getSelectionModel().getSelectedItem() != null) {
//            String fullName = cbox_edit_teacher.getSelectionModel().getSelectedItem();
        String fullName = lb_teacher3.getText();
        int findSymbol = fullName.indexOf(" ");
        String name = fullName.substring(0, findSymbol);
        String familya = fullName.substring(findSymbol + 1);
        for (TeacherModul tm : sq.getTeacherId(name, familya)) {
            teacherID = tm.getId();
        }
//        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_edit_group.setItems(FXCollections.observableList(listGroup));
//         ------------ The End Labelga Teacher name va fan name ni olib beradi ----
    }

    public void save_edit_like_names(ActionEvent actionEvent) {
        String fish = edt_edit_fish.getText();
        String number = edt_edit_phone_number.getText();
        String id = lb_id.getText();
        if (!fish.equals("") && !number.equals("")) {
            q.updatePuipleFishAndNumber(fish, number, id);
            if (q.checkSamePupil(fish) == 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Ma'lumotlar yangilandi...");
                Optional<ButtonType> result = alert.showAndWait();
                updateTB3();
                updateTable1();
            } else {
                cancleUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(fish + " ni biroz o'zgartiring, Chunki u boshqa o'quvchining ism va familyasi bilan bir xil");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    cancleUpdate();
                    tb_view3.refresh();
                } else if (result.get() == ButtonType.CANCEL) {
                    cancleUpdate();
                    tb_view3.refresh();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Siz F.I.SH YOKI TELEFON RAQAMNI KIRITMADINGIZ");
            alert.show();
        }
    }

    public void cancleUpdate() {
        String fish = lb_last_fish.getText();
        String number = lb_last_number.getText();
        String id = lb_id.getText();
        q.updatePuipleFishAndNumber(fish, number, id);
    }

    public void updateTB3() {
        ObservableList<OquvGuruhModul> view = FXCollections.observableList(q.getSelectPuiple(lb_id.getText()));
        t3_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t3_fish.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
        t3_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        t3_teacher.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        t3_group.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
        t3_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
        t3_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
        tb_view3.setItems(view);
    }

    public void put_teacherToEdit(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm : sq.searchFan(cbox_edit_fan.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listTeacher = new ArrayList<>();
        for (TeacherModul tm : sq.searchGroupTeacher(fan_id)) {
            listTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cbox_edit_teacher.setItems(FXCollections.observableList(listTeacher));
    }

    public void put_groupToEdit(ActionEvent actionEvent) {
        String teacherID = "";
        if (cbox_edit_teacher.getSelectionModel().getSelectedItem() != null) {
            String fullName = cbox_edit_teacher.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(" ");
            String name = fullName.substring(0, findSymbol);
            String familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                teacherID = tm.getId();
            }
        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_edit_group.setItems(FXCollections.observableList(listGroup));
    }

    public void ozgarishni_saqlash(ActionEvent actionEvent) {
        String fish = lb_last_fish.getText();
        String fan = lb_fan3.getText();
        String teacher = lb_teacher3.getText();
        String group = cbox_edit_group.getSelectionModel().getSelectedItem();

        String phone_number = edt_insert_phone_number.getText();
        String chegirma = edt_edit_chegirma.getText();
        String oquvchiID = "";
        String fanID = "";
        String teacherID = "";
        String guruhID = "";
        for (OquvchiModul om : q.getOquvchiID(fish)) {
            oquvchiID = om.getId();
        }
        for (FanModel fm : sq.searchFan(fan)) {
            fanID = fm.getId();
        }

        if (!lb_teacher3.getText().equals("O'QITUVCHI") && !lb_teacher3.getText().equals("")) {
            String fullName = lb_teacher3.getText();
            int findSymbol = fullName.indexOf(" ");
            String Tname = fullName.substring(0, findSymbol);
            String Tfamilya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(Tname, Tfamilya)) {
                teacherID = tm.getId();
            }
        }
        for (GroupModul gm : q.getGroupID(group, fanID, teacherID)) {
            guruhID = gm.getId();
        }
        String id = oquv_guruhID.getText();
        System.out.println(oquv_guruhID.getText());
        if (cbox_edit_group.getSelectionModel().getSelectedItem() != null) {
//            -------- if teacher or group will do change --> recalculate new qarz ---------------
            int check = 2;
            String update_dateSt = "";
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (OquvGuruhModul ogm1 : q.getSelectPuipleWithoquvchGuruhId(oquv_guruhID.getText())) {
                update_dateSt = ogm1.getUpdate_date() + " 11:30";
            }
            System.out.println(update_dateSt + " salom");
            LocalDateTime update_date = LocalDateTime.parse(update_dateSt, formatter);
            if (today.getMonth().getValue() == update_date.getMonth().getValue() && today.getYear() == update_date.getYear()) {
                check = 0;
            } else {
                check = 1;
            }

            DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String oqigankuni = off.format(now).substring(0, 2);
            YearMonth yearMonthObject = YearMonth.of(now.getYear(), now.getMonth());
            int daysInMonth = yearMonthObject.lengthOfMonth();
            int newOqishKunlari = daysInMonth - now.getDayOfMonth() + 1;
            String lastGuruhID = last_groupID.getText();
            String lastGuruhNarxi = "";
            for (GroupModul gm : q.searchGetGroupOfNarx(lastGuruhID)) {
                lastGuruhNarxi = gm.getNarx();
            }
            String newGuruhNarxi = "";
            for (GroupModul gm : q.searchGetGroupOfNarx(guruhID)) {
                newGuruhNarxi = gm.getNarx();
            }

            if (check == 0) {
                Double b_qarz = 0.0;
                Double last_chegirma = 0.0;
                for (OquvGuruhModul ogm : q.getSelectPuipleWithoquvchGuruhId(oquv_guruhID.getText())) {
                    b_qarz = Double.parseDouble(ogm.getQarz());
                    last_chegirma = Double.parseDouble(ogm.getChegirma());
                    System.out.println(b_qarz + " - " + last_chegirma);
                }
                Double qarz1 = b_qarz - ((daysInMonth - update_date.getDayOfMonth() + 1) / Double.parseDouble(String.valueOf(daysInMonth)))
                        * (1 - last_chegirma / 100) * Double.parseDouble(lastGuruhNarxi) +
                        ((Double.parseDouble(String.valueOf(now.getDayOfMonth())) - Double.parseDouble(String.valueOf(update_date.getDayOfMonth()))) / daysInMonth) * (1 - last_chegirma / 100) * Double.parseDouble(lastGuruhNarxi);
                Double qarz2 = qarz1 + ((Double.parseDouble(String.valueOf(daysInMonth - now.getDayOfMonth() + 1))) / Double.parseDouble(String.valueOf(daysInMonth))) * (1 - Double.parseDouble(chegirma) / 100) * Double.parseDouble(newGuruhNarxi);
                String StringQarzUmumiyI = String.valueOf(qarz2);
                LocalDateTime noww = LocalDateTime.now();
                q.updatePuipleFanTeacherAndGroup(fanID, teacherID, guruhID, StringQarzUmumiyI, chegirma, off.format(noww), id);
            }

            if (check == 1) {
                Double b_qarz = 0.0;
                Double last_chegirma = 0.0;
                for (OquvGuruhModul ogm : q.getSelectPuiple(oquv_guruhID.getText())) {
                    b_qarz = Double.parseDouble(ogm.getQarz());
                    last_chegirma = Double.parseDouble(ogm.getChegirma());
                }
                Double qarz = b_qarz - Double.parseDouble(lastGuruhNarxi) + ((now.getDayOfMonth() - 1) / Double.parseDouble(String.valueOf(daysInMonth))) * (1 - last_chegirma / 100) * Double.parseDouble(lastGuruhNarxi);
                Double qarz2 = qarz + ((daysInMonth - now.getDayOfMonth() + 1) / Double.parseDouble(String.valueOf(daysInMonth))) * (1 - Double.parseDouble(chegirma) / 100) * Double.parseDouble(newGuruhNarxi);
                String StringQarzUmumiy = String.valueOf(qarz2);
                DateTimeFormatter offf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime noww = LocalDateTime.now();
                q.updatePuipleFanTeacherAndGroup(fanID, teacherID, guruhID, StringQarzUmumiy, chegirma, off.format(noww), id);
            }

            if (q.checkSamePuipleGroup(oquvchiID, fanID, teacherID, guruhID) == 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Bajarildi");
                Optional<ButtonType> result = alert.showAndWait();
                btn_ozgarishni_saqlash.setDisable(true);
                if (result.get() == ButtonType.OK) {
                    updateTB3();
                } else if (result.get() == ButtonType.CANCEL) {
                    updateTB3();
                }
            } else {
                fanID = last_fanID.getText();
                teacherID = last_teacherID.getText();
                guruhID = last_groupID.getText();
                String cancleQarz = lb_qarz.getText();
                chegirma = lb_chegirma3.getText();
                String LastupdateDate = cancle_updateDate.getText();
                q.updatePuipleFanTeacherAndGroup(fanID, teacherID, guruhID, cancleQarz, chegirma, LastupdateDate, id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Bu o'quvchi avvaldan " + teacher + " ning shu guruhida bor... ");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Siz yetarlicha tanlov qilmadingiz. Chapdagi barcha tanlovni amalga oshiring :( ");
            alert.show();
        }

    }

    //    --------- Chapter insert 2 ---------
    @FXML
    AnchorPane panel_insert2;
    @FXML
    ComboBox<String> cbox_insert2_fan;
    @FXML
    ComboBox<String> cbox_insert2_teacher;
    @FXML
    ComboBox<String> cbox_insert2_group;
    @FXML
    Label lb_id;
    int allow = 0;
    int allowForRemove = 0;

    public void allow_insert2(MouseEvent mouseEvent) {
        OquvGuruhModul ogm = tb_view2.getSelectionModel().getSelectedItem();
        allowForRemove = 1;
        if (!ogm.getId().equals("")) {
        } else {
            allowForRemove = 0;
        }
    }

    public void open_insert2_panel(ActionEvent actionEvent) {
        qayd_sana2.setValue(null);
        if (allow == 1) {
            panel_insert2.setDisable(false);
            List<String> listFan = new ArrayList<>();
            for (FanModel fm : sq.getAllFan()) {
                listFan.add(fm.getName());
            }
            cbox_insert2_fan.setItems(FXCollections.observableList(listFan));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Yuqoridagi jadvaldan tanlovni analga oshiring ...");
            alert.show();
            cbox_insert2_fan.getSelectionModel().clearSelection();
            cbox_insert2_teacher.getSelectionModel().clearSelection();
            cbox_insert2_group.getSelectionModel().clearSelection();
        }
    }

    public void put_teacherFromFan_insert2(ActionEvent actionEvent) {
        String fan_id = "";
        for (FanModel fm : sq.searchFan(cbox_insert2_fan.getSelectionModel().getSelectedItem())) {
            fan_id = fm.getId();
        }
        List<String> listTeacher = new ArrayList<>();
        for (TeacherModul tm : sq.searchGroupTeacher(fan_id)) {
            listTeacher.add(tm.getName() + " " + tm.getFamilya());
        }
        cbox_insert2_teacher.setItems(FXCollections.observableList(listTeacher));
    }

    public void put_groupFromteacher_insert2(ActionEvent actionEvent) {
        String teacherID = "";
        if (cbox_insert2_teacher.getSelectionModel().getSelectedItem() != null) {
            String fullName = cbox_insert2_teacher.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(' ');
            String name = fullName.substring(0, findSymbol);
            String familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                teacherID = tm.getId();
            }
        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_insert2_group.setItems(FXCollections.observableList(listGroup));
    }

    @FXML
    TextField edt_chegirma_insert2;

    public void insert2_oquvGuruh(ActionEvent actionEvent) {
        if (cbox_insert2_fan.getSelectionModel().getSelectedItem() != null &&
                cbox_insert2_teacher.getSelectionModel().getSelectedItem() != null &&
                cbox_insert2_group.getSelectionModel().getSelectedItem() != null && qayd_sana2.getValue() != null) {
            String oquvchi_id = lb_id.getText();
            String fanID = "";
            String teacherID = "";
            String guruhID = "";
            String sana = "";
            DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String lsana = qayd_sana2.getValue().format(off);
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            if (qayd_sana2.getValue() == null) {
                LocalDateTime now = LocalDateTime.now();
                System.out.println(off.format(now));
//                String vaqt = date.toString();
                sana = off.format(now);
//                sana = vaqt.substring(0, 11);
            } else {
                sana = lsana;
            }
            for (FanModel fm : sq.searchFan(cbox_insert2_fan.getSelectionModel().getSelectedItem())) {
                fanID = fm.getId();
            }
            if (cbox_insert2_teacher.getSelectionModel().getSelectedItem() != null) {
                String fullName = cbox_insert2_teacher.getSelectionModel().getSelectedItem();
                int findSymbol = fullName.indexOf(' ');
                String Tname = fullName.substring(0, findSymbol);
                String Tfamilya = fullName.substring(findSymbol + 1);
                for (TeacherModul tm : sq.getTeacherId(Tname, Tfamilya)) {
                    teacherID = tm.getId();
                }
            }
            for (GroupModul gm : q.getGroupID(cbox_insert2_group.getSelectionModel().getSelectedItem(), fanID, teacherID)) {
                guruhID = gm.getId();
            }

            if (q.checkSamePuipleGroup(oquvchi_id, fanID, teacherID, guruhID) == 0) {
                LocalDate sanaa = qayd_sana2.getValue();
                YearMonth yearMonthObject = YearMonth.of(sanaa.getYear(), sanaa.getMonth());
                int daysInMonth = yearMonthObject.lengthOfMonth();
                int qaydSana = sanaa.getDayOfMonth();
                int oqiganSana = daysInMonth - qaydSana + 1;
                String guruh_narxi = "";
                for (GroupModul gm : q.searchGetGroupOfNarx(guruhID)) {
                    guruh_narxi = gm.getNarx();
                }
                String chegirma = edt_chegirma_insert2.getText();
                Double intqarz = ((Integer.parseInt(guruh_narxi) * oqiganSana) / daysInMonth) * (100 - Double.parseDouble(chegirma)) / 100;
                String qarz = String.valueOf(intqarz);
                q.insertPuipleToOquvchi_gutuhTable(oquvchi_id, fanID, teacherID, guruhID, qarz, chegirma, sana);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Bajarildi" + qarz + " " + guruh_narxi);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    test();
                } else if (result.get() == ButtonType.CANCEL) {
                    test();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Bu o'quvchida avvaldan o'qtuvchining shu guruhida mavjud !!!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Siz yetarlicha ma'lumot kiritmadingiz !!!");
            alert.show();
        }
    }

    public void test() {
        ObservableList<OquvGuruhModul> view = FXCollections.observableList(q.getSelectPuiple(lb_id.getText()));
        t2_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t2_fish.setCellValueFactory(new PropertyValueFactory<>("oquvchi_id"));
        t2_fan.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        t2_teacher.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        t2_group.setCellValueFactory(new PropertyValueFactory<>("guruh_id"));
        t2_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
        t2_chegirma.setCellValueFactory(new PropertyValueFactory<>("chegirma"));
        t2_sana.setCellValueFactory(new PropertyValueFactory<>("qayd_sanasi"));
        tb_view2.setItems(view);
    }


}
