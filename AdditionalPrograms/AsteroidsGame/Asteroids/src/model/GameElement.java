/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import javafx.scene.paint.Color;

public abstract class GameElement {

    //Felder
    private boolean alive;
    private int posX;
    private int posY;
    private int speed; //Bewegungsschritte pro Turn
    private Direction direction; //Bewegungsrichtung -> einfaches Spiel es gibt nur Himmelsrichtungen
    private Color color;

    //Getter/Setter
    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Methode wird nach jeder Bewegung aufgerufen -> Kann
     * in abgeleiteten Klassen überschrieben werden!
     */
    public void checkAfterMovement() {
    }

    /**
     * Führt Bewegung je nach Direction und Speed durch.
     * Prüfung ob gültige Endposition und Reaktion darauf soll in
     * Methode chackAfterMovement erfolgen.
     */
    public void moveElement() {
        if (direction==Direction.LEFT) {
            this.posX-=speed;
        }
        else if (direction==Direction.RIGHT) {
            this.posX+=speed;
        }
        else if (direction==Direction.UP) {
            this.posY-=speed;
        }

        checkAfterMovement();
    }

    public GameElement(int posX,int posY, int speed, Color color) {
        this.posX = posX;
        this.posY=posY;
        this.speed = speed;
        this.color = color;
        alive=true;
    }
}
