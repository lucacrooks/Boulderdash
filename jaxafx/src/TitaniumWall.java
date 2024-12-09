import javafx.scene.image.Image;

/** Titanium wall class
 * @author Luca Crooks
 */
public class TitaniumWall extends Tile {
    private String letter;
    private Image image;

    /** Wall constructor
     * @author Luca Crooks
     * @param x position of titanium wall
     * @param y position of titanium wall
     */
    public TitaniumWall(int x, int y) {
        super(x, y);
        this.letter = "T";
        this.image = new Image("TITANIUM_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
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