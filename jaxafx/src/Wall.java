import javafx.scene.image.Image;

/** Wall class
 * @author Luca Crooks
 */
public class Wall extends Tile {
    private final String letter;
    private final Image image;

    /** Wall constructor
     * @author Luca Crooks
     * @param x position of wall
     * @param y position of wall
     */
    public Wall(int x, int y) {
        super(x, y);
        this.letter = "W";
        this.image = new Image("NORMAL_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
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