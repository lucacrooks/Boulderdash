import javafx.scene.image.Image;

/** Fly class (Both firefly and butterfly)
 * @author Luca Crooks, Ellis Mann
 */
public class Fly extends Enemy {
    private Image image;
    private final String letter;
    private boolean checked;
    private String direction;
    private boolean setup;

    /** Fly constructor
     * @author Ellis Mann
     * @param enemyType "B" for butterfly "f" for firefly
     * @param x position of fly
     * @param y position of fly
     * @param isAlive whether fly is alive or not
     */
    public Fly(String enemyType, int x, int y, boolean isAlive) {
        super(enemyType, x, y, isAlive);
        this.checked = false;
        if (enemyType.equals("B")) {
            this.image = new Image("BUTTERFLY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (enemyType.equals("f")) {
            this.image = new Image("FIREFLY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        }
        this.letter = enemyType;
        this.checked = false;
        this.direction = "N";
        this.setup = false;
    }

    /** Setting move convenient direction based on starting position
     * @author Luca Crooks
     * @return most favorable starting direction
     */
    public String setDirection() {
        // free space truth values of each cardinal direction
        boolean n = Main.board.getTileLetter(this.x, this.y - 1).equals("P");
        boolean s = Main.board.getTileLetter(this.x, this.y + 1).equals("P");
        boolean e = Main.board.getTileLetter(this.x + 1, this.y).equals("P");
        boolean w = Main.board.getTileLetter(this.x - 1, this.y).equals("P");

        // ideal start is the direction one turn clockwise/anti-clockwise of where a wall is present
        if (this.letter.equals("B")) {
            if (!n) {
                return "E";
            } else if (!e) {
                return "S";
            } else if (!s) {
                return "W";
            } else if (!w) {
                return "N";
            }
        } else {
            if (!n) {
                return "W";
            } else if (!w) {
                return "S";
            } else if (!s) {
                return "E";
            } else if (!e) {
                return "N";
            }
        }
        return "N";
    }

    /** Logic for moving the fly using lookup tables
     * Having the predefined lookup tables makes the logic for the butterfly very elegant and non-repeating
     * @author Luca Crooks
     */
    public void move() {
        if (this.isAlive) {

            int ox = this.x;
            int oy = this.y;

            // redefining free space truth values
            boolean n = Main.board.getTileLetter(this.x, this.y - 1).equals("P");
            boolean s = Main.board.getTileLetter(this.x, this.y + 1).equals("P");
            boolean e = Main.board.getTileLetter(this.x + 1, this.y).equals("P");
            boolean w = Main.board.getTileLetter(this.x - 1, this.y).equals("P");

            String[][] dirs;
            boolean[][] truth;
            int[][][] coords;

            // lookup priority table for each direction for butterfly and firefly
            // format {current direction, priority1, priority2, priority3, priority4}
            if (this.letter.equals("B")) {
                dirs = new String[][]{
                        {"N", "W", "N", "E", "S"},
                        {"S", "E", "S", "W", "N"},
                        {"E", "N", "E", "S", "W"},
                        {"W", "S", "W", "N", "E"}
                };
            } else {
                dirs = new String[][]{
                        {"N", "E", "N", "W", "S"},
                        {"S", "W", "S", "E", "N"},
                        {"E", "S", "E", "N", "W"},
                        {"W", "N", "W", "S", "E"}
                };
            }

            // sam as above but holding the truth values to indicate whether the desired space is free
            if (this.letter.equals("B")) {
                truth = new boolean[][]{
                        {n, w, n, e, s},
                        {s, e, s, w, n},
                        {e, n, e, s, w},
                        {w, s, w, n, e}
                };
            } else {
                truth = new boolean[][]{
                        {n, e, n, w, s},
                        {s, w, s, e, n},
                        {e, s, e, n, w},
                        {w, n, w, s, e}
                };
            }

            // the amount to change the player x and y values by when a direction is chosen
            if (this.letter.equals("B")) {
                coords = new int[][][]{
                        {{0, -1}, {-1, 0}, {0, -1}, {1, 0}, {0, 1}},
                        {{0, 1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}},
                        {{1, 0}, {0, -1}, {1, 0}, {0, 1}, {-1, 0}},
                        {{-1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 0}}
                };
            } else {
                coords = new int[][][]{
                        {{0, -1}, {1, 0}, {0, -1}, {-1, 0}, {0, 1}},
                        {{0, 1}, {-1, 0}, {0, 1}, {1, 0}, {0, -1}},
                        {{1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, 0}},
                        {{-1, 0}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}}
                };
            }

            // locates the row the current direction is located in so the checking priority queue can be accessed
            int i = 0;
            while (!dirs[i][0].equals(this.direction)) {
                i++;
            }

            // once the row is found, highest priority is checked it can be moved to and so on...
            // once found, the variable done acts as a flag and stops the while loop
            int j = 1;
            boolean done = false;
            while (!done && j <= 4) {
                if (truth[i][j]) {
                    done = true;
                    this.x += coords[i][j][0];
                    this.y += coords[i][j][1];
                    this.direction = dirs[i][j];
                }
                j++;
            }

            // position on the board is updated accordingly
            Main.board.swap(this.x, this.y, ox, oy);
        }
    }

    /** Explodes a 3x3 area around an x,y position given (see board class)
     * @author Luca Crooks
     */
    public void kill() {
        Main.board.explode(this.x, this.y);
    }

    /** Sets the fly up once per instance, then moves, the checks for an amoeba or player (see enemy class)
     * @author Luca Crooks, Ellis Mann
     */
    @Override
    public void update() {
        if (!setup) {
            setup = true;
            this.direction = setDirection();
        }
        this.move();
        if (this.checkNextTo("X")) {
            Main.player.kill();
        }
        if (this.checkNextTo("A")) {
            this.kill();
        }
    }

    @Override
    public boolean getChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public Image getImage () {
        return this.image;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }
}
