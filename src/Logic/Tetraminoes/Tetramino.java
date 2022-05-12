package Logic.Tetraminoes;

import Logic.Point;
import javafx.scene.paint.Color;

/**
 * The abstract tetramino.
 */
public abstract class Tetramino {


    private Color color;
    private Point center;
    private Point[] shapePoints;


    enum TetraminoShape {
        I, L, O
    }

    private TetraminoShape shape;


    /**
     * Methodology sourced from the Super Rotation System
     * O: Spawn State
     * R: 1 Rotation Clockwise
     * L: 1 Rotation Counterclockwise
     * TWO: 2 Successive Rotations [equivalent for either direction]
     *
     * We also provide built-in methods to rotate a state in any direction.
     *
     * The overrides are necessary to implement proper rotation state wrapping.
     */
    public enum RotationState {
        O {
            @Override
            public RotationState rotateCounterClockwise() {
                return L;
            }
        },
        R,
        TWO,
        L {
            @Override
            public RotationState rotateClockwise() {
                return O;
            }
        };

        /**
         * Finds the state clock or counter-clockwise to the current state
         * @return Enum representing state.
         */
        public RotationState rotateClockwise() {
            return values()[ordinal()+1];
        }

        /**
         * @see #rotateClockwise()
         */
        public RotationState rotateCounterClockwise() {
            return values()[ordinal()-1];
        }
    }

    private RotationState rotationState;

    public void setRotationState(RotationState rotationState){
        this.rotationState = rotationState;
    }

    public RotationState getRotationState() {
        return rotationState;
    }



    public Color getColor() {
        return color;
    }

    public TetraminoShape getShapeType() {
        return shape;
    }

    public void setCenter(Point center) {
        this.center = center;
    }
    public Point getCenter() {
        return center;
    }

    abstract public Tetramino newCenter(int row, int col);

    /**
     * Returns an array of every point of a tetramino.
     * @return Point[] of the tetramino.
     */
    abstract public Point[] getShapePoints();

    /**
     * Yields a new tetramino that is either counter-or-clockwise rotated.
     * @return The rotated Tetramino [by means of updating the RotationState]
     */
    abstract public Tetramino rotateClockwise();

    /**
     * @see #rotateClockwise()
     */
    abstract public Tetramino rotateCounterclockwise();

    /**
     * Yields a new tetramino with center shifted in the requested direction.
     * @return A new tetramino with center shifted.
     */
    abstract public Tetramino moveDown();

    /**
     * @see #moveDown()
     */
    abstract public Tetramino moveLeft();

    /**
     * @see #moveDown() 
     */
    abstract public Tetramino moveRight();


}
