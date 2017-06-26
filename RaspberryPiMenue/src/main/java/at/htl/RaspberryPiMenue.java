package at.htl;

import at.htl.conrollers.KeyPressController;
import at.htl.utils.RunShellCommandFromJava;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static at.htl.conrollers.KeyPressController.defaultPinPressedState;

/**
 * Created by sakal_andrej on 25.01.17.
 */
public class RaspberryPiMenue {
    private static TimerTask task;
    private static String s[];
    private static KeyPressController keyPressController;
    private static boolean isStarted = false;

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
                    TimeUnit.SECONDS.sleep(10);
                    if (isStarted==false) {
                        isStarted = true;
                        keyPressController.setRunInputHandler(true);
                        RunShellCommandFromJava.main(new String[]{"feh -F /media/USB/"});
                    }
                    System.out.println("task started");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        task.run();
        while(true) {

        }
    }

    public static void runCode(GpioPinDigitalStateChangeEvent event){
        System.out.println("We are in RunCode");
        if (event.getState() == defaultPinPressedState) {
            s = null;
            if (event.getPin().getName() == RaspiPin.GPIO_02.getName()) {
                s = new String[]{"feh -F /media/USB/"};
                System.out.println("We are PIN2");
            } else if (event.getPin().getName() == RaspiPin.GPIO_04.getName()) {
                s = new String[]{"omxplayer -F /media/USB/"};
            } else if (event.getPin().getName() == RaspiPin.GPIO_05.getName()) {
                s = new String[]{"omxplayer -F /media/USB/"};
            } else if (event.getPin().getName() == RaspiPin.GPIO_03.getName()) {
                task.cancel();
            }
            //testphase sudo
            if (s != null && isStarted==false) {
                keyPressController.setRunInputHandler(true);
                isStarted=true;
                RunShellCommandFromJava.main(new String[]{"sudo chmod 777 -R /media/USB/"});
                RunShellCommandFromJava.main(s);
            }
        }
    }
}
