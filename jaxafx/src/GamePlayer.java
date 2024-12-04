/** GamePlayer class
 * @author Plabata Guha
 */
public class GamePlayer {
    private String name;
    private int currentScore;
    private int highestScore;

    /** GamePlayer constructor
     * @author Plabata Guha
     * @param name of player
     */
    public GamePlayer(String name){
        this.name = name;
        this.currentScore = 0;
        this.highestScore = 0;
    }

    public String getName(){
        return name;
    }

    public void setHighestScore(int score){
        this.highestScore = score;
    }

    public int getHighestScore(){
        if (this.currentScore > this.highestScore){
            this.highestScore = this.currentScore;
        }
        return highestScore;
    }

    public void setCurrentScore(int score){
        this.currentScore = score;
    }


    public void resetCurrentScore(){
        this.currentScore = 0;
    }
}