package com.example.playerfx;

import java.io.File;
import java.util.Collection;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class gramolaController {
    static FileChooser chooser = new FileChooser();
    static Boolean play;
    private Double volume;
    private MediaPlayer player;
    private Media media;
    
    static {
        chooser.setInitialDirectory(new File("/home/miquel/Música")); // ruta carpeta
        chooser.setTitle("Selecciona un Archivo de sonido");
        chooser.getExtensionFilters().addAll(new ExtensionFilter("Audio",
                "*.*"),
                new ExtensionFilter("Todo", "*"));
    }

    static File file;

    private Image playImg = new Image(getClass().getResourceAsStream("images/play.png"));
    private Image pauseImg = new Image(getClass().getResourceAsStream("images/pause.png"));

    @FXML
    private ImageView imgPlay;

    @FXML
    private Label actTime;

    @FXML
    private Label infoText;

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
    private Slider volSlider;

    @FXML
    void chooseFile(ActionEvent event) {
        
        file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                media = new Media(file.toURI().toString());
                if (player != null)
                    player.stop();
                player = new MediaPlayer(media);
                player.setVolume(volume);
                player.setAutoPlay(true);
            } catch (MediaException e) {
                System.out.println("m".repeat(150) + e.getMessage());
                e.printStackTrace();
            }
            play = false;
            playSwitch(event);
            hiderBox.setVisible(false);
            media.getMetadata().addListener((MapChangeListener<String,Object>) change -> {showMetadata();});
            

        }
    }
    private void showMetadata(){
        String tx= "";
        for (String str : media.getMetadata().keySet()) {
            tx += str +" -> "+media.getMetadata().get(str).toString()+"\n"; 
        }
        showDialog(tx);
    }

    private void showDialog(String st) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Debug msg");
        alert.setHeaderText("wtfk!");
        alert.setContentText(st);
        alert.showAndWait();
    }

    @FXML
    void playSwitch(ActionEvent event) {
        play = !play;
        imgPlay.setImage((play) ? pauseImg : playImg);
        if (play)
            player.play();
        else
            player.pause();
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

    @FXML
    void editVolume(MouseEvent event) {

    }

    @FXML
    void editSeconds(MouseEvent event) {

    }

    @FXML
    void initialize() {
        volSlider.setVisible(false);
        App.st.setResizable(false);
        hiderBox.setVisible(true);
        play = false;
        volume = 0.8;
        infoText.setText("");
    }

}
