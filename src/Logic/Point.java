package Logic;

import javafx.scene.paint.Color;

public class Point {

    /**
     * Constructs a default, black, unoccupied, point.
     */
    public Point() {
        c = Color.BLACK;
        occupied = false;
    }

    /**
     * Constructs a point on the Grid.
     * May be occupied or unoccupied.
     * May be expanded in the future.
     * @param c Color of point.
     * @param occupied Whether the point is occupied.
     */
    public Point(Color c, boolean occupied) {
        this.c = c;
        this.occupied = occupied;

    }

    private Color c;
    private boolean occupied;

    /**
     * Helper function to get point color
     * @return The color of the point
     */
    public Color getColor() {
        return c;
    }

    /**
     * Helper function to get if point is occupied.
     * @return If point is occupied.
     */
    public boolean isOccupied() {
        return occupied;
    }
}
