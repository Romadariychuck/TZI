module com.example.lab1_2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.lab1_2 to javafx.fxml;
    exports com.example.lab1_2;
}