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
    public boolean runInputHandler;
    private final GpioController gpio = GpioFactory.getInstance();
    private final PinState defaultPinPressedState = PinState.HIGH;
    private final PinState defaultPinReleasedState = PinState.LOW;
    private KeyboardInputListener inputListener = null;


    public void setInputHandler(KeyboardInputListener listener){
        inputListener = listener;
    }

    public KeyPressController(boolean handleInputs){
        System.out.println("Started!");
        runInputHandler = handleInputs;

        final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput myButton3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput myButton4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput myButton5 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput myButton6 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN);
        final GpioPinDigitalInput myButton7 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN);

        myButton2.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent pinEvent) {
                System.out.println("Hey!!");
                System.out.println(pinEvent.getPin().getName() + ": " + pinEvent.getState());
                if(inputListener != null){
                    inputListener.keyPressed(pinEvent);
                }
                if(runInputHandler){
                    handleAction(pinEvent);
                }
            }
        });

        myButton4.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent pinEvent) {
                System.out.println("Hey!!");
                System.out.println(pinEvent.getPin().getName() + ": " + pinEvent.getState());
                if(inputListener != null){
                    inputListener.keyPressed(pinEvent);
                }
                if(runInputHandler){
                    handleAction(pinEvent);
                }
            }
        });

        /*GpioPinDigitalInput[] pins = {
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN),
        };
        gpio.addListener(new GpioPinListenerDigital() {
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent pinEvent) {
                System.out.println("Hey!!");
                System.out.println(pinEvent.getPin().getName() + ": " + pinEvent.getState());
                if(inputListener != null){
                    inputListener.keyPressed(pinEvent);
                }
                if(runInputHandler){
                    handleAction(pinEvent);
                }
            }
        }, pins);*/
        System.out.println("Listener added!");
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
