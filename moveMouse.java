import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class moveMouse {
    private static boolean running = false;
    private static Point clickPoint = new Point(0, 0);
    private static final int CLICK_DELAY = 10000; // Click every 5 seconds
    private static Robot robot;
    private static JFrame frame;
    private static JLabel coordinatesLabel;

    public static void main(String[] args) throws AWTException {
        robot = new Robot();
        frame = new JFrame("Mouse Mover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        frame.add(startButton);
        frame.add(stopButton);

        startButton.addActionListener(e -> startMouseMover());
        stopButton.addActionListener(e -> running = false);

        // Global mouse listener to capture clicks anywhere on the screen

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) event;
                if (me.getID() == MouseEvent.MOUSE_CLICKED) {
                    clickPoint = me.getLocationOnScreen();
                    System.out.println("mouse point at: {}"+clickPoint);
                    coordinatesLabel = new JLabel("Coordinates:{}"+clickPoint);
                    frame.add(coordinatesLabel);
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        frame.setVisible(true);
    }

    private static void startMouseMover() {
        running = true;
        new Thread(() -> {
            try {
                while (running) {
                    //Point point = MouseInfo.getPointerInfo().getLocation();
                    robot.mouseMove(clickPoint.x, clickPoint.y);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(CLICK_DELAY);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
