import javafx.scene.image.Image;

/** Exit class
 * @author Luca Crooks, Ellis Mann
 */
public class Exit extends Tile {
    private String letter;
    private Image image;
    private boolean open;
    private int diamondGoal = 10;

    /** Exit constructor
     * @author Luca Crooks, Ellis Mann
     * @param x position of exit
     * @param y position of exit
     */
    public Exit(int x, int y) {
        super(x, y);
        this.letter = "E";
        // sort these out based on diamond quota
        this.image = new Image("CLOSED_EXIT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.open = false;
    }

    /** If not already open, open the exit if the players diamond quota is reached
     * @author Ellis Mann
     */
    public void openExit() {
        if(!this.open) {
            if (Main.player.getDiamondCount() == diamondGoal) {
                this.open = true;
                this.image = new Image("EXIT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            }
        }
    }

    public boolean getOpen() {
        return this.open;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    /** Checks each frame if exit can open
     * @author Ellis Mann
     */
    @Override
    public void update() {
        openExit();
    }
}