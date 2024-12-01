import javafx.scene.image.Image;

/** Boulder class
 * @author Luca Crooks, Iolo Staniland, Gregory Picton-Turberville
 */
public class Boulder extends Tile {
    private String letter;
    private Image image;
    private boolean isFalling;
    private boolean checked;

    /** Boulder constructor
     * @author Iolo Staniland, Gregory Picton-Turberville
     * @param x position of boulder
     * @param y position of boulder
     */
    public Boulder(int x, int y) {
        super(x, y);
        this.letter = "@";
        this.image = new Image("BOULDER.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.isFalling = false;
        this.checked = false;
    }

    /** Boulder pushing method
     * @author Luca Crooks
     * @param dir is "left" or "right" depending on desired direction of pushing
     */
    public void push(String dir) {
        if (dir.equals("left")) {
            Main.board.swap(this.x, this.y, this.x - 1, this.y);
            this.x--;
        } else {
            Main.board.swap(this.x, this.y, this.x + 1, this.y);
            this.x++;
        }
        this.checked = true;
    }

    /** Returns a number based on which case the boulder can move
     * @author Luca Crooks, Iolo Staniland, Gregory Picton-Turberville
     * @return a number based on what case the boulder is falling under, 0 if it cannot fall
     */
    public int canFall() {

        // storing the letters in each relevant direction in a variable for easier usage
        String below = Main.board.getTileLetter(this.x, this.y + 1);
        String left = Main.board.getTileLetter(this.x - 1, this.y);
        String right = Main.board.getTileLetter(this.x + 1, this.y);
        String belowLeft = Main.board.getTileLetter(this.x - 1, this.y + 1);
        String belowRight = Main.board.getTileLetter(this.x + 1, this.y + 1);

        if (below.equals("X") && this.isFalling) { // if player is under falling boulder
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.killPlayer();

        } else if (((below.equals("B")
                || below.equals("f")
                || below.equals("F"))) && this.isFalling) { //if enemy is under falling boulder
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            if (Main.board.getTileLetter(this.x, this.y + 1).equals("B")) {
                Main.board.explodeDiamond(this.x, this.y + 1);
            } else if (Main.board.getTileLetter(this.x, this.y + 1).equals("f")) {
                Main.board.explode(this.x, this.y + 1);
            }

        } else if (below.equals("P")) { // if path is below
            return 2;

        } else if (below.equals("@") || below.equals("W") || below.equals("*")) { // if wall, boulder, or diamond below

            // cases where boulder can roll left or right
            if (left.equals("P") && belowLeft.equals("P")) {
                return 1;
            }
            if (right.equals("P") && belowRight.equals("P")) {
                return 3;
            }

        } else if (below.equals("M")) { // if below is a magic wall
            return 4;
        }

        return 0;
    }

    /** Moves the boulder and/or tiles around it based on which case it falls under
     * @author Luca Crooks, Iolo Staniland, Gregory Picton-Turberville
     */
    public void fall(int dir) {
        this.isFalling = false;
        if (dir == 2) { // fall directly down
            Main.board.swap(this.x, this.y, this.x, this.y + 1);
            this.y++;
            this.isFalling = true;

        } else if (dir == 4) { // fall through magic wall
            MagicWall mw = (MagicWall) Main.board.get(this.x, this.y + 1);
            mw.setContains("@");
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.isFalling = true;

        } else if (dir == 1) { // slide left
            Main.board.swap(this.x, this.y, this.x - 1, this.y);
            this.x--;

        } else if (dir == 3) { // slide right
            Main.board.swap(this.x, this.y, this.x + 1, this.y);
            this.x++;
        }
    }

    /** Calls kill() in player class and replaces it with the falling boulder
     * @author Luca Crooks
     */
    public void killPlayer() {
        Main.board.swap(this.x, this.y, this.x, this.y + 1);
        Main.board.replace(this.x, this.y, new Path(this.x, this.y));
        this.y++;
        Main.player.kill();
    }

    /** Calls fall() if canFall()
     * @author Luca Crooks
     */
    @Override
    public void update() {
        this.fall(this.canFall());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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