import javafx.scene.image.Image;

/** MagicWall class
 * @author Luca Crooks
 */
public class MagicWall extends Tile{
    private final String letter;
    private final Image image;
    private String contains;
    private boolean checked;

    /** MagicWall constructor
     * @author Luca Crooks
     * @param x position of magic wall
     * @param y position of magic wall
     */
    public MagicWall(int x, int y) {
        super(x, y);
        this.letter = "M";
        this.image = new Image("MAGIC_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.contains = "";
        this.checked = false;
    }

    /** MagicWall logic
     * @author Luca Crooks
     */
    public void transform () {

        // flips the contexts of the magic wall boulder -> diamond, diamond -> boulder
        if (this.contains.equals("@")) {
            this.contains = "*";
        } else if (this.contains.equals("*")) {
            this.contains = "@";
        }

        String l = Main.board.getTileLetter(this.x, this.y + 1);
        boolean enemyBelow = l.equals("f") || l.equals("B") || l.equals("F");

        if (l.equals("P")) { // path below

            // spawns new designated falling object below the magic wall
            if (this.contains.equals("*")) {
                Main.board.replace(this.x, this.y + 1, new FallingObject("*", this.x, this.y + 1));
                FallingObject d = (FallingObject) Main.board.get(this.x, this.y + 1);
                d.setChecked(true);
            } else if (this.contains.equals("@")) {
                Main.board.replace(this.x, this.y + 1, new FallingObject("@", this.x, this.y + 1));
                FallingObject b = (FallingObject) Main.board.get(this.x, this.y + 1);
                b.setChecked(true);
            }

        } else if (enemyBelow && (this.contains.equals("*") || this.contains.equals("@"))) { // if enemy below

            if (l.equals("B")) { // butterfly explodes with diamond
                Main.board.explodeDiamond(this.x, this.y + 1);
            } else {
                Main.board.explode(this.x, this.y + 1);
            }
        }
    }

    public void reset () {
        this.contains = "";
    }

    @Override
    public void update() {
        this.transform();
        this.reset();
    }

    public void setContains(String c) {
        this.contains = c;
    }

    public String getContains() {
        return this.contains;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    @Override
    public Image getImage () {
        return this.image;
    }

    @Override
    public boolean getChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;

    }
}
