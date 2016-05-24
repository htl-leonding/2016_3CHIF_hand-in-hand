package controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import model.ImageGallery;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class FxmlController implements Initializable {

    @FXML
    private Button btRightSwitch;

    @FXML
    private Button btLeftSwitch;

    @FXML
    private ImageView ivMainView;

    @FXML
    private BorderPane bp;

    private ImageGallery img = model.ImageGallery.getInstance();
    private int actPos = 0;


    /**
     * Switch to the next picture (right)
     * @param event
     */
    @FXML
    void RightSwitch(ActionEvent event) {
        if (actPos+1 >= img.getFilesList().size())
            actPos = 0;
        else
            actPos++;
        SetImage(actPos);
    }

    /**
     * Switch to the next picture (left)
     * @param event
     */
    @FXML
    void LeftSwitch(ActionEvent event) {
        if (actPos - 1 < 0)
            actPos = img.getFilesList().size()-1;
        else
            actPos--;
        SetImage(actPos);
    }

    public void initialize(URL location, ResourceBundle resources) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        bp.setPrefWidth(width-150);
        bp.setPrefHeight(height-150);
        ivMainView.setFitHeight(height - 165);
        ivMainView.setFitWidth(width - 165);


        img.searchPicturesInDirectory();
        try {
            if (img.getFilesList().size() > 0)
                ivMainView.setImage(new Image(new FileInputStream(img.getFilesList().get(actPos)), width, height, true, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }

    private void ShowButtons() {
        btLeftSwitch.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                                new java.util.Timer().schedule(
                                        new java.util.TimerTask() {
                                            @Override
                                            public void run() {
                                                btLeftSwitch.setOpacity(0.0);
                                                btRightSwitch.setOpacity(0.0);
                                            }
                                        },
                                        5000
                                );
                    }
                });

        ShowButtons();
        new Thread() {}.start();
        btLeftSwitch.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                btLeftSwitch.setOpacity(100.0);
                                btRightSwitch.setOpacity(100.0);
                            }
                        });
                    }
                });
    }

    /**
     * Set the Image which has to be showed in the MainView
     * @param act
     */
    void SetImage(int act) {
        try {
            ivMainView.setImage(new Image(new FileInputStream(img.getFilesList().get(act)), 1024, 0, true, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void CursorOnButtons(ActionEvent event) {

        System.out.println("Drag detected");

        actPos++;
    }
}
