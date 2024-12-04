import java.util.ArrayList;

/** HighScoreTable class
 * @author Plabata Guha
 */
public class HighScoreTable {
    private static final int MAXIMUM_GAME_PLAYERS_TO_BE_DISPLAYED = 10;
    private ArrayList<GamePlayer> highestScores;

    /** HighScoreTable constructor
     * @author Plabata Guha
     */
    public HighScoreTable() {
        this.highestScores = new ArrayList<>();
    }

    /** Check if the player exists in the table and update their highest score
     * If not add it to the table
     * @author Plabata Guha
     */
    public void addOrUpdateHighScoreTable(GamePlayer gamePlayer) {

        if (!gamePlayerExists(gamePlayer)) {
            highestScores.add(gamePlayer);
        } else {
            updateExistingPlayerScore(gamePlayer);
        }

        // sorts then keeps top 10
        sortHighestScores();
        selectTop10Players();


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
     * @returns
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

                if(gamePlayer1.getHighestScore() > gamePlayer2.getHighestScore()) {
                    highestScores.set(i, gamePlayer1);
                    highestScores.set(j, gamePlayer2);
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
}