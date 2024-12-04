import javafx.scene.image.Image;

/** LockedDoor class
 * @author Luca Crooks, Pearly Bhalani
 */
public class LockedDoor extends Tile {
    private final String letter;
    private Image image;

    /** Key constructor
     * @author Pearly Bhalani
     * @param x position of locked door
     * @param y position of locked door
     *
     */
    public LockedDoor(int x, int y, String letter) {
        super(x,y);
        this.letter = letter;
        this.image = getImage();
    }

    /** Sets designated image based on letter
     * @author Luca Crooks, Pearly Bhalani
     * @return colour image based on door type
     */
    @Override
    public Image getImage () {
        switch (this.letter) {
            case "a" ->
                    this.image = new Image("RED_LOCKED_DOOR.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            case "b" ->
                    this.image = new Image("YELLOW_LOCKED_DOOR.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            case "c" ->
                    this.image = new Image("GREEN_LOCKED_DOOR.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            case "d" ->
                    this.image = new Image("BLUE_LOCKED_DOOR.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        }
        return this.image;
    }

    public String getLetter(){
        return this.letter;
    }
}
