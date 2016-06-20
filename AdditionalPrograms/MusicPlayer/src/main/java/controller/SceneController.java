package controller;

/**
 * Created by Stefan Smiljkovic on 24.05.2016.
 */
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.MusicFinder;
import model.Player;

import java.io.File;
import java.net.URL;
import java.util.*;

public class SceneController implements Initializable{

    @FXML
    private Label labelTitleText;

    @FXML
    private Label labelYearText;

    @FXML
    private Label labelAlbumText;

    @FXML
    private Label labelArtistText;

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
    private boolean musicCoverShowing;

    public static int SEARCHLEVELS = 3;
    public static int FILENUMBER = 600;

    /**
     * Method used for external events
     * Controller reacts to given KeyEvent
     * @param event
     */
    public void handleKeyEvent(KeyEvent event)
    {
        mainKeyHandler(event);
    }

    /**
     * Main-Eventhandler for controller
     * @param event
     */
    @FXML
    void mainKeyHandler(KeyEvent event) {
        if(event.getCode().equals(KeyCode.LEFT))
            btPreviousHandler(null);
        else if(event.getCode().equals(KeyCode.RIGHT))
            btNextHandler(null);
        else if(event.getCharacter().equals(" "))
            btPlayHandler(null);

        event.consume();//Terminate event
    }

    /**
     * Handler for clicking on progrssbar
     * @param event
     */
    @FXML
    void progressClickHandler(MouseEvent event) {
        if(player != null) {
            player.jump(event.getX()/progressBarSong.getWidth());//Calculate percentage and jump to area in file
        }
    }

    /**
     * Handler for "previousButton"
     * @param event
     */
    @FXML
    void btPreviousHandler(MouseEvent event) {
        if(musicFinder.getPosition() > 1 && player != null)//Is not first song and player exists
        {
            player.pause();//Stop playing
            player.start(musicFinder.prevElement());//Load new song
            player.play();//Play new song
            btPlay.setGraphic(new ImageView(getClass().getResource("/pause.png").toExternalForm()));//Change buttongraphic

            setMetadata();//Set metadata
        }
    }

    /**
     * Handler for"Play-/Pause-button"
     * @param event
     */
    @FXML
    void btPlayHandler(MouseEvent event) {
        if(player != null && player.isPlaying())//Is it playing? and does player exists
        {
            player.pause();//Stop player
            btPlay.setGraphic(new ImageView(getClass().getResource("/play.png").toExternalForm()));//Change buttongraphic
        }
        else if(player != null)//Does player exists?
        {
            player.play();//Start player
            btPlay.setGraphic(new ImageView(getClass().getResource("/pause.png").toExternalForm()));//Change buttongraphic
        }
    }

    /**
     * Handler for "Nextbutton"
     * @param event
     */
    @FXML
    void btNextHandler(MouseEvent event) {
        if(player != null && musicFinder.hasMoreElements())//Does player exist? and is last musicfile
        {
            player.pause();//Stop player
            player.start(musicFinder.nextElement());//Load new file
            player.play();//Play music
            btPlay.setGraphic(new ImageView(getClass().getResource("/pause.png").toExternalForm()));//Change buttongraphic

            setMetadata();//Load metadata
        }
    }

    /**
     * Handler for "openDirectoryButton"
     * @param event
     */
    @FXML
    void btOpenDirectoryHandler(MouseEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();

        File f = chooser.showDialog(stage);//Choose directory
        if(f == null)//Nothing selected
            return;

        musicFinder.setDirectoryName(f.getAbsolutePath());//Set initial searchdirectory
        if(!musicFinder.searchForMusic(SEARCHLEVELS, FILENUMBER))//Any music found?
        {
            /*Error - no music found*/
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("No music found!");
            a.setContentText("No music found at path: " + f.getAbsolutePath());
            a.setHeaderText("Error");
            a.show();
        }
        else//Found some music
        {
            if(player == null)//Does Player already exists
                player = new Player();
            else
                player.pause();//Stop playing
            player.start(musicFinder.nextElement());//Load new file
            player.play();//Start playing
            btPlay.setGraphic(new ImageView(getClass().getResource("/pause.png").toExternalForm()));//Change buttongraphic

            setMetadata();//Setting metadata

            /*New Timertast for progressbar*/
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    progressBarSong.setProgress(player.getElapsedTime().toMillis() / player.getDuration().toMillis());//Set progresbarstatus

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            progressTimeString.setText(convertIntSecToTimeSrting((int)player.getElapsedTime().toSeconds())
                                    + " / " + convertIntSecToTimeSrting((int)player.getDuration().toSeconds()));//Write current time onto progressbar
                        }
                    });

                    if(player.getElapsedTime().toMillis() / player.getDuration().toMillis() >= 1)//Reaching end
                    {
                        Platform.runLater(() -> {
                            {
                                btPlay.setGraphic(new ImageView(getClass().getResource("/play.png").toExternalForm()));
                                btNextHandler(null);//Changing to next Song
                            }
                        });
                    }
                }
            }, new Date(), 250);
        }
        System.out.println(musicFinder.getFileList().size() + " file(s) found!");
    }

    /**
     * Init-method
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        musicFinder = new MusicFinder();
        player = null;
        musicCoverShowing = false;

        /*Initialize buttongraphics*/
        btPlay.setGraphic(new ImageView(getClass().getResource("/play.png").toExternalForm()));
        btNext.setGraphic(new ImageView(getClass().getResource("/next.png").toExternalForm()));
        btPrev.setGraphic(new ImageView(getClass().getResource("/previous.png").toExternalForm()));
        btDirectory.setGraphic(new ImageView((getClass().getResource("/file-directory.png").toExternalForm())));
        imageView.setImage(new Image(getClass().getResource("/album.png").toExternalForm()));

        timer = new Timer(true);
        progressTimeString.setDisable(true);//Disable label
        progressTimeString.setOpacity(1);//Setting label over progressbar to 100% visible

        borderPaneMain.setOnKeyPressed((EventHandler<javafx.event.Event>) (event) -> {
                mainKeyHandler((KeyEvent) event);//Handle KeyEvent
        });
    }

    /**
     * Setting Metadata
     */
    private void setMetadata() {
        if (player.getSongInfo().getAlbum() == null || player.getSongInfo().getAlbum() == "")
            labelAlbum.setText("<Empty>");
        else
            labelAlbum.setText(player.getSongInfo().getAlbum());

        if (player.getSongInfo().getArtist() ==  null || player.getSongInfo().getArtist() == "")
            labelArtist.setText("<Empty>");
        else
            labelArtist.setText(player.getSongInfo().getArtist());

        if (player.getSongInfo().getTitle() == null ||player.getSongInfo().getTitle() == "")
            labelTitle.setText("<Empty>");
        else
            labelTitle.setText(player.getSongInfo().getTitle());

        if (player.getSongInfo().getYear() == null || player.getSongInfo().getYear() == "")
            labelYear.setText("<Empty>");
        else
            labelYear.setText(player.getSongInfo().getYear());

        if (player.getSongInfo().getImage() == null) {//Is image available?
            imageView.setImage(new Image(getClass().getResource("/album.png").toExternalForm()));//Load standard cover
            imageView.setFitWidth((stage.getWidth()+600)/2);
            musicCoverShowing = false;
            calcLabelColorOpposite(imageView.getImage());

        } else {//Load cover
            imageView.setImage(player.getSongInfo().getImage());//Load cover out of file
            imageView.setFitWidth(stage.getWidth()/2);
            musicCoverShowing = true;
            calcLabelColorOpposite(imageView.getImage());
        }
    }

    /**
     * Calculates and sets opposite overall-color of cover
     * @param image
     */
    private synchronized void calcLabelColorOpposite(Image image)
    {
        double r = 0;
        double g = 0;
        double b = 0;

        int cnt = 0;

        PixelReader reader = image.getPixelReader();

        double width = image.getWidth();
        double heigth = image.getHeight();

        for (int x = 0; x < width; ++x)
        {
            for(int y = 0;  y < heigth; ++y)
            {
                ++cnt;

                r += reader.getColor(x, y).getRed();
                g += reader.getColor(x, y).getGreen();
                b += reader.getColor(x, y).getBlue();
            }
        }

        r /= (width*heigth);
        g /= (width*heigth);
        b /= (width*heigth);

        double temp = (r + g + b)/3;

        if(temp < 0.4) {
            r = Math.max(r, 0.4);
            g = Math.max(g, 0.4);
            b = Math.max(b, 0.4);
        }

        setLabelColor(new Color(1 - r, 1 - g, 1 - b, 1));//Setting labelcolor
    }

    /**
     * Chancheing color of all etadatalabels
     * @param color
     */
    private void setLabelColor(Color color)
    {
        labelArtist.setTextFill(color);
        labelArtistText.setTextFill(color);
        labelTitle.setTextFill(color);
        labelTitleText.setTextFill(color);
        labelAlbum.setTextFill(color);
        labelAlbumText.setTextFill(color);
        labelYear.setTextFill(color);
        labelYearText.setTextFill(color);
    }

    /**
     * Set stage
     * @param s
     */
    public void setStage(Stage s)
    {
        this.stage = s;
        imageView.setFitWidth((stage.getWidth() + 600)/2);
        calcLabelColorOpposite(imageView.getImage());

        /*Handels events for resizeing cover*/
        stage.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(musicCoverShowing)
                    imageView.setFitWidth(stage.getWidth()/2);//Resize cover
                else
                    imageView.setFitWidth((stage.getWidth() + 600)/2);//Resize cover
            }
        });
    }

    /**
     * Converts give seconds to Time-String
     * @param sec
     * @return
     */
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

    /**
     * Close and end all timers
     */
    public void close()
    {
        if(timer != null)
            timer.cancel();
    }
}
