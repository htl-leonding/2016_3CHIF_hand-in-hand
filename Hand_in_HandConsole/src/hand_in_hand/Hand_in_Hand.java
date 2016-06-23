package hand_in_hand;

import model.Key;
import model.PortListener;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Hand_in_Hand {

    //For the Language if the act Lang is german
    private boolean isGerman = true;
    //For I18n
    ResourceBundle rs = null;
    //LanguagePropertyFile
    private String languagePropertyFile = "languageProperty";

    //Creating an new instance of the portlistener
    private static PortListener model;

    //A new List where the Assigned function for the buttons is managed
    private static List<Key> keyList;


    public static void main(String[] args) throws IOException {

        model = new PortListener();
        setKeyList(new LinkedList<>());
        KeyController k = new KeyController();

        //Add as Observer
        //getModel().addObserver(this);
        //Set load animation to visible
        //this.progressbar.setVisible(false);
        getKeyList().add(new Key(KeyEvent.VK_SPACE, "key3:p", "Left"));
        getKeyList().add(new Key(KeyEvent.VK_LEFT, "key0:p", "noth"));
        getKeyList().add(new Key(KeyEvent.VK_RIGHT, "key4:p", "noth"));
        //k.update(null, null);

        //If there is no connection...
        if (getModel().getSerialPort() == null) {
            System.out.print("Not Connected");
        } else if (getModel().getSerialPort() != null) {
            System.out.print("Connected");

        }
        System.in.read();
    }

    /**
     * @return the model
     */
    public static PortListener getModel() {
        return model;
    }


    public static List<Key> getKeyList() {
        return keyList;
    }

    public static void setKeyList(List<Key> keyList) {
        Hand_in_Hand.keyList = keyList;
    }
}



