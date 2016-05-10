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
import java.util.*;
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

    private String languagePropertyFile = "languageProperty";
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
    @FXML
    private Button btDeutsch;
    ObservableList<Key> keys;
    private boolean isGerman = false;
    ResourceBundle rs = null;



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
        changeLang("de","DE");

        //For testing of the listView
        for (int i = 0; i < 3; i++) {
            keyList.add(new Key(i, "", "-"));
        }

        //Refresh the ListView
        RefreshListView();

        toAssignKey = 0;
        ivInfoPic.setImage(new Image(getClass().getResource("/htl_logo.png").toExternalForm()));
        ivHomePic.setImage(new Image(getClass().getResource("/HandinHand.PNG").toExternalForm()));
        ivAndrejPic.setImage(new Image(getClass().getResource("/Sakal.jpg").toExternalForm()));

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
            label.setText(rs.getString("NotConnected"));
        } else if (getModel().getSerialPort() != null) {
            label.setText(rs.getString("ConnectedSuc"));
        }
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
        label.setText(rs.getString("WaitingForInput"));

        //ForTesting
        //System.out.println("Left button pressed!");

        toAssignKey = KeyEvent.VK_LEFT;//Save last pressed button

        //Check if device is connected
        if (getModel().getSerialPort() == null) {
            label.setText(rs.getString("NotConnectedError"));
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
        label.setText(rs.getString("WaitingForInput"));

        //For Testing
        //System.out.println("Space button pressed!");
        toAssignKey = KeyEvent.VK_SPACE;//Save last pressed button

        //Check if device is connected
        if (getModel().getSerialPort() == null) {
            label.setText(rs.getString("NotConnectedError"));
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
        label.setText(rs.getString("WaitingForInput"));

        //For Testing
        //System.out.println("Right button pressed!");
        toAssignKey = KeyEvent.VK_RIGHT;//Save last pressed button

        //Check if device is connected
        if (getModel().getSerialPort() == null) {
            label.setText(rs.getString("NotConnectedError"));
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

        //For Testing
        //System.out.println("Update - method called!");
        String input = getModel().getInputString();

        boolean isInList = false;
        
                //Looking if key is in List
        for (int i = 0; i < keyList.size(); ++i) {

            //If inputvalue maches with key
            if (keyList.get(i).getInputString().equals(input)) {

                //For Testing
                //System.out.println("Key in list!");

                isInList = true;
                progressbar.setVisible(false);//Hide loadingbar
                robot.keyPress(keyList.get(i).getOutputKey());//"PRESS" button
                if(toAssignKey != 0)
                label.setText(rs.getString("AlreadyAssinged"));
            }
        }

        //When key is not in list
        if (!isInList && toAssignKey != 0) {
            label.setText(rs.getString("KeyAssignedSuc"));
            keyList.add(new Key(toAssignKey, input, ""));//Adding new key to List
            RefreshListView();
            toAssignKey = 0;
            progressbar.setVisible(false);//Hide loadingbar

            //For Testing
            //System.out.println("New key added! Size: " + keyList.size());
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
        //keyList.clear();
        for (int i = 0; i < keyList.size(); i++){
            keyList.get(i).setActionOfButton("not assigned");
            keyList.get(i).setInputKey("");
            keyList.get(i).setOutputKey(0);
        }
        lvButtonsFunctionOverview.refresh();
    }

    private void RefreshListView() {
        keys = FXCollections.observableList(keyList);
        lvButtonsFunctionOverview.setItems(keys);
        lvButtonsFunctionOverview.refresh();
    }
    public void changeLang(String language, String country) {
        Locale l = new Locale(language, country);
        rs = ResourceBundle.getBundle(languagePropertyFile, l);

        lbSupervised.setText(rs.getString("SuperBy"));
        leftButton.setText(rs.getString("Left"));
        rightButton.setText(rs.getString("Right"));
        spaceButton.setText(rs.getString("Space"));
        btReset.setText(rs.getString("Reset"));
        tHome.setText(rs.getString("Home"));
        tSettings.setText(rs.getString("Settings"));
        helpHyper.setText(rs.getString("Help"));
    }
    @FXML
    public void handleGermanButton() {
        if (isGerman)
        {
            changeLang("en","US");
        }
        else {
            changeLang("de", "DE");
        }
    }
}
