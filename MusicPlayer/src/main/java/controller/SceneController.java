package controller;

/**
 * Created by Stefan Smiljkovic on 24.05.2016.
 */
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.MusicFinder;
import model.Player;

import java.io.File;
import java.net.URL;
import java.util.*;

public class SceneController implements Initializable{

    @FXML
    private Label progressTimeString;

    @FXML
    private BorderPane borderPaneMain;

    @FXML
    private Button btPlay;

    @FXML
    private Button btPrev;

    @FXML
    private Button btNext;

    @FXML
    private Button btDirectory;

    @FXML
    private ImageView imageView;

    @FXML
    private ProgressBar progressBarSong;

    @FXML
    private Label labelYear;

    @FXML
    private Label labelTitle;

    @FXML
    private Label labelArtist;

    @FXML
    private Label labelAlbum;


    private Stage stage;
    private MusicFinder musicFinder;
    private Player player;
    private Timer timer;
    private Timer resizeTimer;
    private boolean musicCoverShowing;


    @FXML
    void progressClickHandler(MouseEvent event) {
        if(player != null) {
            player.jump(event.getX()/progressBarSong.getWidth());
        }
    }

    @FXML
    void btPreviousHandler(ActionEvent event) {
        if(musicFinder.getPosition() > 1 && player != null)
        {
            player.pause();
            player.start(musicFinder.prevElement());
            player.play();
            btPlay.setGraphic(new ImageView(getClass().getResource("/pause.png").toExternalForm()));

            setMetadata();
        }
    }

    @FXML
    void btPlayHandler(ActionEvent event) {
        if(player != null && player.isPlaying())
        {
            player.pause();
            btPlay.setGraphic(new ImageView(getClass().getResource("/play.png").toExternalForm()));
        }
        else if(player != null)
        {
            player.play();
            btPlay.setGraphic(new ImageView(getClass().getResource("/pause.png").toExternalForm()));
        }
    }

    @FXML
    void btNextHandler(ActionEvent event) {

        if(musicFinder.hasMoreElements() && player != null)
        {
            player.pause();
            player.start(musicFinder.nextElement());
            player.play();
            btPlay.setGraphic(new ImageView(getClass().getResource("/pause.png").toExternalForm()));

            setMetadata();
        }
    }

    @FXML
    void btOpenDirectoryHandler(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();

        File f = chooser.showDialog(stage);
        if(f == null)
            return;

        musicFinder.setDirectoryName(f.getAbsolutePath());
        if(!musicFinder.searchForMusic())
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("No music found!");
            a.setContentText("No music found at path: " + f.getAbsolutePath());
            a.setHeaderText("Error");
            a.show();
        }
        else
        {
            if(player == null)
                player = new Player();
            else
                player.pause();
            player.start(musicFinder.nextElement());
            player.play();
            btPlay.setGraphic(new ImageView(getClass().getResource("/pause.png").toExternalForm()));

            setMetadata();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    progressBarSong.setProgress(player.getElapsedTime().toMillis() / player.getDuration().toMillis());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            progressTimeString.setText(convertIntSecToTimeSrting((int)player.getElapsedTime().toSeconds())
                                    + " / " + convertIntSecToTimeSrting((int)player.getDuration().toSeconds()));
                        }
                    });

                    if(player.getElapsedTime().toMillis() / player.getDuration().toMillis() >= 1)
                    {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                btNextHandler(null);
                            }
                        });
                    }
                }
            }, new Date(), 250);
        }
        System.out.println(musicFinder.getFileList().size() + " file(s) found!");
    }

    public void initialize(URL location, ResourceBundle resources) {
        musicFinder = new MusicFinder();
        player = null;
        musicCoverShowing = false;

        btPlay.setGraphic(new ImageView(getClass().getResource("/play.png").toExternalForm()));
        btNext.setGraphic(new ImageView(getClass().getResource("/next.png").toExternalForm()));
        btPrev.setGraphic(new ImageView(getClass().getResource("/previous.png").toExternalForm()));
        btDirectory.setGraphic(new ImageView((getClass().getResource("/file-directory.png").toExternalForm())));
        imageView.setImage(new Image(getClass().getResource("/album.png").toExternalForm()));

        timer = new Timer(true);
        progressTimeString.setDisable(true);
        progressTimeString.setOpacity(1);
    }

    public void startResizableProperty()
    {
        stage.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(musicCoverShowing)
                    imageView.setFitWidth(stage.getWidth()/2);
                else
                    imageView.setFitWidth((stage.getWidth() + 600)/2);
            }
        });
    }


    private void setMetadata() {
        if (player.getSongInfo().getAlbum() == null)
            labelAlbum.setText("<Empty>");
        else
            labelAlbum.setText(player.getSongInfo().getAlbum());

        if (player.getSongInfo().getArtist() == null)
            labelArtist.setText("<Empty>");
        else
            labelArtist.setText(player.getSongInfo().getArtist());

        if (player.getSongInfo().getTitle() == null)
            labelTitle.setText("<Empty>");
        else
            labelTitle.setText(player.getSongInfo().getTitle());

        if (player.getSongInfo().getYear() == null)
            labelYear.setText("<Empty>");
        else
            labelYear.setText(player.getSongInfo().getYear());

        if (player.getSongInfo().getImage() == null) {
            imageView.setImage(new Image(getClass().getResource("/album.png").toExternalForm()));
            imageView.setFitWidth((stage.getWidth()+600)/2);
            musicCoverShowing = false;
        } else {
            imageView.setImage(player.getSongInfo().getImage());
            imageView.setFitWidth(stage.getWidth()/2);
            musicCoverShowing = true;
        }
    }

    public void setStage(Stage s)
    {
        this.stage = s;
        imageView.setFitWidth((stage.getWidth() + 600)/2);
    }

    private String convertIntSecToTimeSrting(int sec)
    {
        String newString = "";
        int temp = 0;

        if(sec >= 60)//Minutes
        {
            if(sec >= 60*60)//Hours
            {
                temp = sec /(60*60);
                newString = Integer.toString(temp) + "h";
                sec -= temp*60*60;
            }
            temp = sec/60;
            newString += " " + Integer.toString(temp) + "min";
            sec -= temp*60;
        }
        newString += " " + Integer.toString(sec) + "sec";

        return newString;
    }

    public void close()
    {
        if(timer != null)
            timer.cancel();
        if(resizeTimer != null)
            resizeTimer.cancel();
    }
}
