import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class TableViewExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        TableView<Person> table = new TableView<Person>();

        ObservableList<Person> teamMembers = getTeamMembers();
        table.setItems(teamMembers);

        TableColumn<Person,String> firstNameCol = new TableColumn<Person,String>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        TableColumn<Person,String> lastNameCol = new TableColumn<Person,String>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        ObservableList<TableColumn<Person, ?>> columns = table.getColumns();
        columns.add(firstNameCol);
        columns.add(lastNameCol);

        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < table.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
        }

        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if(table.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }

        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        workbook.write(fileOut);
        fileOut.close();

        Platform.exit();

    }

    private ObservableList<Person> getTeamMembers() {

        ObservableList<Person> people = FXCollections.observableArrayList();
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        people.add(person1);
        people.add(person1);
        people.add(person1);
        people.add(person1);
        people.add(person1);
        people.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        people.add(person2);
        people.add(person2);
        people.add(person2);
        people.add(person2);
        people.add(person2);

        return people;
    }

    public class Person {
        private StringProperty firstName;
        public void setFirstName(String value) { firstNameProperty().set(value); }
        public String getFirstName() { return firstNameProperty().get(); }
        public StringProperty firstNameProperty() {
            if (firstName == null) firstName = new SimpleStringProperty(this, "firstName");
            return firstName;
        }

        private StringProperty lastName;
        public void setLastName(String value) { lastNameProperty().set(value); }
        public String getLastName() { return lastNameProperty().get(); }
        public StringProperty lastNameProperty() {
            if (lastName == null) lastName = new SimpleStringProperty(this, "lastName");
            return lastName;
        }
    }
}