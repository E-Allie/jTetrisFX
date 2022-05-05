package Logic.Movers;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.robot.Robot;

public class DemoModeMover implements Controller {

    public void move() {
        Robot jeff = new Robot();
        jeff.keyPress(KeyCode.LEFT);
    }

}
