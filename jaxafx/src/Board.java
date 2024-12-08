import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** Board class
 * @author Luca Crooks
 */
public class Board {

    private Tile[][] array;
    private int level;
    private int x;
    private int y;

    /** Board constructor
     * @author Luca Crooks
     */
    public Board() {
        this.array = makeArray();
        this.level = 0;
    }

    /** Reads the level file then takes the data and arranges it into a 2d array of tile objects
     * @author Luca Crooks
     * @return the array full of the respective tile objects based on the level file
     */
    public Tile[][] makeArray() {
        Tile[][] a = new Tile[Main.GRID_HEIGHT][Main.GRID_WIDTH];
        try {
            int row = 0;
            File f = new File(Main.levels[this.level]);
            Scanner reader = new Scanner(f);

            // creates an instance of each subclass based on the letter it corresponds to
            while (row < Main.GRID_HEIGHT) {
                String data = reader.nextLine();
                for (int col = 0; col < Main.GRID_WIDTH; col++) {
                    String l = data.substring(col, col + 1);
                    switch (l) {
                        case "X" -> a[row][col] = new Player(col, row, Main.player.getLives());
                        case "*" -> a[row][col] = new FallingObject("*", col, row);
                        case "@" -> a[row][col] = new FallingObject("@", col, row);
                        case "M" -> a[row][col] = new MagicWall(col, row);
                        case "D" -> a[row][col] = new Dirt(col, row);
                        case "P" -> a[row][col] = new Path(col, row);
                        case "W" -> a[row][col] = new Wall(col, row);
                        case "T" -> a[row][col] = new TitaniumWall(col, row);
                        case "E" -> a[row][col] = new Exit(col, row, 5);
                        case "B" -> a[row][col] = new Fly("B", col, row, true);
                        case "f" -> a[row][col] = new Fly("f", col, row, true);
                        case "F" -> a[row][col] = new Frog("F", col, row, true);
                        case "A" -> a[row][col] = new Amoeba(col, row, Main.MAX_AMOEBA_CAP, 0);
                        case "a" -> a[row][col] = new LockedDoor(col, row, "a");
                        case "b" -> a[row][col] = new LockedDoor(col, row, "b");
                        case "c" -> a[row][col] = new LockedDoor(col, row, "c");
                        case "d" -> a[row][col] = new LockedDoor(col, row, "d");
                        case "1" -> a[row][col] = new Key(col, row, "1");
                        case "2" -> a[row][col] = new Key(col, row, "2");
                        case "3" -> a[row][col] = new Key(col, row, "3");
                        case "4" -> a[row][col] = new Key(col, row, "4");
                    }
                }
                row++;
            }
            reader.close();

        } catch (FileNotFoundException e) { // always catch a reader error
            System.out.println("An error occurred.");
        }
        return a;
    }

    /** Replaces a given x,y position in the board with a new given tile
     * @author Luca Crooks
     * @param x position of target tile
     * @param y position of target tile
     */
    public void replace(int x, int y, Tile t) {
        this.array[y][x] = t;
    }

    /** Performs a swap of tiles at position x,y and x2,y2
     * @author Luca Crooks
     * @param x position of first tile
     * @param y position of first tile
     * @param x2 position of second tile
     * @param y2 position of second tile
     */
    public void swap (int x, int y, int x2, int y2) {
        Tile temp = this.array[y][x];
        this.array[y][x] = this.array[y2][x2];
        this.array[y2][x2] = temp;
    }

    /** Gets letter of target tile
     * @author Luca Crooks
     * @param x position of tile
     * @param y position of tile
     * @return letter of target tile
     */
    public String getTileLetter(int x, int y) {
        return this.array[y][x].getLetter();
    }

    /** Gets a tile given an x,y
     * @author Luca Crooks
     * @param x position of tile
     * @param y position of tile
     * @return a tile based on x,y position
     */
    public Tile get(int x, int y) {
        return this.array[y][x];
    }

    /** Removes all destructible tiles in the surrounding 3x3 square
     * @author Luca Crooks
     * @param x position of explosion centre
     * @param y position of explosion centre
     */
    public void explode(int x, int y) {
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                String l = this.array[y + dy][x + dx].getLetter();
                if (!l.equals("T") && !l.equals("E")) {
                    this.replace(x + dx, y + dy, new Path(x + dx, y + dy));
                }
            }
        }
    }

    /** Replaces all destructible tiles in the surrounding 3x3 square with diamonds
     * @author Luca Crooks
     * @param x position of explosion centre
     * @param y position of explosion centre
     */
    public void explodeDiamond(int x, int y) {
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                String l = this.array[y + dy][x + dx].getLetter();
                if (!l.equals("T") && !l.equals("E")) {
                    this.replace(x + dx, y + dy, new FallingObject("*", x + dx, y + dy));
                }
            }
        }
    }

    /** Unlocks each amoeba to prevent more than one from spreading each frame
     * @author Luca Crooks
     */
    public void unlockAmoebas() {
        for (int row = Main.GRID_HEIGHT - 1; row >= 0; row--) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (Main.board.get(col, row).getLetter().equals("A")) {
                    Amoeba a = (Amoeba) Main.board.get(col, row);
                    a.setLocked(false);
                }
            }
        }
    }

    /** Moves to next level, redraws it to screen
     * @author Luca Crooks
     */
    public void nextLevel() {
        this.level += 1;
        this.resetLevel();
    }

    /** Redraws original level to screen
     * @author Luca Crooks
     */
    public void resetLevel() {
        this.array = this.makeArray();
        Main.player = new Player(1, 1, Main.player.getLives());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int nx) {
        this.x = nx;
    }

    public void setY(int ny) {
        this.y = ny;
    }

    /** Draws the board using the overridden image getter in each subclass of tile
     * @author Luca Crooks
     * @param canvas the canvas where the graphics window will reside
     */
    public void draw(Canvas canvas, int timeRemaining) {
        // get the graphic context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // set the background to black
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // game over image
        Image gameOverImage = new Image("GAME_OVER.png", Main.CANVAS_WIDTH, Main.CANVAS_HEIGHT, false, false);

        // draw each tile in the x,y position it holds, multiplied by a factor of GRID_CELL_WIDTH / HEIGHT
        for (int row = 0; row < Main.GRID_HEIGHT; row++) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                Image img = array[row][col].getImage();
                gc.drawImage(img, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
            }
        }
        if (Main.player.getLives() == 0 || timeRemaining == 0) {
            gc.drawImage(gameOverImage, 0, 0);
        }
    }
}
