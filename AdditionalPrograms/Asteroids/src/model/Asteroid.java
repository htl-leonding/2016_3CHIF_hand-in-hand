/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import javafx.scene.paint.Color;

public class Asteroid extends GameElement {

    int posiX;
    int posY;
    int speed;
    String richt = "Links";


    public Asteroid(int posX, int posY, int speed, Color color) {
        super(posX, posY, speed, color);
        setDirection(Direction.RIGHT);
        richt = "Rechts";
        if (richt == "Rechts") {
            posX = posX - speed;
        }
        else {
            posX = posX + speed;
        }
    }

    /**
     * Wenn ein Asteroid den Bildschirmrand erreicht, soll er die Richtung wechseln
     * und um einen fixen Wert nach unten rutschen!
     */
    @Override
    public void checkAfterMovement() {
        if (getPosX()-GameModel.ASTEROIDWIDTH/2.0<0) {
            setPosX(GameModel.ASTEROIDWIDTH/2);
            setDirection(Direction.RIGHT);
            setPosY(getPosY()+GameModel.STEPDOWN);
        }
        else if ((getPosX()+GameModel.ASTEROIDWIDTH/2.0)>GameModel.GAMESIZE) {
            setPosX(GameModel.GAMESIZE-GameModel.ASTEROIDWIDTH/2);
            setDirection(Direction.LEFT);
            setPosY(getPosY()+GameModel.STEPDOWN);
        }
    }


}
