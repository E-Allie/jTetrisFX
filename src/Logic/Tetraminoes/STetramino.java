package Logic.Tetraminoes;

import Logic.Point;
import javafx.scene.paint.Color;

public class STetramino extends Tetramino {

    public STetramino(Point center) {
        super(Color.GREEN, center, RotationState.O, TetraminoShape.S);
    }

    public STetramino(Point center, RotationState rotationState) {
        super(Color.GREEN, center, rotationState, TetraminoShape.S);
    }

    /**
     * @see Tetramino#getShapePoints()
     */
    @Override
    public Point[] getShapePoints() {
        switch (getRotationState()) {
            case O:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getAbove(), getCenter().getAbove().getRight()};
            case R:
                return new Point[]{getCenter(), getCenter().getAbove(), getCenter().getRight(), getCenter().getRight().getBelow()};
            case TWO:
                return new Point[]{getCenter(), getCenter().getRight(), getCenter().getBelow(), getCenter().getBelow().getLeft()};
            case L:
                return new Point[]{getCenter(), getCenter().getBelow(), getCenter().getLeft(), getCenter().getLeft().getAbove()};

            //Necessary to Compile
            default:
                throw new IllegalStateException();
        }
    }
}
