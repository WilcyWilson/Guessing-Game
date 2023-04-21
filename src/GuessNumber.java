import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessNumber extends JFrame implements ActionListener {

    private static int secondsLeft = 5;
    Timer timer;
    long lastUpdate;
    private static final long serialVersionUID = 1L;
    private JPanel panel = new JPanel(new BorderLayout());
    private JPanel panelButtons = new JPanel(new FlowLayout());
    private JPanel panelBottom = new JPanel(new BorderLayout());
    private JTextField fieldBox = new JTextField(10);
    private JButton tryButton = new JButton("Try");
    private JButton resetButton = new JButton("Reset");
    private JButton quitButton = new JButton("Quit");
    private JLabel comment = new JLabel("Guess the number please");
    private JLabel secondsLabel = new JLabel("Countdown: " + secondsLeft);
    private static int guess = 3;
    private JLabel numberOfTries = new JLabel("Number of tries left: " + guess);
    private int randomNumber;

    public GuessNumber() {
        super("Guess Number");
        add(buildWindow());
        randomNumber = new Random().nextInt(1000) + 1;
        timer = new Timer(1000, e -> {
            secondsLeft--;
            secondsLabel.setText("Countdown: " + secondsLeft);
            System.out.println("Seconds left: " + secondsLeft);
            if (secondsLeft == 0) {
                setBackgroundColor(Color.red);
                JOptionPane.showMessageDialog(null, "Sorry time's up", "You Lose !!!!",
                        JOptionPane.INFORMATION_MESSAGE);
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
        tryButton.addActionListener(this);
        quitButton.addActionListener(this);
        resetButton.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
    }

    private JComponent buildWindow() {
        panelButtons.add(tryButton);
        panelButtons.add(quitButton);
        panelButtons.add(resetButton);
        panelBottom.add(panelButtons, BorderLayout.NORTH);
        panelBottom.add(numberOfTries, BorderLayout.EAST);
        panelBottom.add(comment, BorderLayout.WEST);
        panelBottom.add(secondsLabel, BorderLayout.SOUTH);
        panel.add(new JLabel("Guess a number from 1 to 1000"), BorderLayout.NORTH);
        panel.add(fieldBox, BorderLayout.CENTER);
        panel.add(panelBottom, BorderLayout.SOUTH);
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(tryButton)) {
            compareResult();
        } else if (obj.equals(resetButton)) {
            restart();
        } else {
            System.exit(0);
        }
    }

    private void restart() {
        guess = 3;
        setBackgroundColor(UIManager.getColor("Panel.background"));
        numberOfTries.setText("Number of tries left: " + guess);
        comment.setText("Guess the number please");
        secondsLeft = 5;
        timer.start();
    }

    private void resetRandomNumber() {
        randomNumber = new Random().nextInt(1000) + 1;
    }

    private void compareResult() {
        int userInput = 0;
        int diff;
        int Difference = 0;
        if (guess <= 0) {
            setBackgroundColor(Color.red);
            JOptionPane.showMessageDialog(null, "Sorry you lost the game", "You Lose !!!!",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            userInput = Integer.parseInt(fieldBox.getText().trim());
        } catch (Exception ex) {
            comment.setText("Enter a valid Number");
            return;
        }
        if (userInput == randomNumber) {
            setBackgroundColor(Color.green);
            JOptionPane.showMessageDialog(null, "Congratulations you got the correct answer",
                    "Result", JOptionPane.INFORMATION_MESSAGE);
            restart();
            resetRandomNumber();
            return;
        }
        if (userInput > randomNumber) {
            guess -= 1;
            numberOfTries.setText("Number of tries left: " + guess);
            comment.setText("Number is too high");
            checkGuess(guess);
            diff = userInput - randomNumber;
            Difference = Math.abs(diff);
        }
        if (userInput < randomNumber) {
            guess -= 1;
            numberOfTries.setText("Number of tries left: " + guess);
            comment.setText("Number is too low");
            checkGuess(guess);
            diff = userInput - randomNumber;
            Difference = Math.abs(diff);
        }
        if (Difference <= 10) {
            comment.setText("Difference is less than or equal to 10. You are very close, keep trying");
        }
    }

    private void checkGuess(int guess) {
        if (guess <= 0) {
            comment.setText("Click Reset Button to reset the game");
            setBackgroundColor(Color.red);
            JOptionPane.showMessageDialog(null, "Sorry you lost the game", "You Lose !!!!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setBackgroundColor(Color color) {
        panel.setBackground(color);
        panelBottom.setBackground(color);
        panelButtons.setBackground(color);
    }

    void resume() {
        // Restore the time we're counting down from and restart the timer.
        lastUpdate = System.currentTimeMillis();
        timer.start(); // Start the timer
    }

//    void pause() {
//        // Subtract elapsed time from the remaining time and stop timing
//        long now = System.currentTimeMillis();
//        remaining -= (now - lastUpdate);
//        timer.stop(); // Stop the timer
//    }

//    void updateDisplay() {
//        timer.start();
//        panelBottom.add(new JLabel("Countdown: " + secondsLeft), BorderLayout.SOUTH);
//        secondsLeft--;
//        if (secondsLeft == 0) {
//            timer.stop();
//        }
//    }

    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GuessNumber g = new GuessNumber();
                g.setVisible(true);
            }
        });
    }

}
