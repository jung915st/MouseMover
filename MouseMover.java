
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
//ToDo: add function to let user setup delay timer
public class MouseMover {

    private static boolean running = false;
    private static int clickX = 0;
    private static int clickY = 0;
    private static final int CLICK_DELAY = 5000; // Click every 10 seconds
    private static Robot robot;

    public MouseMover() {
        // Setup the UI
        JFrame frame = new JFrame("Mouse Clicker");
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 200);

        JPanel timepanel = new JPanel();
        JLabel timelabel = new JLabel("Enter time interval in seconds:");
        timepanel.add(timelabel);
        JTextField timeInput = new JTextField(5);
        timepanel.add(timeInput);
        String labeltext ="<html>move this window onto the position you wish,<br>Double-click anywhere to set click position.</html>";
        JLabel statusLabel = new JLabel("Double-click anywhere to set click position.");
        JPanel buttonPanel = new JPanel();
        JLabel infoLabel = new JLabel(labeltext);


        JButton startButton = new JButton("Start Clicking");
        JButton stopButton = new JButton("Stop Clicking");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        frame.add(infoLabel, BorderLayout.NORTH);
        //frame.add(statusLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(timepanel, BorderLayout.CENTER);

        // Mouse adapter for double click to set position
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    clickX = MouseInfo.getPointerInfo().getLocation().x;
                    clickY = MouseInfo.getPointerInfo().getLocation().y;
                    infoLabel.setText("Click position set to: [" + clickX + ", " + clickY + "]");
                    System.out.println("mouse position: "+clickX+","+clickY);
                }
            }
        });

        // Start button action listener
        startButton.addActionListener(e -> {
            try {
                int timeInterval = Integer.parseInt(timeInput.getText());
                if (timeInterval > 0 && !running) {
                    running = true;
                    startClicking(timeInterval);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a positive integer.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid integer.");
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
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void startClicking(int interval) {
        Thread clickerThread = new Thread(() -> {
            while (running) {
                robot.mouseMove(clickX, clickY);
                Integer clickx = clickX;
                Integer clicky = clickY;
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                System.out.println("coordinate: "+clickx+","+clicky);
                try {
                    Thread.sleep((long) interval*1000);
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

    public static void main(String[] args) {
        new MouseMover();
    }
}
