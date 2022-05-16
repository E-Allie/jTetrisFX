package Logic.Tetraminoes;

import Logic.Point;
import java.awt.Color;

/**
 * The Z Tetramino.
 */
public class ZTetramino extends Tetramino {

    public ZTetramino(Point center) {
        super(Color.RED, center, RotationState.O, TetraminoShape.Z);
    }

    public ZTetramino(Point center, RotationState rotationState) {
        super(Color.RED, center, rotationState, TetraminoShape.Z);
    }

    /**
     * @see Tetramino#getShapePoints()
     */
    @Override
    public Point[] getShapePoints() {
        switch (getRotationState()) {
            case O:
                return new Point[]{getCenter(), getCenter().getRight(), getCenter().getAbove(), getCenter().getAbove().getLeft()};
            case R:
                return new Point[]{getCenter(), getCenter().getBelow(), getCenter().getRight(), getCenter().getRight().getAbove()};
            case TWO:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getBelow(), getCenter().getBelow().getRight()};
            case L:
                return new Point[]{getCenter(), getCenter().getAbove(), getCenter().getLeft(), getCenter().getLeft().getBelow()};

            //Necessary to Compile
            default:
                throw new IllegalStateException();
        }
    }
}