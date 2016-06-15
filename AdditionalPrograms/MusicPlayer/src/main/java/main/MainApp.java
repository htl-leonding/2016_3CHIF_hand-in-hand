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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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

        stage.getIcons().add(new Image(getClass().getResource("/music-icon.png").toExternalForm()));//Setting icon
        stage.setTitle("MusicPlayer");
        stage.setScene(scene);
        stage.show();

        /*Handler for pressed keys - mainly for spacebar*/
        scene.setOnKeyTyped((event) -> {
            {
                if(event.getCharacter().equals(" "))
                    ((SceneController)FXLoader.getController()).handleKeyEvent(event);//Inform controller
            }
        });

        /*Setting minimum sizes*/
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());

        s = FXLoader.getController();

        if(scene != null) {
            s.setStage(stage);

            /*Handler for closing window*/
            stage.setOnCloseRequest((event) ->
                 {
                    s.close();
                }
            );
        }
    }
}
