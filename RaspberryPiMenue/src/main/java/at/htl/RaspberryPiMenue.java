package at.htl;

import at.htl.conrollers.KeyPressController;
import at.htl.utils.RunShellCommandFromJava;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by sakal_andrej on 25.01.17.
 */
public class RaspberryPiMenue {
    private static TimerTask task;
    private static String s[];
    private static KeyPressController keyPressController;

    public static void main(String[] args) throws IOException {
        //String[] ab = new String[1];

        System.out.print("******************************************************************************\n");
        System.out.print("******************************************************************************\n");
        System.out.print("******************************    Hand-In-Hand    ****************************\n");
        System.out.print("******************************************************************************\n");
        System.out.print("******************************************************************************\n\n");
        //Zwei Leerzeilen
        System.out.print("Bitte die Taste für das entsprechende Programm drücken!\n");

        //Zuerst Hand-In-Hand mit Blauen Button als Enter ausführen & die Buttons als Ziffern einstellen dann
        //wenn 1, 2 oder 3 auf normal Betrieb nehmen

        System.out.print("1. Diashow (Vom USB-Stick) bitte den ganz linken Blauen Knopf drücken! (LINKER KNOP) \n");
        System.out.print("2. Videos abspielen (Vom USB-Stick) bitte den obersten roten Knopf drücken! (OBERER KNOPF)\n");
        System.out.print("3. Korrektur! (UNTERER KNOPF) \n\n");

        System.out.print("AUSWAHL MIT ENTER BESTÄTIGEN! (RECHTER KNOPF)\n");

        keyPressController = new KeyPressController(false);
        keyPressController.setInputHandler(new KeyPressController.KeyboardInputListener() {
            public void keyPressed(GpioPinDigitalStateChangeEvent pinEvent) {
                runCode(pinEvent);
            }
        });
        task = new TimerTask() {
            @Override
            public void run() {
                try {
                    this.wait(10000);
                    RunShellCommandFromJava.main(new String[]{"feh -F -r -x /media/USB"});
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        task.run();

    }

    public static void runCode(GpioPinDigitalStateChangeEvent event){

        if(event.getPin() == RaspiPin.GPIO_02){
            s = new String[]{"feh -F -r -x /media/USB"};
        } else if(event.getPin() == RaspiPin.GPIO_04){
            s = new String[]{"omxplayer -F /media/USB"};
        } else if(event.getPin() == RaspiPin.GPIO_05){
            s = new String[]{"omxplayer -F /media/USB"};
        } else if(event.getPin() == RaspiPin.GPIO_03){
            RunShellCommandFromJava.main(s);
        }
    }
}
