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
import javafx.scene.input.DragEvent;
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
    private Boolean mute;
    static Track actTrack;
    private Integer volumeLevel;
    private static MediaPlayer player;
    private Media media;
    private Integer infoChars = 53;
    private static ScrollText infoTrack;
    private static Timeline timeline;
    private Double durationSecs;
    private Boolean noProgressRefresh;
    private int animaStep;
    private Boolean repeat;

    static {
        chooser.setInitialDirectory(new File("./src/main/resources/com/example/playerfx/music/")); // ruta carpeta
        chooser.setTitle("Selecciona un Archivo de sonido");
        chooser.getExtensionFilters().addAll(new ExtensionFilter("Audio",
                "*.*"),
                new ExtensionFilter("Todo", "*"));
    }

    static File file;
    private Image loopImage = new Image(getClass().getResourceAsStream("images/bucle.png"));
    private Image noLoopImage = new Image(getClass().getResourceAsStream("images/bucleOff.png"));
    private Image playImg = new Image(getClass().getResourceAsStream("images/play.png"));
    private Image pauseImg = new Image(getClass().getResourceAsStream("images/pause.png"));
    private Image muteImage = new Image(getClass().getResourceAsStream("images/mute.png"));
    private Image volumeImage = new Image(getClass().getResourceAsStream("images/volume.png"));
    private Image volume1 = new Image(getClass().getResourceAsStream("images/volume1.png"));
    private Image volume2 = new Image(getClass().getResourceAsStream("images/volume2.png"));
    private Image volume3 = new Image(getClass().getResourceAsStream("images/volume3.png"));
    private Image volume4 = new Image(getClass().getResourceAsStream("images/volume4.png"));
    private Image volume5 = new Image(getClass().getResourceAsStream("images/volume5.png"));
    private Image[] volumeImages = new Image[] { muteImage, volume1, volume2, volume3, volume4, volume5 };
    private Image volumeA0 = new Image(getClass().getResourceAsStream("images/volumeA0.png"));
    private Image volumeA1 = new Image(getClass().getResourceAsStream("images/volumeA1.png"));
    private Image volumeA2 = new Image(getClass().getResourceAsStream("images/volumeA2.png"));
    private Image volumeA3 = new Image(getClass().getResourceAsStream("images/volumeA3.png"));
    private Image volumeA4 = new Image(getClass().getResourceAsStream("images/volumeA4.png"));
    private Image volumeA5 = new Image(getClass().getResourceAsStream("images/volumeA5.png"));
    private Image[] volumeAnim = new Image[] { volumeA0, volumeA1, volumeA2, volumeA3, volumeA4, volumeA5 };

    // *********** */

    @FXML
    void hideVolumeSlider(MouseEvent event) {
        volumeSlider.setVisible(false);

    }

    @FXML
    void showVolumeSlider(MouseEvent event) {
        volumeSlider.setVisible(true);
        animaStep = 0;

    }

    @FXML
    private ImageView imgBucle;

    @FXML
    private ImageView imgPlay;

    @FXML
    private ImageView imgVolume;

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
    private Slider volumeSlider;

    @FXML
    void chooseFile(ActionEvent event) {

        file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                media = new Media(file.toURI().toString());
                if (player != null)
                    player.stop();
                player = new MediaPlayer(media);
            } catch (MediaException e) {
                e.printStackTrace();
            }
            play = false;
            playSwitch(event);
            editVolume(null);
            noProgressRefresh = false;
            hiderBox.setVisible(false);
            player.totalDurationProperty().addListener(ob -> setDuration(player));
            player.setOnEndOfMedia(() -> endOfTrack());
            animaStep = 0;

        }
    }

    @FXML
    private void endOfTrack() {
        play = repeat;
        toInit(null);
    }

    @FXML
    private void setDuration(MediaPlayer player) {
        durationSecs = player.totalDurationProperty().getValue().toSeconds();
        trackLength.setText(Track.toMmSs(durationSecs));
        addedMetadata();
    }

    @FXML
    private void addedMetadata() {
        actTrack = new Track(media.getSource(), media.getMetadata(), durationSecs);
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

    void playIfPlaying() {
        play = !play;
        playSwitch(null);
    }

    @FXML
    void playSwitch(ActionEvent event) {
        play = !play;
        if (play) {
            imgPlay.setImage(pauseImg);
            player.play();
        } else {
            imgPlay.setImage(playImg);
            player.pause();
        }

    }

    @FXML
    void muteSwitch(ActionEvent event) {
        mute = !mute;
        imgVolume.setImage((mute) ? muteImage : volumeImages[volumeLevel]);
        player.setMute(mute);
        if (!mute && volumeLevel == 0) {
            volumeSlider.setValue(2);
            editVolume(null);
        }
        hideVolumeSlider(null);
    }

    @FXML
    void repeatSw(ActionEvent event) {
        repeat = !repeat;
        imgBucle.setImage((repeat) ? loopImage : noLoopImage);
        player.setMute(mute);
        hideVolumeSlider(null);
    }

    @FXML
    void toEnd(ActionEvent event) {
        // en un unica cancion "toEnd no tiene sentido, cambio la funcionalidad del
        // botón a -> avance 30 secs"
        Double currentTime = player.getCurrentTime().toSeconds();
        if (currentTime != null && durationSecs != null) {
            Duration newTime = new Duration((currentTime + 30) * 1000);
            player.seek(newTime);
            playIfPlaying();
        }
    }

    @FXML
    void toInit(ActionEvent event) {
        player.stop();
        player.seek(Duration.ZERO);
        playIfPlaying();
    }

    @FXML
    void editVolume(MouseEvent event) {
        if (player != null) {
            volumeLevel = (int) volumeSlider.getValue();
            if (volumeLevel > 0) {
                mute = true;
                muteSwitch(null);
                imgVolume.setImage(volumeImages[volumeLevel]);
                player.setVolume((double) (volumeLevel / 5D));
            } else {
                mute = false;
                muteSwitch(null);
            }
        }

    }

    @FXML
    void editSeekMode(MouseEvent event){
        noProgressRefresh = true;
    }

    @FXML
    void editSeekDone(MouseEvent event) {
        Double point= timeSlider.getValue();
        if (durationSecs != null) {
            Duration newTime = new Duration(durationSecs*point * 1000);
            player.seek(newTime);
            playIfPlaying();
        }
        noProgressRefresh = false;
        
    }

    @FXML
    private void refresh() {
        animaStep++;
        infoText.setText(infoTrack.getInstantString());
        if (animaStep == 20)
            volumeSlider.setVisible(false);
        if (play) {
            if (mute)
                imgVolume.setImage(muteImage);
            else
                imgVolume.setImage(volumeAnim[((animaStep) % (volumeLevel + 1))]);
        } else {
            imgVolume.setImage(volumeImages[volumeLevel]);
        }
        if (!noProgressRefresh) {
            if (player != null) {
                Double currentTime = player.getCurrentTime().toSeconds();
                if (currentTime != null && durationSecs != null) {
                    actTime.setText(Track.toMmSs(currentTime));
                    timeSlider.setValue(currentTime / durationSecs);
                }
            }
        }

    }

    @FXML
    void initialize() {
        volumeLevel=5;
        repeat = true;
        animaStep = 0;
        noProgressRefresh = true;
        timeline = new Timeline(new KeyFrame(Duration.millis(150), event -> {
            refresh();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        volumeSlider.setVisible(false);
        volumeSlider.setValue(1);
        App.st.setResizable(false);
        hiderBox.setVisible(true);
        play = false;
        mute = false;
        infoTrack = new ScrollText(infoChars,
                "Seleccione un archivo de música para empezar la reproducción, tenga en cuenta que el regetón no es un género musical, es más bien un tipo de ruido");
    }

}
