package sample.admin.teacher;

import com.mysql.jdbc.Connection;
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
import sample.Tools.MysqlConnection;
import sample.Tools.SqlQuerys;
import sample.admin.fan.FanModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Teacher implements Initializable {
    @FXML
    TableView<TeacherModul> tb_teacher;
    @FXML
    TableColumn<TeacherModul, String> id;
    @FXML
    TableColumn<TeacherModul, String> name;
    @FXML
    TableColumn<TeacherModul, String> familya;
    @FXML
    TableColumn<TeacherModul, String> mutaxasis;
    @FXML
    TableColumn<TeacherModul, String> parol;
    @FXML
    TableColumn<TeacherModul, String> miqdor;

    @FXML
    AnchorPane panel_add_teacher;
    @FXML
    AnchorPane panel_edit_teacher;

    @FXML
    Button btn_add;
    @FXML
    TextField edt_search;
    @FXML
    TextField edt_add_name;
    @FXML
    TextField edt_add_familya;
    @FXML
    TextField edt_add_password;
    @FXML
    ComboBox<String> comboBox;

    SqlQuerys q = new SqlQuerys();
    Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = MysqlConnection.conDb();
        panel_add_teacher.setVisible(false);
        panel_edit_teacher.setVisible(false);
//        temporary_id.setVisible(true);
        updateTable();
        List<String> itemsComboBox = new ArrayList<>();
        for (FanModel tm : q.getAllFan()) {
            itemsComboBox.add(tm.getName());
        }
        comboBox.setItems(FXCollections.observableList(itemsComboBox));
        comboBox2.setItems(FXCollections.observableList(itemsComboBox));
    }

    private void updateTable() {
        ObservableList<TeacherModul> view = FXCollections.observableList(q.getAllTeacherForTable());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        familya.setCellValueFactory(new PropertyValueFactory<>("familya"));
        mutaxasis.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        parol.setCellValueFactory(new PropertyValueFactory<>("parol"));
        miqdor.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tb_teacher.setItems(view);
    }

    @FXML
    TextField edt_add_miqdor;

    public void add_teacher(ActionEvent actionEvent) {
        panel_add_teacher.setVisible(true);
    }

    @FXML
    Label lb_info_insert;

    public void insert_teacher(ActionEvent actionEvent) {
        if (q.checkSameTeacher(edt_add_name.getText(), edt_add_familya.getText()) == 0 && (!edt_add_name.getText().equals("") || !edt_add_familya.getText().equals("") || !edt_add_password.getText().equals(""))) {
            if (comboBox.getSelectionModel().getSelectedItem() != null) {
                String parol = String.valueOf(String.valueOf(edt_add_password.getText().hashCode()).hashCode());
                if (q.checkSamePasswordTeacher(parol) == 0) {
                    if (!edt_add_miqdor.getText().trim().equals("")) {
                        String fan_id = "";
                        for (FanModel fm : q.searchFan(comboBox.getSelectionModel().getSelectedItem())) {
                            fan_id = fm.getId();
                        }
                        String miqdor = String.valueOf(Double.parseDouble(edt_add_miqdor.getText()) / 100);
                        q.insertTeacher(edt_add_name.getText(), edt_add_familya.getText(), fan_id, parol, miqdor);
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.show();
                        lb_info_insert.setText("");
                        panel_add_teacher.setVisible(false);
                        updateTable();
                    } else {
                        lb_info_insert.setText("Siz o'qituvchiga miqdor kiritmadingiz !");
                    }
                } else {
                    lb_info_insert.setText("parol avval boshqa o'qituvchiga qayt etilgan !");
                }
            } else {
                lb_info_insert.setText("fan tanlanmadi");
            }
        }
        if (q.checkSameTeacher(edt_add_name.getText(), edt_add_familya.getText()) != 0 && (!edt_add_name.getText().equals("") || !edt_add_familya.getText().equals("") || !edt_add_password.getText().equals(""))) {
            lb_info_insert.setText("Bu o'qituvchi mavjud !");
        }
        if (q.checkSameTeacher(edt_add_name.getText(), edt_add_familya.getText()) == 0 && (edt_add_name.getText().equals("") || edt_add_familya.getText().equals("") || edt_add_password.getText().equals(""))) {
            lb_info_insert.setText("Siz yetarlicha ma'lumot kiritmadingiz !");
        }
    }

    public void search_teacher(KeyEvent keyEvent) throws SQLException {
        ObservableList<TeacherModul> view = FXCollections.observableList(q.searchTeacher(edt_search.getText()));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        familya.setCellValueFactory(new PropertyValueFactory<>("familya"));
        mutaxasis.setCellValueFactory(new PropertyValueFactory<>("fan_id"));
        parol.setCellValueFactory(new PropertyValueFactory<>("parol"));
        miqdor.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tb_teacher.setItems(view);
    }

    @FXML
    TextField edt_new_name;
    @FXML
    TextField edt_new_familya;
    @FXML
    Label edt_new_fan;
    @FXML
    ComboBox<String> comboBox2;
    @FXML
    TextField edt_new_password;
    @FXML
    TextField edt_new_miqdor;
    @FXML
    Label temporary_id;
    String beforePassword = "";

    public void open_panel_edit_teacher(ActionEvent actionEvent) {
        panel_edit_teacher.setVisible(true);
    }

    public void put_items_edit_panel(MouseEvent mouseEvent) {// jadvalga chertilganda
        TeacherModul tm = tb_teacher.getSelectionModel().getSelectedItem();
        edt_new_name.setText(tm.getName());
        edt_new_familya.setText(tm.getFamilya());
        edt_new_fan.setText(tm.getFan_id());
        edt_new_password.setText(tm.getParol());
        temporary_id.setText(tm.getId());
        beforePassword = edt_new_password.getText().toLowerCase();
        edt_new_miqdor.setText(String.valueOf(Double.parseDouble(tm.getAmount()) * 100));

        panel_edit_teacher.setVisible(true);
    }

    public void edit_teacher(ActionEvent actionEvent) {
        String name = edt_new_name.getText();
        String familya = edt_new_familya.getText();
        String miqdor = String.valueOf(Double.parseDouble(edt_new_miqdor.getText()) / 100);
        String fan_id = "";
        for (FanModel fm : q.searchFan(edt_new_fan.getText())) {
            fan_id = fm.getId();
        }
        String find_id = temporary_id.getText();
        if (!name.equals("") && !familya.equals("") && !fan_id.equals("")) {
            q.updateTeacher(name, familya, fan_id, miqdor, find_id);
            q.updateGroupDirect(fan_id, find_id);

            updateTable();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Ma'lumotlar yangilandi");
            a.show();
        }
    }

    public void update_teacher_password(ActionEvent actionEvent) {
        if (edt_new_password.getText() != "") {
            String parol = String.valueOf(String.valueOf(edt_new_password.getText().hashCode()).hashCode());
            q.updateTeacherPassword(parol, temporary_id.getText());
            if (q.checkSamePasswordTeacher(parol) == 1) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Parol yangilandi...");
                a.show();
                updateTable();
            } else {
                q.updateTeacherPassword(beforePassword, temporary_id.getText());
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Bu parol allaqachon qayt etilgan...");
                a.show();
                updateTable();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Siz yangi parol kiritmadingiz...");
        }
    }

    public void put_new_fan_byComBox(ActionEvent actionEvent) {
        edt_new_fan.setText(comboBox2.getSelectionModel().getSelectedItem().toString());
    }

    public void btn_hide_edit_panel(ActionEvent actionEvent) {
        panel_edit_teacher.setVisible(false);
    }

}
