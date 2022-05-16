package Logic.Tetraminoes;

import Logic.Point;
import java.awt.Color;

import java.io.Serializable;

/**
 * The abstract tetramino.
 */
public abstract class Tetramino implements Serializable {

    /**
     * A total constructor for the tetramino.
     * @param color
     * @param center
     * @param rotationState
     * @param shape
     */
    public Tetramino(Color color, Point center, RotationState rotationState, TetraminoShape shape) {
        this.color = color;
        this.center = center;
        this.rotationState = rotationState;
        this.shape = shape;
    }


    private final Color color;
    private final Point center;


    /**
     * All Possible tetramino shapes.
     */
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

    /**
     * The Rotation state of a tetramino!
     * Can take 1 of 4 values [besides null], and implements wrapping.
     */
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

    /**
     * Returns a tetramino with a new center, using the shape and rotation of the caller tetramino.
     * @param row A new row as integer.
     * @param col A new column as integer.
     * @return A new, identical tetramino with updated center.
     */
    public Tetramino newCenter(int row, int col) {
        return createTetramino(shape, new Point(getColor(), row, col), rotationState);
    };


    /**
     * Yields a new tetramino that is clockwise rotated.
     * @return The rotated Tetramino [by means of updating the RotationState]
     */
    public Tetramino rotateClockwise() {
        return createTetramino(shape, center, rotationState.rotateClockwise());
    };

    /**
     * Yields a new tetramino that is counter-clockwise rotated.
     * @return The rotated Tetramino [by means of updating the RotationState]
     */
    public Tetramino rotateCounterclockwise(){
        return createTetramino(shape, center, rotationState.rotateCounterClockwise());
    }

    /**
     * Yields a new tetramino with center shifted down 1.
     * @return A new tetramino with center shifted.
     */
    public Tetramino moveDown() {
        return createTetramino(shape, center.getBelow(), rotationState);
    };

    /**
     * Yields a new tetramino with center shifted left 1.
     * @return A new tetramino with center shifted.
     */
    public Tetramino moveLeft() {
        return createTetramino(shape, center.getLeft(), rotationState);
    }

    /**
     * Yields a new tetramino with center shifted right 1.
     * @return A new tetramino with center shifted.
     */
    public Tetramino moveRight() {
        return createTetramino(shape, center.getRight(), rotationState);
    }


    /**
     * Creates a tetramino [or rather, a subclass of it] with Given Shape, Center, and rotation state.
     * @param shape The Tetramino Type/Shape
     * @param center The Center as a Point
     * @param rotationState The rotation state of the tetramino
     * @return A Tetramino with the input properties
     */
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
