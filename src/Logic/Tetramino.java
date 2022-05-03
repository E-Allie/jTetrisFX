package Logic;

import javafx.scene.paint.Color;

import java.util.Random;

public abstract class Tetramino {
    private Point[][] shape;
    public String shapeName;

    /**
     * All possible colors for tetraminoes.
     */
    public final Color[] colorList = {Color.BLUE, Color.GREEN, Color.PURPLE, Color.RED};

    /**
     * Helper function to get the points of any shape
     * @return The smallest array containing the shape.
     */
    public abstract Point[][] getShape();

    /**
     * Helper function to get a random color from a predetermined color list.
     * @return A color.
     */
    public final Color getRandomColor() {
        Random rand = new Random();
        return colorList[rand.nextInt(colorList.length)];
    }
}
