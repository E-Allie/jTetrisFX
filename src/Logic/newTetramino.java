package Logic;

import javafx.scene.paint.Color;

public interface newTetramino {

    TetraminoShape getShapeType();
    Point getCenter();

    Color getColor();

    /**
     * Helper function to get the points of any shape
     * @return A Point[] describing every point.
     */
    Point[] getShapePoints();

    newTetramino rotateClockwise();
    newTetramino rotateCounterclockwise();

    /**
     * Yields a new tetramino downshifted one, IGNORING COLLISION. CALLER RESPONSIBILITY.
     * @return The downshifted tetramino.
     */
    newTetramino moveDown();
    /**
     * Yields a new tetramino left-shifted one, IGNORING COLLISION. CALLER RESPONSIBILITY.
     * @return The downshifted tetramino.
     */
    newTetramino moveLeft();
    /**
     * Yields a new tetramino right-shifted one, IGNORING COLLISION. CALLER RESPONSIBILITY.
     * @return The downshifted tetramino.
     */
    newTetramino moveRight();

    /**
     * Helper function that will yield a new tetramino of the given center.
     * @return
     */
    newTetramino newCenter(int row, int col);

    enum TetraminoShape {
        I, L, O
    }

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




}
