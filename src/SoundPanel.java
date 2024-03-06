import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundPanel {
    private Map<String, Clip> soundMap;

    //contains sound for the main, and for the collision
    public SoundPanel() {
        soundMap = new HashMap<>();
        loadSound("background", "sound/music.wav");
        loadSound("collision", "sound/car.wav");
    }

    private void loadSound(String key, String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            soundMap.put(key, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //It starts the sound
    public void playSound(String key) {
        Clip clip = soundMap.get(key);
        if (clip != null) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }

    //It stops the sound
    public void stopSound(String key) {
        Clip clip = soundMap.get(key);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

}
