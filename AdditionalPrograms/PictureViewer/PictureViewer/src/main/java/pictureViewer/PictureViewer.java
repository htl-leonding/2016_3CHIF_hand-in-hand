package pictureViewer;

import controller.FxmlController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static javafx.application.Application.launch;

/**
 * Created by Andrej on 20.05.2016.
 */
public class PictureViewer extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader FXLoader = new FXMLLoader(getClass().getResource("/FxmlView.fxml"));
        Parent root = FXLoader.load();

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);

        FxmlController f = FXLoader.getController();

        if(scene != null) {
            f.setStage(stage);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
