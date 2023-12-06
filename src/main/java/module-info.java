module com.example.cocas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cocas to javafx.fxml;
    exports com.example.cocas;
}