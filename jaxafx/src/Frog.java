import javafx.scene.image.Image;

/** Frog class
 * @author Luca Crooks
 */
public class Frog extends Enemy {
    private final Image image;
    private final String letter;
    private boolean checked;
    private int elapsed;
    private boolean moved;

    /** Frog constructor
     * @author Luca Crooks
     */
    public Frog(String enemyType, int x, int y, boolean isAlive) {
        super(enemyType, x, y, isAlive);
        this.checked = false;
        this.image = new Image("FROG.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.letter = enemyType;
        this.elapsed = 0;
        this.moved = false;
    }

    /** Checks if a given x,y is a path
     * @author Luca Crooks
     * @param x position to be checked
     * @param y position to be checked
     */
    public void checkMove(int x, int y) {
        if (Main.board.getTileLetter(x, y).equals("P")) {
            Main.board.swap(this.x, this.y, x, y);
            this.x = x;
            this.y = y;
            this.moved = true;
        }
    }

    /** My own pathfinding code for frog because programming the A* algorithm is beyond my knowledge
     * It attempts to walk directly to the player at all times, sliding along walls where it can
     * Updates position every 2 ticks
     * @author Luca Crooks
     */
    public void move() {
        this.elapsed++;
        if (this.isAlive && this.elapsed % 2 == 0) {

            int px = Main.player.getX();
            int py = Main.player.getY();

            int dx = Math.abs(this.x - px);
            int dy = Math.abs(this.y - py);

            this.moved = false;

            // used to lock either x or y, if running along a wall
            boolean xLock = false;
            boolean yLock = false;

            if (dx >= dy) { // if player is further away in the x direction than y
                if (px >= this.x) {
                    this.checkMove(this.x + 1, this.y);
                } else {
                    this.checkMove(this.x - 1, this.y);
                }
                if (!this.moved) { // if it cant move along x-axis to player
                    xLock = true;
                }
            }
            if (dy >= dx && !moved || xLock) { // if player is further away in the y direction than x
                if (py > this.y) {
                    this.checkMove(this.x, this.y + 1);
                } else {
                    this.checkMove(this.x, this.y - 1);
                }
                if (!this.moved) { // if it cant move along y-axis to player
                    yLock = true;
                }
            }
            if (dx >= dy && !moved || yLock) { // extra case if nowhere was moved
                if (px >= this.x) {
                    this.checkMove(this.x + 1, this.y);
                } else {
                    this.checkMove(this.x - 1, this.y);
                }
            }
        }
    }

    /** Moves, then kills player if needed, kills itself if next to amoeba
     * @author Luca Crooks
     */
    public void update() {
        this.move();
        if (this.checkNextTo("X")) {
            Main.player.kill();
        }
        if (this.checkNextTo("A")) {
            this.kill();
        }
    }

    /** Explodes where it dies
     * @author Luca Crooks
     */
    public void kill() {
        Main.board.explode(this.x, this.y);
    }

    @Override
    public Image getImage () {
        return this.image;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    @Override
    public boolean getChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
