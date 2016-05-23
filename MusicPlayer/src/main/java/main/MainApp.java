package main; /**
 * Created by Stefan Smiljkovic on 20.05.2016.
 */

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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
//        stage.getIcons().add(new Image(getClass().getResource("/ico.jpg").toExternalForm()));
        stage.setTitle("Hand in Hand");
        stage.setScene(scene);
        stage.show();
    }
}
