import Exceptions.initPlaceCollision;
import Logic.Board;
import Logic.Point;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main extends Application {


    /** Declare our fundamental variables that will need behave nicely with Global State */

    private Board tetrisBoard;
    private GridPane grid;
    private Scene scene;
    private Stage stage;
    private Label pointsLabel;
    private int finalScore;

    @Override
    public void start(Stage primaryStage) {


        /** Create a Grid view */

        grid = new GridPane();
        
        pointsLabel = new Label();

        finalScore = 0;

        stage = primaryStage;

        gameMenu();
    }

    /**
     * Constructs the Menu for the game
     * Includes graphics, buttons and their handlers, etc.
     */
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
        /** A very specific pixel height to make the menu and the game the same height */
        stage.setHeight(706);

        draw(titleGrid);

        EventHandler<MouseEvent> gameStartHandler = e -> {
            System.out.println("Game start");
            startGameLoop(false);
        };

        /**
         * Here we handle Game Loading.
         * We attempt to load the board from save.dat, and if successful, start the game.
         */
        EventHandler<MouseEvent> gameLoadHandler = e -> {
            try (FileInputStream fileIn = new FileInputStream("save.dat");
                 ObjectInputStream objIn = new ObjectInputStream(fileIn)) {

                tetrisBoard = (Board) objIn.readObject();

                objIn.close();
                fileIn.close();

                startGameLoop(true);

            } catch (FileNotFoundException err) {
                System.out.println("No SaveGame Found!");
                throw new RuntimeException(err);

            } catch (IOException ex) {
                System.out.println("Something beyond the savegame not existing has occurred");
                ex.printStackTrace();
                throw new RuntimeException(ex);

            } catch (ClassNotFoundException ex) {
                System.out.println("Hm, the save file is corrupt/incompatible");
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
            System.out.println("Game loaded");
            //The game is loaded
        };

        EventHandler<MouseEvent> gameEndHandler = e -> {
            System.out.println("Game exit");
            Platform.exit();
        };

        startGame.addEventFilter(MouseEvent.MOUSE_CLICKED, gameStartHandler);
        loadGame.addEventFilter(MouseEvent.MOUSE_CLICKED, gameLoadHandler);
        exitGame.addEventFilter(MouseEvent.MOUSE_CLICKED, gameEndHandler);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * To be called while handling Game Overs.
     * Opens a new menu allowing exiting/restarting.
     */
    public void gameOver()
    {
        GridPane menuGrid = new GridPane();
        BorderPane bPane = new BorderPane(menuGrid);

        scene = new Scene(bPane);

        Button gameRestart = new Button("RESTART GAME");
        Button gameExit = new Button("EXIT GAME");

        HBox inputs = new HBox(gameRestart, gameExit);
        inputs.setAlignment(Pos.TOP_CENTER);
        inputs.setSpacing(5.0);

        Image gameOverImage = new Image("https://i.imgur.com/EBj9xIB.png");
        ImageView gameOverView = new ImageView(gameOverImage);
        gameOverView.setFitWidth(370);
        gameOverView.setFitHeight(200);

        Text points = new Text("Final Score: " + finalScore);
        points.setFill(Color.WHITE);
        points.setFont(Font.font("Arial", 30));
        //I am too dumb to realize how to get the text centered without throwing it into an hbox, if u can fix before we finish then please do so
        HBox pointBox = new HBox(points);
        pointBox.setAlignment(Pos.TOP_CENTER);

        VBox gameOver = new VBox(gameOverView, inputs, pointBox);
        gameOver.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        bPane.setTop(gameOver);
        
        stage.setWidth(389);
        stage.setHeight(706);
        
        EventHandler<MouseEvent> gameRestartHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                System.out.println("Game start");
                startGameLoop(false);
            }
        };

        EventHandler<MouseEvent> gameExitHandler = new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                System.out.println("Game exit");
                Platform.exit();
            }
        };


        gameRestart.addEventFilter(MouseEvent.MOUSE_CLICKED, gameRestartHandler);
        gameExit.addEventFilter(MouseEvent.MOUSE_CLICKED, gameExitHandler);

        stage.setScene(scene);
        stage.show();

        draw(menuGrid);
    }

    /**
     * Function to begin the game loop.
     * Either instantiates a new tetrisBoard, or uses a loaded one.
     *
     * Many key events for the game loop are also handled within here.
     *
     * @param load Whether a tetrisBoard has been loaded in already.
     */
    public void startGameLoop(boolean load) {

        if (!load) {
            tetrisBoard = new Board();
        }

        BorderPane bPane = new BorderPane(grid);
        scene = new Scene(bPane);  //scene will have dimensions equal to the grid

        HBox pointsBox = new HBox(pointsLabel);
        pointsBox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        pointsBox.setAlignment(Pos.TOP_CENTER);
        bPane.setTop(pointsBox);
        
        // Here we handle key events

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

                        // Handling for Game Saving.

                        if (event.getCode() == KeyCode.ENTER) {

                            try (FileOutputStream fileOut = new FileOutputStream("save.dat");
                                 ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {

                                    objOut.writeObject(tetrisBoard);
                                    objOut.close();
                                    fileOut.close();

                                } catch (FileNotFoundException e) {
                               System.out.println("File Cannot be Created");
                               throw new RuntimeException(e);

                           } catch (IOException e) {
                               System.out.println("Something Else Happened");
                                throw new RuntimeException(e);
                            }

                            System.out.println("GAME SAVED");
                        }
                    }
                }
                
        );


        // Here we handle the Actual Game Loop. Runs the "event" every second by default.

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            try{
                tetrisBoard = tetrisBoard.piecePlaceAndFall();
            } catch (initPlaceCollision e) {
                finalScore = tetrisBoard.getPoints();
                tetrisBoard = new Board();
                System.out.println("HANDLE GAME OVER");
                gameOver();
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

        // This line is incredibly necessary. Without it, every previous grid "exists", stacked under the "top" grid.
        // This was the origin of later-game lag during development.

        grid.getChildren().clear();

        //Go through the model, using its values to initialize the view
        for(int row = 0; row < tetrisBoard.getPointArray().length; row++) {
            for(int col = 0; col < tetrisBoard.getPointArray()[row].length; col++) {

                //Create a square as the tetris point
                //TODO: Change the overall size based on screen resolution
                Rectangle rect = new Rectangle(30,30);

                //Set the color of the point
                rect.setFill(colorAWTtoFX(tetrisBoard.getPoint(row,col).getColor()));

                //Give each rectangle a border.
                //TODO: This should also be a function of screen res
                rect.setStrokeWidth(1);
                rect.setStroke(Color.WHITESMOKE);

                grid.add(rect, col, row);
            }
        }

        pointsLabel.setText("Score: " + Integer.toString(tetrisBoard.getPoints()));
        pointsLabel.setTextFill(Color.WHITE);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Helper Function to convert AWT color to JavaFX color
     * This is convenient, as an AWT color is serializable.
     * @param awtColor The java.awt.Color
     * @return A JavaFX Paint Color
     */
    public javafx.scene.paint.Color colorAWTtoFX(java.awt.Color awtColor) {
        return new javafx.scene.paint.Color((double)awtColor.getRed()/255.0,
                                    (double)awtColor.getGreen()/255.0,
                                     (double)awtColor.getBlue()/255.0,
                                   (double)awtColor.getAlpha()/255.0);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
