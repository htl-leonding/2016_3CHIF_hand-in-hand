package at.htl.conrollers;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Stefan Smiljkovic on 14.06.2017.
 */
public class KeyPressController implements GpioPinListenerDigital
{
    public boolean runInputHandler;
    private final GpioController gpio = GpioFactory.getInstance();
    private final PinState defaultPinPressedState = PinState.HIGH;
    private final PinState defaultPinReleasedState = PinState.LOW;
    private KeyboardInputListener inputListener = null;


    public void setInputHandler(KeyboardInputListener listener){
        inputListener = listener;
    }

    public KeyPressController(boolean handleInputs){
        runInputHandler = handleInputs;
        gpio.addListener(this);
    }

    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent pinEvent) {
        if(inputListener != null){
            inputListener.keyPressed(pinEvent);
        }
        if(runInputHandler){
            handleAction(pinEvent);
        }
    }

    private void handleAction(GpioPinDigitalStateChangeEvent pinEvent){
        try {
            Robot virtualKeyboard = new Robot();
            if(pinEvent.getPin() == RaspiPin.GPIO_02 && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_LEFT);
            } else if(pinEvent.getPin() == RaspiPin.GPIO_03 && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_RIGHT);
            } else if(pinEvent.getPin() == RaspiPin.GPIO_04 && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_UP);
            } else if(pinEvent.getPin() == RaspiPin.GPIO_05 && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_DOWN);
            } else if(pinEvent.getPin() == RaspiPin.GPIO_06 && pinEvent.getState() == defaultPinPressedState){
                virtualKeyboard.keyPress(KeyEvent.VK_SPACE);
            }

            if(pinEvent.getPin() == RaspiPin.GPIO_02 && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_LEFT);
            } else if(pinEvent.getPin() == RaspiPin.GPIO_03 && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_RIGHT);
            } else if(pinEvent.getPin() == RaspiPin.GPIO_04 && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_UP);
            } else if(pinEvent.getPin() == RaspiPin.GPIO_05 && pinEvent.getState() == defaultPinReleasedState){
                virtualKeyboard.keyRelease(KeyEvent.VK_DOWN);
            } else if(pinEvent.getPin() == RaspiPin.GPIO_06 && pinEvent.getState() == defaultPinReleasedState){
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
