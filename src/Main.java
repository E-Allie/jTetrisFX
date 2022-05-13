import Exceptions.initPlaceCollision;
import Logic.Board;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {


    //Declare our fundamental variables that will need Global State

    private Board tetrisBoard;
    private GridPane grid;
    private FlowPane flowpane;
    private Scene scene;
    private Stage stage;


    @Override
    public void start(Stage primaryStage) {

        //Create new flowplane;
        flowpane = new FlowPane();
        //TODO: Ideally more will be displayed than just the grid

        //Create a Grid view
        grid = new GridPane();
        stage = primaryStage;



        startGameLoop();

    }


    public void startGameLoop() {
        tetrisBoard = new Board();

        scene = new Scene(grid);  //scene will have dimensions equal to the grid

        /**
         * Here we handle key events
         */
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ESCAPE){
                            Platform.exit();
                        };
                        /**
                         * Rotate piece Counterclockwise on A press.
                         */
                        if (event.getCode() == KeyCode.A) {
                            tetrisBoard = tetrisBoard.pieceCounterOrClockwise(true);
                            System.out.println("APRESSED");
                            draw();
                        }

                        /**
                         * Rotate piece clockwise on D press
                         */
                        if (event.getCode() == KeyCode.D) {
                            tetrisBoard = tetrisBoard.pieceCounterOrClockwise(false);
                            System.out.println("DPRESSED");
                            draw();
                        }

                        /**
                         * Force piece down on S or Down press
                         */
                        if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
                            tetrisBoard = tetrisBoard.pieceLDR(Board.Direction.Down);
                            System.out.println("DOWNPRESSED");
                            draw();
                        }

                        if (event.getCode() == KeyCode.LEFT) {
                            tetrisBoard = tetrisBoard.pieceLDR(Board.Direction.Left);
                            System.out.println("LeftPRESSED");
                            draw();
                        }

                        if (event.getCode() == KeyCode.RIGHT) {
                            tetrisBoard = tetrisBoard.pieceLDR(Board.Direction.Right);
                            System.out.println("RightPRESSED");
                            draw();
                        }
                    }
                }
        );


        /**
         * Here we handle the Actual Game Loop. Runs the "event" every second by default.
         */
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            try{
                tetrisBoard = tetrisBoard.piecePlaceAndFall();
            } catch (initPlaceCollision e) {
                System.out.println("HANDLE GAME OVER");

            }
            draw();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }



    /**
     * A function to be called every draw-frame.
     * Destructs the old elements of the grid from last draw.
     * Then adds the "new" board.
     */
    public void draw() {

        /**
         * This line is incredibly necessary. Without it, every previous grid "exists", stacked under the "top" grid.
         */
        grid.getChildren().clear();

        //Go through the model, using its values to initialize the view
        for(int row = 0; row < tetrisBoard.getPointArray().length; row++) {
            for(int col = 0; col < tetrisBoard.getPointArray()[row].length; col++) {

                //Create a square as the tetris point
                //TODO: Change the overall size based on screen resolution
                Rectangle rect = new Rectangle(30,30);

                //Set the color of the point
                rect.setFill(tetrisBoard.getPoint(row,col).getColor());

                //Now the Rectangle has a internal color. Need to give it a border:
                //TODO: This should also be a function of screen res
                rect.setStrokeWidth(1);
                rect.setStroke(Color.WHITESMOKE);

                //This is a test2
                //rect.setStroke(colorList[rand.nextInt(colorList.length)]);


                grid.add(rect, col, row);
            }
        }


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
