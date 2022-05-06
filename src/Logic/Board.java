package Logic;

import Exceptions.initPlaceCollision;
import Tetraminoes.ITetramino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Board {


    /**
     * Default Constructor for an Initial Logic.Board.
     * Generates a board of width 10 and height 20.
     */
    public Board() {
        boardGrid = initBoardGrid(20,10);
        isPieceFalling = false;
        totalRows = 20;
        totalCols = 10;
        bagOfPieces = new ArrayList<>(List.of(new ITetramino(new Point(), newTetramino.RotationState.O)));
    }

    /**
     * Arbitrary Size Initial Logic.Board Constructor.
     * @param rows The height of the board.
     * @param cols The width of the board.
     */
    public Board(int rows, int cols) {
        boardGrid = initBoardGrid(rows,cols);
        isPieceFalling = false;
        totalRows = rows;
        totalCols = cols;
        bagOfPieces = new ArrayList<>(List.of(new ITetramino(new Point(), newTetramino.RotationState.O)));
    }

    /**
     * A Total Board Constructor. It will give a new board with every member class caller set.
     * This is useful for the functional return of new boards.
     * @param boardGrid
     * @param fallingTetramino
     * @param bagOfPieces
     * @param isPieceFalling
     * @param totalRows
     * @param totalCols
     */
    public Board(Point[][] boardGrid, newTetramino fallingTetramino, ArrayList<newTetramino> bagOfPieces, boolean isPieceFalling, int totalRows, int totalCols) {
        this.boardGrid = boardGrid;
        this.fallingTetramino = fallingTetramino;
        this.bagOfPieces = bagOfPieces;
        this.isPieceFalling = isPieceFalling;
        this.totalRows = totalRows;
        this.totalCols = totalCols;
    }

    /**
     * The actual board, which can contain tetraminoes.
     * It does not, however, STORE tetraminoes. It stores POINTS.
     * A Logic.Point array is used for specific extra data that needs to be encoded, such as color.
     */
    private Point[][] boardGrid;

    /**
     * The Active Tetramino Falling.
     */
    private newTetramino fallingTetramino;

    /**
     * This will contain spawnable pieces.
     * The tetris standard denotes we have a shuffled bag of the 7 tetraminos.
     * We refill the bag when empty.
     */
    private ArrayList<newTetramino> bagOfPieces;
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
     * Helper function to be called when a piece is needed from the bag.
     * If the bag is not empty, returns the piece and removed it from the bag.
     * Refills the board's bag with a randomly ordered list of all tetraminoes when it is empty.
     * This is Tetris Specification Compliant.
     */
    private newTetramino retrieveFromBag() {
        if(bagOfPieces.isEmpty()) {
            bagOfPieces = new ArrayList<>(List.of(new ITetramino(new Point(), newTetramino.RotationState.O)));
        }
        return bagOfPieces.remove(0);
    }

    /**
     * Piece placement.
     * Adds a piece if there is no piece, moves piece down 1 if there is one.
     * @return A new board with the modified change
     */
    public Board pieceFall() throws initPlaceCollision {

        /**
         * If no piece is falling, try to add a new piece from the bag.
         * If a piece IS falling, try to move it down one.
         */
        if(!isPieceFalling) {

            //Integer division rounding is acceptable here.
            newTetramino shapeToAdd = bagOfPieces.remove(0).newCenter(3, totalCols/2);

            /**
             * If the points can be added to the grid, returns a new Board with updated board grid and falling tetramino state.
             * If it CANT be added, throw an exception. This should lead to a game over state.
             */
            if (canPointsToGrid(shapeToAdd.getShapePoints())){
                for (Point point: shapeToAdd.getShapePoints()) {
                    boardGrid[point.getRow()][point.getCol()] = point;
                }
                return new Board(boardGrid,
                                shapeToAdd, //The Falling Tetramino
                                bagOfPieces,
                                true, //Whether a tetramino is falling
                                totalRows,
                                totalCols
                                );
            } else {
                throw new initPlaceCollision();
            }

        } else {
            newTetramino downedShape = fallingTetramino.moveDown();
            System.out.println(downedShape);
            System.out.println(fallingTetramino);

            /**
             * If the down shifted state is possible,
             * Remove the old tetramino points from the grid
             * Add the new ones, and return the new board.
             */
            if (canPointsToGrid(downedShape.getShapePoints())) {
                /**
                 * Sequential for loops are needed, as there is a chance a new point is also an old point.
                 * If we were to merge the loops as is, points could go missing.
                 */
                for (Point point : fallingTetramino.getShapePoints()) {
                    boardGrid[point.getRow()][point.getCol()] = new Point();
                }

                for (Point point : downedShape.getShapePoints()) {
                    point.setColor(downedShape.getColor());
                    boardGrid[point.getRow()][point.getCol()] = point;
                }

                return new Board(boardGrid,
                        downedShape, //The Falling Tetramino
                        bagOfPieces,
                        true, //Whether a tetramino is falling
                        totalRows,
                        totalCols
                );
            } else {
                /**
                 * If the down-shifted state is IMpossible,
                 * Return a new board with isPieceFalling Reset.
                 */

                return new Board(boardGrid,
                        null, //The Falling Tetramino
                        bagOfPieces,
                        false, //Whether a tetramino is falling
                        totalRows,
                        totalCols
                );
            }
        }
    }

    /**
     * Produces a new board with the Active Tetramino rotated in the requested direction.
     * If it cannot be rotated, no action will be performed.
     * TODO: Make compliant with Super Rotation System.
     * @param counter If the rotation is Counterclockwise or not.
     * @return New Board with associated tetramino vars updated.
     */
    public Board pieceCounterOrClockwise(boolean counter) {

        newTetramino rotateTet;

        if(counter){
            rotateTet = fallingTetramino.rotateCounterclockwise();
        } else {
            rotateTet = fallingTetramino.rotateClockwise();
        }

        /**
         * If a piece is actually falling and the rotated tetramino can be placed,
         * Clear the grid of the old tetramino, add the new one, and return the new board.
         * Otherwise, return an exact copy of the existing board.
         */

        if(isPieceFalling && canPointsToGrid(rotateTet.getShapePoints())) {
            /**
             * Sequential for loops are needed, as there is a chance a new point is also an old point.
             * If we were to merge the loops as is, points could go missing.
             */
            for (Point point : fallingTetramino.getShapePoints()) {
                boardGrid[point.getRow()][point.getCol()] = new Point();
            }

            for (Point point : rotateTet.getShapePoints()) {
                boardGrid[point.getRow()][point.getCol()] = point;
            }

            return new Board(boardGrid,
                    rotateTet, //The Falling Tetramino
                    bagOfPieces,
                    true, //Whether a tetramino is falling
                    totalRows,
                    totalCols
            );
        } else {
            return new Board(
                    boardGrid,
                    fallingTetramino,
                    bagOfPieces,
                    isPieceFalling,
                    totalRows,
                    totalCols
            );
        }


    }

    /**
     * Checks to see if points produce a collision with existing grid.
     * @param points The Points to be added.
     * @return Whether the points can be added without collision [True = No Collision]
     */
    private boolean canPointsToGrid(Point[] points) {
        for (Point point: points) {
            if (boardGrid[point.getRow()][point.getCol()] == point) {
                return false;
            }
        }
        return true;
    }
}
