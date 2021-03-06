package controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.ImageGallery;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class FxmlController implements Initializable {

    @FXML
    private Label lbSize;

    @FXML
    private Button btRightSwitch;

    @FXML
    private Label lbName;

    @FXML
    private Button btLeftSwitch;

    @FXML
    private Label lbChanged;

    @FXML
    private BorderPane bp;

    @FXML
    private ImageView ivMainView;

    @FXML
    private Button btDirectory;


    private ImageGallery img = model.ImageGallery.getInstance();
    private int actPos = 0;
    private Stage stage;


    @FXML
    void DirectoryChooser(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();

        File folder = chooser.showDialog(getStage());
        if(folder == null)
            return;
        img.setFolder(folder);
        if(img.searchPicturesInDirectory() == false)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("No pictures found!");
            a.setContentText("No pictures found at path: " + folder.getAbsolutePath());
            a.setHeaderText("Error");
            a.show();
        }
        else {

            try {
                if (img.getFilesList().size() > 0)
                    ivMainView.setImage(new Image(new FileInputStream(img.getFilesList().get(actPos)), 1920, 1080, true, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

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
        btDirectory.setGraphic(new ImageView((getClass().getResource("/file-directory.png").toExternalForm())));
        bp.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    LeftSwitch(null);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    RightSwitch(null);
                }
                else if (event.getCode() == KeyCode.SPACE) {

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage s) {
        this.stage = s;
    }
}
