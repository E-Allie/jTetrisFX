package Logic;

public class Board {


    /**
     * Default Constructor for a Logic.Board.
     * Generates a board of width 10 and height 20.
     */
    public Board() {
        boardGrid = initBoardGrid(20,10);
    }

    /**
     * Arbitrary Size Logic.Board Constructor.
     * @param rows The height of the board.
     * @param cols The width of the board.
     */
    public Board(int rows, int cols) {
        boardGrid = initBoardGrid(rows,cols);
    }

    /**
     * The actual board, which can contain tetraminoes.
     * A Logic.Point array is used for specific extra data that needs to be encoded, such as color.
     */
    private Point[][] boardGrid;


    /**
     * Helper function to initialize a default-initialized Logic.Point[][]
     * @param rows The height of the array
     * @param cols The width of the array
     * @return A Logic.Point[][] of default constructed points.
     */
    private Point[][] initBoardGrid(int rows, int cols) {

        Point[][] tempGrid = new Point[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tempGrid[i][j] = new Point();
            }
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
}
