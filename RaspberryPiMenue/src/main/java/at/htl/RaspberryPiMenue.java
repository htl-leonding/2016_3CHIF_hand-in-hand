package at.htl;

import at.htl.at.htl.utils.RunShellCommandFromJava;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        System.out.print("1. Diashow (Vom USB-Stick) bitte den ganz linken Blauen Knopf drücken! (LEFT) \n");
        System.out.print("2. Videos abspielen (Vom USB-Stick) bitte den obersten roten Knopf drücken! (UP)\n");
        System.out.print("3. Roboter steuern bitte den rechten gelben Knopf drücken! (RIGHT) \n\n");
        System.out.print("4. Korrektur! (DOWN) \n\n");

        System.out.print("AUSWAHL MIT ENTER BESTÄTIGEN! (SPACE)\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter Integer:");
        try{
            int i = Integer.parseInt(br.readLine());
        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }


    }
}
