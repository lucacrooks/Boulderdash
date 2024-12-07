import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Arrays;

/** Player class
 * @author Luca Crooks
 */
public class Player extends Tile {
    private Image image;
    private int diamondCount;
    private final ArrayList<String> inventory;
    private final boolean isAlive;
    private final String letter;
    private int lives;

    /** Player constructor
     * @author Luca Crooks
     * @param x position of player
     * @param y position of player
     */
    public Player(int x, int y, int lives) {
        super(x, y);
        this.letter = "X";
        this.image = new Image("PLAYER_FRONT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.diamondCount = 0;
        this.isAlive = true;
        this.inventory = new ArrayList<>();
        this.lives = lives;
    }

    /** Kills self if there is an enemy occupying any of the 4 adjacent squares
     * @author Luca Crooks
     */
    public void checkNextToEnemy() {
        String[] surround = {Main.board.getTileLetter(this.x + 1, this.y),
                Main.board.getTileLetter(this.x - 1, this.y),
                Main.board.getTileLetter(this.x, this.y + 1),
                Main.board.getTileLetter(this.x, this.y - 1)};
        if (Arrays.asList(surround).contains("B") || Arrays.asList(surround).contains("F") || Arrays.asList(surround).contains("f")) {
            this.kill();
        }
    }

    /** Checks all cases to see if a move is valid before moving
     * @author Luca Crooks
     * @param dir direction of desired move to check
     * @return true if player can move, false if not
     */
    public boolean checkValidMove(String dir) {

        // moves only if alive
        if (!this.isAlive) {
            return false;
        }

        // holds what new square the player is trying to move to in nx,ny
        int nx = 0;
        int ny = 0;
        switch (dir) {
            case "right" -> {
                this.image = new Image("PLAYER_RIGHT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
                nx = this.x + 1;
                ny = this.y;
            }
            case "left" -> {
                this.image = new Image("PLAYER_LEFT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
                nx = this.x - 1;
                ny = this.y;
            }
            case "up" -> {
                this.image = new Image("PLAYER_BACK.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
                nx = this.x;
                ny = this.y - 1;
            }
            case "down" -> {
                this.image = new Image("PLAYER_FRONT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
                nx = this.x;
                ny = this.y + 1;
            }
        }
        String targetLetter = Main.board.getTileLetter(nx, ny);

        // checks the logic for each case a player can come across
        // keys, doors, pushing boulders, walls, diamonds, exits
        if (targetLetter.equals("1") || targetLetter.equals("2") || targetLetter.equals("3") || targetLetter.equals("4")) {
            this.inventory.add(targetLetter);
            return true;
        } else if ((targetLetter.equals("a") && this.inventory.contains("1"))
                || (targetLetter.equals("b") && this.inventory.contains("2"))
                || (targetLetter.equals("c") && this.inventory.contains("3"))
                || (targetLetter.equals("d") && this.inventory.contains("4"))) {
            return true;
        } else if (dir.equals("left") && targetLetter.equals("@") && Main.board.getTileLetter(nx - 1, ny).equals("P")) {
            FallingObject b = (FallingObject) Main.board.get(nx, ny);
            b.push("left");
            return true;
        } else if (dir.equals("right") && targetLetter.equals("@") && Main.board.getTileLetter(nx + 1, ny).equals("P")) {
            FallingObject b = (FallingObject) Main.board.get(nx, ny);
            b.push("right");
            return true;
        } else if (targetLetter.equals("D") || targetLetter.equals("P")) {
            return true;
        } else if (targetLetter.equals("*")) {
            this.diamondCount++;
            System.out.println(this.diamondCount);
            return true;
        } else if (targetLetter.equals("E")) {
            Exit e = (Exit) Main.board.get(nx, ny);
            if (e.getOpen()) {
                Main.board.nextLevel();
            }
        }
        return false;
    }

    /** Moves player if it can be moved
     * @author Luca Crooks
     * @param dir direction of desired move to check
     */
    public void move(String dir) {
        Main.board.replace(this.x, this.y, new Player(this.x, this.y, this.lives));
        switch (dir) {
            case "right" -> {
                Main.board.swap(this.x, this.y, this.x + 1, this.y);
                Main.board.replace(this.x, this.y, new Path(this.x, this.y));
                this.x++;
                Main.player.setX(this.x);
            }
            case "left" -> {
                Main.board.swap(this.x, this.y, this.x - 1, this.y);
                Main.board.replace(this.x, this.y, new Path(this.x, this.y));
                this.x--;
                Main.player.setX(this.x);
            }
            case "up" -> {
                Main.board.swap(this.x, this.y, this.x, this.y - 1);
                Main.board.replace(this.x, this.y, new Path(this.x, this.y));
                this.y--;
                Main.player.setY(this.y);
            }
            default -> {
                Main.board.swap(this.x, this.y, this.x, this.y + 1);
                Main.board.replace(this.x, this.y, new Path(this.x, this.y));
                this.y++;
                Main.player.setY(this.y);
            }
        }
    }

    /** Puts the player in its new spot
     * @author Luca Crooks
     */
    public void dig() {
        Main.board.replace(this.x, this.y, Main.player);
    }

    /** Subtracts life when player dies and shows game over screen when all lives are lost
     * @author Luca Crooks
     */
    public void kill() {
        this.lives--;
        if (this.lives <= 0) {
            Main.board.explode(this.x, this.y);
        } else {
            Main.board.explode(this.x, this.y);
            Main.board.resetLevel();
            Main.player = new Player(2, 2, this.lives);
            System.out.println("LIVES: " + this.lives);
        }
    }

    @Override
    public Image getImage () {
        return this.image;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    public int getLives() {
        return this.lives;
    }

    public int getDiamondCount() { return this.diamondCount; }

    /** Moves if move is valid, then digs, then checks for an enemy
     * @author Luca Crooks
     * @param dir direction of desired move to check
     */
    @Override
    public void update(String dir) {
        if (this.checkValidMove(dir)) {
            this.move(dir);
            this.dig();
        }
        this.checkNextToEnemy();
    }

}
