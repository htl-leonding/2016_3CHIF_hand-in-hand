package controller;

/**
 * Created by Andrej on 01.06.2016.
 */

        import java.net.URL;
        import java.util.Observable;
        import java.util.Observer;
        import java.util.ResourceBundle;
        import javafx.application.Platform;
        import javafx.event.ActionEvent;
        import javafx.event.Event;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.canvas.Canvas;
        import javafx.scene.canvas.GraphicsContext;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Label;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.BorderPane;
        import javafx.scene.paint.Color;
        import javafx.scene.paint.Paint;
        import model.Asteroid;
        import model.Direction;
        import model.GameModel;
        import model.Shoot;

public class GameController implements Initializable, Observer {

    @FXML
    private Label lblPoints;
    @FXML
    private Canvas cvGame;

    GameModel model;

    @FXML
    private BorderPane borderPaneGame;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        model = new GameModel();
        model.addObserver(this);

        //cvGame ist das Canvas auf dem gezeichnet wird.
        //Damit ein Key Event von der Komponente empfangen werden kann,
        //muss diese den Focus besitzen! Dies kann mit folgenden zwei Befehlen
        //bewirkt werden
        cvGame.setFocusTraversable(true);
        cvGame.requestFocus();
        //Nun kann das gewünschte Key-Event abonniert werden
        cvGame.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    model.getPlayerShip().setDirection(Direction.LEFT);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    model.getPlayerShip().setDirection(Direction.RIGHT);
                }
            }
        });


        cvGame.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.LEFT && model.getPlayerShip().getDirection() == Direction.LEFT)
                        || (event.getCode() == KeyCode.RIGHT && model.getPlayerShip().getDirection() == Direction.RIGHT)) {
                    model.getPlayerShip().setDirection(Direction.NONE);
                }
                if (event.getCode() == KeyCode.SPACE) {
                    model.shoot();
                }
            }
        });
    }

    private void drawWorld() {
        if (borderPaneGame.getScene() == null) //Falls Scene noch nicht fertig verknüpft -> return
        {
            return;
        }

        double factor;

        //Kleinere Seite suchen! Breite / Höhe nicht von borderPaneGame verwendbar, da dieses so klein wie
        //möglich wird. D.h. wird nicht kleiner als das Canvas.
        //Window hat jedoch die tatsächliche Fenstergröße
        double availableMaxWidth = borderPaneGame.getScene().getWindow().getWidth();
        double availableMaxHeight = borderPaneGame.getScene().getWindow().getHeight() - 50; //Fenstergröße - Bereich für Label (Kostanter Wert)

        cvGame.setWidth(Math.min(availableMaxWidth, availableMaxHeight) - 40);
        cvGame.setHeight(cvGame.getWidth());

        factor = cvGame.getWidth() / 500.0;

        lblPoints.setText("Highscore: " + model.getPoints());

        GraphicsContext ctx = cvGame.getGraphicsContext2D();

        double width = cvGame.getWidth();
        double height = cvGame.getHeight();

        //ctx.clearRect(0, 0, cvGame.getWidth(), cvGame.getHeight());
        //Hintergrund schwarz füllen
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, width, height);

        //Asterioden Zeichnen
        for (Asteroid asteroid : model.getAsteroids()) {
            ctx.setFill(asteroid.getColor());
            ctx.fillRect((asteroid.getPosX() - GameModel.ASTEROIDWIDTH / 2.0) * factor, (asteroid.getPosY() - GameModel.ASTEROIDWIDTH / 2.0) * factor, GameModel.ASTEROIDWIDTH * factor, GameModel.ASTEROIDWIDTH * factor);
        };

        //Spieler zeichnen
        ctx.setFill(model.getPlayerShip().getColor());
        ctx.fillPolygon(new double[]{(model.getPlayerShip().getPosX() - GameModel.PLAYERSIZE / 2) * factor, (model.getPlayerShip().getPosX() + GameModel.PLAYERSIZE / 2) * factor, model.getPlayerShip().getPosX() * factor},
                new double[]{model.getPlayerShip().getPosY() * factor, model.getPlayerShip().getPosY() * factor, (model.getPlayerShip().getPosY() - GameModel.PLAYERSIZE / 2) * factor}, 3);

        //Schüsse zeichnen
        for (Shoot shoot : model.getShoots()) {
            ctx.setFill(shoot.getColor());
            ctx.fillOval((shoot.getPosX() - GameModel.SHOOTWIDTH / 2) * factor, (shoot.getPosY() - GameModel.SHOOTWIDTH / 2) * factor, GameModel.SHOOTWIDTH * factor, GameModel.SHOOTWIDTH * factor);
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        if (model.getGameOver()) {
            Platform.runLater(() -> {
                Alert a = new Alert(Alert.AlertType.INFORMATION, ("GAME OVER! \nYour Highscore: " + model.getPoints()));
                a.showAndWait();
                System.exit(0);
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    drawWorld();

                }
            });
        }
    }
}

