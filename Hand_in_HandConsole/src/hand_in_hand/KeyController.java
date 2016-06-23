package hand_in_hand;

import model.Key;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Andrej on 23.06.2016.
 */
public class KeyController implements Observer{
    Robot robot;

    public KeyController() {
        Hand_in_Hand.getModel().addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.print("TEST");
        String input = Hand_in_Hand.getModel().getInputString();
        String[] split = input.split(":");
        int toAssignKey;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        boolean isInList = false;

        //Looking if key is in List
        for (int i = 0; i < Hand_in_Hand.getKeyList().size(); ++i) {

            //If inputvalue maches with key
            if (Hand_in_Hand.getKeyList().get(i).getInputString().equals(split[0])) {

                isInList = true;
                if (split[1].equals("p")) {
                    robot.keyPress(Hand_in_Hand.getKeyList().get(i).getOutputKey());//"PRESS" button
                }
                else {
                    robot.keyRelease(Hand_in_Hand.getKeyList().get(i).getOutputKey());
                }
            }
        }

        //keyList.add(new Key(toAssignKey, input, ""));//Adding new key to List
        toAssignKey = 0;

    }

}
