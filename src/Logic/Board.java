package Logic;

import Shapes.I;
import Shapes.L;
import Shapes.O;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {


    /**
     * Default Constructor for a Logic.Board.
     * Generates a board of width 10 and height 20.
     */
    public Board() {
        boardGrid = initBoardGrid(20,10);
        isPieceFalling = false;
        totalRows = 20;
        totalCols = 10;
    }

    /**
     * Arbitrary Size Logic.Board Constructor.
     * @param rows The height of the board.
     * @param cols The width of the board.
     */
    public Board(int rows, int cols) {
        boardGrid = initBoardGrid(rows,cols);
        isPieceFalling = false;
        totalRows = rows;
        totalCols = cols;
    }

    /**
     * The actual board, which can contain tetraminoes.
     * A Logic.Point array is used for specific extra data that needs to be encoded, such as color.
     */
    private Point[][] boardGrid;

    /**
     * Points in the process of falling.
     */
    private Point[] fallingPoints = new Point[4];

    //A list of all potential shapes
    //public final Tetramino[] allShapes = {new I(), new L(), new O()};

    public boolean isPieceFalling;

    private int totalRows;
    private int totalCols;



    /**
     * Helper function to initialize a default-initialized Logic.Point[][]
     * @param rows The height of the array
     * @param cols The width of the array
     * @return A Logic.Point[][] of default constructed points.
     */
    private Point[][] initBoardGrid(int rows, int cols) {

        /**
         * Construct a default-initialized array with the streams api
         * Theoretically faster than the for loop
         */
        Point[][] tempGrid = new Point[rows][cols];
        IntStream.range(0, rows).parallel()
                .forEach(x -> Arrays.setAll(tempGrid[x], y -> new Point()));


        return tempGrid;
    }

    /**
     * Helper Function to access the Logic.Point Array
     * @return The entire board as a Logic.Point Array
     */
    public Point[][] getPointArray() {
        return boardGrid;
    }

    /**
     * Helper Function to access a specific point
     * @param row Row of point
     * @param col Column of point
     * @return A specific point
     */
    public Point getPoint(int row, int col) {
        return boardGrid[row][col];
    }


    /**
     * Piece placement.
     * Adds a piece if there is no piece, moves piece down 1 if there is one.
     * @return Whether the piece placement/movement was successful. If it fails, a Game-Over should occur.
     */
    public boolean pieceFall() {

        if(isPieceFalling = false) {
            //Integer division rounding is acceptable here.
            int[] center = {totalRows-3, totalCols/2};

            I shape = new I(new Point(center[0],center[1]));

            if (!pointsToGrid(shape.getShapePoints())){
                return false;
            }

            isPieceFalling = true;
        } else {
            //I newshape = shape.moveDown();
        }

        return true;
    }

    /**
     * Adds points to grid with collision.
     * If a colission is detected, returns false.
     * @param points The Points to be added.
     * @return Whether the action was wholly successful.
     */
    private boolean pointsToGrid(Point[] points) {
        for (Point point: points) {
            if (boardGrid[point.getRow()][point.getCol()] == point) {
                return false;
            } else {
                boardGrid[point.getRow()][point.getCol()] = point;
            }
        }
        return true;
    }
}
