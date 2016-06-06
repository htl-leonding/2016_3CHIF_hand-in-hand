/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import javafx.scene.paint.Color;

public class PlayerShip extends GameElement {

    public PlayerShip(int posX, int posY, int speed, Color color) {
        super(posX, posY, speed, color);
    }

    /**
     * Wenn der Spieler den Rand erreicht, soll er stehenbleiben
     */
    @Override
    public void checkAfterMovement() {
        if (getPosX()-GameModel.PLAYERSIZE/2<0) {
            setPosX(GameModel.PLAYERSIZE/2);
        }
        else if ((getPosX()+GameModel.PLAYERSIZE/2)>GameModel.GAMESIZE) {
            setPosX(GameModel.GAMESIZE-GameModel.PLAYERSIZE/2);
        }


    }



}
