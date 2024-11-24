import javafx.scene.image.Image;

public class Wall extends Tile {
    private String letter;
    private Image image;

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

    @Override
    public void update() {

    }
}