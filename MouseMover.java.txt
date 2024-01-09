import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class MouseMover {
    public static int SECONDS = 180000;
    public static int DELAY = 2000;
    public static final int MAX_Y = 900;
    public static final int MAX_X = 400;
    public static final int ORIGIN_X = 590;
    public static final int ORIGIN_Y = 610;
    public static final int BOUND_X = 1900;
    public static final int BOUND_Y = 900;

    public static void main(String... args) throws Exception {
        Robot robot = new Robot();
        Random random = new Random();
        //int mask1 = InputEvent.ALT_DOWN_MASK;
        int mask2 = InputEvent.BUTTON1_DOWN_MASK;
        while (true) {
            Point point = MouseInfo.getPointerInfo().getLocation();
            //Double pointx = point.getX();
            //Double pointy = point.getY();
            Double pointx = 1135.0;
            Double pointy = 402.0;
            //int x = random.nextInt(MAX_X);
            //int y = random.nextInt(MAX_Y);
            //int randomx = random.ints(ORIGIN_X, BOUND_X).findFirst().getAsInt();
            //int randomy= random.ints(ORIGIN_Y, BOUND_Y).findFirst().getAsInt();

            //robot.mouseMove(point.x, point.y);
            robot.mouseMove(pointx.intValue(), pointy.intValue());
            System.out.println("mouse location:("+pointx+","+pointy+")");
            Thread.sleep(DELAY);
            robot.mousePress(mask2);
            robot.mouseRelease(mask2);
            Thread.sleep(DELAY);
            robot.mousePress(mask2);
            robot.mouseRelease(mask2);
            //robot.mouseMove(randomx,randomy);
            //System.out.println("mouse location:("+randomx+","+randomy+")");
            //Thread.sleep(SECONDS);
            
            robot.mouseRelease(mask2);
        }
    }
}
