package at.htl.conrollers;

import at.htl.RaspberryPiMenue;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Stefan Smiljkovic on 14.06.2017.
 */
public class KeyPressController
{
    public boolean isRunInputHandler() {
        return runInputHandler;
    }

    public boolean runInputHandler;
    private final GpioController gpio = GpioFactory.getInstance();
    public static final PinState defaultPinPressedState = PinState.HIGH;
    public static final PinState defaultPinReleasedState = PinState.LOW;
    private KeyboardInputListener inputListener = null;


    public void setInputHandler(KeyboardInputListener listener){
        inputListener = listener;
    }

    public void setRunInputHandler(boolean var) {
        this.runInputHandler = var;
    }

    public KeyPressController(boolean handleInputs){
        System.out.println("Started!");
        runInputHandler = handleInputs;

        GpioPinDigitalInput[] pins = {
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_17, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_27, PinPullResistance.PULL_DOWN),
        };
        gpio.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent pinEvent) {
                System.out.println(pinEvent.getPin().getName() + ": " + pinEvent.getState());
                if(inputListener != null){
                    inputListener.keyPressed(pinEvent);
                }
                if(runInputHandler){
                    handleAction(pinEvent);
                }
            }
        }, pins);
        System.out.println("Listener added!");
    }

    private void handleAction(GpioPinDigitalStateChangeEvent pinEvent){
        try {
            Robot virtualKeyboard = new Robot();
            System.out.printf("PINEVENT: " + pinEvent.getPin() + " STATE: " + pinEvent.getState() + "RASPI: " + RaspiPin.GPIO_07);
            if(pinEvent.getPin().getName() == RaspiPin.GPIO_02.getName() && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_LEFT);
            } else if(pinEvent.getPin().getName() == RaspiPin.GPIO_03.getName() && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_RIGHT);
            } else if(pinEvent.getPin().getName() == RaspiPin.GPIO_04.getName() && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_UP);
            } else if(pinEvent.getPin().getName() == RaspiPin.GPIO_17.getName() && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_DOWN);
            } else if(pinEvent.getPin().getName() == RaspiPin.GPIO_27.getName() && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_SPACE);
            }

            if(pinEvent.getPin().getName() == RaspiPin.GPIO_02.getName() && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_LEFT);
            } else if(pinEvent.getPin().getName() == RaspiPin.GPIO_03.getName() && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_RIGHT);
            } else if(pinEvent.getPin().getName() == RaspiPin.GPIO_04.getName() && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_UP);
            } else if(pinEvent.getPin().getName() == RaspiPin.GPIO_17.getName() && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_DOWN);
            } else if(pinEvent.getPin().getName() == RaspiPin.GPIO_27.getName() && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_SPACE);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public interface KeyboardInputListener {
        void keyPressed(GpioPinDigitalStateChangeEvent pinEvent);
    }
}
