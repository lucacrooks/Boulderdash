import javafx.scene.image.Image;

public class Player extends Tile {
    private int start_x;
    private int start_y;
    private Image image;
    private int diamondCount;
    private boolean isAlive;
    private String letter;

    public Player(int x, int y) {
        super(x, y);
        this.start_x = this.x;
        this.start_y = this.y;
        this.letter = "X";
        this.image = new Image("PLAYER_FRONT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.diamondCount = 0;
        this.isAlive = true;
    }

    public void reset() {
        this.x = this.start_x;
        this.y = this.start_y;
    }

    public boolean checkValidMove(String dir) {
        if (this.isAlive == false) {
            return false;
        }

        int nx = 0;
        int ny = 0;
        if (dir.equals("right")) {
            this.image = new Image("PLAYER_RIGHT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            nx = this.x + 1;
            ny = this.y;
        } else if (dir.equals("left")) {
            this.image = new Image("PLAYER_LEFT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            nx = this.x - 1;
            ny = this.y;
        } else if (dir.equals("up")) {
            this.image = new Image("PLAYER_BACK.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            nx = this.x;
            ny = this.y - 1;
        } else {
            this.image = new Image("PLAYER_FRONT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
            nx = this.x;
            ny = this.y + 1;
        }

        //can make these 2 into one
        if (dir.equals("left") && Main.board.getTileLetter(nx, ny).equals("@") && Main.board.getTileLetter(nx - 1, ny).equals("P")) {
            Boulder b = (Boulder) Main.board.get(nx, ny);
            b.push("left");
            return true;
        } else if (dir.equals("right") && Main.board.getTileLetter(nx, ny).equals("@") && Main.board.getTileLetter(nx + 1, ny).equals("P")) {
            Boulder b = (Boulder) Main.board.get(nx, ny);
            b.push("right");
            return true;
        }
        else if (Main.board.getTileLetter(nx, ny).equals("D") || Main.board.getTileLetter(nx, ny).equals("P")) {
            return true;
        } else if (Main.board.getTileLetter(nx, ny).equals("*")) {
            this.diamondCount++;
            return true;
        } else if (Main.board.getTileLetter(nx, ny).equals("E")) {
            this.reset();
        }
        return false;
    }

    public void move(String dir) {
        Main.board.replace(this.x, this.y, new Player(this.x, this.y));
        if (dir.equals("right")) {
            Main.board.swap(this.x, this.y, this.x + 1, this.y);
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.x++;
            Main.player.setX(this.x);
        } else if (dir.equals("left")){
            Main.board.swap(this.x, this.y, this.x - 1, this.y);
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.x--;
            Main.player.setX(this.x);
        } else if (dir.equals("up")){
            Main.board.swap(this.x, this.y, this.x, this.y - 1);
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.y--;
            Main.player.setY(this.y);
        } else {
            Main.board.swap(this.x, this.y, this.x, this.y + 1);
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.y++;
            Main.player.setY(this.y);
        }
    }

    public void dig() {
        Main.board.replace(this.x, this.y, Main.player);
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public void setIsAlive(boolean b) {
        this.isAlive = b;
    }

    public void kill() {
        this.isAlive = false;
        Main.board.explode("X", this.x, this.y);
    }

    @Override
    public Image getImage () {
        return this.image;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    /**
     * move the players x and y position to the new ones passed in.
     * @param x current x pos of player.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * move the players x and y position to the new ones passed in.
     * @param y current y pos of player.
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void update(String dir) {
        if (this.checkValidMove(dir)) {
            this.move(dir);
            this.dig();
        }
    }

}
