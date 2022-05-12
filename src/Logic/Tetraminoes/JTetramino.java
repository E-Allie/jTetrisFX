package Logic.Tetraminoes;

import Logic.Point;
import javafx.scene.paint.Color;

public class JTetramino extends Tetramino {

    public JTetramino(Point center) {
        super(Color.BLUE, center, RotationState.O, TetraminoShape.J);
    }

    public JTetramino(Point center, RotationState rotationState) {
        super(Color.BLUE, center, rotationState, TetraminoShape.J);
    }

    /**
     * @see Tetramino#getShapePoints()
     */
    @Override
    public Point[] getShapePoints() {
        switch (getRotationState()) {
            case O:
                return new Point[]{getCenter(), getCenter().getRight(), getCenter().getLeft(), getCenter().getLeft().getAbove()};
            case R:
                return new Point[]{getCenter(), getCenter().getBelow(), getCenter().getAbove(), getCenter().getAbove().getRight()};
            case TWO:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getRight(), getCenter().getRight().getBelow()};
            case L:
                return new Point[]{getCenter(), getCenter().getAbove(), getCenter().getBelow(), getCenter().getBelow().getLeft()};

            //Necessary to Compile
            default:
                throw new IllegalStateException();
        }
    }
}
