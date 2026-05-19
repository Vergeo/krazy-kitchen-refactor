package utilities;

import java.util.ArrayList;

// (B-04) EXTRACTED from Input: solely responsible for reading and writing score to file
public class ScoreRepository {

    public int readScore() {
        ArrayList<String> data = IO.readFile("score.txt");
        return Integer.parseInt(data.get(0));
    }

    public void writeScore(int score) {
        ArrayList<String> data = new ArrayList<String>();
        data.add(Integer.toString(score));
        IO.writeFile("score.txt", data);
    }

    public void addScore(int amount) {
        writeScore(readScore() + amount);
    }

    public void subtractScore(int amount) {
        writeScore(readScore() - amount);
    }

    // (TCP-02) CHANGED: Added new method to control the score format
    public String formatScore(int score) {
        return "$" + score;
    }

}