/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import javafx.scene.paint.Color;

public class Shoot extends GameElement {

    public Shoot(int posX, int posY, int speed, Color color) {
        super(posX, posY, speed, color);
        setDirection(Direction.UP);
    }

    /**
     * Wenn der Schuss den oberen Bildschirmrand erreicht, wird alive auf false gesetzt
     * Schuss wird dann aus der Model-Kollektion vom Model entfernt werden.
     */
    @Override
    public void checkAfterMovement() {
        super.checkAfterMovement(); //To change body of generated methods, choose Tools | Templates.
        if (getPosY()<0) {
            setAlive(false);
        }
    }


}
