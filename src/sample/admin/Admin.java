package sample.admin;

import com.jfoenix.controls.JFXSpinner;
import com.mysql.jdbc.Connection;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Tools.MysqlConnection;
import sample.Tools.SqlQuerys;
import sample.admin.guruh.GroupModul;
import sample.admin.oquvchi.OquvGuruhModul;
import sample.admin.oquvchi.OquvchiQuerys;
import sample.admin.tolov.TolovQuery;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    Connection conn;
    OquvchiQuerys q = new OquvchiQuerys();
    TolovQuery tq = new TolovQuery();

    @FXML
    AnchorPane pane1, pane2, pane3, pane4, menu_bar;

    @FXML
    ImageView imageMenuBar, imageMenuBar1;

    @FXML
    private JFXSpinner spinner;

    @FXML
    Label infoOquvchiSoni, infoTolovFoizda,infoGuruhAzolari;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = MysqlConnection.conDb();
        spinner.setVisible(false);
        imageMenuBar1.setVisible(false);
        menu_bar.setVisible(false);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), menu_bar);
        translateTransition.setByX(-202);
        translateTransition.play();
        Animations();
        checkDateForPay();
    }

    boolean openOrHide = true;
    public void open_menuBar(MouseEvent mouseEvent) {
        if (openOrHide) {
            OquvchiQuerys oqk = new OquvchiQuerys();
            infoOquvchiSoni.setText("Markazda o'quvchilar : " + oqk.getAllpupils().size());

            int tqarzdors = 0;
            int allPayments = 0;
            for (OquvGuruhModul ogm:oqk.getAllPuipleForPay()) {
                allPayments++;
                if (Double.parseDouble(ogm.getQarz()) < 500){
                    tqarzdors++;
                }
            }
            Double tolovFoizda = Double.valueOf(tqarzdors) * 100/allPayments;
            infoTolovFoizda.setText("To'lov miqdori : " + tolovFoizda + "%");
            infoGuruhAzolari.setText("Guruhga azaloari : " + allPayments);


            menu_bar.setVisible(true);
            imageMenuBar.setVisible(false);
            spinner.setVisible(true);
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), menu_bar);
            translateTransition.setByX(+202);
            translateTransition.play();
            translateTransition.setOnFinished(event -> {
                openOrHide = false;
                spinner.setVisible(false);
                imageMenuBar1.setVisible(true);
            });
        } else {
            imageMenuBar1.setVisible(false);
            spinner.setVisible(true);
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), menu_bar);
            translateTransition1.setByX(-202);
            translateTransition1.play();
            translateTransition1.setOnFinished(event -> {
                spinner.setVisible(false);
                imageMenuBar.setVisible(true);
                openOrHide = true;
            });
        }
    }

    private void Animations() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(6), pane4);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(6), pane3);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(6), pane2);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();

                fadeTransition2.setOnFinished(event2 -> {
                    FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(6), pane2);
                    fadeTransition3.setFromValue(0);
                    fadeTransition3.setToValue(1);
                    fadeTransition3.play();

                    fadeTransition3.setOnFinished(event3 -> {
                        FadeTransition fadeTransition33 = new FadeTransition(Duration.seconds(6), pane3);
                        fadeTransition33.setFromValue(0);
                        fadeTransition33.setToValue(1);
                        fadeTransition33.play();

                        fadeTransition33.setOnFinished(event4 -> {
                            FadeTransition fadeTransition22 = new FadeTransition(Duration.seconds(6), pane4);
                            fadeTransition22.setFromValue(0);
                            fadeTransition22.setToValue(1);
                            fadeTransition22.play();

                            fadeTransition22.setOnFinished(event5 -> {
                                Animations();
                            });
                        });
                    });
                });
            });
        });
    }

    public void checkDateForPay() {
        DateTimeFormatter off = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        for (OquvGuruhModul ogm1 : q.getAllPuipleForPay()) {
            LocalDateTime updateDate = LocalDateTime.parse(ogm1.getUpdate_date() + " 11:30", off);
            if (now.getMonth().getValue() > updateDate.getMonth().getValue() || now.getYear() > updateDate.getYear()) {
                System.out.println(ogm1.getOquvchi_id() + " ga bir oylik to'lov +");
                String chegirma = ogm1.getChegirma();
                String viznos = "";
                for (GroupModul gm : q.searchGetGroupOfNarx(ogm1.getGuruh_id())) {
                    viznos = gm.getNarx();
                }
                Double newQarz = Double.parseDouble(ogm1.getQarz()) + Double.parseDouble(viznos) * (1 - Double.parseDouble(chegirma) / 100);
                tq.updateOquvchiQarzFromOquvchiGuruh(String.valueOf(newQarz), ogm1.getId());
                if (now.getMonth().getValue() < 10) {
                    q.updateUpdateDateOfALLForNewMonth("01/0" + now.getMonth().getValue() + "/" + now.getYear(), ogm1.getId());
                }
                if (now.getMonth().getValue() >= 10) {
                    q.updateUpdateDateOfALLForNewMonth("01/" + now.getMonth().getValue() + "/" + now.getYear(), ogm1.getId());
                }

            }
        }
    }

    @FXML
    Button btn_fan;

    public void fan(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/admin/fan/fan.fxml")));
            Stage stage = new Stage();
            stage.setTitle("FANLAR");
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            // Hide this current window (if this is what you want)
//            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void teacher(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/admin/teacher/teacher.fxml")));
            Stage stage = new Stage();
            stage.setTitle("O'QITUVCHILAR");
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            stage.setScene(new Scene(root, 800, 670));
            stage.show();
            // Hide this current window (if this is what you want)
//            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open_guruh(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/admin/guruh/guruh.fxml")));
            Stage stage = new Stage();
            stage.setTitle("GURUHLAR");
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            stage.setResizable(true);
            stage.setScene(new Scene(root, 1350, 730));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open_oquvchi(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/admin/oquvchi/oquvchilar.fxml")));
            Stage stage = new Stage();
            stage.setTitle("O'QUVCHILAR");
            stage.setResizable(true);
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            stage.setScene(new Scene(root, 1350, 710));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open_tolov(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/admin/tolov/tolov.fxml")));
            Stage stage = new Stage();
            stage.setTitle("O'QUVCHILAR");
            stage.setResizable(true);
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            stage.setScene(new Scene(root, 1350, 700));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open_maosh(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/admin/maosh/maosh.fxml")));
            Stage stage = new Stage();
            stage.setTitle("MAOSH $ * % = ?");
            stage.setResizable(true);
            stage.getIcons().add(new Image("file:src/img/icon.png"));
            stage.setScene(new Scene(root, 700, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open_davomad(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/admin/davomad/davomad.fxml")));
            Stage stage = new Stage();
            stage.setTitle("DAVOMAD + OR -");
            stage.setResizable(true);

            stage.getIcons().add(new Image("file:src/img/icon.png"));
            stage.setScene(new Scene(root, 1340, 670));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
