package sample.teacher;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Tools.MysqlConnection;
import sample.Tools.SqlQuerys;
import sample.admin.guruh.GroupModul;
import sample.admin.teacher.TeacherModul;
import sample.admin.oquvchi.OquvGuruhModul;
import sample.admin.oquvchi.OquvchiModul;
import sample.admin.oquvchi.OquvchiQuerys;
import sample.teacher.davomad.TeacherDavomadQuery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TeacherRoom implements Initializable {
    TeacherRoomQuery q = new TeacherRoomQuery();
    SqlQuerys sq = new SqlQuerys();
    OquvchiQuerys oq = new OquvchiQuerys();
    TeacherDavomadQuery tdq = new TeacherDavomadQuery();
    Connection conn;

    @FXML
    TableColumn<TeacherRoomModul, String> tb_id;
    @FXML
    TableColumn<TeacherRoomModul, String> tb_tr;
    @FXML
    TableColumn<TeacherRoomModul, String> tb_fish;
    @FXML
    TableColumn<TeacherRoomModul, String> tb_guruh;
    @FXML
    TableColumn<TeacherRoomModul, String> tb_qarz;
    @FXML
    TableColumn<TeacherRoomModul, String> tb_sana;
    @FXML
    TableView<TeacherRoomModul> tb_view;
    @FXML
    PasswordField password;
    @FXML
    ComboBox<String> cbox_sort;
    @FXML
    ComboBox<String> cbox_choose;

    private String teacherID = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = MysqlConnection.conDb();
//        read  password
        String parol = "";
        try {
            FileReader reader = new FileReader("text.css");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                parol = line.trim();
                password.setText(line.trim());
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        lb_removID.setText("lb_removID");
        for (TeacherModul tm : q.getTeacherID(String.valueOf(String.valueOf(parol.hashCode()).hashCode()))) {
            teacherID = tm.getId();
            lb_fanID.setText(tm.getFan_id());
            updateTable1(teacherID);
        }
        List<String> guruhlist = new ArrayList<>();
        for (GroupModul gm : q.getGuruh(teacherID)) {
            guruhlist.add(gm.getGuruh_name());
        }
        cbox_sort.setItems(FXCollections.observableList(guruhlist));
        cbox_choose.setItems(FXCollections.observableList(guruhlist));
        cbox_edit.setItems(FXCollections.observableList(guruhlist));


        updateTable2();
    }

    @FXML
    Label lb_fanID;
    @FXML
    ComboBox<String> cbox_edit;

    public void updateTable1(String teacherId) {
        ObservableList<TeacherRoomModul> view = FXCollections.observableList(q.getAllPuiple(teacherId));
        tableItems();
        tb_view.setItems(view);
    }

    @FXML
    TextField search;

    private void tableItems() {
        tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tb_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        tb_fish.setCellValueFactory(new PropertyValueFactory<>("fish"));
        tb_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh"));
        tb_qarz.setCellValueFactory(new PropertyValueFactory<>("qarz"));
        tb_sana.setCellValueFactory(new PropertyValueFactory<>("sana"));
    }

    public void search(KeyEvent keyEvent) {
        ObservableList<TeacherRoomModul> view = FXCollections.observableList(q.searchPuiple(password.getText(), search.getText()));
        tableItems();
        tb_view.setItems(view);
    }

    public void sortFromGuruh(ActionEvent actionEvent) {
        String parol = password.getText();
        String teacherID = "";
        for (TeacherModul tm : q.getTeacherID(String.valueOf(String.valueOf(parol.hashCode()).hashCode()))) {
            teacherID = tm.getId();
        }

        ObservableList<TeacherRoomModul> view = FXCollections.observableList(q.sotrGuruh(teacherID, cbox_sort.getSelectionModel().getSelectedItem()));
        tableItems();
        tb_view.setItems(view);
    }

    @FXML
    TableView<OquvchiModul> tb2_view;
    @FXML
    TableColumn<OquvchiModul, String> tb2_tr;
    @FXML
    TableColumn<OquvchiModul, String> tb2_id;
    @FXML
    TableColumn<OquvchiModul, String> tb2_fish;
    @FXML
    TableColumn<OquvchiModul, String> tb2_tel;
    @FXML
    TableColumn<OquvchiModul, String> tb2_sana;

    public void updateTable2() {
        ObservableList<OquvchiModul> view = FXCollections.observableList(oq.getAllpupils());
        tb2_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        tb2_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tb2_fish.setCellValueFactory(new PropertyValueFactory<>("fish"));
        tb2_tel.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        tb2_sana.setCellValueFactory(new PropertyValueFactory<>("qoshilgan_sana"));
        tb2_view.setItems(view);
    }

    @FXML
    TextField edt_find;
    @FXML
    TextField edt_telNumber;

    public void find(KeyEvent keyEvent) {
        String detail = edt_find.getText();
        ObservableList<OquvchiModul> view = FXCollections.observableList(oq.searchPupils(detail));
        tb2_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        tb2_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tb2_fish.setCellValueFactory(new PropertyValueFactory<>("fish"));
        tb2_tel.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        tb2_sana.setCellValueFactory(new PropertyValueFactory<>("qoshilgan_sana"));
        tb2_view.setItems(view);
    }

    @FXML
    Label lb_oquvchiID;

    public void click_table2(MouseEvent mouseEvent) {
        OquvchiModul om = tb2_view.getSelectionModel().getSelectedItem();
        edt_find.setText(om.getFish());
        edt_telNumber.setText(om.getPhone_number());
        for (OquvchiModul om2 : oq.getOquvchiID(om.getFish())) {
            lb_oquvchiID.setText(om2.getId());
        }
    }

    @FXML
    Button btn_insertPuiple;
    @FXML
    DatePicker date_picker;

    public void insert_puipleToGroup(ActionEvent actionEvent) {
        String teacherID = "";
        String fanID = "";
        String guruhID = "";
        LocalDate sana = null;
        String parol = password.getText();
        for (TeacherModul tm : q.getTeacherID(String.valueOf(String.valueOf(parol.hashCode()).hashCode()))) {
            teacherID = tm.getId();
            fanID = tm.getFan_id();
        }
        String guruh_name = cbox_choose.getSelectionModel().getSelectedItem();
        for (GroupModul gm : oq.getGroupID(guruh_name, fanID, teacherID)) {
            guruhID = gm.getId();
        }
        if (date_picker.getValue() != null && !edt_find.getText().equals("") && !edt_telNumber.getText().equals("") && !guruh_name.equals("")) {
            sana = date_picker.getValue();
            DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String sanaFromat = date_picker.getValue().format(off);
            if (oq.checkSamePupil(edt_find.getText()) == 0) {
                oq.insertPuipleToOquvchilarTable(edt_find.getText(), edt_telNumber.getText(), sanaFromat);
                YearMonth yearMonthObject = YearMonth.of(sana.getYear(), sana.getMonth());
                int daysInMonth = yearMonthObject.lengthOfMonth();
                int qaydSana = sana.getDayOfMonth();
                int oqiydigankunlari = daysInMonth - qaydSana + 1;
                String guruh_narxi = "";
                for (GroupModul gm : oq.searchGetGroupOfNarx(guruhID)) {
                    guruh_narxi = gm.getNarx();
                }
                String oquvchiID = "";
                for (OquvchiModul om2 : oq.getOquvchiID(edt_find.getText())) {
                    oquvchiID = om2.getId();
                }
                String chegirma1 = "0";
                Double intqarz = ((Double.parseDouble(guruh_narxi) * oqiydigankunlari) / daysInMonth) * (100 - Double.parseDouble(chegirma1)) / 100;
                String qarz = String.valueOf(intqarz);
                oq.insertPuipleToOquvchi_gutuhTable(oquvchiID, fanID, teacherID, guruhID, qarz, chegirma1, sanaFromat);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("O'quvchi bazaga ilk bora kiritildi...");
                alert.show();
                updateTable1(teacherID);
                updateTable2();
                edt_find.setText("");
            } else {
//                if (oq.checkSamePuipleGroup(oquvchiID, fanID, teacherID, guruhID) == 0) {
                if (oq.checkSamePuipleGroup(lb_oquvchiID.getText(), fanID, teacherID, guruhID) == 0) {
                    YearMonth yearMonthObject = YearMonth.of(sana.getYear(), sana.getMonth());
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    int qaydSana = sana.getDayOfMonth();
                    int oqiydigankunlari = daysInMonth - qaydSana + 1;
                    String guruh_narxi = "";
                    for (GroupModul gm : oq.searchGetGroupOfNarx(guruhID)) {
                        guruh_narxi = gm.getNarx();
                    }
                    String chegirma1 = "0";
                    Double intqarz = ((Double.parseDouble(guruh_narxi) * oqiydigankunlari) / daysInMonth) * (100 - Double.parseDouble(chegirma1)) / 100;
                    String qarz = String.valueOf(intqarz);
                    oq.insertPuipleToOquvchi_gutuhTable(lb_oquvchiID.getText(), fanID, teacherID, guruhID, qarz, chegirma1, sanaFromat);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("O'quvchi bazaga kiritildi...");
                    alert.show();
                    updateTable1(teacherID);
                    updateTable2();
                    edt_find.setText("");
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(edt_find.getText() + " allaqachon guruhda mavjud !!!");
                    alert.show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Siz yetarlicha ma'lumot kiritmadingiz.");
            alert.show();
        }
    }

    @FXML
    HBox create_groupWindow;

    public void openCreateGroupWindow(ActionEvent actionEvent) {
        create_groupWindow.setDisable(false);
    }

    @FXML
    TextField edt_guruhNomi;
    @FXML
    TextField edt_guruhNarxi;

    public void insertNewgroup(ActionEvent actionEvent) {
        if (!edt_guruhNomi.getText().equals("") && !edt_guruhNarxi.getText().equals("") && !password.getText().equals("")) {
            String guruhNomi = edt_guruhNomi.getText();
            String guruhNarxi = edt_guruhNarxi.getText();
            String teacherID = "";
            String fanID = "";
            String parol = password.getText();
            for (TeacherModul tm : q.getTeacherID(String.valueOf(String.valueOf(parol.hashCode()).hashCode()))) {
                teacherID = tm.getId();
                fanID = tm.getFan_id();
            }
            if (sq.checkSameGroup(guruhNomi, teacherID) == 0) {
                sq.insertGroup(guruhNomi, fanID, teacherID, guruhNarxi);
                create_groupWindow.setDisable(true);
                List<String> guruhlist = new ArrayList<>();
                for (GroupModul gm : q.getGuruh(teacherID)) {
                    guruhlist.add(gm.getGuruh_name());
                }
                cbox_sort.getSelectionModel().clearSelection();
                cbox_choose.getSelectionModel().clearSelection();
                cbox_sort.setItems(FXCollections.observableList(guruhlist));
                cbox_choose.setItems(FXCollections.observableList(guruhlist));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Guruh muoffaqqiyatli yaratildi");
                alert.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Bu guruh allaqachon yaratilgan !!!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Siz yetarlicha ma'lumot kiritmadingiz !!!");
            alert.show();
        }
    }

    @FXML
    Label lb_removID;
    @FXML
    Label lb_puipleName;
    @FXML
    Label lb_guruhName;
    @FXML
    Label lb_lastQarz;
    @FXML
    Label lb_lastTel;
    @FXML
    Label lb_puipleID;
    @FXML
    Label lb_lastSana;
    @FXML
    AnchorPane edit_window;

    public void click_table1(MouseEvent mouseEvent) {
        edit_window.setDisable(false);
        TeacherRoomModul trm = tb_view.getSelectionModel().getSelectedItem();
        lb_removID.setText(trm.getId());
        lb_puipleName.setText(trm.getFish());
        lb_guruhName.setText(trm.getGuruh());
        lb_lastSana.setText(trm.getSana());

        lb_lastQarz.setText(trm.getQarz());
        for (OquvchiModul om : q.getOquvchi(trm.getFish())) {
            lb_puipleID.setText(om.getId());
            lb_lastTel.setText(om.getPhone_number());
        }

        edt_edit_fish.setText(trm.getFish());
        edt_edit_phone_number.setText(lb_lastTel.getText());


    }

    public void delete_puipleFromGroup(ActionEvent actionEvent) {
        if (!lb_removID.getText().equals("lb_removID") && !lb_fanID.getText().equals("lb_fanID")) {
            String qarz = "";
            for (OquvGuruhModul ogm : oq.getSelectPuipleWithoquvchGuruhId(lb_removID.getText())) {
                qarz = ogm.getQarz();
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Chindan ham siz " + lb_puipleName.getText() + " ni " + lb_guruhName.getText() + " guruhdan o'chirmoqchimisiz ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String teacherID = "";
                String parol = password.getText();
                for (TeacherModul tm : q.getTeacherID(String.valueOf(String.valueOf(parol.hashCode()).hashCode()))) {
                    teacherID = tm.getId();
                }
                DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(off.format(now));
                String sana = off.format(now);
                oq.insertPuipleFromOquvguruhToKarzinka(lb_puipleName.getText(), lb_fanID.getText(), teacherID, qarz, sana);
                oq.deleteOquvchiGuruh(lb_removID.getText());
                updateTable1(teacherID);
//                Agar o'quvchini guruhi umuman qolmasa uni oquvchi bazasidan o'chiradi.
                String fishID = "";
                for (OquvchiModul ogm : oq.getOquvchiID(lb_puipleName.getText())) {
                    fishID = ogm.getId();
                }
                if (oq.getPuipleOfGroupsCount(fishID) == 0) {
                    oq.deletePuipleFromOquvchilar(fishID);
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText(lb_puipleName.getText() + " hech qaysi guruhda yo'qligi sababli u o'quvchilar bazasidan o'chirildi va karzinkaga saqlandi");
                    a.show();
                }
            } else if (result.get() == ButtonType.CANCEL) {
            }
        }
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Iltimos o'quvchini o'chirish uchun yuqoridagi jadvaldan tanlang !!!");
        }
    }

    @FXML
    TextField edt_edit_fish;
    @FXML
    TextField edt_edit_phone_number;

    public void updatePuipleData(ActionEvent actionEvent) {
        String fish = edt_edit_fish.getText();
        String number = edt_edit_phone_number.getText();
        String id = lb_puipleID.getText();
        if (!fish.equals("") && !number.equals("")) {
            oq.updatePuipleFishAndNumber(fish, number, id);
            if (oq.checkSamePupil(fish) == 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Ma'lumotlar o'gartirildi...");
                updateTable2();
                updateTable1(teacherID);
            } else {
                cancleUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(fish + " ni biroz o'zgartiring, Chunki u boshqa o'quvchining ism va familyasi bilan bir xil");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    cancleUpdate();
                    updateTable1(teacherID);
                    updateTable2();
                } else if (result.get() == ButtonType.CANCEL) {
                    cancleUpdate();
                    updateTable1(teacherID);
                    updateTable2();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Siz F.I.SH YOKI TELEFON RAQAMNI KIRITMADINGIZ");
            alert.show();
        }
    }

    public void cancleUpdate() {
        String fish = lb_puipleName.getText();
        String number = lb_lastTel.getText();
        String id = lb_puipleID.getText();
        oq.updatePuipleFishAndNumber(fish, number, id);
    }

    @FXML
    Button btn_changeGroup;

    public void click_cbox(ActionEvent actionEvent) {
        if (cbox_edit.getSelectionModel().getSelectedItem() != null) {
            btn_changeGroup.setDisable(false);
        }
    }

    public void change_group(ActionEvent actionEvent) {
        if (cbox_edit.getSelectionModel().getSelectedItem() != null && !edt_edit_fish.getText().equals("")) {
            int check = 2;
            String update_dateSt = "";
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (OquvGuruhModul ogm1 : oq.getSelectPuipleWithoquvchGuruhId(lb_removID.getText())) {
                update_dateSt = ogm1.getUpdate_date() + " 11:30";
            }
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
            String teacherID = "";
            String lastGuruhID = "";
            String lastGuruhNarxi = "";
            String chegirma = "0";
            String parol = password.getText();
            for (TeacherModul tm : q.getTeacherID(String.valueOf(String.valueOf(parol.hashCode()).hashCode()))) {
                teacherID = tm.getId();
            }
            for (GroupModul gm : q.getGuruhID(teacherID, lb_guruhName.getText())) {
                lastGuruhID = gm.getId();
                lastGuruhNarxi = gm.getNarx();
            }

            String newGuruhNarxi = "";
            String newGuruhID = "";
            for (GroupModul gm : q.getGuruhID(teacherID, cbox_edit.getSelectionModel().getSelectedItem().toString())) {
                newGuruhNarxi = gm.getNarx();
                newGuruhID = gm.getId();
            }

            if (check == 0) {
                Double b_qarz = 0.0;
                Double last_chegirma = 0.0;
                for (OquvGuruhModul ogm : oq.getSelectPuipleWithoquvchGuruhId(lb_removID.getText())) {
                    b_qarz = Double.parseDouble(ogm.getQarz());
                    last_chegirma = Double.parseDouble(ogm.getChegirma());
                }
                Double qarz1 = b_qarz - ((daysInMonth - update_date.getDayOfMonth() + 1) / Double.parseDouble(String.valueOf(daysInMonth)))
                        * (1 - last_chegirma / 100) * Double.parseDouble(lastGuruhNarxi) +
                        ((Double.parseDouble(String.valueOf(now.getDayOfMonth())) - Double.parseDouble(String.valueOf(update_date.getDayOfMonth()))) / daysInMonth) * (1 - last_chegirma / 100) * Double.parseDouble(lastGuruhNarxi);
                Double qarz2 = qarz1 + ((Double.parseDouble(String.valueOf(daysInMonth - now.getDayOfMonth() + 1))) / Double.parseDouble(String.valueOf(daysInMonth))) * (1 - Double.parseDouble(chegirma) / 100) * Double.parseDouble(newGuruhNarxi);
                String StringQarzUmumiyI = String.valueOf(qarz2);
                LocalDateTime noww = LocalDateTime.now();
                oq.updatePuipleFanTeacherAndGroup(lb_fanID.getText(), teacherID, newGuruhID, StringQarzUmumiyI, chegirma, off.format(noww), lb_removID.getText());
                lb_lastQarz.setText(StringQarzUmumiyI);
            }

            if (check == 1) {
                Double b_qarz = 0.0;
                Double last_chegirma = 0.0;
                for (OquvGuruhModul ogm : oq.getSelectPuiple(lb_removID.getText())) {
                    b_qarz = Double.parseDouble(ogm.getQarz());
                    last_chegirma = Double.parseDouble(ogm.getChegirma());
                }
                Double qarz = b_qarz - Double.parseDouble(lastGuruhNarxi) + ((now.getDayOfMonth() - 1) / Double.parseDouble(String.valueOf(daysInMonth))) * (1 - last_chegirma / 100) * Double.parseDouble(lastGuruhNarxi);
                Double qarz2 = qarz + ((daysInMonth - now.getDayOfMonth() + 1) / Double.parseDouble(String.valueOf(daysInMonth))) * (1 - Double.parseDouble(chegirma) / 100) * Double.parseDouble(newGuruhNarxi);
                String StringQarzUmumiy = String.valueOf(qarz2);
                DateTimeFormatter offf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime noww = LocalDateTime.now();
                oq.updatePuipleFanTeacherAndGroup(lb_fanID.getText(), teacherID, newGuruhID, StringQarzUmumiy, chegirma, off.format(noww), lb_removID.getText());
                lb_lastQarz.setText(StringQarzUmumiy);
            }
            String oquvchiID = lb_puipleID.getText();
            String fanID = lb_fanID.getText();
            if (oq.checkSamePuipleGroup(oquvchiID, fanID, teacherID, newGuruhID) == 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Bajarildi");
                edit_window.setDisable(true);
                Optional<ButtonType> result = alert.showAndWait();
                btn_changeGroup.setDisable(true);
                if (result.get() == ButtonType.OK) {
                    updateTable1(teacherID);
                    updateTable2();
                } else if (result.get() == ButtonType.CANCEL) {
                    updateTable1(teacherID);
                    updateTable2();
                }
            } else {
                fanID = lb_fanID.getText();
                String cancleQarz = lb_lastQarz.getText();
                String LastupdateDate = lb_lastSana.getText();
                oq.updatePuipleFanTeacherAndGroup(fanID, teacherID, lastGuruhID, cancleQarz, chegirma, LastupdateDate, lb_removID.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Bu o'quvchi avvaldan  sizning shu guruhida bor... ");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Siz yetarlicha tanlov qilmadingiz. Chapdagi barcha tanlovni amalga oshiring :( ");
            alert.show();
        }
    }

    @FXML
    AnchorPane insertPuipleWindow;

    public void insertNewPuiple(ActionEvent actionEvent) {
        if (!lb_fanID.getText().equals("lb_fanID")) {
            insertPuipleWindow.setDisable(false);
        }
    }

    public void hide_insertPuipleWindow(ActionEvent actionEvent) {
        insertPuipleWindow.setDisable(true);
    }

    public void open_davomad(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/teacher/davomad/teacherDavomad.fxml")));
            Stage stage = new Stage();
            stage.setTitle("DAVOMAD + - ");
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            stage.setResizable(true);
            stage.setScene(new Scene(root, 1200, 650));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void about(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("DASTUR 2020-YIL 'PERSONAL DEVELOPER' DASTURCHILARI TOMONIDAN YARATILDI. TAKLIF " +
                "VA MULOHAZALAR UCHUN" + "\n" +
                " '@don_1996' TELEGRAM MANZILI YOKI 'dondilshod@096@gmail.com' GA HABAR YUBORING.");
        alert.show();
    }
}
