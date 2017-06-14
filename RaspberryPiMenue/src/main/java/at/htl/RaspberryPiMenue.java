package at.htl;

import at.htl.at.htl.utils.RunShellCommandFromJava;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;

/**
 * Created by sakal_andrej on 25.01.17.
 */
public class RaspberryPiMenue {


    public static void main(String[] args) throws IOException {

        String[] ab = new String[1];

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
        System.out.print("3. Korrektur! (DOWN) \n\n");

        System.out.print("AUSWAHL MIT ENTER BESTÄTIGEN! (RECHTER KNOPF)\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Eingabe tätigen:");
        int i = 4;
        try{
            i = Integer.parseInt(br.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Nur Zahlen eingeben bitte!");
        }
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.keyPress(KeyEvent.VK_ENTER);

    switch (i) {
            case 1:
            String[] s = new String[] {"feh -F -r -x /media/USB"};
            RunShellCommandFromJava.main(s);
            break;
        case 2:
            String[] s2 = new String[] {"omxplayer -F /media/USB"};
            RunShellCommandFromJava.main(s2);
            break;
        case 3:
            String[] s3 = new String[] {"omxplayer -F /media/USB"};
            RunShellCommandFromJava.main(s3);
            break;
    }



    }
}
