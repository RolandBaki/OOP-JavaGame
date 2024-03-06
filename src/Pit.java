import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Pit extends GameElements {
    private BufferedImage godorKep;

    //Randomly choose a picture of a pit
    public Pit(int x, int y) {
        super(x, y, 50);
        Random random = new Random();
        int rand = random.nextInt(5) + 1;
        try {
            switch (rand) {
                case 1:
                    godorKep = ImageIO.read(new File("img/pit1.png"));
                    godorKep = resizeImage(godorKep, 100, 100);
                    break;
                case 2:
                    godorKep = ImageIO.read(new File("img/pit2.png"));
                    godorKep = resizeImage(godorKep, 100, 100);
                    break;
                case 3:
                    godorKep = ImageIO.read(new File("img/pit3.png"));
                    godorKep = resizeImage(godorKep, 100, 100);
                    break;
                case 4:
                    godorKep = ImageIO.read(new File("img/pit4.png"));
                    godorKep = resizeImage(godorKep, 100, 100);
                    break;
                case 5:
                    godorKep = ImageIO.read(new File("img/pit5.png"));
                    godorKep = resizeImage(godorKep, 100, 100);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getGodorKep() {
        return godorKep;
    }

    @Override
    public void rajzol(Graphics g) {
        g.drawImage(godorKep, getX(), getY(), null);
    }

    //Resizing the pit picture
    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        return resizedImage;
    }
}
