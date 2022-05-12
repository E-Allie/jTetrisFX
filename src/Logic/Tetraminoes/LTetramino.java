package Logic.Tetraminoes;

import Logic.Point;
import javafx.scene.paint.Color;

public class LTetramino extends Tetramino {

    public LTetramino(Point center) {
        super(Color.ORANGE, center, RotationState.O, TetraminoShape.L);
    }

    public LTetramino(Point center, RotationState rotationState) {
        super(Color.ORANGE, center, rotationState, TetraminoShape.L);
    }

    /**
     * @see Tetramino#getShapePoints()
     */
    @Override
    public Point[] getShapePoints() {
        switch (getRotationState()) {
            case O:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getRight(), getCenter().getRight().getAbove()};
            case R:
                return new Point[]{getCenter(), getCenter().getAbove(), getCenter().getBelow(), getCenter().getBelow().getRight()};
            case TWO:
                return new Point[]{getCenter(), getCenter().getRight(), getCenter().getLeft(), getCenter().getLeft().getBelow()};
            case L:
                return new Point[]{getCenter(), getCenter().getBelow(), getCenter().getAbove(), getCenter().getAbove().getLeft()};

            //Necessary to Compile
            default:
                throw new IllegalStateException();
        }
    }
}
