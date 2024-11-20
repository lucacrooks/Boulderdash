import javafx.scene.image.Image;

public class Player {
    private int start_x;
    private int start_y;
    private int x;
    private int y;
    private Image image;
    private int diamondCount;

    public Player(int start_x, int start_y) {
        this.start_x = start_x;
        this.start_y = start_y;
        this.x = start_x;
        this.y = start_y;
        this.image = new Image("PLAYER_FRONT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.diamondCount = 0;
    }

    public void reset() {
        this.x = this.start_x;
        this.y = this.start_y;
    }

    public boolean checkValidMove(String dir) {
        int nx = 0;
        int ny = 0;
        if (dir.equals("right")) {
            nx = this.x + 1;
            ny = this.y;
        } else if (dir.equals("left")) {
            nx = this.x - 1;
            ny = this.y;
        } else if (dir.equals("up")) {
            nx = this.x;
            ny = this.y - 1;
        } else {
            nx = this.x;
            ny = this.y + 1;
        }

        if (Main.board.getTileLetter(nx, ny).equals("D") || Main.board.getTileLetter(nx, ny).equals("P")) {
            return true;
        } else if (Main.board.getTileLetter(nx, ny).equals("*")) {
            for (int i = 0; i < Main.diamonds.size(); i++) {
                int[] pos = {nx, ny};
                if (Main.diamonds.get(i).getX() == nx && Main.diamonds.get(i).getY() == ny) {
                    Main.diamonds.remove(Main.diamonds.get(i));
                    this.diamondCount++;
                }
            }
            return true;
        } else if (Main.board.getTileLetter(nx, ny).equals("E")) {
            this.reset();
        }
        return false;
    }

    public void move(String dir) {
        Main.board.getArray()[this.y][this.x] = new Tile(this.x, this.y, "P");
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
        Main.board.getArray()[this.y][this.x] = new Tile(this.x, this.y, "X");
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
