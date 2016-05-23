package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ImageGallery;

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

    private ImageGallery img = model.ImageGallery.getInstance();
    private int actPos = 0;


    @FXML
    void RightSwitch(ActionEvent event) {
        if (actPos+1 >= img.getFilesList().size())
            actPos = 0;
        else
            actPos++;
        SetImage(actPos);
    }

    @FXML
    void LeftSwitch(ActionEvent event) {
        if (actPos - 1 < 0)
            actPos = img.getFilesList().size()-1;
        else
            actPos--;
        SetImage(actPos);
    }



    public void initialize(URL location, ResourceBundle resources) {

        img.searchPicturesInDirectory();
        try {
            if (img.getFilesList().size() > 0)
                ivMainView.setImage(new Image(new FileInputStream(img.getFilesList().get(actPos)), 1024, 0, true, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //ivLeftButton.setImage(new Image(getClass().getResource("/LeftButton.png").toExternalForm()));
        //ivRightButton.setImage(new Image(getClass().getResource("/RightButton.png").toExternalForm()));


        /* Testing for left or rigth button
        Frame f = new Frame("Example");

        Component iv = new TextArea();
        iv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                TextArea source = (TextArea) evt.getSource();
                if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
                    source.setText("funt");
                }
            }
        });

        f.add(iv, BorderLayout.NORTH);
        int width = 500;
        int height = 500;
        f.setSize(width, height);
        f.setVisible(true);
        */
    }
    void SetImage(int act) {
        try {
            ivMainView.setImage(new Image(new FileInputStream(img.getFilesList().get(act)), 1024, 0, true, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}