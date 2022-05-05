package Logic;

//For defining all tetraminoes, and algorithms on rotation, we explicitly refer to https://tetris.wiki/Super_Rotation_System
//There is an official Tetris Guideline which we will rely upon.

import javafx.scene.paint.Color;

public abstract class Tetramino {
    private Point[] shapePoints;
    private Point center;
    private String shapeName;

    /**
     * All tetraminoes have exactly 4 possible rotation states, 0-3
     * 0 is the base state, and every increment up is a clockwise rotation.
     */
    private int rotationState;

    private Color color;

    public final void rotateClockwise() {
        rotationState = (rotationState+1 % 4);
    }

    public final void rotateCounterclockwise() {
        rotationState = (rotationState-1 % 4);
    }

    /**
     * Yields a new tetramino downshifted one, IGNORING COLLISION. CALLER RESPONSIBILITY.
     * @return The downshifted tetramino.
     */
    public abstract Tetramino moveDown();

    /**
     * Helper function to find the points of a tetramino, CONSIDERING its rotation state.
     * @return A Point[], that should represent the correct structure.
     */
    public abstract Point[] calcPoints();

    public final String getShapeName() {
        return shapeName;
    }

    public final Point getCenter() {
        return center;
    }

    /**
     * Helper function to get the points of any shape
     * @return A Point[] describing every point.
     */
    public final Point[] getShapePoints() {
        return shapePoints;
    };


}
