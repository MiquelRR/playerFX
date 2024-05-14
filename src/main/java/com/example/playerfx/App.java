package com.example.playerfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    @SuppressWarnings("exports")
    public static Stage st;

    @Override
    public void start(Stage stage) throws IOException {
        st=stage;
        scene = new Scene(loadFXML("gramola"),600 ,600);
        stage.setScene(scene);
        stage.setTitle("DAMe música! MiquelRR");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}