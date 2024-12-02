import javafx.scene.image.Image;

/** Dirt class
 * @author Luca Crooks
 */
public class Dirt extends Tile {
    private String letter;
    private Image image;

    /** Dirt constructor
     * @author Luca Crooks
     * @param x position of dirt
     * @param y position of dirt
     */
    public Dirt(int x, int y) {
        super(x, y);
        this.letter = "D";
        this.image = new Image("DIRT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
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
    public void update() {
    }
}
