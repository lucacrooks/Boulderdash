import java.util.ArrayList;
public class HighScoreTable{
    private static final int MAXIMUM_GAME_PLAYERS_TO_BE_DISPLAYED = 10;
    private ArrayList<GamePlayer> highestScores;

    public HighScoreTable(){
        this.highestScores = new ArrayList<>();
    }

    public void addOrUpdateHighScoreTable(GamePlayer gamePlayer){

        // Check if the player exists in the table if yes update it's highest score
        // If not add it to the table
        // Sort the highest scores of all the players
        // Remove the rest and output the top 10

        if (!gamePlayerExists(gamePlayer)){
            highestScores.add(gamePlayer);
        } else {
            updateExistingPlayerScore(gamePlayer);
        }

        sortHighestScores();
        selectTop10Players();


    }

    private boolean gamePlayerExists(GamePlayer gamePlayer){
        for (GamePlayer player : highestScores){
            if(player.getName().equals(gamePlayer.getName())){
                return true;
            }
        }
        return false;
    }

    private void updateExistingPlayerScore(GamePlayer gamePlayer){
        for (GamePlayer player: highestScores){
            if (player.getName().equals(gamePlayer.getName())){
                if (gamePlayer.getHighestScore()>player.getHighestScore()){
                    player.setHighestScore(gamePlayer.getHighestScore());
                }
                return;
            }
        }
    }

    private void sortHighestScores(){
        for (int i = 0; i<highestScores.size(); i++){
            for (int j = i+1; j<highestScores.size();j++){
                GamePlayer gamePlayer1 = highestScores.get(i);
                GamePlayer gamePlayer2 = highestScores.get(j);

                if(gamePlayer1.getHighestScore() > gamePlayer2.getHighestScore()){
                    highestScores.set(i,gamePlayer1);
                    highestScores.set(j,gamePlayer2);
                }
            }
        }
    }

    public void selectTop10Players(){
        while(highestScores.size()>MAXIMUM_GAME_PLAYERS_TO_BE_DISPLAYED){
            highestScores.remove(highestScores.size()-1);
        }
    }
}