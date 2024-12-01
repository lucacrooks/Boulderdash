import javafx.scene.image.Image;

public class LockedDoor extends Tile {

    private String letter;
    private boolean isOpen;
    private Image image;
    private int key;
    public LockedDoor(int x, int y, String letter){
        super(x,y);
        this.letter = letter;
        this.isOpen = false;
        this.key = findKey(letter);
        this.image = getImage();
    }

    public int findKey(String letter){
        if (letter.equals("a")){
            key = 1;
        } else if (letter.equals("b")){
            key = 2;
        } else if (letter.equals("c")){
            key = 3;
        } else if (letter.equals("d")){
            key = 4;
        }
        return key;
    }

    @Override
    public Image getImage () {
        if (letter.equals("a")){
            this.image = new Image("RED_LOCKED_DOOR.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (letter.equals("b")){
            this.image = new Image("YELLOW_LOCKED_DOOR.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (letter.equals("c")){
            this.image = new Image("GREEN_LOCKED_DOOR.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (letter.equals("d")){
            this.image = new Image("BLUE_LOCKED_DOOR.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        }
        return this.image;
    }

    public String getLetter(){
        return this.letter;
    }

    public void update(){

    }
}
