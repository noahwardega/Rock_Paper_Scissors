import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame {

    private JButton rockButton, paperButton, scissorsButton, quitButton;
    private JTextArea resultsTextArea;
    private JLabel playerWinsLabel, computerWinsLabel, tiesLabel;
    private int playerWins, computerWins, ties;

    public RockPaperScissorsFrame() {
        super("Rock Paper Scissors Game");

        rockButton = createButton("Rock", "rock.png");
        paperButton = createButton("Paper", "paper.png");
        scissorsButton = createButton("Scissors", "scissors.png");
        quitButton = createButton("Quit", null);

        playerWinsLabel = new JLabel("Player Wins: 0");
        computerWinsLabel = new JLabel("Computer Wins: 0");
        tiesLabel = new JLabel("Ties: 0");

        resultsTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);

        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Choose Gesture"));
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.add(quitButton);

        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Stats"));
        statsPanel.add(playerWinsLabel);
        statsPanel.add(computerWinsLabel);
        statsPanel.add(tiesLabel);

        add(buttonPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        rockButton.addActionListener(new GestureButtonListener("Rock"));
        paperButton.addActionListener(new GestureButtonListener("Paper"));
        scissorsButton.addActionListener(new GestureButtonListener("Scissors"));
        quitButton.addActionListener(e -> System.exit(0));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JButton createButton(String text, String imagePath) {
        JButton button = new JButton(text);
        if (imagePath != null) {
            try {
                InputStream resourceStream = getClass().getResourceAsStream(imagePath);
                if (resourceStream != null) {
                    byte[] imageBytes = resourceStream.readAllBytes();
                    ImageIcon icon = new ImageIcon(imageBytes);
                    button.setIcon(icon);
                } else {
                    System.err.println("Image not found: " + imagePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return button;
    }

    private void updateStats() {
        playerWinsLabel.setText("Player Wins: " + playerWins);
        computerWinsLabel.setText("Computer Wins: " + computerWins);
        tiesLabel.setText("Ties: " + ties);
    }

    private class GestureButtonListener implements ActionListener {
        private String playerGesture;

        public GestureButtonListener(String gesture) {
            playerGesture = gesture;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] gestures = {"Rock", "Paper", "Scissors"};
            String computerGesture = gestures[new Random().nextInt(3)];

            String result = determineWinner(playerGesture, computerGesture);

            resultsTextArea.append(result + "\n");
            resultsTextArea.setCaretPosition(resultsTextArea.getDocument().getLength());  // Scroll to the bottom
            updateStats();
        }
        private String determineWinner(String player, String computer) {
            if (player.equals(computer)) {
                ties++;
                return "Tie: " + player + " ties with " + computer;
            } else if ((player.equals("Rock") && computer.equals("Scissors")) ||
                    (player.equals("Paper") && computer.equals("Rock")) ||
                    (player.equals("Scissors") && computer.equals("Paper"))) {
                playerWins++;
                return "Player wins: " + player + " beats " + computer;
            } else {
                computerWins++;
                return "Computer wins: " + computer + " beats " + player;
            }
        }
    }

}


