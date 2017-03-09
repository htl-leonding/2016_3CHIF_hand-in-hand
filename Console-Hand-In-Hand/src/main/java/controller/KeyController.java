package controller;

import hand_in_hand.HandInHand;
import model.Key;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Andrej on 23.06.2016.
 */
public class KeyController implements Observer {

    //The Robot is going to send the keystroker

    private Robot robot = null;
    private Timer timer = null;
    private final int SPACETIME_MS = 10000 ;

    public KeyController() {
        HandInHand.getModel().addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        String input = HandInHand.getModel().getInputString();
        String[] split = input.split(":");
        try {
            if (robot == null)
                robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            return;
        }
        if (input.equals("key0:p")) {
            if (timer == null) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        robot.keyPress(KeyEvent.VK_SPACE);
                        robot.keyRelease(KeyEvent.VK_SPACE);
                    }
                }, 0, SPACETIME_MS);
            } else {
                timer.cancel();
                timer = null;
            }
            return;
        }
        //Looking if key is in List
        for (int i = 0; i < HandInHand.getKeyList().size(); ++i) {
            if (HandInHand.getKeyList().get(i).getInputString().equals(split[0])) {
                if (split[1].equals("p") && !HandInHand.getKeyList().get(i).isPressed()) {
                    robot.keyPress(HandInHand.getKeyList().get(i).getOutputKey());
                    robot.keyRelease(HandInHand.getKeyList().get(i).getOutputKey());
                    HandInHand.getKeyList().get(i).setPressed(true);
                } else {
                    HandInHand.getKeyList().get(i).setPressed(false);
                }
            }
        }
    }
}