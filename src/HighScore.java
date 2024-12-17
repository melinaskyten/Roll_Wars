import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScore {

    private static HighScore instance;
    private static final String FILENAME = "highscores.txt";

//    Singleton
//     Hidden constructor
//     Only way to create an instance of HighScore is through
//     the static method getInstance()

    private HighScore() {
    }


    public static synchronized HighScore getInstance() {
        if (instance == null) {
            instance = new HighScore();
        }
        return instance;
    }


    public void addScore(Player player) {
        writeScoreEntry(player);
    }

    private void writeScoreEntry(Player player) {
        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write(player.toString() + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void printHighScore() {
        List<Player> playerScores = readPlayerScores();
        // Sort on score
        playerScores.sort((p1, p2) -> p2.getScore() - p1.getScore());
        for (Player p : playerScores) {
            System.out.println(p.getColorCode() + p.getName() + "\u001B[0m" + " : " + p.getScore());
        }
    }

    private List<Player> readPlayerScores() {
        List<Player> playerScores = new ArrayList<>();
        File file = new File(FILENAME);

        if (file.exists()) {

            try (FileReader reader = new FileReader(FILENAME);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        Player p = new Player();
                        String[] nameParts = parts[0].split(" ");
                        p.setName(nameParts[0]);
                        String colorString = parts[1].trim().toUpperCase();
                        int colorInt = switch (colorString) {
                            case "RED" -> 1;
                            case "GREEN" -> 2;
                            case "BLUE" -> 3;
                            default -> 0; // Default color or error handling
                        };
                        p.setColor(colorInt);
                        p.setName(parts[0]);
                        int score = Integer.parseInt(parts[2]);
                        p.setScore(score);
                        playerScores.add(p);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
            }
        }
        return playerScores;
    }

}
