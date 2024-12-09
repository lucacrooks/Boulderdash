import java.io.*;
import java.util.ArrayList;

/** HighScoreTable class
 * @author Plabata Guha
 */
public class HighScoreTable {
    private static final int MAXIMUM_GAME_PLAYERS_TO_BE_DISPLAYED = 10;
    private ArrayList<GamePlayer> highestScores = new ArrayList<>();
    private final String filePath;

    /** HighScoreTable constructor
     * @author Plabata Guha
     */
    public HighScoreTable() {
        filePath = "src/PlayerScores.txt";
        loadFromFile();
    }

    /** Checks to see if a player already exists in the file
     * @author Plabata Guha
     */
    private boolean gamePlayerExists(GamePlayer gamePlayer) {
        for (GamePlayer player : highestScores) {
            if (player.getName().equals(gamePlayer.getName())) {
                return true;
            }
        }
        return false;
    }

    /** Updates players score rather than making a whole new entry
     * @author Plabata Guha
     */
    private void updateExistingPlayerScore(GamePlayer gamePlayer) {
        for (GamePlayer player: highestScores) {
            if (player.getName().equals(gamePlayer.getName())) {
                if (gamePlayer.getHighestScore() > player.getHighestScore()) {
                    player.setHighestScore(gamePlayer.getHighestScore());
                }
                return;
            }
        }
    }

    /** Bubble sorts the scores
     * @author Plabata Guha
     */
    private void sortHighestScores() {
        for (int i = 0; i < highestScores.size(); i++) {
            for (int j = i + 1; j < highestScores.size(); j++) {
                GamePlayer gamePlayer1 = highestScores.get(i);
                GamePlayer gamePlayer2 = highestScores.get(j);

                if(gamePlayer1.getHighestScore() < gamePlayer2.getHighestScore()) {
                    highestScores.set(i, gamePlayer2);
                    highestScores.set(j, gamePlayer1);
                }
            }
        }
    }

    /** Removes anyone not in top 10
     * @author Plabata Guha
     */
    public void selectTop10Players() {
        while(highestScores.size() > MAXIMUM_GAME_PLAYERS_TO_BE_DISPLAYED) {
            highestScores.remove(highestScores.size() - 1);
        }
    }

    public void saveToFile() {
        //BufferedWriter used to write on to text file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            //goes through each gameplayer in the arraylist.
            for (GamePlayer gamePlayer : highestScores) {
                //write the players name and high score on to the textfile.
                writer.write(gamePlayer.getName()+", "+gamePlayer.getHighestScore());
                writer.newLine();
            }
        } catch (IOException e) {
            //gives an error message if there is an issue saving to the file.
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        //bufferedReader used to read line by line from a file.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            //clears what is currently in highestScore array to avoid dupilicates.
            highestScores.clear();
            String line;
            //read the file line by line
            while ((line = reader.readLine()) != null) {
                //splits the line into two parts, split by ",".
                String[] parts = line.split(",\\s*");
                //checks if there are two parts.
                if (parts.length == 2) {
                    //first part being the name, and the latter the score.
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    //creates a new player with the name from the first split.
                    GamePlayer player = new GamePlayer(name);
                    //set the score form the second part of the split to the new player.
                    player.setCurrentScore(score);
                    //adds the player to the arraylist.
                    highestScores.add(player);
                }
            }
        } catch (IOException e) {
            //gives a message if there is an issue readinf the file.
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public void displayHighScores(){
        sortHighestScores();
        selectTop10Players();
        System.out.println("High Scores: ");
        for (GamePlayer gamePlayer : highestScores){
            System.out.println(gamePlayer.getName() + " - LEVEL " + gamePlayer.getHighestScore());
        }
    }

    public ArrayList<GamePlayer> getHighestScores() {
        sortHighestScores();
        selectTop10Players();
        return highestScores;
    }
}