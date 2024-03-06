import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements GameRestartListener {
    private Car car;
    private List<Pit> pits;
    private Pontok pointsPanel;
    private int GameDiff;
    private volatile boolean stopThreads = false;
    private String name;

    private MoveCar moveCar;

    public GamePanel(int diff, String namee) {
        name = namee;
        GameDiff = diff;

        //Lists of cars and potholes
        car = new Car(700, 600);
        pits = new ArrayList<>(GameDiff);

        //Data which helps generating the potholes
        int leftBorder = 180;
        int rightBorder = 180;
        int minWidth = 180;
        int maxWidth = 1536 - leftBorder - rightBorder + 50;
        int randomX = 0, randomY = 0;

        //Generate the potholes, without merging into each-other
        for (int i = 0; i < GameDiff; i++) {
            boolean init = true;
            while (init) {
                randomX = generateRandomX(minWidth, maxWidth);
                randomY = generateRandomX(-700, -100);
                Pit pit = new Pit(randomX, randomY);

                int j = 0;
                while (j < i) {
                    if (isCarOnPit(pits.get(j), pit)) {
                        break;
                    }
                    j++;
                }
                if (j == i) {
                    init = false;
                }
            }
            pits.add(new Pit(randomX, randomY));
        }

        //Panel, that shows the points
        pointsPanel = new Pontok();
        setLayout(new BorderLayout());
        add(pointsPanel, BorderLayout.NORTH);

        //Listener for the Car
        moveCar = new MoveCar(car, pits, stopThreads, this, pointsPanel, name, this);
        SwingUtilities.invokeLater(() -> {
            Thread operatorThread = new Thread(moveCar);
            operatorThread.start();
        });

        addKeyListener(moveCar);

        //Refocusing to the correct wintdow, and starting the threads, pont calculator, potholes
        setFocusable(true);
        requestFocusInWindow();
        startThreads();
    }

    //Game starter thread
    public void startThreads() {
        Thread pitThread = new Thread(() -> {
            while (!stopThreads) {
                movePits();
                pointsPanel.incrementPoints(100 - pits.get(0).getSpeed());
                pointsPanel.repaint();
                try {
                    Thread.sleep(pits.get(0).getSpeed());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                repaint();
            }
        });
        pitThread.start();
    }

    //The potholes are coming down 5px
    private void movePits() {
        for (Pit pit : pits) {
            if (pit.getY() > getHeight()) {
                resetPit(pit);
            } else {
                pit.setY(pit.getY() + 5);
            }
        }
    }

    //If a pothole is way too down, it generates a new position for it
    private void resetPit(Pit pit) {
        int leftBorder = 180;
        int rightBorder = 180;
        int minWidth = 180;
        int maxWidth = getWidth() - leftBorder - rightBorder - 100;

        boolean init = true;
        int randomX = 0, randomY = 0;

        while (init) {
            randomX = generateRandomX(minWidth, maxWidth);
            randomY = generateRandomX(-700, -100);

            int j = 0;
            while (j < pits.size()) {
                if (j != pits.indexOf(pit) && isCarOnPit(pits.get(j), new Pit(randomX, randomY))) {
                    break;
                }
                j++;
            }

            if (j == pits.size()) {
                init = false;
            }
        }

        pit.setX(randomX);
        pit.setY(randomY);
    }

    //Checks if 2 potholes are merging into each-other
    private boolean isCarOnPit(Pit pit1, Pit pit2) {
        Rectangle pit1Rect = new Rectangle(pit1.getX(), pit1.getY(), pit1.getGodorKep().getWidth(), pit1.getGodorKep().getHeight());
        Rectangle pit2Rect = new Rectangle(pit2.getX(), pit2.getY(), pit2.getGodorKep().getWidth(), pit2.getGodorKep().getHeight());
        return pit1Rect.intersects(pit2Rect);
    }

    //Random number generator
    private int generateRandomX(int minWidth, int maxWidth) {
        return (int) (Math.random() * (maxWidth - minWidth + 1) + minWidth);
    }

    //By setting it to true, it stops the threads
    public void stopThreads() {
        stopThreads = true;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0x41831F));
        g.fillRect(0, 0, 150, getHeight());
        g.setColor(new Color(0x41831F));
        g.fillRect(getWidth() - 150, 0, 150, getHeight());

        g.setColor(new Color(0xFF0000));
        g.fillRect(150, 0, 30, getHeight());
        g.setColor(new Color(0xFFFF0000, true));
        g.fillRect(getWidth() - 180, 0, 30, getHeight());

        g.setColor(new Color(0x4B4747));
        g.fillRect(180, 0, getWidth() - 360, getHeight());
        car.rajzol(g);

        for (Pit pit : pits) {
            pit.rajzol(g);
        }
    }

    //Its shows us the main panel for the game
    @Override
    public void restartGame() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            MainPanel gamePanel = new MainPanel();
            frame.getContentPane().add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusInWindow();
        });
    }

    //Getter, gets the difficulty of the game
    public int getGameDiff() {
        return GameDiff;
    }

}
