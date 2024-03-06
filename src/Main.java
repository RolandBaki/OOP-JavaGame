import javax.swing.*;
import java.awt.*;

//Generating the main frame and panel
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                MainPanel mainPanel = new MainPanel();
                frame.getContentPane().add(mainPanel);
                frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                frame.setLocationRelativeTo(null); // Center the frame on the screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
