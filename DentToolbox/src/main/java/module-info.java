module com.example.denttoolbox {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires poi;

    opens com.example.denttoolbox to javafx.fxml;
    exports com.example.denttoolbox;
    exports com.example.denttoolbox.entity;
    opens com.example.denttoolbox.entity to javafx.fxml;
}