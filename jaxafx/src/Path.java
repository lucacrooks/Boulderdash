import javafx.scene.image.Image;

/** Path class
 * @author Luca Crooks
 */
public class Path extends Tile {
    private final String letter;
    private final Image image;

    /** Path constructor
     * @author Luca Crooks
     */
    public Path(int x, int y) {
        super(x, y);
        this.letter = "P";
        this.image = new Image("PATH.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
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
