import javafx.scene.image.Image;

/** Key class
 * @author Luca Crooks
 */
public class Key extends Tile {

    private final String letter;
    private Image image;

    /** Key constructor
     * @author Luca Crooks
     * @param x position of key
     * @param y position og key
     * @param letter 1 2 3 or 4
     */
    public Key(int x, int y, String letter) {
        super(x,y);
        this.letter = letter;
        this.image = new Image("PATH.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    }

    /** Image getter chooses image based on the key letter
     * @author Luca Crooks
     * @return the image being used for this instance
     */
    @Override
    public Image getImage () {
        switch (this.letter) {
            case "1" ->
                    this.image = new Image("RED_KEY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            case "2" ->
                    this.image = new Image("YELLOW_KEY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            case "3" ->
                    this.image = new Image("GREEN_KEY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            case "4" ->
                    this.image = new Image("BLUE_KEY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        }
        return this.image;
    }

    public String getLetter(){
        return this.letter;
    }
}
