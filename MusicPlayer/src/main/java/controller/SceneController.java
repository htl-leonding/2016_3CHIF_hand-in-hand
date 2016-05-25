package controller;

/**
 * Created by Stefan Smiljkovic on 24.05.2016.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.MusicFinder;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable{

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
    private Label labelYear;

    @FXML
    private Label labelTitle;

    @FXML
    private Label labelArtist;

    @FXML
    private Label labelAlbum;


    private Stage stage;
    private boolean isStopped = true;
    private MusicFinder musicFinder;


    @FXML
    void progressClickHandler(MouseEvent event) {

    }

    @FXML
    void btPreviousHandler(ActionEvent event) {

    }

    @FXML
    void btPlayHandler(ActionEvent event) {

    }

    @FXML
    void btNextHandler(ActionEvent event) {

    }

    @FXML
    void btOpenDirectoryHandler(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        File f = chooser.showDialog(stage);
        if(f == null)
            return;

        musicFinder.setDirectoryName(f.getAbsolutePath());
        if(musicFinder.searchForMusic())
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("No music found!");
            a.setContentText("No music found at path: " + f.getAbsolutePath());
            a.setHeaderText("Error");
        }
        System.out.println(musicFinder.getFileList().size() + " Datein gefunden!");
    }

    public void initialize(URL location, ResourceBundle resources) {
        musicFinder = new MusicFinder();

        btPlay.setGraphic(new ImageView(getClass().getResource("/play.png").toExternalForm()));
        btNext.setGraphic(new ImageView(getClass().getResource("/next.png").toExternalForm()));
        btPrev.setGraphic(new ImageView(getClass().getResource("/previous.png").toExternalForm()));
        btDirectory.setGraphic(new ImageView((getClass().getResource("/file-directory.png").toExternalForm())));
        imageView.setImage(new Image(getClass().getResource("/album.png").toExternalForm()));
        imageView.setFitWidth(500);
    }

    public void setStage(Stage s)
    {
        this.stage = s;
    }
}
