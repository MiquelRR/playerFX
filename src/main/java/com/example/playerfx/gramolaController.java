//https://openjfx.io/javadoc/19/javafx.media/javafx/scene/media/package-summary.html

package com.example.playerfx;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class gramolaController {
    static FileChooser chooser = new FileChooser();
    private Boolean play;
    static Track actTrack;
    private Double volume;
    private MediaPlayer player;
    private Media media;
    private Integer infoChars = 53;
    private static ScrollText infoTrack;
    private static Timeline timeline;
    private Double durationSecs;
    private Boolean wellcome;

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
    private ImageView background;

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
    private Label trackLength;

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
            } catch (MediaException e) {
                System.out.println("m".repeat(150) + e.getMessage());
                e.printStackTrace();
            }
            play = false;
            playSwitch(event);
            wellcome=false;
            hiderBox.setVisible(false);
            player.totalDurationProperty().addListener(ob -> setDuration(player));

        }
    }

    @FXML
    private void setDuration(MediaPlayer player) {
        durationSecs = player.totalDurationProperty().getValue().toSeconds();
        addedMetadata();

    }

    @FXML
    private void addedMetadata() {
        actTrack = new Track(media.getSource(), media.getMetadata(), durationSecs);
        System.out.println(">".repeat(150) + actTrack.getDurationString());
        trackLength.setText(actTrack.getDurationString());
        infoTrack = new ScrollText(infoChars, actTrack.getAllinfo());

    }

    private void showDialog(String st) {
        if (st == null)
            st = "null";
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
    private void refresh() {
        if (play || wellcome ) {
            infoText.setText(infoTrack.getInstantString());
            Double currentTime = player.getCurrentTime().toSeconds();
            if (currentTime != null && durationSecs != null) {
                actTime.setText(Track.toMmSs(currentTime));
                timeSlider.setValue(currentTime / durationSecs);
            }
        }
    }

    @FXML
    void initialize() {
        wellcome=true;
        timeline = new Timeline(new KeyFrame(Duration.millis(150), event -> {
            refresh();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        volSlider.setVisible(false);
        App.st.setResizable(false);
        hiderBox.setVisible(true);
        play = false;
        volume = 0.8;
        infoTrack = new ScrollText(infoChars,
                "Seleccione un archivo de música para empezar la reproducción, tenga en cuenta que el regetón no es un género musical, es más bien un tipo de ruido");
    }

}
