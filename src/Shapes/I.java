package Shapes;


import Logic.Point;
import Logic.Tetramino;
import javafx.scene.paint.Color;

public class I extends Tetramino {

    private Point[][] shape;
    public final String shapeName = "I";

    public I(){
        Color c = getRandomColor();
        shape = new Point[][]{{new Point(c, true)},
                              {new Point(c, true)},
                              {new Point(c, true)},
                              {new Point(c, true)}};
    }

    @Override
    public Point[][] getShape(){
        return shape;
    };


}
