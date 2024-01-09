
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MoveMouse {
    private static boolean running = false;
    private static Point clickPoint = new Point(0, 0);
    private static final int CLICK_DELAY = 10000; // Click every 10 seconds
    private static Robot robot;

    public MoveMouse() {
        // Setup the UI
        JFrame frame = new JFrame("Mouse Clicker");
        frame.setLayout(new BorderLayout());
        frame.setSize(300, 150);

        JLabel instructionLabel = new JLabel("Click on this window to set the click position.");
        JLabel statusLabel = new JLabel("Click position not set.");
        JPanel buttonPanel = new JPanel();

        JButton startButton = new JButton("Start Clicking");
        JButton stopButton = new JButton("Stop Clicking");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        frame.add(instructionLabel, BorderLayout.NORTH);
        frame.add(statusLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Mouse listener to capture click position outside of buttons
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isClickOnButton(frame, e)) {
                    clickPoint = e.getPoint();
                    statusLabel.setText("Click position set to: " + clickPoint);
                }
            }
        });

        // Start button action listener
        startButton.addActionListener(e -> {
            if (!running) {
                running = true;
                startClicking();
            }
        });

        // Stop button action listener
        stopButton.addActionListener(e -> {
            if (running) {
                stopClicking();
                running = false;
            }
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize robot for control
        try {
            robot = a new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void startClicking() {
        Thread clickerThread = new Thread(() -> {
            while (running) {
                robot.mouseMove(clickPoint.x, clickPoint.y);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                try {
                    Thread.sleep(CLICK_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        clickerThread.start();
    }

    private void stopClicking() {
        running = false;
    }

    private boolean isClickOnButton(JFrame frame, MouseEvent e) {
        return frame.getContentPane().getComponentAt(e.getPoint()) instanceof JButton;
    }

    public static void main(String[] args) {
        new MoveMouse();
    }
}
