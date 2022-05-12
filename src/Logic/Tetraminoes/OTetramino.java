package Logic.Tetraminoes;

import Logic.Point;
import javafx.scene.paint.Color;

public class OTetramino extends Tetramino {

    public OTetramino(Point center) {
        super(Color.YELLOW, center, RotationState.O, TetraminoShape.O);
    }

    public OTetramino(Point center, RotationState rotationState) {
        super(Color.YELLOW, center, rotationState, TetraminoShape.O);
    }

    /**
     * @see Tetramino#getShapePoints()
     */
    @Override
    public Point[] getShapePoints() {
        switch (getRotationState()) {
            case O:
                return new Point[]{getCenter(), getCenter().getAbove(), getCenter().getRight(), getCenter().getRight().getAbove()};
            case R:
                return new Point[]{getCenter(), getCenter().getRight(), getCenter().getBelow(), getCenter().getRight().getBelow()};
            case TWO:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getBelow(), getCenter().getBelow().getLeft()};
            case L:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getAbove(), getCenter().getLeft().getAbove()};

            //Necessary to Compile
            default:
                throw new IllegalStateException();
        }
    }
}
