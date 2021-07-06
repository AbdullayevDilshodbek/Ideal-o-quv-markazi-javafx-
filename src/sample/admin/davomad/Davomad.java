package sample.admin.davomad;

import com.mysql.jdbc.Connection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Tools.MysqlConnection;
import sample.Tools.SqlQuerys;
import sample.admin.oquvchi.OquvchiQuerys;
import sample.admin.tolov.TolovQuery;
import sample.teacher.TeacherRoomQuery;
import sample.teacher.davomad.TeacherDavomadQuery;
import sample.teacher.davomad.lineChart.TeacherChartModul;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Davomad implements Initializable {
    TeacherRoomQuery q = new TeacherRoomQuery();
    SqlQuerys sq = new SqlQuerys();
    OquvchiQuerys oq = new OquvchiQuerys();
    TolovQuery tq = new TolovQuery();
    TeacherDavomadQuery tdq = new TeacherDavomadQuery();
    AdminDavomadQuery adq = new AdminDavomadQuery();
    Connection conn;

    LocalDateTime now = LocalDateTime.now();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        updateTable();
        totalDavomad();

        ArrayList<Integer> yil = new ArrayList<>();
        for (int i = now.getYear() - 4; i <= now.getYear() + 4; i++) {
            yil.add(i);
        }
        cbox_yil.setItems(FXCollections.observableList(yil));

        ArrayList<Integer> oy = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            oy.add(i);
        }
//        uploadLineChartForThisYears();
        XYChart.Series series = new XYChart.Series();
        series.setName(String.valueOf(now.getYear()));
//        chart();
    }

    @FXML
    TableColumn<AdminDavomadModul, String> tb_id;
    @FXML
    TableColumn<AdminDavomadModul, String> tb_tr;
    @FXML
    TableColumn<AdminDavomadModul, String> tb_oquvchi;
    @FXML
    TableColumn<AdminDavomadModul, String> tb_teacher;
    @FXML
    TableColumn<AdminDavomadModul, String> tb_guruh;
    @FXML
    TableColumn<AdminDavomadModul, String> tb_sana;
    @FXML
    TableColumn<AdminDavomadModul, String> tb_status;
    @FXML
    TableView<AdminDavomadModul> tb_view;


    public void updateTable() {
        try {
            ObservableList<AdminDavomadModul> view = FXCollections.observableList(adq.getAllDavomad());
            tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_tr.setCellValueFactory(new PropertyValueFactory<>("tr"));
            tb_oquvchi.setCellValueFactory(new PropertyValueFactory<>("oquvchi"));
            tb_teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
            tb_guruh.setCellValueFactory(new PropertyValueFactory<>("guruh"));
            tb_sana.setCellValueFactory(new PropertyValueFactory<>("sana"));
            tb_status.setCellValueFactory(new PropertyValueFactory<>("status"));
            tb_view.setItems(view);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    ComboBox<Integer> cbox_yil;
    @FXML
    ComboBox<String> cbox_oy;
    @FXML
    ComboBox<String> cbox_kun;

    @FXML
    PieChart chart_yil;
    @FXML
    PieChart chart_oy;

    public void totalDavomad() {
        int bor = 0;
        int yoq = 0;
        int bor1 = 0;
        int yoq1 = 0;
        try {
            for (AdminDavomadModul adm : adq.getAllDavomad()) {
                if (adm.getStatus() == "+") {
                    bor++;
                } else {
                    yoq++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Double umumiy = Double.parseDouble(String.valueOf(bor)) + Double.parseDouble(String.valueOf(yoq));
        ObservableList<PieChart.Data> chart_yilData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Kelganlar " + (int) (bor * 100 / umumiy) + " %", bor),
                        new PieChart.Data("Yo'qlar " + (int) (yoq * 100 / umumiy) + " %", yoq));
        chart_yil.setData(chart_yilData);

        try {
            for (AdminDavomadModul adm : adq.getAllDavomad()) {
                if (adm.getSana().contains(String.valueOf(now.getYear()))) {
                    String shuoy = "";
                    String day = "";
                    if (now.getDayOfMonth() > 9) {
                        day = String.valueOf(now.getDayOfMonth());
                    } else {
                        day = "0" + (now.getDayOfMonth());
                    }
                    if (now.getMonth().getValue() > 9) {
                        shuoy = day + "/" + (now.getMonth().getValue()) + "/" + now.getYear();
                    } else {
                        shuoy = day + "/0" + (now.getMonth().getValue()) + "/" + now.getYear();
                    }
                    if (adm.getSana().contains(shuoy)) {
                        if (adm.getStatus() == "+") {
                            bor1++;
                        } else {
                            yoq1++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        double um = Double.parseDouble(String.valueOf(bor1)) + Double.parseDouble(String.valueOf(yoq1));
        ObservableList<PieChart.Data> chart_oyData = FXCollections.observableArrayList(
                new PieChart.Data("Kelgan " + (int) (bor1 * 100 / um) + " %", bor1),
                new PieChart.Data("Kelmagan " + (int) (yoq1 * 100 / um) + " %", yoq1));
        chart_oy.setLabelLineLength(2);
        chart_oy.setData(chart_oyData);

//        Oqtuvchilar davomad diagrammasi
        List<String> listTeacher = new ArrayList<>();
        for (int i = 0; i < tb_view.getItems().size(); i++) {
            tb_view.getSelectionModel().select(i);
            AdminDavomadModul btn = tb_view.getSelectionModel().getSelectedItem();
            if (!listTeacher.contains(btn.getTeacher())) {
                listTeacher.add(btn.getTeacher());
            }
        }
        List<TeacherChartModul> teacherData = new ArrayList<>();
        for (int i = 0; i < listTeacher.size(); i++) {
            int count = 0;
            Double borlar = 0.0;
            List<String> oy = new ArrayList<>();
            for (int j = 0; j < tb_view.getItems().size(); j++) {
                tb_view.getSelectionModel().select(i);
                AdminDavomadModul btn = tb_view.getSelectionModel().getSelectedItem();
                if (btn.getTeacher().equals(listTeacher.get(i))) {
                    count++;
                    if (btn.getStatus().equals("+")) {
                        borlar++;

                    }
                }
            }
        }

    }

    public void sortFromYil(ActionEvent actionEvent) {
        try {
            uploadLineChartForThisYears();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void uploadLineChartForThisYears() throws SQLException {
        Double bor = 0.0;
        Double yoq = 0.0;

        Double Ybor = 0.0;
        Double Yyoq = 0.0;
        int Yfoiz = 0;

        Double Fbor = 0.0;
        Double Fyoq = 0.0;
        int Ffoiz = 0;

        Double Mbor = 0.0;
        Double Myoq = 0.0;
        int Mfoiz = 0;

        Double Abor = 0.0;
        Double Ayoq = 0.0;
        int Afoiz = 0;

        Double MAYbor = 0.0;
        Double MAYyoq = 0.0;
        int MAYfoiz = 0;

        Double IYULbor = 0.0;
        Double IYULyoq = 0.0;
        int IYULfoiz = 0;

        Double IYUNbor = 0.0;
        Double IYUNyoq = 0.0;
        int IYUNfoiz = 0;

        Double AVbor = 0.0;
        Double AVyoq = 0.0;
        int AVfoiz = 0;

        Double Sbor = 0.0;
        Double Syoq = 0.0;
        int Sfoiz = 0;

        Double Obor = 0.0;
        Double Oyoq = 0.0;
        int Ofoiz = 0;

        Double Nbor = 0.0;
        Double Nyoq = 0.0;
        int Nfoiz = 0;

        Double Dbor = 0.0;
        Double Dyoq = 0.0;
        int Dfoiz = 0;

        for (AdminDavomadModul adm : adq.getAllDavomad()) {
            String yil = String.valueOf(cbox_yil.getSelectionModel().getSelectedItem());
            if (adm.getSana().contains(yil)) {
                String yan = "/01/" + yil;
                if (adm.getSana().contains(yan)) {
                    if (adm.getStatus().equals("+")) {
                        Ybor++;
                    } else {
                        Yyoq++;
                    }
                }

                String fev = "/02/" + yil;
                if (adm.getSana().contains(fev)) {
                    if (adm.getStatus().equals("+")) {
                        Fbor++;
                    } else {
                        Fyoq++;
                    }
                }

                String mart = "/03/" + yil;
                if (adm.getSana().contains(mart)) {
                    if (adm.getStatus().equals("+")) {
                        Mbor++;
                    } else {
                        Myoq++;
                    }
                }

                String aprel = "/04/" + yil;
                if (adm.getSana().contains(aprel)) {
                    if (adm.getStatus().equals("+")) {
                        Abor++;
                    } else {
                        Ayoq++;
                    }
                }

                String may = "/05/" + yil;
                if (adm.getSana().contains(may)) {
                    if (adm.getStatus().equals("+")) {
                        MAYbor++;
                    } else {
                        MAYyoq++;
                    }
                }

                String iyun = "/06/" + yil;
                if (adm.getSana().contains(iyun)) {
                    if (adm.getStatus().equals("+")) {
                        IYUNbor++;
                    } else {
                        IYUNyoq++;
                    }
                }

                String iyul = "/07/" + yil;
                if (adm.getSana().contains(iyul)) {
                    if (adm.getStatus().equals("+")) {
                        IYULbor++;
                    } else {
                        IYULyoq++;
                    }
                }

                String avgust = "/08/" + yil;
                if (adm.getSana().contains(avgust)) {
                    if (adm.getStatus().equals("+")) {
                        AVbor++;
                    } else {
                        AVyoq++;
                    }
                }

                String sen = "/09/" + yil;
                if (adm.getSana().contains(sen)) {
                    if (adm.getStatus().equals("+")) {
                        Sbor++;
                    } else {
                        Syoq++;
                    }
                }

                String ok = "/10/" + yil;
                if (adm.getSana().contains(ok)) {
                    if (adm.getStatus().equals("+")) {
                        Obor++;
                    } else {
                        Oyoq++;
                    }
                }

                String nay = "/11/" + yil;
                if (adm.getSana().contains(nay)) {
                    if (adm.getStatus().equals("+")) {
                        Nbor++;
                    } else {
                        Nyoq++;
                    }
                }

                String dek = "/12/" + yil;
                if (adm.getSana().contains(dek)) {
                    if (adm.getStatus().equals("+")) {
                        Dbor++;
                    } else {
                        Dyoq++;
                    }
                }
            }
        }
        Yfoiz = (int) (Ybor * 100 / (Ybor + Yyoq));
        Ffoiz = (int) (Fbor * 100 / (Fbor + Fyoq));
        Mfoiz = (int) (Mbor * 100 / (Mbor + Myoq));
        Afoiz = (int) (Abor * 100 / (Abor + Ayoq));
        MAYfoiz = (int) (MAYbor * 100 / (MAYbor + MAYyoq));
        IYUNfoiz = (int) (IYUNbor * 100 / (IYUNbor + IYUNyoq));
        IYULfoiz = (int) (IYULbor * 100 / (IYULbor + IYULyoq));
        AVfoiz = (int) (AVbor * 100 / (AVbor + AVyoq));
        Sfoiz = (int) (Sbor * 100 / (Sbor + Syoq));
        Ofoiz = (int) (Obor * 100 / (Obor + Oyoq));
        Nfoiz = (int) (Nbor * 100 / (Nbor + Nyoq));
        Dfoiz = (int) (Dbor * 100 / (Dbor + Dyoq));

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("Davomad Monitoring, " + cbox_yil.getSelectionModel().getSelectedItem());
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Ideal o'quv markazi");
        if (Yfoiz > 0) {
            series.getData().add(new XYChart.Data("YAN", Yfoiz));
        }
        if (Ffoiz > 0) {
            series.getData().add(new XYChart.Data("FEV", Ffoiz));
        }
        if (Mfoiz > 0) {
            series.getData().add(new XYChart.Data("MAR", Mfoiz));
        }
        if (Afoiz > 0) {
            series.getData().add(new XYChart.Data("APR", Afoiz));
        }
        if (MAYfoiz > 0) {
            series.getData().add(new XYChart.Data("MAY", MAYfoiz));
        }
        if (IYUNfoiz > 0) {
            series.getData().add(new XYChart.Data("IUN", IYUNfoiz));
        }
        if (IYULfoiz > 0) {
            series.getData().add(new XYChart.Data("IUL", IYULfoiz));
        }
        if (AVfoiz > 0) {
            series.getData().add(new XYChart.Data("AUG", AVfoiz));
        }
        if (Sfoiz > 0) {
            series.getData().add(new XYChart.Data("SEP", Sfoiz));
        }
        if (Ofoiz > 0) {
            series.getData().add(new XYChart.Data("OKT", Ofoiz));
        }
        if (Nfoiz > 0) {
            series.getData().add(new XYChart.Data("NOY", Nfoiz));
        }
        if (Dfoiz > 0) {
            series.getData().add(new XYChart.Data("DEK", Dfoiz));
        }
        lineChart.getData().add(series);
        paneView.getChildren().clear();
        paneView.setOpacity(0.8);
        paneView.getChildren().add(lineChart);
    }

    @FXML
    Pane paneView;
}
