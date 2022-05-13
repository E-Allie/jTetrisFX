package Logic;

import Exceptions.initPlaceCollision;
import Logic.Tetraminoes.BagofPieces;
import Logic.Tetraminoes.Tetramino;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Board {


    /**
     * Default Constructor for an Initial Logic.Board.
     * Generates a board of width 10 and height 20.
     */
    public Board() {
        boardGrid = initBoardGrid(21,12);
        isPieceFalling = false;
        totalRows = 21;
        totalCols = 12;
        bag = new BagofPieces();
        points = 0;
    }

    /**
     * Arbitrary Size Initial Logic.Board Constructor.
     * We add 1 and 2 to row and col to allocate a border in the grid.
     * @param rows The height of the playable board.
     * @param cols The width of the playable board.
     */
    public Board(int rows, int cols) {
        boardGrid = initBoardGrid(rows+1,cols+2);
        isPieceFalling = false;
        totalRows = rows+1;
        totalCols = cols+2;
        bag = new BagofPieces();
        points = 0;
    }

    /**
     * A Total Board Constructor. It will give a new board with every member class caller set.
     * This is useful for the functional return of new boards.
     *
     * @param boardGrid
     * @param fallingTetramino
     * @param bag
     * @param isPieceFalling
     * @param totalRows
     * @param totalCols
     * @param points
     */
    public Board(Point[][] boardGrid, Tetramino fallingTetramino, BagofPieces bag, boolean isPieceFalling, int totalRows, int totalCols, int points) {
        this.boardGrid = boardGrid;
        this.fallingTetramino = fallingTetramino;
        this.bag = bag;
        this.isPieceFalling = isPieceFalling;
        this.totalRows = totalRows;
        this.totalCols = totalCols;
        this.points = points;
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
    private Tetramino fallingTetramino;

    /**
     * This will contain spawnable pieces.
     * The tetris standard denotes we have a shuffled bag of the 7 tetraminos.
     * We refill the bag when empty.
     */
    private BagofPieces bag;
    public boolean isPieceFalling;

    /**
     * Total rows and columns. Should actually be 2 or 1 greater than total playable area, for border..
     */
    private int totalRows;

    /**
     * @see #totalRows
     */
    private int totalCols;

    /**
     * The amount of points a player amasses.
     */
    private int points;

    /**
     * Helper Type[ish] to declare the direction of a movement.
     */
    public enum Direction {
        Left, Right, Down
    }



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


        /**
         * Add Real Point Borders to the Grid
         */
        for (int row = 0; row < tempGrid.length; row++) {
            tempGrid[row][0] = new Point(Color.DARKGREY, row, 0);
            tempGrid[row][cols-1] = new Point(Color.DARKGREY, row, cols-1);
        }

        for(int col = 0; col < tempGrid[rows-1].length; col++) {
            tempGrid[rows-1][col] = new Point(Color.DARKGREY, rows-1, col);
        }



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
     * Helper functions to add/remove a tetramino from the grid.
     *
     * Note: These are 2 of the very FEW callable functions that DO mutate the board grid in place.
     * Make note of this when using.
     * @param tetramino The Given Tetramino
     */
    public void removeTetramino(Tetramino tetramino) {
        for (Point point : tetramino.getShapePoints()) {
            boardGrid[point.getRow()][point.getCol()] = new Point();
        }
    }

    /**
     * @see #removeTetramino(Tetramino)
     */
    public void addTetramino(Tetramino tetramino) {
        for (Point point : tetramino.getShapePoints()) {
            boardGrid[point.getRow()][point.getCol()] = point;
        }
    }



    /**
     * Piece placement.
     * Adds a piece if there is no piece, moves piece down 1 if there is one.
     * @return A new board with the modified change
     */
    public Board piecePlaceAndFall() throws initPlaceCollision {

        /**
         * If no piece is falling, try to add a new piece from the bag.
         * If a piece IS falling, try to move it down one.
         */

        if(!isPieceFalling) {
            //Integer division rounding is acceptable here.
            Tetramino shapeToAdd = bag.retrieveFromBag().newCenter(3, totalCols/2);


            /**
             * If the points can be added to the grid, returns a new Board with updated board grid and falling tetramino state.
             * If it CANT be added, throw an exception. This should lead to a game over state.
             */
            if (canPointsToGrid(shapeToAdd.getShapePoints())){
                addTetramino(shapeToAdd);
                return new Board(boardGrid,
                                shapeToAdd, //The Falling Tetramino
                                bag,
                                true, //Whether a tetramino is falling
                                totalRows,
                                totalCols,
                                points
                );
            } else {
                throw new initPlaceCollision();
            }

        } else {
            //If there IS ALREADY a piece falling, make it move down if possible.
            return pieceLDR(Direction.Down);
        }
    }

    /**
     * Attempts to move a piece Left, Down, Or Right.
     * If it is possible, returns a new Board with the piece moved in the direction.
     */
    public Board pieceLDR(Direction direction) {
        Tetramino movedShape;

        switch(direction) {
            case Left:
                movedShape = fallingTetramino.moveLeft();
                break;
            case Right:
                movedShape = fallingTetramino.moveRight();
                break;
            default:
                movedShape = fallingTetramino.moveDown();
        }

        /**
         * We must first remove the falling tetramino from the grid to check if the new state is possible.
         *
         * If the new state is possible, add it to the grid.
         *
         * If it isn't, re-add the original tetramino.
         */

        removeTetramino(fallingTetramino);

        if (canPointsToGrid(movedShape.getShapePoints())) {

            addTetramino(movedShape);

            return new Board(boardGrid,
                    movedShape, //The Falling Tetramino
                    bag,
                    true, //Whether a tetramino is falling
                    totalRows,
                    totalCols,
                    points
            );
        } else {
            /**
             * If the new state is IMpossible,
             * Again re-add the original removed fallingtetramino.
             * Return a new board, with isPieceFalling Reset IF it was a failed Downshift.
             *
             * IF it was failed downshift, also check to see if we can remove a line and add points.
             */

            addTetramino(fallingTetramino);

            if (direction == Direction.Down) {

                int combo = 0;

                /**Check if a row is full
                 * totalRows-2 to ignore the bottom "barrier" row.
                 * We also loop from bottom to top, to have proper row shifting when a row is cleared.
                 */

                for(int i = totalRows-2; i >= 0; i--) {

                    if (Arrays.stream(boardGrid[i]).allMatch(Point::isOccupied)) {

                        /** If a given row is occupied, we can downshift all rows above by cloning them on top. */
                        for(int j = i; j >= 1; j--) {
                            boardGrid[j] = boardGrid[j-1].clone();
                        }


                        /** We re-increment i, so the "same row", now occupied by what was above it, may be checked again for completion. */
                        i++;

                        combo += 1;
                    }
                }
                points += combo*100;

                //Return the board with pieces removed
                return new Board(
                        boardGrid,
                        null,
                        bag,
                        false,
                        totalRows,
                        totalCols,
                        points
                );
            }


            /** When the direction isn't down, simply return */

            return new Board(
                    boardGrid,
                    fallingTetramino, //The Falling Tetramino
                    bag,
                    isPieceFalling, //Whether a tetramino is falling
                    totalRows,
                    totalCols,
                    points
            );
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

        Tetramino rotateTet;

        if(counter){
            rotateTet = fallingTetramino.rotateCounterclockwise();
        } else {
            rotateTet = fallingTetramino.rotateClockwise();
        }

        /**
         * If a piece is actually falling and the rotated tetramino can be placed,
         * Clear the grid of the old tetramino, add the new one, and return the new board.
         * Otherwise, return an exact copy of the existing board.
         *
         * In order to check if the new piece can be placed, we must first 0 out the old locations.
         */

        if(isPieceFalling) {
            removeTetramino(fallingTetramino);

            /**
             * If the Grid can take the rotates tet, insert it.
             * If it can't, return the original falling tetramino.
             */
            if(canPointsToGrid(rotateTet.getShapePoints())) {
                addTetramino(rotateTet);
                return new Board(
                        boardGrid,
                        rotateTet, //The Falling Tetramino
                        bag,
                        true, //Whether a tetramino is falling
                        totalRows,
                        totalCols,
                        points
                );

            } else {
                addTetramino(fallingTetramino);
            }
        }
        return new Board(
                boardGrid,
                fallingTetramino,
                bag,
                isPieceFalling,
                totalRows,
                totalCols,
                points
        );
    }

    /**
     * Checks to see if points produce a collision with existing grid.
     * @param points The Points to be added.
     * @return Whether the points can be added without collision [True = No Collision]
     */
    private boolean canPointsToGrid(Point[] points) {
        for (Point point: points) {
            if (boardGrid[point.getRow()][point.getCol()].isOccupied()) {
                System.out.println(boardGrid[point.getRow()][point.getCol()].isOccupied());
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns the (score) point total
     * @return Current point total
     */
    public int getPoints()
    {
        return points;
    }
}
