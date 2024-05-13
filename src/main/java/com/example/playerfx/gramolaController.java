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
    private Boolean mute;
    static Track actTrack;
    private Integer volumeLevel;
    private static MediaPlayer player;
    private Media media;
    private Integer infoChars = 53;
    private static ScrollText infoTrack;
    private static Timeline timeline;
    private Double durationSecs;
    private Boolean welcome;
    private int animaStep;
    private Boolean repeat;

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

    // ***************** */

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
                System.out.println("x".repeat(100) + ((double) (volumeLevel / 5)));
                player.setVolume(.8);
            } catch (MediaException e) {
                System.out.println("m".repeat(150) + e.getMessage());
                e.printStackTrace();
            }
            play = false;
            playSwitch(event);
            welcome = false;
            hiderBox.setVisible(false);
            player.totalDurationProperty().addListener(ob -> setDuration(player));
            player.setOnEndOfMedia(() -> {
                endOfTrack();
            });

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
    void muteSwitch(ActionEvent event) {
        mute = !mute;
        imgVolume.setImage((mute) ? muteImage : volumeImages[volumeLevel]);
        player.setMute(mute);
        hideVolumeSlider(null);
    }

    @FXML
    void repeatSw(ActionEvent event) {

    }

    @FXML
    void toEnd(ActionEvent event) {
        
        Double currentTime = player.getCurrentTime().toSeconds();
        if (currentTime != null && durationSecs != null) {
            //Duration newTime = new Duration((currentTime+10)*1000);
            Duration newTime = new Duration((50+10)*1000);
            System.out.println("HOlaa "+Track.toMmSs(newTime.toSeconds()));
            //player.stop();
            player.seek(newTime);
            if(play) player.play();
        }
        
        
        //timeSlider.setValue(1D);
        
        ///actTime.setText(Track.toMmSs(durationSecs));
        //playSwitch(null);

    }

    @FXML
    void toInit(ActionEvent event) {
        player.stop();
        
        player.seek(Duration.ZERO);
        //player.seek(new Duration((50+10)*1000));
        if (play)
            player.play();
    }

    @FXML
    void editVolume(MouseEvent event) {
        System.out.println(Double.toString(volumeSlider.getValue()));
        volumeLevel = (int) volumeSlider.getValue();
        imgVolume.setImage(volumeImages[volumeLevel]);
        System.out.println("v".repeat(100) + ((double) (volumeLevel / 5D)));
        player.setVolume((double) (volumeLevel / 5D));
        // showDialog(Double.toString(volSlider.getValue()));

    }

    @FXML
    void editSeconds(MouseEvent event) {

    }

    @FXML
    private void refresh() {
        if (player != null) {
            Double currentTime = player.getCurrentTime().toSeconds();
            if (currentTime != null && durationSecs != null) {
                actTime.setText(Track.toMmSs(currentTime));
                timeSlider.setValue(currentTime / durationSecs);
            }
        }
        if (welcome) {
            infoText.setText(infoTrack.getInstantString());
        } else {
            if (play) {
                animaStep++;
                if (animaStep == 20)
                    volumeSlider.setVisible(false);
                ;
                infoText.setText(infoTrack.getInstantString());
                if (mute)
                    imgVolume.setImage(muteImage);
                else
                    imgVolume.setImage(volumeAnim[((animaStep) % (volumeLevel + 1))]);
            }
        }

    }

    @FXML
    void initialize() {
        repeat = true;
        animaStep = 0;
        welcome = true;
        timeline = new Timeline(new KeyFrame(Duration.millis(150), event -> {
            refresh();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        volumeSlider.setVisible(false);
        App.st.setResizable(false);
        hiderBox.setVisible(true);
        play = false;
        mute = false;
        volumeLevel = 1;
        imgVolume.setImage(volumeImages[volumeLevel]);
        infoTrack = new ScrollText(infoChars,
                "Seleccione un archivo de música para empezar la reproducción, tenga en cuenta que el regetón no es un género musical, es más bien un tipo de ruido");
    }

}
