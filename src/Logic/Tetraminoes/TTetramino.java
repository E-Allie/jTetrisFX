package Logic.Tetraminoes;

import Logic.Point;
import javafx.scene.paint.Color;

public class TTetramino extends Tetramino {

    public TTetramino(Point center) {
        super(Color.PURPLE, center, RotationState.O, TetraminoShape.T);
    }

    public TTetramino(Point center, RotationState rotationState) {
        super(Color.PURPLE, center, rotationState, TetraminoShape.T);
    }

    /**
     * @see Tetramino#getShapePoints()
     */
    @Override
    public Point[] getShapePoints() {
        switch (getRotationState()) {
            case O:
                return new Point[]{getCenter(), getCenter().getAbove(), getCenter().getLeft(), getCenter().getRight()};
            case R:
                return new Point[]{getCenter(), getCenter().getRight(), getCenter().getAbove(), getCenter().getBelow()};
            case TWO:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getRight(), getCenter().getBelow()};
            case L:
                return new Point[]{getCenter(), getCenter().getLeft(), getCenter().getAbove(), getCenter().getBelow()};

            //Necessary to Compile
            default:
                throw new IllegalStateException();
        }
    }
}
