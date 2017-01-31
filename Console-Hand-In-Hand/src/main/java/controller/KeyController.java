package controller;

import hand_in_hand.HandInHand;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Andrej on 23.06.2016.
 */
public class KeyController implements Observer {

    //The Robot is going to send the keystroker

    Robot robot = null;

    public KeyController() {
        HandInHand.getModel().addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        String input = HandInHand.getModel().getInputString();
        String[] split = input.split(":");
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        //Looking if key is in List
        for (int i = 0; i < HandInHand.getKeyList().size(); ++i) {
            if (HandInHand.getKeyList().get(i).getInputString().equals(split[0]))
                if (split[1].equals("p"))
                    robot.keyPress(HandInHand.getKeyList().get(i).getOutputKey());
                else
                    robot.keyRelease(HandInHand.getKeyList().get(i).getOutputKey());
        }
    }
}
