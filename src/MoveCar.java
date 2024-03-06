import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.List;

public class MoveCar implements KeyListener, Runnable {
    private Car auto;
    private List<Pit> pits;
    private boolean gameRunning;
    private GamePanel panel;
    private Pontok pointsPanel;
    private String name;
    private GameRestartListener restartListener;
    private SoundPanel soundManager;

    public MoveCar(Car auto, List<Pit> pits, boolean gameRunning, GamePanel gamePanel, Pontok pointsPanel, String name, GameRestartListener restartListener) {
        this.auto = auto;
        this.pits = pits;
        this.gameRunning = gameRunning;
        this.pointsPanel = pointsPanel;
        this.panel = gamePanel;
        this.name = name;
        this.restartListener = restartListener;
        soundManager = new SoundPanel();

    }


    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but required for KeyListener interface
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (gameRunning) {
            handleKeyInput(e);
            panel.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used, but required for KeyListener interface
    }

    //Moves the car, within the borders
    private void handleKeyInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
            if (auto.getX() > 160) {
                auto.setX(auto.getX() - 10);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
            if (auto.getX() < panel.getWidth() - 270) {
                auto.setX(auto.getX() + 10);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
            for (Pit pit : pits) {
                if (pit.getSpeed() > 5) {
                    pit.setSpeed(pit.getSpeed() - 5);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
            for (Pit pit : pits) {
                if (pit.getSpeed() < 40) {
                    pit.setSpeed(pit.getSpeed() + 5);
                }
            }
        }
    }

    //if there is a car pit collision, its is shown to the user, and  the game session ends
    public void checkCarPitCollision() {
        // Check if the car is on a pit
        for (Pit pit : pits) {
            if (isCarOnPit(auto, pit) && gameRunning) {
                pit.setSpeed(200);
                soundManager.playSound("collision");

                saveProgress();

                gameRunning = false;  // Set game state to not running
                panel.stopThreads(); // Stop the threads when the game is over
                JOptionPane.showMessageDialog(panel, "GAME OVER!\n Pontok:" + pointsPanel.getPoints() + "\n" + name);
                restartListener.restartGame();
            }
        }
    }

    //Chech if the car is ona  pit
    private boolean isCarOnPit(Car car, Pit pit) {
        // Get the rectangles representing the car and the pit
        Rectangle carRect = new Rectangle(car.getX(), car.getY(), car.getAutoKep().getWidth() - 30, car.getAutoKep().getHeight() - 30);
        Rectangle pitRect = new Rectangle(pit.getX(), pit.getY(), pit.getGodorKep().getWidth() - 20, pit.getGodorKep().getHeight() - 20);

        // Check if the rectangles intersect
        return carRect.intersects(pitRect);
    }

    @Override
    public void run() {
        gameRunning = true;
        while (gameRunning) {
            checkCarPitCollision();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Its saves the name point and difficulty off a game
    private void saveProgress() {
        String filePath = "file/Scores.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            GameProgress progress = new GameProgress();
            progress.setPlayerName(name);
            progress.setDifficultyLevel(panel.getGameDiff());
            progress.setScore(pointsPanel.getPoints());

            // Format the progress information as a line
            String progressLine = String.format("%s %d %d%n", progress.getPlayerName(), progress.getDifficultyLevel(), progress.getScore());

            // Write the line to the file
            writer.write(progressLine);

            System.out.println("Game progress saved automatically.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
