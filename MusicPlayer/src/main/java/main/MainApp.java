package main; /**
 * Created by Stefan Smiljkovic on 20.05.2016.
 */

import controller.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader FXLoader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));

        Parent root = FXLoader.load();
        Scene scene = new Scene(root);

        //scene.getStylesheets().add("/styles/Styles.css");
        stage.getIcons().add(new Image(getClass().getResource("/music-icon.png").toExternalForm()));
        stage.setTitle("MusicPlayer");
        stage.setScene(scene);
        stage.show();

        SceneController s = FXLoader.getController();

        if(scene != null)
            s.setStage(stage);
    }
}
