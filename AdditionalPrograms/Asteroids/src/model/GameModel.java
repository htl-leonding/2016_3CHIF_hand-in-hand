/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.paint.Color;

public class GameModel extends Observable {

    //Konstante des Spieles

    public static final int GAMESIZE = 500;         //Spielfläche ist immer quadratisch
    public static final int PLAYERPOS_Y = 470;      //Startposition des Spielers (y-Koordinate)
    public static final int ASTEROIDSTART_Y = 20;   //Startposition der Asteroiden (y-Koordinate)
    public static final int PLAYERSIZE = 40;        //Größe des Spielers (Gleichschenkeliges Dreieck mit der Seitenlänge PLAYERSIZE und der Höhe PLAYERSIZE/2)
    public static final int SHOOTWIDTH = 10;         //Größe eines Schusses (Schuss ist ausgefüllter Kreis)
    public static final int MAXSHOTS = 5;           //Maximale Anzahl gleichzeitig im Spiel befindlicher Schüsse
    public static final int ASTEROIDWIDTH = 50;     //Größe des Asteroiden (Einfaches Quadrat dieser Seitenlänge)
    public static final int STEPDOWN = 20;          //Jedesmal wenn ein Asteroid den Bildschirmrand berührt wird y-Koordinate um diesen Wert erhöht und Richtung geändert
    public static final int NEWASTEROIDTURNS = 500; //Nach wieviel Spielrunden erscheint ein neuer Asteroid

    //Geschwindigkeiten: Pro Runde bewegt sich das jeweilige Objekt um diesen Wert in die aktuelle Bewegungsrichtung
    public static final int PLAYERSPEED = 2;         //Geschwindigkeit des Spielers
    public static final int SHOOTSPEED = 2;         //Geschwindigkeit des Schussen
    public static final int MAXASTEROIDSPEED = 3;  //Maximale Geschwindigkeit eines Asteroiden
    public static final int MINASTEROIDSPEED = 1;   //Minimale Geschwindigkeit eines Asteroiden

    PlayerShip playerShip;
    List<Asteroid> asteroids;
    List<Shoot> shoots;
    Random random;
    int turnCounter = 0;
    Timer t;
    int level;

    boolean gameOver;

    int points;

    public PlayerShip getPlayerShip() {
        return playerShip;
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public List<Shoot> getShoots() {
        return shoots;
    }

    public int getPoints() {
        return points;
    }

    public GameModel() {
        random = new Random();
        playerShip = new PlayerShip(GAMESIZE / 2, PLAYERPOS_Y, PLAYERSPEED, Color.YELLOW);
        shoots = new LinkedList<>();
        asteroids = new LinkedList<>();

        t = new Timer(true);
        t.scheduleAtFixedRate((new TimerTask() {

            @Override
            public void run() {
                gameTurn();
            }
        }), 100, 10);
    }

    /**
     * Eigentliche Spielrunde. Spieler, Schüsse und Asteroiden werden bewegt.
     * Kollisionsprüfungen werden ausgeführt. Richtungsänderung / Reaktion wenn
     * am Rand der Spielfläche geschieht in den einzelnen GameElementen
     */
    public void gameTurn() {
        if (gameOver) {
            return;
        }

        //Spieler bewegen wenn erforderlich
        playerShip.moveElement();

        //Schüsse bewegen, falls außerhalb des Bildschirms, wird Schuss aus der Kollektion entfernt
        for (int i = shoots.size() - 1; i >= 0; i--) {
            shoots.get(i).moveElement();
            if (!shoots.get(i).getAlive()) {
                shoots.remove(i);
            }
        }

        if (asteroids.size() == 0) //Immer ein neuer Asteroid wenn aktuell keiner am Spielfeld
        {
            AddAsteroid();
        }

        //Asteroiden bewegen.. Kollisionsprüfgen
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            asteroids.get(i).moveElement();
            if (!asteroids.get(i).getAlive()) {
                asteroids.remove(i);
            }

            //Prüfen auf Spielende (wenn Asteroid die Spielerebene erreicht hat.
            if (asteroids.get(i).getPosY() >= PLAYERPOS_Y) {
                gameOver = true;
                t.cancel();
                break;
            }

            boolean removed = false;
            //Kollisionprüfungen:
            //Asteroiden mit schüssen
            for (int s = shoots.size() - 1; s >= 0; s--) {
                if ((shoots.get(s).getPosX() >= asteroids.get(i).getPosX() - ASTEROIDWIDTH / 2) && (shoots.get(s).getPosX() <= asteroids.get(i).getPosX() + ASTEROIDWIDTH / 2)
                        && (shoots.get(s).getPosY() <= asteroids.get(i).getPosY() + ASTEROIDWIDTH / 2) && (shoots.get(s).getPosY() >= asteroids.get(i).getPosY() - ASTEROIDWIDTH / 2)) {
                    //Treffer -> Punkte erhöhen
                    points += 1000;
                    shoots.remove(s);

                    removed = true;
                    break; //Keine weiteren Schüsse mehr vergleichen
                }
            }
            if (removed) {
                //Auch Asteroiden entfernen
                asteroids.remove(i);
            }
        }

        if (!gameOver) {
            points++;
            if (points > 5000)
            {
                level = 2;
            }
            turnCounter++;

            //Nach einer bestimmten Rundenanzahl wird immer ein neuer Asteroid platziert

            if (turnCounter > NEWASTEROIDTURNS) {
                AddAsteroid();
                turnCounter = 0;
            }
            else if (level == 2 && (turnCounter*2) > NEWASTEROIDTURNS) {
                AddAsteroid();
                turnCounter = 0;
            }

        }
        //Observer über Änderungen im Model informieren
        setChanged();
        notifyObservers();
    }

    /**
     * Neuer Schuss soll abgefeuert werden
     */
    public void shoot() {
        if (shoots.size() < MAXSHOTS) {
            shoots.add(new Shoot(playerShip.getPosX(), playerShip.getPosY() - PLAYERSIZE / 2, SHOOTSPEED, Color.WHITE));
        }
        //Observer Verständigung muss hier nicht eigens passieren...(da nach jeder Runde ausreichend)
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    /**
     * Neuer Asteroid mit zufälliger Geschwindigkeit wird platziert
     */
    private void AddAsteroid() {
        asteroids.add(new Asteroid(GAMESIZE / 2, ASTEROIDSTART_Y, random.nextInt(MAXASTEROIDSPEED - MINASTEROIDSPEED + 1) + MINASTEROIDSPEED, Color.RED));
    }
}
