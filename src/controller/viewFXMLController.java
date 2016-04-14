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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import model.Key;
import model.PortListener;

/**
 *
 * @author Andrej Sakal, Gabriel Ionescu
 */
public class viewFXMLController implements Initializable, Observer 
{
    
    @FXML
    private Label label;
    @FXML
    private Button spaceButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;    
    @FXML
    private Hyperlink helpHyper;
    @FXML
    private BorderPane bordPane;
    @FXML
    private ProgressIndicator progressbar;
    
    //Eine Istanz des ProtListeners wird erstellt
    private PortListener model;
    
    //Eine Liste der Keys
    static List<Key> keyList;
    public boolean ret;
    public boolean waitingToAssign;
    String input;
    public int intRet;
    String toAssign;
    Robot robot;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        model = new PortListener();
        keyList = new LinkedList<>();
        toAssign = "";
        
        try 
        {
            robot = new Robot();
        } catch (AWTException ex) 
        {
            Logger.getLogger(viewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addObserver(this);
        this.progressbar.setVisible(false);
        
        //If there is no connection...
        if (model.getSerialPort() == null) 
        {
            printToLabel("The Device is not connected. "
                    + "Please check the connection!");
        }
        else if (model.getSerialPort() != null){
            printToLabel("The Device is successfuly connected!");
        }
    }    

    @Override
    public void update(Observable o, Object o1) 
    {
        input = model.getInputString();
        if(waitingToAssign == true)
            toAssign = input;
        else
            pressKey(input);
        
        input = "";
   }

    /**
     * Falls einer der 3-Knöpfe gedrückt wird dann wird überprüft ob überhaupt
     * eine Verbindung zum Microcontroller besteht falls ja wird auf ein
     * Signal  vom MC gewartet.
     * @param event 
     */
    @FXML
    private void handleLeftButton(ActionEvent event)
    {
        if(checkIfKeyEventAssigned(KeyEvent.VK_LEFT) == true)
        {
            printToLabel(leftButton.getText() + " is already assigned");
        }
        else
        {
            waitingToAssign = true;
            if (model.getSerialPort() == null)
            {
                printToLabel("Please check the connection!\nYou can't assign a "
                        + "button!");
            }   
            else if (model.getSerialPort() != null) 
            {
                assignKey(KeyEvent.VK_LEFT);
                
            }
        }
    }
    
    @FXML
    private void handleSpaceButton(ActionEvent event)
    {
        if(checkIfKeyEventAssigned(KeyEvent.VK_SPACE) == true)
        {
            printToLabel(spaceButton.getText() + " is already assigned");
        }
        else
        {
            waitingToAssign = true;
            if (model.getSerialPort() == null)
            {
                printToLabel("Please check the connection!\nYou can't assign a "
                        + "button!");
            }   
            else if (model.getSerialPort() != null) 
            {
                assignKey(KeyEvent.VK_SPACE);
            }
        }
    }
    
    @FXML
    private void handleRightButton(ActionEvent event)
    {
        if(checkIfKeyEventAssigned(KeyEvent.VK_RIGHT) == true)
        {
            printToLabel(rightButton.getText() + " is already assigned");
        }
        else
        {
            waitingToAssign = true;
            if (model.getSerialPort() == null)
            {
                printToLabel("Please check the connection!\nYou can't assign a "
                        + "button!");
            }   
            else if (model.getSerialPort() != null) 
            {
                assignKey(KeyEvent.VK_RIGHT);
            }
        }
    }
    
    /**
     * Falls der Help-Hyperlink aufgerufen wird, dann öffnet sich ein 
     * Textdokument mit einer Hilfe.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleHyperLink(ActionEvent event) throws IOException 
    {
        //Open Help-File with the use of System.getPropertys 
        String filename = System.getProperty("user.dir") + 
        System.getProperty("file.separator") + "Ressources" + 
        System.getProperty("file.separator") + "Help_de_De.pdf";
        
        Desktop.getDesktop().open(new File(filename));
    }
    
    private boolean checkIfKeyEventAssigned(int key)
    {
        ret = false;
        keyList.forEach(p -> 
        {
           if(p.getOutputKey() == key) 
               ret = true;
        });
        return ret;
    }
    
    private void printToLabel(String text)
    {
        Platform.runLater(() -> label.setText(text));
    }
    
    private int getKeyEvent(String key)
    {
        intRet = -1;
        keyList.forEach(p -> 
        {
            if(p.getKeyString().matches(key) == true)
                intRet = p.getOutputKey();
        });
        return intRet;
    }
    /**
     * 
     * @param key The input key from the aruduino
     */
    public void pressKey(String key)
    {
        int toPress = getKeyEvent(key);
        if(toPress == -1)
            printToLabel(key + " is not assigned");
        else
        {
            if(toPress == KeyEvent.VK_SPACE)
            {
                robot.keyPress(toPress);
                printToLabel("SPACE pressed");
            }
            else if(toPress == KeyEvent.VK_RIGHT)
            {
                robot.keyPress(toPress);
                printToLabel("RIGHT pressed");
            }
            else if(toPress == KeyEvent.VK_LEFT)
            {
                robot.keyPress(toPress);
                printToLabel("LEFT pressed");
            }
        }
    }
    
    public synchronized void assignKey(int keyToAssign)
    {
        Platform.runLater(()-> {
            progressbar.setVisible(true);
        });
        printToLabel("Waiting for input from the device!");
        while("".equals(toAssign)) { System.out.println("waiting"); }
        
        if (getKeyEvent(toAssign) == -1)
        {
            keyList.add(new Key(toAssign, keyToAssign));
            printToLabel("Key successfuly assigned");
            
        }
        else
        {
            printToLabel(toAssign + " is already assigned \ntry again");
        }
        progressbar.setVisible(false);
        waitingToAssign = false;
        toAssign = "";
    }
}