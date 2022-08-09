import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class MouseMover {
    public static int SECONDS = 5000;
    public static final int MAX_Y = 400;
    public static final int MAX_X = 400;

    public static void main(String... args) throws Exception {
        Robot robot = new Robot();
        Random random = new Random();
        int mask1 = InputEvent.ALT_DOWN_MASK;
        int mask2 = InputEvent.BUTTON1_DOWN_MASK;
        while (true) {
            Point point = MouseInfo.getPointerInfo().getLocation();
            //Double pointx = point.getX();
            //Double pointy = point.getY();
            Double pointx = 1124.0;
            Double pointy = 430.0;

            System.out.println("mouse location:("+pointx+","+pointy+")");

            //robot.mouseMove(point.x, point.y);
            robot.mouseMove(pointx.intValue(), pointy.intValue());
            robot.mousePress(mask2);
            robot.mouseRelease(mask2);
            //robot.mouseMove(random.nextInt(MAX_X), random.nextInt(MAX_Y));
            Thread.sleep(SECONDS);
            
            //robot.mouseRelease(mask2);
        }
    }
}
