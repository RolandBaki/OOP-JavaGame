import java.awt.*;

public abstract class GameElements {
    //Position X coordinate
    private int x;
    //Position Y coordinate
    private int y;

    //Speen is actually setting the speed of the potholes
    private int speed;

    public GameElements(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    //Setters and getters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getY() {
        return y;
    }

    public abstract void rajzol(Graphics g);
}
