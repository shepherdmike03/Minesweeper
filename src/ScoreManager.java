/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ScoreManager {

    private String fileName;

    public ScoreManager(String fileName) {
        this.fileName = fileName;
    }

    public void saveScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(playerName + ";" + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ScoreEntry> loadAndSortScores() {
        List<ScoreEntry> entries = new ArrayList<>();
        if (!Files.exists(Paths.get(fileName))) {
            return entries;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0];
                    int sc;
                    try {
                        sc = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException ex) {
                        sc = 0;
                    }
                    entries.add(new ScoreEntry(name, sc));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        return entries;
    }
}

class ScoreEntry {
    private String playerName;
    private int score;

    public ScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
