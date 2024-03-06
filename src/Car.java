import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Car extends GameElements {
    //Containing tha image about the car
    private BufferedImage autoKep;

    //Constructor, setting the coordinates for the start position
    public Car(int x, int y) {
        super(x, y, 1);
        try {
            autoKep = ImageIO.read(new File("img/car.png"));
            autoKep = resizeImage(autoKep, 100, 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getAutoKep() {
        return autoKep;
    }

    @Override
    public void rajzol(Graphics g) {
        g.drawImage(autoKep, getX(), getY(), null);
    }

    //To resize the image about the car in this case
    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        return resizedImage;
    }
}

