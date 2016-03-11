/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hand_in_hand;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andrej Sakal
 */
public class Hand_in_Hand extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
   Parent root = FXMLLoader.load(getClass().getResource("/view/viewFXML.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Hand in Hand");
        
        /*
        -------------------Icon einbinden-------------------------
        String filePath = System.getProperty("user.dir") + 
        System.getProperty("file.separator") + "Ressources" + 
        System.getProperty("file.separator") + "Help_de_De.pdf";
        Image ico = new Image(filePath);
        stage.getIcons().add(ico);
        */
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
