import java.io.Serial;
import java.io.Serializable;

public class GameProgress implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String playerName;
    //for the pothole number
    private int difficultyLevel;
    private int score;

    public GameProgress() {
        this.playerName = "";
        this.difficultyLevel = 0;
        this.score = 0;
    }

    //Getters
    public String getPlayerName() {
        return playerName;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getScore() {
        return score;
    }

    //Setters
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
