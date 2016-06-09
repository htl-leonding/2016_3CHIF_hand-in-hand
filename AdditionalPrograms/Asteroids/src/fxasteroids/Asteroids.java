package fxasteroids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Asteroids extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/GameView.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResource("/asteroids-icon.png").toExternalForm()));
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        primaryStage.setFullScreen(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
