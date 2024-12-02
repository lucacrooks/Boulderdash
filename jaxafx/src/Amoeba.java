import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

/** Amoeba class
 * @author Luca Crooks, Iolo Staniland, Gregory Picton-Turberville
 */
public class Amoeba extends Tile {
    private int maxCap;
    private String letter;
    private Image image;
    private ArrayList<Amoeba> amoebas;
    private ArrayList<Tile> validTiles;
    private boolean locked;
    private int elapsed;

    /** Amoeba constructor
     * @author Iolo Staniland, Gregory Picton-Turberville
     * @param x Amoeba x position on the board
     * @param y Amoeba y position on the board
     * @param maxCap maximim capacity one amoeba cluster can reach before dissipating into boulders
     * @param elapsed ticks elapsed since first update
     */
    public Amoeba(int x, int y, int maxCap, int elapsed) {
        super(x,y);
        this.maxCap = maxCap;
        this.letter = "A";
        this.image = new Image("AMOEBA.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.amoebas = new ArrayList<>();
        this.validTiles = new ArrayList<>();
        this.locked = false;
        this.elapsed = elapsed;
    }


    /** Adds all amoebas on the board to an array
     * @author Luca Crooks
     * @return
     */
    public void makeAmoebasArray() {
        this.amoebas.clear();
        for (int row = Main.GRID_HEIGHT - 1; row >= 0; row--) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (Main.board.get(row, col).getLetter().equals("A")) {
                    this.amoebas.add((Amoeba) Main.board.get(row, col));
                }
            }
        }
    }

    /** Adds possible Tiles to an array that any amoeba can spread to
     * @author Luca Crooks
     */
    public void makeValidTiles() {
        this.validTiles.clear();
        for (Amoeba amoeba : amoebas) {
            int ax = amoeba.getX();
            int ay = amoeba.getY();

            // checks each of the 4 cardinal directions for each amoeba and adds it to the list if it is valid
            if (isInBounds(ax, ay - 1) && isPathOrDirt(ax, ay - 1)){
                validTiles.add(Main.board.get(ax, ay - 1));
            } else if (isInBounds(ax - 1, ay) && isPathOrDirt(ax - 1, ay)) {
                validTiles.add(Main.board.get(ax - 1, ay));
            } else if (isInBounds(ax, ay + 1) && isPathOrDirt(ax, ay + 1)){
                validTiles.add(Main.board.get(ax, ay + 1));
            } else if (isInBounds(ax + 1, ay) && isPathOrDirt(ax + 1, ay)) {
                validTiles.add(Main.board.get(ax + 1, ay));
            }
        }
    }

    /** Chooses a random tile to spread to, then locks the others until the next amoeba tick
     * Replaces each amoeba with diamonds if it cannot spread anywhere
     * Replaces each amoeba with boulders if the maximum capacity is reached
     * @author Iolo Staniland, Gregory Picton-Turberville
     */
    public void spread(){
        if(!isMaxCapReached()) {
            if (validTiles.isEmpty()) { //turns all amoebas into diamonds
                for (Amoeba amoeba : amoebas) {
                    Main.board.replace(amoeba.x, amoeba.y, new FallingObject("*", amoeba.x, amoeba.y));
                }
            } else {
                // chooses a random tile from the list
                Random ran = new Random();
                int amoebaIndex = ran.nextInt(validTiles.size());
                Tile randomTile = validTiles.get(amoebaIndex);

                int rtx = randomTile.getX();
                int rty = randomTile.getY();

                // replaces it with a new amoeba
                Main.board.replace(rtx, rty, new Amoeba(rtx, rty, this.maxCap, this.elapsed));
                Amoeba a = (Amoeba) Main.board.get(rtx, rty);
                a.setLocked(true);

                this.setAllLocked(true);
            }
        } else { // turns all amoebas into boulders
            for (Amoeba amoeba : amoebas) {
                Main.board.replace(amoeba.x, amoeba.y, new FallingObject("@", amoeba.x, amoeba.y));
            }
        }
    }

    /** Checks if amoeba cluster maximum capacity is reached
     * @author Iolo Staniland, Gregory Picton-Turberville
     * @return True if the size of amoebas array is above the predefined threshold, false if not
     */
    public boolean isMaxCapReached() {
        if (amoebas.size() >= maxCap){
            return true;
        } else {
            return false;
        }
    }

    /** Checks if a given x, y position is in bounds of the board
     * @author Luca Crooks
     * @param x position of tile
     * @param y position of tile
     * @return true if the given x,y position is a valid position on the board
     */
    public boolean isInBounds(int x, int y) {
        if (x >= 0 && x < Main.GRID_WIDTH && y >= 0 && y < Main.GRID_HEIGHT) {
            return true;
        }
        return false;
    }

    /** Checks if a given x,y position can be spread to by an amoeba
     * @author Luca Crooks
     * @param x position of tile
     * @param y position of tile
     * @return true if the given tile position is path or dirt
     */
    public boolean isPathOrDirt(int x, int y) {
        return Main.board.getTileLetter(x, y).equals("P") || Main.board.getTileLetter(x, y).equals("D");
    }

    /** Each amoeba in the board is set to the boolean given
     * @author Luca Crooks
     * @param l the boolean for which each amoeba in the board is set to
     */
    public void setAllLocked(boolean l) {
        for (int row = Main.GRID_HEIGHT - 1; row >= 0; row--) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (Main.board.get(row, col).getLetter().equals("A")) {
                    Amoeba a = (Amoeba) Main.board.get(row, col);
                    a.setLocked(l);
                }
            }
        }
    }

    /** Setter for attribute locked
     * @author Luca Crooks
     * @param l new value for attribute locked
     */
    public void setLocked(boolean l) {
        this.locked = l;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    /** Update method executes every 10 regular frames
     * Generates arrays, spreads one amoeba, then locks the rest
     * @author Luca Crooks
     */
    @Override
    public void update() {
        this.elapsed++;
        if (this.elapsed % 10 == 0) {
            this.makeAmoebasArray();
            this.makeValidTiles();
            if (!this.locked) {
                this.spread();
            }
        } else {
            this.setAllLocked(true);
        }
    }
}