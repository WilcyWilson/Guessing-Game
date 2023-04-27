import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessNumber extends JFrame implements ActionListener {

    private static int secondsLeft = 60;

    private static int guess = 5;

    private final int setRandomNumber = 100;
    Timer timer;
    private final JPanel panel = new JPanel(new BorderLayout());
    private final JPanel panelButtons = new JPanel(new FlowLayout());
    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField fieldBox = new JTextField(10);
    private final JButton tryButton = new JButton("Try");
    private final JButton resetButton = new JButton("Reset");
    private final JButton quitButton = new JButton("Quit");
    private final JLabel comment = new JLabel("Guess the number please");
    private final JLabel secondsLabel = new JLabel("Countdown: " + secondsLeft);
    private final JLabel numberOfTries = new JLabel("Number of tries left: " + guess);
    private int randomNumber;

    public GuessNumber() {
        super("Guess Number");
        add(buildWindow());
        randomNumber = new Random().nextInt(setRandomNumber) + 1;
        timer = new Timer(1000, e -> {
            secondsLeft--;
            secondsLabel.setText("Countdown: " + secondsLeft);
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
        panel.add(new JLabel("Guess a number from 1 to " + setRandomNumber), BorderLayout.NORTH);
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
        setGuess();
        setBackgroundColor(UIManager.getColor("Panel.background"));
        numberOfTries.setText("Number of tries left: " + guess);
        comment.setText("Guess the number please");
        setSecondsLeft();
        timer.start();
    }

    private void setSecondsLeft(){
        secondsLeft = 60;
    }

    private void setGuess(){
        guess = 5;
    }

    private void resetRandomNumber() {
        randomNumber = new Random().nextInt(setRandomNumber) + 1;
    }

    private void compareResult() {
        int userInput;
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
        if (Difference <= 5 && guess > 0) {
            comment.setText("Difference is less than or equal to 5. You are very close, keep trying");
        }
    }

    private void checkGuess(int guess) {
        if (guess <= 0) {
            comment.setText("Click Reset Button to reset the game");
            timer.stop();
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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            GuessNumber g = new GuessNumber();
            g.setSize(800,300);
            g.setVisible(true);
        });
    }

}
