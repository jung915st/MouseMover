
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;

public class MouseMover {

    private static boolean running = false;
    private static int clickX = 0;
    private static int clickY = 0;
    private static JFrame frame;
    private static Robot robot;

    public static void main(String[] args) throws AWTException {
        robot = new Robot();
        frame = new JFrame("Mouse Mover");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("Enter time interval in seconds:");
        panel.add(label);

        // Text field for user to input time interval
        JTextField timeInput = new JTextField(5);
        panel.add(timeInput);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int timeInterval = Integer.parseInt(timeInput.getText());
                    if (timeInterval > 0) {
                        running = true;
                        clickX = MouseInfo.getPointerInfo().getLocation().x;
                        clickY = MouseInfo.getPointerInfo().getLocation().y;
                        startClicking(timeInterval);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a positive integer.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid integer.");
                }
            }
        });
        panel.add(startButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = false;
            }
        });
        panel.add(stopButton);

        frame.setVisible(true);
    }

    private static void startClicking(int interval) {
        new Thread(() -> {
            while (running) {
                robot.mouseMove(clickX, clickY);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                try {
                    Thread.sleep(interval * 1000); // Convert seconds to milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
