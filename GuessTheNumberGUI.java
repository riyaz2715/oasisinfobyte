import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGUI {
    private int numberToGuess;
    private int attempts;
    private final int maxAttempts = 10;
    private int score = 0;

    private JFrame frame;
    private JLabel messageLabel;
    private JTextField inputField;
    private JButton guessButton;
    private JButton playAgainButton;
    private JLabel scoreLabel;
    private JLabel titleLabel;

    public GuessTheNumberGUI() {
        frame = new JFrame("Guess The Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen mode
        frame.setLayout(new BorderLayout());

        // Set background image
        JLabel background = new JLabel(new ImageIcon("background.jpg"));
        frame.setContentPane(background);
        frame.setLayout(new BorderLayout());

        titleLabel = new JLabel("Guess The Number Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(153, 153, 153));
        frame.add(titleLabel, BorderLayout.NORTH);

        messageLabel = new JLabel("You have 10 attempts to guess a number between 1 and 100.", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        frame.add(messageLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setOpaque(false); // Make panel transparent

        inputField = new JTextField(10);
        inputPanel.add(inputField);

        guessButton = new JButton("Guess");
        guessButton.setPreferredSize(new Dimension(150, 50)); // Reduce button size
        guessButton.addActionListener(new GuessButtonListener());
        inputPanel.add(guessButton);

        // Add ActionListener to inputField to trigger guess action on Enter key press
        inputField.addActionListener(new GuessButtonListener());

        playAgainButton = new JButton("Play Again");
        playAgainButton.setPreferredSize(new Dimension(150, 50)); // Reduce button size
        playAgainButton.addActionListener(new PlayAgainButtonListener());
        playAgainButton.setVisible(false);
        inputPanel.add(playAgainButton);

        frame.add(inputPanel, BorderLayout.SOUTH);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        frame.add(scoreLabel, BorderLayout.WEST);

        frame.setVisible(true);

        startNewGame();
    }

    private void startNewGame() {
        Random rand = new Random();
        numberToGuess = rand.nextInt(100) + 1;
        attempts = 0;
        messageLabel.setText("You have 10 attempts to guess a number between 1 and 100.");
        inputField.setEnabled(true);
        guessButton.setEnabled(true);
        playAgainButton.setVisible(false);
        inputField.setText("");
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(inputField.getText());
                attempts++;

                if (guess == numberToGuess) {
                    messageLabel.setText("Congratulations! You guessed the number (" + numberToGuess + ") correctly in " + attempts + " attempts.");
                    score += maxAttempts - attempts + 1; // Higher score for fewer attempts
                    endGame();
                } else if (guess < numberToGuess) {
                    messageLabel.setText("Too low! Try again. Attempts left: " + (maxAttempts - attempts));
                } else {
                    messageLabel.setText("Too high! Try again. Attempts left: " + (maxAttempts - attempts));
                }

                if (attempts == maxAttempts && guess != numberToGuess) {
                    messageLabel.setText("Sorry, you've used all your attempts. The correct number was: " + numberToGuess);
                    endGame();
                }

                inputField.setText("");
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid input. Please enter a valid number.");
                inputField.setText("");
            }
        }
    }

    private class PlayAgainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startNewGame();
        }
    }

    private void endGame() {
        scoreLabel.setText("Score: " + score);
        inputField.setEnabled(false);
        guessButton.setEnabled(false);
        playAgainButton.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessTheNumberGUI::new);
    }
}

