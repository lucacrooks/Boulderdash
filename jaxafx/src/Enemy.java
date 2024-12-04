/** Enemy class
 * @author Ellis Mann
 */
public class Enemy extends Tile {
    protected String enemyType;
    protected boolean isAlive;

    /** Enemy constructor
     * @author Ellis Mann
     * @param enemyType "B" for butterfly "f" for firefly
     * @param x position of enemy
     * @param y position of enemy
     * @param isAlive whether enemy is alive or not
     */
    public Enemy(String enemyType, int x, int y, boolean isAlive) {
        super(x, y);
        this.enemyType = enemyType;
        this.isAlive = isAlive;
    }

    /** Checks if a given letter is adjacent to an enemy
     * @author Luca Crooks
     * @param l the letter to check for
     * @return true if it finds one, false if not
     */
    protected boolean checkNextTo(String l) {
        return Main.board.getTileLetter(this.x + 1, this.y).equals(l)
                || Main.board.getTileLetter(this.x - 1, this.y).equals(l)
                || Main.board.getTileLetter(this.x, this.y + 1).equals(l)
                || Main.board.getTileLetter(this.x, this.y - 1).equals(l);
    }
}
