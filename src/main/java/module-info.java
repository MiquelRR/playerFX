module com.example.playerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;


    opens com.example.playerfx to javafx.fxml;
    exports com.example.playerfx;
}