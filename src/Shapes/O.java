package Shapes;


import Logic.Point;
import Logic.Tetramino;
import javafx.scene.paint.Color;

public class O extends Tetramino {

    private Point[] shapePoints;
    public final String shapeName = "O";

    public O(){
        Color c = Color.YELLOW;
        shapePoints = new Point[]{new Point(c, 0, 0), new Point(c, 0, 1),
                            new Point(c, 1, 0), new Point(c, 1, 1)};
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