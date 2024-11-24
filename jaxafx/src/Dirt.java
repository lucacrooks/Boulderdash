import javafx.scene.image.Image;

public class Dirt extends Tile {
    private String letter;
    private Image image;

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
