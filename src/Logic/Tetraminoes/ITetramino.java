package Logic.Tetraminoes;

import Logic.Point;
import java.awt.Color;

/**
 * The I Tetramino.
 */
public class ITetramino extends Tetramino {

    public ITetramino(Point center) {
        super(Color.cyan, center, RotationState.O, TetraminoShape.I);
    }

    public ITetramino(Point center, RotationState rotationState) {
        super(Color.cyan, center, rotationState, TetraminoShape.I);
    }

    /**
     * @see Tetramino#getShapePoints()
     */
    @Override
    public Point[] getShapePoints() {
        switch (getRotationState()) {
            case O:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getRight(), getCenter().getRight().getRight()};
            case R:
                return new Point[]{getCenter(), getCenter().getAbove(), getCenter().getBelow(), getCenter().getBelow().getBelow()};
            case TWO:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getRight(), getCenter().getLeft().getLeft()};
            case L:
                return new Point[]{getCenter(), getCenter().getAbove(), getCenter().getBelow(), getCenter().getAbove().getAbove()};

            //Necessary to compile.
            default:
                throw new IllegalStateException();
        }
    }
}
