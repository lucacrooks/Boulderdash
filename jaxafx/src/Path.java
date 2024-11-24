import javafx.scene.image.Image;

public class Path extends Tile {
    private int x;
    private int y;
    private String letter;
    private Image image;

    public Path(int x, int y) {
        super(x, y);
        this.letter = "P";
        this.image = new Image("DIAMOND.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
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
