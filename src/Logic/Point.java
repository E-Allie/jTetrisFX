package Logic;

import java.awt.Color;

import java.io.Serializable;
import java.util.Objects;

/**
 * A point Structure.
 * Keeps internal track of its location in a hypothetical 2D Array, as well as occupation state and color.
 */
public class Point implements Serializable {

    /**
     * Constructs a default, black, unoccupied, point.
     */
    public Point() {
        c = Color.BLACK;
        occupied = false;
        row = 0;
        col = 0;
    }

    /**
     * Constructs a specific, occupied point on the grid, where color can be null.
     * @param row Row of point.
     * @param col Column of point.
     */
    public Point(int row, int col) {
        occupied = true;
        this.row = row;
        this.col = col;
    }

    /**
     * Constructs a specific, occupied point on the grid.
     * @param c Color of point.
     * @param row Row of point.
     * @param col Column of point.
     */
    public Point(Color c, int row, int col) {
        this.c = c;
        occupied = true;
        this.row = row;
        this.col = col;
    }

    private Color c;
    private final boolean occupied;
    private int row;
    private int col;

    /**
     * Helper function to get point color
     * @return The color of the point
     */
    public Color getColor() {
        return c;
    }

    /**
     * Helper function to set point color
     */
    public void setColor(Color c) {
        this.c = c;
    }

    /**
     * Helper function to get if point is occupied.
     * @return If point is occupied.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Helper Functions to get or set the internal integer row or col of Point.
     * @return A point's internal row or col.
     */
    public int getRow() {
        return row;
    }

    /**
     * @see #getRow()
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @see #getRow()
     */
    public int getCol() {
        return col;
    }

    /**
     * @see #getRow()
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Helper Functions to get adjacent Points.
     * Useful for constructing tetraminoes and collision detection.
     * @return Adjacent point.
     */
    public Point getAbove() {
        return new Point(this.c, this.row - 1, this.col);
    }

    /**
     * @see #getAbove()
     */
    public Point getBelow() {
        return new Point(this.c, this.row + 1, this.col);
    }

    /**
     * @see #getAbove()
     */
    public Point getLeft() {
        return new Point(this.c, this.row, this.col - 1);
    }

    /**
     * @see #getAbove()
     */
    public Point getRight() {
        return new Point(this.c, this.row, this.col + 1);
    }

    /**
     * Boolean Equality Override.
     * Color Doesn't matter, but if 2 points occupy the same location, they are equivalent.
     * @param o Another Object, optimally a Point.
     * @return True or False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return occupied == point.occupied && row == point.row && col == point.col;
    }

    /**
     * Recommended to be overridden with equality
     * @return The Point hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(occupied, row, col);
    }

    @Override
    public String toString() {
        return "Point{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
