package Shapes;


import Logic.Point;
import Logic.Tetramino;
import javafx.scene.paint.Color;

public class I extends Tetramino {


    private Point[] shapePoints;
    private Point center;

    public final String shapeName = "I";

    private int rotationState;

    private Color color = Color.LIGHTBLUE;

    public I(Point center){
        this.center = center;
        rotationState = 0;
        shapePoints = calcPoints();
    }

    public I(Point center, int rotationState){
        this.center = center;
        this.rotationState = rotationState;
        shapePoints = calcPoints();
    }


    @Override
    public I moveDown() {
        return new I(center.getBelow(), rotationState);
    }

    @Override
    public Point[] calcPoints() {
        Point[] result = new Point[4];
        switch (rotationState) {
            case 0 :
                result = new Point[]{center.getLeft(),center,center.getRight(),center.getRight().getRight()};
            case 1 :
                result = new Point[]{center.getAbove(),center,center.getBelow(),center.getBelow().getBelow()};
            case 2 :
                result = new Point[]{center.getLeft().getLeft(),center.getLeft(),center,center.getRight()};
            case 3 :
                result = new Point[]{center.getAbove().getAbove(),center.getAbove(),center,center.getBelow()};
        }
        return result;
    }
}
