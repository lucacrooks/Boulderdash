import javafx.scene.image.Image;

public class Key extends Tile {

    private String letter;
    private Image image;

    public Key(int x, int y, String letter){
        super(x,y);
        this.letter = letter;
        this.image = new Image("PATH.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    }

    @Override
    public Image getImage () {
        if (this.letter.equals("1")){
            this.image = new Image("RED_KEY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (this.letter.equals("2")){
            this.image = new Image("YELLOW_KEY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (this.letter.equals("3")){
            this.image = new Image("GREEN_KEY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (this.letter.equals("4")){
            this.image = new Image("BLUE_KEY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        }
        return this.image;
    }

    public String getLetter(){
        return this.letter;
    }

    public void update(){

    }
}
