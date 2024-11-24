import javafx.scene.image.Image;

public class Exit extends Tile {
    private String letter;
    private Image image;

    public Exit(int x, int y) {
        super(x, y);
        this.letter = "E";
        this.image = new Image("EXIT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
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