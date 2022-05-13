import Exceptions.initPlaceCollision;
import Logic.Board;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Main extends Application {


    //Declare our fundamental variables that will need Global State

    private Board tetrisBoard;
    private GridPane grid;
    private FlowPane flowpane;
    private Scene scene;
    private Stage stage;
    private Label pointsLabel;

    @Override
    public void start(Stage primaryStage) {

        //Create new flowplane;
        flowpane = new FlowPane();
 
        //TODO: Ideally more will be displayed than just the grid

        //Create a Grid view
        grid = new GridPane();
        
        pointsLabel = new Label();

        stage = primaryStage;

        gameMenu();
    }

    public void gameMenu()
    {
        GridPane titleGrid = new GridPane();
        BorderPane bPane = new BorderPane(titleGrid);

        scene = new Scene(bPane);

        Button startGame = new Button("START GAME");
        Button exitGame = new Button("EXIT GAME");
        Button loadGame = new Button("LOAD GAME");

        HBox inputs = new HBox(exitGame, startGame, loadGame);
        inputs.setAlignment(Pos.TOP_CENTER);
        inputs.setSpacing(5.0);

        Image tetrisImage = new Image("https://i.imgur.com/3QzH1Kh.png");
        ImageView tetrisView = new ImageView(tetrisImage);
        tetrisView.setFitWidth(370);
        tetrisBoard = new Board();
    
        VBox topOfMenu = new VBox(tetrisView, inputs);
        topOfMenu.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        bPane.setTop(topOfMenu);
        //A very specific pixel height just to make the menu and the game the same height
        stage.setHeight(706);

        draw(titleGrid);

        EventHandler<MouseEvent> gameStartHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                System.out.println("Game start");
                startGameLoop();
            }
        };

        EventHandler<MouseEvent> gameLoadHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                System.out.println("Game loaded");
                //The game is loaded
            }
        };

        EventHandler<MouseEvent> gameEndHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                System.out.println("Game exit");
                Platform.exit();
            }
        };

        startGame.addEventFilter(MouseEvent.MOUSE_CLICKED, gameStartHandler);
        loadGame.addEventFilter(MouseEvent.MOUSE_CLICKED, gameLoadHandler);
        exitGame.addEventFilter(MouseEvent.MOUSE_CLICKED, gameEndHandler);

        stage.setScene(scene);
        stage.show();
    }

    public void startGameLoop() {

        tetrisBoard = new Board();
        BorderPane bPane = new BorderPane(grid);
        scene = new Scene(bPane);  //scene will have dimensions equal to the grid

        HBox pointsBox = new HBox(pointsLabel);
        pointsBox.setAlignment(Pos.TOP_CENTER);
        bPane.setTop(pointsBox);
        
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
                            pointsLabel.setText("Points: " + Integer.toString(tetrisBoard.getPoints()));
                            draw(grid);
                        }

                        /**
                         * Rotate piece clockwise on D press
                         */
                        if (event.getCode() == KeyCode.D) {
                            tetrisBoard = tetrisBoard.pieceCounterOrClockwise(false);
                            System.out.println("DPRESSED");
                            draw(grid);
                        }

                        /**
                         * Force piece down on S or Down press
                         */
                        if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
                            tetrisBoard = tetrisBoard.pieceLDR(Board.Direction.Down);
                            System.out.println("DOWNPRESSED");
                            draw(grid);
                        }

                        if (event.getCode() == KeyCode.LEFT) {
                            tetrisBoard = tetrisBoard.pieceLDR(Board.Direction.Left);
                            System.out.println("LeftPRESSED");
                            draw(grid);
                        }

                        if (event.getCode() == KeyCode.RIGHT) {
                            tetrisBoard = tetrisBoard.pieceLDR(Board.Direction.Right);
                            System.out.println("RightPRESSED");
                            draw(grid);
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
            draw(grid);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }



    /**
     * A function to be called every draw-frame.
     * Destructs the old elements of the grid from last draw.
     * Then adds the "new" board.
     */
    public void draw(GridPane grid) {

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

        pointsLabel.setText("Score: " + Integer.toString(tetrisBoard.getPoints()));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
