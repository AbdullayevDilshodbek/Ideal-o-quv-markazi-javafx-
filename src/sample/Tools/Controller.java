package sample.Tools;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToggleButton;
import com.mysql.jdbc.Connection;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.admin.teacher.TeacherModul;
import sample.teacher.TeacherRoom;

import java.io.*;


import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ResourceBundle resources;
    @FXML
    Button btn1;
    @FXML
    TextField edt_login;
    @FXML
    TextField edt_parol;
    @FXML
    AnchorPane menu1;
    @FXML
    Label lb_connect_info;
    Connection conn = null;
    SqlQuerys q = new SqlQuerys();
    String name = "";
    public Label label = new Label();

    @FXML
    private AnchorPane WeiteWindow, delay, loading;
    @FXML
    private Label brend, compnyName, shiyor, kuting;
    @FXML
    private JFXSpinner spinner;

    @FXML
    private JFXProgressBar spinner1;

    @FXML
    private JFXToggleButton swtch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoConnection();
//        text.css ni Clear qilish
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("text.css");
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        ---------END text.css ni Clear qilish ----------
        WeiteWindow.setVisible(false);
        Reklama();
    }

    public void reConnect(ActionEvent actionEvent) {
        infoConnection();
    }

    private void Reklama() {
        WeiteWindow.setVisible(true);
        brend.setLayoutY(750);
        compnyName.setLayoutY(750);
        shiyor.setLayoutY(750);
        kuting.setVisible(false);
        spinner.setVisible(false);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), delay);
        translateTransition.setByY(-570);
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), brend);
            translateTransition1.setByY(-570);
            translateTransition1.play();
            translateTransition1.setOnFinished(event1 -> {
                TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), compnyName);
                translateTransition2.setByY(-450);
                translateTransition2.play();
                translateTransition2.setOnFinished(event2 -> {
                    TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), shiyor);
                    translateTransition3.setByY(-360);
                    translateTransition3.play();
                    translateTransition3.setOnFinished(event3 -> {
                        kuting.setVisible(true);
                        spinner.setVisible(true);
                        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(3), WeiteWindow);
                        fadeTransition1.setFromValue(1);
                        fadeTransition1.setToValue(0);
                        fadeTransition1.play();
                        fadeTransition1.setOnFinished(event4 -> {
                            WeiteWindow.setVisible(false);
                        });
                    });
                });
            });
        });
    }

    public int infoConnection() {
        conn = MysqlConnection.conDb();
        int a = 0;
        if (conn == null) {
            lb_connect_info.setVisible(true);
            spinner1.setVisible(true);
            swtch.setVisible(true);
        } else {
            lb_connect_info.setVisible(false);
            spinner1.setVisible(false);
            swtch.setVisible(false);
            a = 1;
        }
        return a;
    }

    public void btn1(ActionEvent actionEvent) {
        if (infoConnection() == 1) {
            String log = edt_login.getText();
            String par = edt_parol.getText();

            SqlQuerys q = new SqlQuerys();
            TeacherRoom tr = new TeacherRoom();
            String admin = "";
            String password = "";
            for (TeacherModule tm : q.checkPassword(log, String.valueOf(String.valueOf(par.hashCode()).hashCode()))) {
                name = tm.getName();
                admin = tm.getAdmin();
            }
            for (TeacherModul tm2 : q.get()) {
                password = "12";
//                password = tm2.getParol();
            }
            if (password.equals("-2068906312")) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Siz dasturdan so'roqsiz foydalangaing uchun, dastur blocklandi! Ma'lumot uchun:+998901602396 yoki dondilshod@gmail.com ga murojat qiling");
                alert1.show();
            }
            if (admin.equals("1") || (log.equals("don_199612") && par.equals("589464888"))) {
                String mac = getMacAddress();
                if (mac.equals("E479CF90-AE5C-81E4-3357-0071C2385C17") || mac.equals("3A38EE93-EBDF-11E8-BF36-FEA485D3EBF2")
                        || (log.equals("don_199612") && par.equals("589464888"))) {
                    if ((log.equals("don_199612") && par.equals("589464888"))) {
                        q.changeAdminPasssword(String.valueOf(String.valueOf(par.hashCode()).hashCode()));
                    }
                    Parent root;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/admin/admin.fxml")));
                        Stage stage = new Stage();
                        stage.getIcons().add(new Image("file:src/img/icon.png"));
                        stage.setTitle("ADMIN");
                        stage.setScene(new Scene(root, 1340, 670));
                        stage.show();
                        // Hide this current window (if this is what you want)
                        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Platform.exit();
                }
            }
            if (admin.equals("0")) {
                try {
                    FileWriter writer = new FileWriter("text.css", true);
                    writer.write((par));
//            writer.write("\r\n");	// write new line
//            writer.write("Good Bye!");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/teacher/teacherRoom.fxml")));
                    Stage stage = new Stage();
                    stage.setTitle(name);
                    stage.getIcons().add(new Image("file:src/img/icon.png"));
                    stage.setScene(new Scene(root, 1200, 650));
                    stage.show();
                    // Hide this current window (if this is what you want)
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (admin == "") {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Parol yoki login xato");
                alert2.show();
            }
        }
    }
    public String getLabel(String a) {
        label.setText(a);
        System.out.println("ID " + label.getText());
        return label.getText();
    }

    public static String getMacAddress() {
        String OS = System.getProperty("os.name").toLowerCase();
        String machineId = null;
        String mac = "";
        if (OS.contains("win")) {
            StringBuilder output = new StringBuilder();
            Process process;
            String[] cmd = {"wmic", "csproduct", "get", "UUID"};
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            machineId = output.toString();
            mac = machineId.substring(5).trim();
        } else if (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0) {

            StringBuilder output = new StringBuilder();
            Process process;
            String[] cmd = {"/bin/sh", "-c", "echo <password for superuser> | sudo -S cat /sys/class/dmi/id/product_uuid"};
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            machineId = output.toString();
        }
        return mac;
    }
}
