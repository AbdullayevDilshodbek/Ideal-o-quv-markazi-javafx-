package sample.Tools;

import com.mysql.jdbc.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    Connection connection = null;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("IDEAL O'QUV MARKAZI");
        primaryStage.getIcons().add(new Image("file:src/img/icon.png"));
//        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1340, 670));
        primaryStage.show();
        checkDB();
    }

    public void checkDB(){
        connection = MysqlConnection.conDb();
        if (connection == null){
            InternalError error = new InternalError();
            error.getMessage();
            System.out.println("No");
        }else {
            System.out.println("Connected");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
