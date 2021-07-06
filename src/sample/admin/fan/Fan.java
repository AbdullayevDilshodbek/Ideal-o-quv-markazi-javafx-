package sample.admin.fan;

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

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Fan implements Initializable {
    Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = MysqlConnection.conDb();
        panel_insert_fan.setVisible(false);
        panel_edit_fan.setVisible(false);
        temporary_id.setVisible(false);
        lb_info.setText("");
        updateTable();
    }

    @FXML
    TableView<FanModel> tb_fan;
    @FXML
    TableColumn<FanModel, String> id;
    @FXML
    TableColumn<FanModel, String> name;

    @FXML
    AnchorPane panel_insert_fan;
    @FXML
    AnchorPane panel_edit_fan;

    @FXML
    TextField edt_search;
    @FXML
    TextField edt_insert;
    @FXML
    TextField edt_new_name;
    @FXML
    Label lb_info;
    @FXML
    Label lb_info2;
    @FXML
    Label temporary_id;

    int allowInsert = 0;
    SqlQuerys q = new SqlQuerys();

    public void search(KeyEvent keyEvent) {
        ObservableList<FanModel> view = FXCollections.observableList(q.searchFan(edt_search.getText()));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tb_fan.setItems(view);
    }

    public void open_add_panel(ActionEvent actionEvent) {
        panel_insert_fan.setVisible(true);
    }

    public void check_fan(KeyEvent keyEvent) {
        if (q.searchFan(edt_insert.getText()).size() == 0) {
            allowInsert = 1;
        }
    }

    public void insert(ActionEvent actionEvent) {
        if (!edt_insert.getText().equals("") && allowInsert == 1) {
            q.insertFan(edt_insert.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Fan muoffaqqiyatli qo'shildi");
            alert.show();
            q.getAllFan();
            updateTable();
            edt_insert.setText("");
            allowInsert = 0;
            panel_insert_fan.setVisible(false);
        }
        if (edt_insert.getText().equals("")) {
            lb_info.setText("Siz fanga nom bermadingiz !");
        }
        if (allowInsert == 0 && !edt_insert.getText().equals("")) {
            lb_info.setText("Bu fan allaqachon kiritilgan !");
        }
    }

    public void updateTable() {
        ObservableList<FanModel> view = FXCollections.observableList(q.getAllFan());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tb_fan.setItems(view);
    }

    public void sde(MouseEvent mouseEvent) {
        panel_edit_fan.setVisible(true);
        FanModel fm = tb_fan.getSelectionModel().getSelectedItem();
        edt_new_name.setText(fm.getName());
        temporary_id.setText(fm.getId());
    }

    public void btn_change_fan_name(ActionEvent actionEvent) {
        if (!edt_new_name.getText().equals("") && q.checkSameFan(edt_new_name.getText()) == 0) {
            q.updateFan(temporary_id.getText(), edt_new_name.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.show();
            updateTable();
        } else if (q.searchFan(edt_new_name.getText()).size() != 0) {
            lb_info2.setText("Bu fan ro'yxatda bor !");
        }
        if (edt_new_name.getText().equals("")) {
            lb_info2.setText("Siz nom bermadingiz !");
        }
    }

    public void btn_hide2(ActionEvent actionEvent) {
        panel_edit_fan.setVisible(false);
    }

    public void open_edit_panel(ActionEvent actionEvent) {
        panel_edit_fan.setVisible(true);
    }

    public void open_taxrir(ActionEvent actionEvent) {
        panel_edit_fan.setVisible(true);
    }

    public void deleteFan(ActionEvent actionEvent) {
        if (tb_fan.getSelectionModel().getSelectedItem() != null){
            FanQuery fanQuery = new FanQuery();
            FanModel fm = tb_fan.getSelectionModel().getSelectedItem();
            if (fanQuery.CheckFanOfTeacher(fm.getId()) == 0 ){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Siz " + fm.getName() + " fani o'chrimoqchimisiz ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    fanQuery.deleteFan(fm.getId());
                    updateTable();
                    edt_new_name.setText("");
                } else if (result.get() == ButtonType.CANCEL) {
//                amla bekor qilindi
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Fan aloqador bo'lgan o'qituvchi mavjud, balki o'quvchilar ham. Shu fani o'chirish uchun " +
                        "avval shu fan o'qituvchilari yo'q bo'lish kerak !!!");
            }
        }
    }
}
