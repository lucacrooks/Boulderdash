import javafx.scene.image.Image;

public class Exit extends Tile {
    private String letter;
    private Image image;
    private boolean open;

    public Exit(int x, int y) {
        super(x, y);
        this.letter = "E";
        // sort these out based on diamond quota
        this.image = new Image("EXIT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.image = new Image("CLOSED_EXIT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.open = true;
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

    @Override
    public void update() {

    }
}