package hand_in_hand;

import controller.KeyController;
import model.Key;
import model.PortListener;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

public class HandInHand {

    //Creating an new instance of the portlistener
    private static PortListener model;
    private static List<Key> keyList = new LinkedList<Key>();

    public static void main(String[] args) throws IOException {

        model = new PortListener();
        KeyController k = new KeyController();

        //keyList.add(new Key(KeyEvent.VK_SPACE, "key0:p"));
        keyList.add(new Key(KeyEvent.VK_LEFT,"key1:p"));
        keyList.add(new Key(KeyEvent.VK_RIGHT, "key2:p"));
        keyList.add(new Key(KeyEvent.VK_UP, "key3:p"));
        keyList.add(new Key(KeyEvent.VK_DOWN, "key4:p"));

        //If there is no connection...
        if (getModel().getSerialPort() == null)
            System.out.print("Not Connected");
        else if (getModel().getSerialPort() != null)
            System.out.print("Connected");



        System.in.read();
    }

    /**
     * @return the model
     */
    public static PortListener getModel() {
        return model;
    }

    /**
     *
     * @return the keyList
     */
    public static List<Key> getKeyList() {
        return keyList;
    }
}



