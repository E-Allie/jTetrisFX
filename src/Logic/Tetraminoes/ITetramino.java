package Logic.Tetraminoes;

import Logic.Point;
import javafx.scene.paint.Color;

import java.util.Arrays;

import static Logic.Tetraminoes.Tetramino.TetraminoShape.I;

public class ITetramino extends Tetramino {

    public ITetramino(Point center, RotationState rotationState) {
        setCenter(center);
        setRotationState(rotationState);
    }


    private TetraminoShape shape = I;
    private Color color = Color.LIGHTBLUE;




    /**
     * Get's the points that comprise the tetramino, factoring in rotation state.
     * @return A Point[] of the tetramino
     */
    @Override
    public Point[] getShapePoints() {
        switch (getRotationState()) {
            case O :
                return new Point[]{getCenter().getLeft(),getCenter(),getCenter().getRight(),getCenter().getRight().getRight()};
            case R :
                return new Point[]{getCenter().getAbove(),getCenter(),getCenter().getBelow(),getCenter().getBelow().getBelow()};
            case TWO :
                return new Point[]{getCenter().getLeft().getLeft(),getCenter().getLeft(),getCenter(),getCenter().getRight()};
            case L :
                return new Point[]{getCenter().getAbove().getAbove(),getCenter().getAbove(),getCenter(),getCenter().getBelow()};

            //Fun fact, unless this default is here, Java will not be able to confirm that this expression always returns.
            //However, the enum can ONLY be the above cases. This switch, ignoring the default, IS exhaustive.

            default:
                //TODO: I guess ergonomically handle this error. Make it halt the gameloop or something i don't know.
                throw new IllegalStateException("If you end up here, I'll be impressed.");
        }
    }

    @Override
    public Tetramino rotateClockwise() {
        return new ITetramino(getCenter(), getRotationState().rotateClockwise());
    }

    @Override
    public Tetramino rotateCounterclockwise() {
        return new ITetramino(getCenter(), getRotationState().rotateCounterClockwise());
    }

    @Override
    public Tetramino moveDown() {
        return new ITetramino(getCenter().getBelow(), getRotationState());
    }

    @Override
    public Tetramino moveLeft() {
        return new ITetramino(getCenter().getLeft(), getRotationState());
    }
    @Override
    public Tetramino moveRight() {
        return new ITetramino(getCenter().getRight(), getRotationState());
    }

    @Override
    public Tetramino newCenter(int row, int col) {
        return new ITetramino(new Point(color, row, col), RotationState.O);
    }

    @Override
    public String toString() {
        return "ITetramino{" +
                "color=" + color +
                ", getCenter()=" + getCenter() +
                ", shapePoints=" + Arrays.toString(getShapePoints()) +
                ", getRotationState()=" + getRotationState() +
                '}';
    }
}
