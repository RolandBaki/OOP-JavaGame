import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainPanel extends JPanel {

    //Checkboxes for game difficulty, made to play with the user, by just being a checkbox :))
    private JCheckBox konnyu;
    private JCheckBox kozepes;
    private JCheckBox nehez;

    //StartButton
    private JButton start;

    //Field for name, where the user can change it
    private JTextField nameField;

    //Difficulty shown by the pothole number
    private int diff;

    //PLayer name
    private String name;

    //Difficulty selected
    private boolean wasSelected = false;

    //Text space for the score
    private JTextArea score;
    private SoundPanel soundManager;
    private JButton file;

    private List<GameProgress> progressList;

    MainPanel() {

        //Adding to the main panel, the buttons and text areas,etc
        konnyu = new JCheckBox("Easy (4 pothole)");
        kozepes = new JCheckBox("Medium (8 pothole)");
        nehez = new JCheckBox("Hard (12 pothole)");

        nameField = new JTextField("User484351");
        nameField.setPreferredSize(new Dimension(150, 25));

        progressList = new ArrayList<>();

        progressList = loadProgress(); // loading the file with the progress

        score = new JTextArea("No score yet\n");
        score.setEditable(false);
        score.setPreferredSize(new Dimension(300, getHeight()));

        //Updating the point area
        updateScoreField();

        start = new JButton("Start");
        file = new JButton("Delete Progress");

        //Shows the points
        loadProgress();

        //It deletes the txt file
        file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = "file/Scores.txt";

                try {
                    // Use Files.write to overwrite the file with an empty content
                    Files.write(Paths.get(filePath), new byte[0]);
                    System.out.println("File content deleted.");
                    progressList = loadProgress();
                    updateScoreField();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        //Check if the difficulty is set, and if it is, the game starts
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the selected difficulty levels
                if ((konnyu.isSelected() && kozepes.isSelected() && nehez.isSelected()) ||
                        (konnyu.isSelected() && kozepes.isSelected()) ||
                        (konnyu.isSelected() && nehez.isSelected()) ||
                        (kozepes.isSelected() && nehez.isSelected())) {
                    System.out.println("Just one for one, thank you");
                    JOptionPane.showMessageDialog(MainPanel.this, "Just one for one, thank you");
                } else {
                    if (!konnyu.isSelected() && !kozepes.isSelected() && !nehez.isSelected()) {
                        System.out.println("Select at least one, thank you");
                        JOptionPane.showMessageDialog(MainPanel.this, "Select one diff, thank you!");
                    }
                    if (konnyu.isSelected()) {
                        System.out.println("Easy difficulty selected");
                        wasSelected = true;
                        JOptionPane.showMessageDialog(MainPanel.this, "Easy difficulty selected");
                        diff = 4;
                    }
                    if (kozepes.isSelected()) {
                        System.out.println("Medium difficulty selected");
                        wasSelected = true;
                        JOptionPane.showMessageDialog(MainPanel.this, "Medium difficulty selected");
                        diff = 8;
                    }
                    if (nehez.isSelected()) {
                        System.out.println("Hard difficulty selected");
                        wasSelected = true;
                        JOptionPane.showMessageDialog(MainPanel.this, "Hard difficulty selected");
                        diff = 12;
                    }
                }
                name = nameField.getText();
                System.out.println(name);
                if (wasSelected) {
                    startGame();
                }

            }
        });


        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);


        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(konnyu)
                        .addComponent(kozepes)
                        .addComponent(nehez)
                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(start)
                        .addComponent(file))
                .addComponent(score) // Add the score to the right
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(konnyu)
                                .addComponent(kozepes)
                                .addComponent(nehez)
                                .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(start)
                                .addComponent(file))
                        .addComponent(score)) // Add the score below
        );
        setBackground(new Color(0x645A53));
        soundManager = new SoundPanel();
        soundManager.playSound("background");
    }


    //load the GamePanel
    public void startGame() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(diff, name);
        frame.getContentPane().add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow();
        soundManager.stopSound("background");

    }

    //Updates the scorer field using streams
    private void updateScoreField() {
        String scoreText = progressList.stream()
                .map(progress -> String.format("Player: %s | Difficulty: %d | Score: %d",
                        progress.getPlayerName(), progress.getDifficultyLevel(), progress.getScore()))
                .collect(Collectors.joining("\n"));

        score.setText("Game Progress:\n" + scoreText);
    }


    //loading the list of the progress using streams
    private List<GameProgress> loadProgress() {
        String filePath = "file/Scores.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split(" ");
                        GameProgress progress = new GameProgress();
                        progress.setPlayerName(parts[0]);
                        progress.setDifficultyLevel(Integer.parseInt(parts[1]));
                        progress.setScore(Integer.parseInt(parts[2]));
                        return progress;
                    })
                    .toList();
        } catch (IOException | NumberFormatException ex) {
            System.out.println("No existing game progress found.");
        }

        return List.of();
    }


}
