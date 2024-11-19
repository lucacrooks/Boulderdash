import javafx.scene.image.Image;

public class Player {

    private int x;
    private int y;
    private Image image;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = new Image("PLAYER_FRONT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    }

    public boolean checkValidMove(String dir) {
        if (dir.equals("left")) {
            if (Main.board.getTileLetter(this.x - 1, this.y).equals("P") || Main.board.getTileLetter(this.x - 1, this.y).equals("D")) {
                return true;
            }
        } else if (dir.equals("right")) {
            if (Main.board.getTileLetter(this.x + 1, this.y).equals("P") || Main.board.getTileLetter(this.x + 1, this.y).equals("D")) {
                return true;
            }
        } else if (dir.equals("up")) {
            if (Main.board.getTileLetter(this.x, this.y - 1).equals("P") || Main.board.getTileLetter(this.x, this.y - 1).equals("D")) {
                return true;
            }
        } else {
            if (Main.board.getTileLetter(this.x, this.y + 1).equals("P") || Main.board.getTileLetter(this.x, this.y + 1).equals("D")) {
                return true;
            }
        }
        return false;
    }

    public void move(String dir) {
        if (dir.equals("right")) {
            this.x++;
        } else if (dir.equals("left")){
            this.x--;
        } else if (dir.equals("up")){
            this.y--;
        } else {
            this.y++;
        }
    }

    public void dig() {
        Main.board.getArray()[this.y][this.x] = new Tile(this.x, this.y, "P");
    }

    public Image getImage() {
        return this.image;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * move the players x and y position to the new ones passed in.
     * @param x current x pos of player.
     * @param y current y pos of player.
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(String dir) {
        if (this.checkValidMove(dir)) {
            this.move(dir);
            this.dig();
        }
    }

}
