package com.htlleonding.hand_in_hand;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainApp extends Application {    

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        //stage.getIcons().add(new Image("C:\\Users\\Andrej\\Documents\\GitHub\\2016_3CHIF_hand-in-hand\\Hand_in_Hand\\ressources\\HighlLightIndustries_Logo.png"));
        stage.setTitle("Hand in Hand");
        stage.setScene(scene);
        stage.show();
        
           
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                controller.viewFXMLController.getModel().close();
            }
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
