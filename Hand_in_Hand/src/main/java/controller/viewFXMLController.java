/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import model.Key;
import model.PortListener;
import javafx.scene.image.ImageView;

/**
 *
 * @author Andrej Sakal, Stefan Smiljkovic, Gabriel Ionescu
 */
public class viewFXMLController implements Initializable, Observer {

    @FXML
    private Label lbInfoStefan;
    @FXML
    private ImageView ivInfoPic;
    @FXML
    private BorderPane bpTwo;
    @FXML
    private Tab tInfo;
    @FXML
    private TabPane tbPane;
    @FXML
    private ImageView ivHomePic;
    @FXML
    private ListView<Key> lvButtonsFunctionOverview;
    @FXML
    private Label lbInfoAndrej;
    @FXML
    private Button leftButton;
    @FXML
    private Button btReset;
    @FXML
    private Label lbSupervised;
    @FXML
    private ImageView ivAndrejPic;
    @FXML
    private BorderPane bpOne;
    @FXML
    private ImageView ivStefanPic;
    @FXML
    private Button spaceButton;
    @FXML
    private Label lbPowered;
    @FXML
    private Tab tSettings;
    @FXML
    private ImageView ivGabrielPic;
    @FXML
    private Label label;
    @FXML
    private Label lbInfoGabriel;
    @FXML
    private BorderPane bordPane;
    @FXML
    private Tab tHome;
    @FXML
    private ProgressIndicator progressbar;
    @FXML
    private Hyperlink helpHyper;
    @FXML
    private Button rightButton;
    ObservableList<Key> keys;



    //Creating an new instance of the portlistener
    private static PortListener model;

    //A new List where the Assigned function for the buttons is managed
    private static List<Key> keyList;
    private int toAssignKey;
    Robot robot;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new PortListener();
        keyList = new LinkedList<>();

        //For testing
        for (int i = 0; i < 3; i++) {
            keyList.add(new Key(i, "", "not assigned"));
        }
        //Refresh the ListView
        RefreshListView();

        toAssignKey = 0;
        ivInfoPic.setImage(new Image(getClass().getResource("/htl_logo.png").toExternalForm()));
        ivHomePic.setImage(new Image(getClass().getResource("/HandinHand.PNG").toExternalForm()));

        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(viewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Add as Observer
        getModel().addObserver(this);
        //Set load animation to visible
        this.progressbar.setVisible(false);

        //If there is no connection...
        if (getModel().getSerialPort() == null) {
            printToLabel("The Device is not connected. "
                    + "Please check the connection!");
        } else if (getModel().getSerialPort() != null) {
            printToLabel("The Device is successfuly connected!");
        }

        /*
        lvButtonsFunctionOverview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Key>() {
            @Override
            public void changed(ObservableValue<? extends Key> observable, Key oldValue, Key newValue) {

            }
        });
        */
    }

    /**
     * Sets Label.Text to the given String
     * @param text 
     */
    private void printToLabel(final String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(text);
            }
        });
    }

    /**
     * HyperLink handler for Help
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleHyperLink(ActionEvent event) throws IOException {
        Desktop.getDesktop().open(new File(getClass().getResource("/Help_de_De.pdf").getFile()));//Open file
    }

    /**
     * Handler for the very left button
     * @param event 
     */
    @FXML
    private void handleLeftButton(ActionEvent event) {
        printToLabel("Waiting for input from the microcontroller...");

        System.out.println("Left button pressed!");
        toAssignKey = KeyEvent.VK_LEFT;//Save last pressed button

        //Check if device is connected
        if (getModel().getSerialPort() == null) {
            printToLabel("Please check the connection!\nYou can't assign a "
                    + "button!");
        } else {
            progressbar.setVisible(true);//Show loadingbar
        }
    }

    /**
     * Handler for middle button
     * @param event 
     */
    @FXML
    private void handleSpaceButton(ActionEvent event) {
        printToLabel("Waiting for input from the microcontroller...");

        System.out.println("Space button pressed!");
        toAssignKey = KeyEvent.VK_SPACE;//Save last pressed button

        //Check if device is connected
        if (getModel().getSerialPort() == null) {
            printToLabel("Please check the connection!\nYou can't assign a "
                    + "button!");
        } else {
            progressbar.setVisible(true);//Show loadingbar
        }
    }

    /**
     * Handler for the right button
     * @param event 
     */
    @FXML
    private void handleRightButton(ActionEvent event) {
        printToLabel("Waiting for input from the microcontroller...");

        System.out.println("Right button pressed!");
        toAssignKey = KeyEvent.VK_RIGHT;//Save last pressed button

        //Check if device is connected
        if (getModel().getSerialPort() == null) {
            printToLabel("Please check the connection!\nYou can't assign a "
                    + "button!");
        } else {
            progressbar.setVisible(true);//Show loadingbar
        }
    }

    /**
     * Handles incoming key-value
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update - method called!");
        String input = getModel().getInputString();

        boolean isInList = false;
        
                //Looking if key is in List
        for (int i = 0; i < keyList.size(); ++i) {
            //If inputvalue maches with key
            if (keyList.get(i).getInputString().equals(input)) {
                System.out.println("Key in list!");
                isInList = true;
                progressbar.setVisible(false);//Hide loadingbar
                robot.keyPress(keyList.get(i).getOutputKey());//"PRESS" button
                if(toAssignKey != 0)
                printToLabel("The button is already assigned!");
            }
        }

        //When key is not in list
        if (!isInList && toAssignKey != 0) {
            printToLabel("Key successfully asigned!");
            keyList.add(new Key(toAssignKey, input, ""));//Adding new key to List
            RefreshListView();
            toAssignKey = 0;
            progressbar.setVisible(false);//Hide loadingbar
            System.out.println("New key added! Size: " + keyList.size());
        }
    }

    /**
     * @return the model
     */
    public static PortListener getModel() {
        return model;
    }

    /**
     * Resets all assigned Keys
     * @param event
     */
    @FXML
    private void handleResetButton(ActionEvent event) {
        keyList.clear();
    }

    private void RefreshListView() {
        keys = FXCollections.observableList(keyList);
        lvButtonsFunctionOverview.setItems(keys);
        lvButtonsFunctionOverview.refresh();
    }
}
