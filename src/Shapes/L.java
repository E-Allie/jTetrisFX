package Shapes;


import Logic.Point;
import Logic.Tetramino;
import javafx.scene.paint.Color;

public class L extends Tetramino {

    private Point[] shapePoints;
    public final String shapeName = "L";

    public L(){
        Color c = Color.ORANGE;
        shapePoints = null;
    }

    @Override
    public Tetramino moveDown() {
        return null;
    }

    @Override
    public Point[] calcPoints() {
        return new Point[0];
    }
}
