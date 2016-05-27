package controller;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.ImageGallery;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FxmlController implements Initializable {

    @FXML
    private Button btRightSwitch;

    @FXML
    private Button btLeftSwitch;

    @FXML
    private BorderPane bp;

    @FXML
    private ImageView ivMainView;


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
        ShowButtons();

        bp.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    LeftSwitch(null);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    RightSwitch(null);
                }
            }
        });

        //Setting the BP & ImageView
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        bp.setPrefWidth(width-50);
        bp.setPrefHeight(height-150);
        ivMainView.setFitHeight(height - 165);
        ivMainView.setFitWidth(width - 50);

        img.searchPicturesInDirectory();

        try {
            if (img.getFilesList().size() > 0)
                ivMainView.setImage(new Image(new FileInputStream(img.getFilesList().get(actPos)), 1920, 1080, true, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void ShowButtons() {

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        btLeftSwitch.setOpacity(0.0);
                        btRightSwitch.setOpacity(0.0);
                    }
                },
                2500
        );

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
        btRightSwitch.addEventHandler(MouseEvent.MOUSE_EXITED,
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

        btRightSwitch.addEventHandler(MouseEvent.MOUSE_ENTERED,
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
            SetImageMetadata();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SetImageMetadata() {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(img.getFilesList().get(actPos));

            print(metadata);
        } catch (ImageProcessingException e) {
            // handle exception
        } catch (IOException e) {
            // handle exception
        }
    }

    private void print(Metadata metadata) {
        System.out.println("-------------------------------------");
        // Iterate over the data and print to System.out

        //
        // A Metadata object contains multiple Directory objects
        //
        for (Directory directory : metadata.getDirectories()) {

            //
            // Each Directory stores values in Tag objects
            //
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);

            }

            //
            // Each Directory may also contain error messages
            //
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.println("ERROR: " + error);
                }
            }
        }
    }
}
