import javax.swing.*;
import java.awt.*;

public class Pontok extends JPanel {
    private int points;

    public Pontok() {
        points = 0;
        setPreferredSize(new Dimension(100, 30));
    }

    //Its incrementing tha points, based on the speed
    public void incrementPoints(int x) {
        points+=x;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set background color
        setBackground(new Color(255, 255, 255));
        // Draw border
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        // Set font
        g.setFont(new Font("Arial", Font.BOLD, 16));

        // Draw points
        String pointsString = "Points: " + points;
        g.drawString(pointsString, 10, getHeight() / 2 + 5);
    }

    public int getPoints() {
        return points;
    }
}
