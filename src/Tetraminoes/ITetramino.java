package Tetraminoes;

import Logic.Point;
import Logic.Tetramino;
import Logic.newTetramino;
import javafx.scene.paint.Color;

import java.util.Arrays;

import static Logic.newTetramino.TetraminoShape.I;

public class ITetramino implements newTetramino {

    public ITetramino(Point center, RotationState rotationState) {
        this.center = center;
        this.rotationState = rotationState;
        shapePoints = getShapePoints();
    }

    /**
     * A static final method as every I tetramino will always be what it is.
     * There will never ever be shape mutation. That would be very VERY messy.
     */
    public static final TetraminoShape shape = I;
    private Color color = Color.LIGHTBLUE;

    @Override
    public Color getColor() {
        return color;
    }

    private Point center;
    private Point[] shapePoints;
    private RotationState rotationState;

    @Override
    public TetraminoShape getShapeType() {
        return shape;
    }

    @Override
    public Point getCenter() {
        return center;
    }

    /**
     * Get's the points that comprise the tetramino, factoring in rotation state.
     * @return A Point[] of the tetramino
     */
    @Override
    public Point[] getShapePoints() {
        switch (rotationState) {
            case O :
                return new Point[]{center.getLeft(),center,center.getRight(),center.getRight().getRight()};
            case R :
                return new Point[]{center.getAbove(),center,center.getBelow(),center.getBelow().getBelow()};
            case TWO :
                return new Point[]{center.getLeft().getLeft(),center.getLeft(),center,center.getRight()};
            case L :
                return new Point[]{center.getAbove().getAbove(),center.getAbove(),center,center.getBelow()};

            //Fun fact, unless this default is here, Java will not be able to confirm that this expression always returns.
            //However, the enum can ONLY be the above cases. This switch, ignoring the default, IS exhaustive.

            default:
                //TODO: I guess ergonomically handle this error. Make it halt the gameloop or something i don't know.
                throw new IllegalStateException("If you end up here, I'll be impressed.");
        }
    }

    @Override
    public newTetramino rotateClockwise() {
        return new ITetramino(center, rotationState.rotateClockwise());
    }

    @Override
    public newTetramino rotateCounterclockwise() {
        return new ITetramino(center, rotationState.rotateCounterClockwise());
    }

    @Override
    public newTetramino moveDown() {
        return new ITetramino(center.getBelow(), rotationState);
    }

    @Override
    public newTetramino moveLeft() {
        return new ITetramino(center.getLeft(), rotationState);
    }
    @Override
    public newTetramino moveRight() {
        return new ITetramino(center.getRight(), rotationState);
    }

    @Override
    public newTetramino newCenter(int row, int col) {
        return new ITetramino(new Point(color, row, col), RotationState.O);
    }

    @Override
    public String toString() {
        return "ITetramino{" +
                "color=" + color +
                ", center=" + center +
                ", shapePoints=" + Arrays.toString(shapePoints) +
                ", rotationState=" + rotationState +
                '}';
    }
}
