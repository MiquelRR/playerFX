package com.example.playerfx;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class gramolaController {
    static FileChooser chooser = new FileChooser();
    static{
        chooser.setInitialDirectory(new File("/home/miquel/Música")); //ruta carpeta
        chooser.setTitle("Selecciona un Archivo de sonido");
        chooser.getExtensionFilters().addAll(new ExtensionFilter("Audio",
                "*.*"),
                new ExtensionFilter("Todo", "*"));
    }

    static File file; 

    @FXML
    private Label actTime;

    @FXML
    private Rectangle hiderBox;

    @FXML
    private Button muteOrNot;

    @FXML
    private Button playOrPause;

    @FXML
    private Button repeatOsNot;

    @FXML
    private Label songTime;

    @FXML
    private Slider timeSlider;

    @FXML
    void chooseFile(ActionEvent event) {
        file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                hiderBox.setVisible(false);
            System.out.println("*".repeat(150)+file.toURI().toString());
            Media media = new Media(file.toURI().toString());
            playSong(media);
                
            } catch (URISyntaxException e) {
                System.out.println("e".repeat(150)+e.getMessage());
               
            }catch (MediaException e) {
                System.out.println("m".repeat(150)+e.getMessage());
                e.printStackTrace();
            } 
            
        }
    }

    @FXML
    void playSwitch(ActionEvent event) {

    }

    @FXML
    void repeatSw(ActionEvent event) {

    }

    @FXML
    void toEnd(ActionEvent event) {

    }

    @FXML
    void toInit(ActionEvent event) {

    }

    @SuppressWarnings("exports")
    @FXML
    public void playSong(Media media) throws URISyntaxException{
        MediaPlayer player = new MediaPlayer(media);
        player.setVolume(0.5); // volumen a la mitad
        player.setAutoPlay(true); // play??
    }

    @FXML
    void initialize(){
        App.st.setResizable(false);
        hiderBox.setVisible(true);
    }

}
