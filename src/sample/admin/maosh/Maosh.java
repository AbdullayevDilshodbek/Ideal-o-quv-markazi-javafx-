package sample.admin.maosh;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import sample.Tools.MysqlConnection;
import sample.Tools.SqlQuerys;
import sample.admin.fan.FanModel;
import sample.admin.guruh.GroupModul;
import sample.admin.teacher.TeacherModul;
import sample.admin.oquvchi.OquvchiQuerys;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Maosh implements Initializable {
    MaoshQuery mq = new MaoshQuery();
    SqlQuerys sq = new SqlQuerys();
    OquvchiQuerys q = new OquvchiQuerys();
    String statusV = "1";
    Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = MysqlConnection.conDb();
        updateTable();
        List<String> list = new ArrayList<>();
        for (MaoshModul mm : mq.getAllTolovs(statusV)) {
            if (!list.contains(mm.getFan())) {
                list.add(mm.getFan());
            }
        }
        cbox_fan.setItems(FXCollections.observableList(list));
    }

    @FXML
    CheckBox checkBox;

    public void checkBox(ActionEvent actionEvent) {
        if (checkBox.isSelected()) {
            statusV = "0";
            updateTable();
            List<String> list = new ArrayList<>();
            for (MaoshModul mm : mq.getAllTolovs(statusV)) {
                if (!list.contains(mm.getFan())) {
                    list.add(mm.getFan());
                }
            }
            cbox_fan.getSelectionModel().clearSelection();
            cbox_fan.setItems(FXCollections.observableList(list));
        } else {
            statusV = "1";
            updateTable();
            List<String> list = new ArrayList<>();
            for (MaoshModul mm : mq.getAllTolovs(statusV)) {
                if (!list.contains(mm.getFan())) {
                    list.add(mm.getFan());
                }
            }
            cbox_fan.getSelectionModel().clearSelection();
            cbox_fan.setItems(FXCollections.observableList(list));
        }
    }

    @FXML
    TableColumn<MaoshModul, String> id;
    @FXML
    TableColumn<MaoshModul, String> tr;
    @FXML
    TableColumn<MaoshModul, String> oquvchi;
    @FXML
    TableColumn<MaoshModul, String> fan;
    @FXML
    TableColumn<MaoshModul, String> oqituvchi;
    @FXML
    TableColumn<MaoshModul, String> guruh;
    @FXML
    TableColumn<MaoshModul, String> tolov;
    @FXML
    TableColumn<MaoshModul, String> sana;
    @FXML
    TableColumn<MaoshModul, String> status;
    @FXML
    TableView<MaoshModul> tb_view;
    @FXML
    ComboBox<String> cbox_fan;
    @FXML
    ComboBox<String> cbox_teacher;
    @FXML
    ComboBox<String> cbox_group;


    public void updateTable() {
        ObservableList<MaoshModul> view = FXCollections.observableList(mq.getAllTolovs(statusV));
        colms();
//        int lenght = mq.getAllTolovs(statusV).size();
//        List<MaoshModul> list = new ArrayList<>();
//        list.add(new MaoshModul("9",lenght + 1,"Dilshodbek","rus","Max","group","12","12/12/2020","1"));
//        view.addAll(lenght,list);
        tb_view.setItems(view);

    }

    public void colms() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
        oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi"));
        fan.setCellValueFactory(new PropertyValueFactory<>("fan"));
        oqituvchi.setCellValueFactory(new PropertyValueFactory<>("oqituvchi"));
        guruh.setCellValueFactory(new PropertyValueFactory<>("guruh"));
        tolov.setCellValueFactory(new PropertyValueFactory<>("tolov"));
        sana.setCellValueFactory(new PropertyValueFactory<>("sana"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    TextField edt_search;

    public void search(KeyEvent keyEvent) {
        ObservableList<MaoshModul> view = FXCollections.observableList(mq.searchPuiple(edt_search.getText(), statusV));
        colms();
        tb_view.setItems(view);
    }

    public void put_teacherToCbox(ActionEvent actionEvent) {
        String fann = cbox_fan.getSelectionModel().getSelectedItem();
        if (cbox_fan.getSelectionModel().getSelectedItem() != null) {
            String fan_id = "";
            for (FanModel fm : sq.searchFan(fann)) {
                fan_id = fm.getId();
            }
            List<String> listTeacher = new ArrayList<>();
            for (TeacherModul tm : sq.searchGroupTeacher(fan_id)) {
                listTeacher.add(tm.getName() + " " + tm.getFamilya());
            }
            cbox_teacher.setItems(FXCollections.observableList(listTeacher));
        }
        ObservableList<MaoshModul> view = FXCollections.observableList(mq.sortFromFan(fann, statusV));
        colms();
        tb_view.setItems(view);
    }

    @FXML
    Label lb_fan;
    @FXML
    Label lb_teacher;
    @FXML
    Label lb_summa;
    @FXML
    Label lb_miqdor;
    @FXML
    Label lb_maosh;

    public void put_group(ActionEvent actionEvent) {
        String teacherID = "";
        String name = "";
        String familya = "";
        String fann = cbox_fan.getSelectionModel().getSelectedItem();
        if (cbox_teacher.getSelectionModel().getSelectedItem() != null && cbox_fan.getSelectionModel().getSelectedItem() != null) {
            String fullName = cbox_teacher.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(" ");
            name = fullName.substring(0, findSymbol);
            familya = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(name, familya)) {
                teacherID = tm.getId();
            }
        }
        List<String> listGroup = new ArrayList<>();
        for (GroupModul gm : q.searchGroupOfTeacher(teacherID)) {
            listGroup.add(gm.getGuruh_name());
        }
        cbox_group.setItems(FXCollections.observableList(listGroup));
        ObservableList<MaoshModul> view = FXCollections.observableList(mq.sortFromFanAndTeacher(fann, name, familya, statusV));
        if (!statusV.equals("0")) {
            int summa = 0;
            if (cbox_fan.getSelectionModel().getSelectedItem() != null && cbox_teacher.getSelectionModel().getSelectedItem() != null) {
                lb_fan.setText(cbox_fan.getSelectionModel().getSelectedItem());
                lb_teacher.setText(cbox_teacher.getSelectionModel().getSelectedItem());
                for (MaoshModul mm : mq.sortFromFanAndTeacher(fann, name, familya, statusV)) {
                    summa += Integer.parseInt(mm.getTolov());
                }
                lb_summa.setText(String.valueOf(summa));
            }

            String fullName = "";
            if (cbox_teacher.getSelectionModel().getSelectedItem() != null) {
                fullName = cbox_teacher.getSelectionModel().getSelectedItem();
                int findSymbol = fullName.indexOf(" ");
                String namee = fullName.substring(0, findSymbol);
                String familyaa = fullName.substring(findSymbol + 1);
                for (TeacherModul tm : sq.getTeacherId(namee, familyaa)) {
                    teacherID = tm.getId();
                }
            }

            for (TeacherModul tm : mq.getTeacherAmount(teacherID)) {
                lb_miqdor.setText(tm.getAmount());
            }
            Double maosh = summa * Double.parseDouble(lb_miqdor.getText());
            lb_maosh.setText(String.valueOf(maosh));

            int lenght = mq.sortFromFanAndTeacher(fann, name, familya, statusV).size();
            List<MaoshModul> list = new ArrayList<>();
            list.add(new MaoshModul("", lenght + 1, "FAN", lb_fan.getText(), fullName, lb_summa.getText()
                    , lb_miqdor.getText(), lb_maosh.getText(), "Maosh"));
            view.addAll(lenght, list);
            colms();
            tb_view.setItems(view);
        }
    }

    public void sotr_group(ActionEvent actionEvent) {
        if (cbox_group.getSelectionModel().getSelectedItem() != null &&
                cbox_teacher.getSelectionModel().getSelectedItem() != null && cbox_fan.getSelectionModel().getSelectedItem() != null) {
            String guruh = cbox_group.getSelectionModel().getSelectedItem();
            String name = "";
            String familya = "";
            String fann = cbox_fan.getSelectionModel().getSelectedItem();
            String fullName = cbox_teacher.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(" ");
            name = fullName.substring(0, findSymbol);
            familya = fullName.substring(findSymbol + 1);
            ObservableList<MaoshModul> view = FXCollections.observableList(mq.sortFromFanTeacherAndGroup(fann, name, familya, guruh, statusV));
            colms();
            tb_view.setItems(view);
        }
    }

    public void printToExcel(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Excel ga chop etildi...");
        alert.show();
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Xisobot");

        Row row = spreadsheet.createRow(0);

        for (int j = 1; j < tb_view.getColumns().size() - 1; j++) {
            row.createCell(j).setCellValue(tb_view.getColumns().get(j).getText());
        }

        for (int i = 0; i < tb_view.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 1; j < tb_view.getColumns().size() - 1; j++) {
                if (tb_view.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(tb_view.getColumns().get(j).getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        FileOutputStream fileOut = null;
        try {
            String fullName = cbox_teacher.getSelectionModel().getSelectedItem() + " ";
            DateTimeFormatter off = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime now = LocalDateTime.now();
            String sana = fullName + off.format(now) + ".xls";
            fileOut = new FileOutputStream(sana);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tolov(MouseEvent mouseEvent) {
        //        jadvaldagilarni statsini 0 qiladi.
        if (cbox_teacher.getSelectionModel().getSelectedItem() != null && cbox_fan.getSelectionModel().getSelectedItem() != null
                && !checkBox.isSelected()) {
            String teacherID = "";
            String fullName = cbox_teacher.getSelectionModel().getSelectedItem();
            int findSymbol = fullName.indexOf(" ");
            String namee = fullName.substring(0, findSymbol);
            String familyaa = fullName.substring(findSymbol + 1);
            for (TeacherModul tm : sq.getTeacherId(namee, familyaa)) {
                teacherID = tm.getId();
            }
//        String fanID = "";
//        for (FanModel fm : sq.searchFan(cbox_fan.getSelectionModel().getSelectedItem())) {
//            fanID = fm.getId();
//        }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(fullName + " ga maosh bermoqchimisiz ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                mq.maoshBerish(teacherID);
                LocalDateTime now = LocalDateTime.now();
                if (now.getMonth().getValue() > 9) {
                    if (now.getDayOfMonth() > 9) {
                        mq.insertTolovHistory(teacherID, lb_summa.getText(), lb_miqdor.getText(), lb_maosh.getText(), now.getDayOfMonth() + "/" + now.getMonth().getValue() + "/" + now.getYear());
                    } else {
                        mq.insertTolovHistory(teacherID, lb_summa.getText(), lb_miqdor.getText(), lb_maosh.getText(), "0" + now.getDayOfMonth() + "/" + now.getMonth().getValue() + "/" + now.getYear());
                    }
                } else {
                    if (now.getDayOfMonth() > 9) {
                        mq.insertTolovHistory(teacherID, lb_summa.getText(), lb_miqdor.getText(), lb_maosh.getText(), now.getDayOfMonth() + "/" + "0" + now.getMonth().getValue() + "/" + now.getYear());
                    } else {
                        mq.insertTolovHistory(teacherID, lb_summa.getText(), lb_miqdor.getText(), lb_maosh.getText(), "0" + now.getDayOfMonth() + "/" + "0" + now.getMonth().getValue() + "/" + now.getYear());
                    }
                }
                updateTable();
            } else if (result.get() == ButtonType.CANCEL) {

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Yetarlicha tanlov qilinmagan yoki 'Barcha to'lovni ko'rish' tanlab qo'yilgan");
            alert.show();
        }
    }

    @FXML
    ImageView img_tolov;

    public void hover_tolov(MouseEvent mouseEvent) {
        img_tolov.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.9), 10, 10, 0, 0)");
    }

    public void hoverExit_tolov(MouseEvent mouseEvent) {
        img_tolov.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.9), 10, 0, 0, 0)");
    }
}
