package Logic.Tetraminoes;

import Logic.Point;
import javafx.scene.paint.Color;

/**
 * The abstract tetramino.
 */
public abstract class Tetramino {

    public Tetramino(Color color, Point center, RotationState rotationState, TetraminoShape shape) {
        this.color = color;
        this.center = center;
        this.rotationState = rotationState;
        this.shape = shape;
    }


    private final Color color;
    private final Point center;


    enum TetraminoShape {
        I, L, O, J, T, Z, S
    }

    private final TetraminoShape shape;


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
    enum RotationState {
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

    private final RotationState rotationState;


    public RotationState getRotationState() {
        return rotationState;
    }



    public Color getColor() {
        return color;
    }

    public Point getCenter() {
        return center;
    }

    public Tetramino newCenter(int row, int col) {
        return createTetramino(shape, new Point(getColor(), row, col), rotationState);
    };


    /**
     * Yields a new tetramino that is either counter-or-clockwise rotated.
     * @return The rotated Tetramino [by means of updating the RotationState]
     */
    public Tetramino rotateClockwise() {
        return createTetramino(shape, center, rotationState.rotateClockwise());
    };

    /**
     * @see #rotateClockwise()
     */
    public Tetramino rotateCounterclockwise(){
        return createTetramino(shape, center, rotationState.rotateCounterClockwise());
    }

    /**
     * Yields a new tetramino with center shifted in the requested direction.
     * @return A new tetramino with center shifted.
     */
    public Tetramino moveDown() {
        return createTetramino(shape, center.getBelow(), rotationState);
    };

    /**
     * @see #moveDown()
     */
    public Tetramino moveLeft() {
        return createTetramino(shape, center.getLeft(), rotationState);
    }

    /**
     * @see #moveDown() 
     */
    public Tetramino moveRight() {
        return createTetramino(shape, center.getRight(), rotationState);
    }


    protected static Tetramino createTetramino(TetraminoShape shape, Point center, Tetramino.RotationState rotationState) {
        switch (shape) {
            case I:
                return new ITetramino(center, rotationState);
            case L:
                return new LTetramino(center, rotationState);
            case O:
                return new OTetramino(center, rotationState);
            case T:
                return new TTetramino(center, rotationState);
            case J:
                return new JTetramino(center, rotationState);
            case S:
                return new STetramino(center, rotationState);
            case Z:
                return new ZTetramino(center, rotationState);
            default:
                throw new IllegalStateException("No tetramino Shape");
        }
    }

    /**
     * Returns an array of every point of a tetramino, factoring in rotation state.
     * After implementing createTetramino [not quite a factory pattern, but similar],
     * This is the only truly abstract function
     *
     * All Rotation states are directly sourced from <a href="https://tetris.wiki/Super_Rotation_System">the Tetris Guidelines</a>
     * @return Point[] of the tetramino.
     */
    abstract public Point[] getShapePoints();

}
