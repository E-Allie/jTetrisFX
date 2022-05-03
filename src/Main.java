import Logic.Board;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
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
/*
    @Override
    public void start(Stage primaryStage) {

        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,900,900);
            scene.getStylesheets().add(getClass().getResource("Resources/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/

    //Declare our fundamental variables that will need Global State
    //It's either this or wrapper classes because of pass-by-value
    //Both are ugly
    private Board tetrisBoard;
    private GridPane grid;

    private FlowPane flowpane;

    private Scene scene;
    private Stage stage;

    //These next 2 lines are tests remove from final code
    Random rand = new Random();
    public final Color[] colorList = {Color.BLUE, Color.GREEN, Color.PURPLE, Color.RED};

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
                    }
                }
        );


        /**
         * Here we handle the Actual Game Loop. Runs the "event" every second by default.
         */
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            draw();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void draw() {

        //Go through the model, using its values to initialize the view
        for(int row = 0; row < tetrisBoard.getPointArray().length; row++) {
            for(int col = 0; col < tetrisBoard.getPointArray()[row].length; col++) {

                //Create a square as the tetris point
                Rectangle rect = new Rectangle(50,50);

                //Set the color of the point
                rect.setFill(tetrisBoard.getPoint(row,col).getColor());

                //Now the Rectangle has a internal color. Need to give it a border:
                rect.setStrokeWidth(2);
                //rect.setStroke(Color.WHITE);

                //This is a test
                rect.setStroke(colorList[rand.nextInt(colorList.length)]);


                grid.add(rect, col, row);
            }
        }

        //scene = new Scene(grid);  //scene will have dimensions equal to the grid

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}