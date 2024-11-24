import javafx.scene.image.Image;

public class TitaniumWall extends Tile {
    private String letter;
    private Image image;

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