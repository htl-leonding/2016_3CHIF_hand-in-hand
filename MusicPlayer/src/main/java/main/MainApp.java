package main; /**
 * Created by Stefan Smiljkovic on 20.05.2016.
 */

import controller.SceneController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class MainApp extends Application {

    SceneController s;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader FXLoader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));

        Parent root = FXLoader.load();
        Scene scene = new Scene(root);

        stage.getIcons().add(new Image(getClass().getResource("/music-icon.png").toExternalForm()));
        stage.setTitle("MusicPlayer");
        stage.setScene(scene);
        stage.show();

        s = FXLoader.getController();

        if(scene != null) {
            s.setStage(stage);
            s.startResizableProperty();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    s.close();
                }
            });
        }
    }
}
