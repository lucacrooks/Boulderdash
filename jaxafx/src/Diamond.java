import javafx.scene.image.Image;

public class Diamond extends Tile {
    private String letter;
    private Image image;
    private boolean isFalling;
    private boolean checked;

    public Diamond(int x, int y) {
        super(x, y);
        this.letter = "*";
        this.image = new Image("DIAMOND.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.isFalling = false;
        this.checked = false;
    }

    public int canFall() {
        String below = Main.board.getArray()[this.y + 1][this.x].getLetter();
        String left = Main.board.getArray()[this.y][this.x - 1].getLetter();
        String right = Main.board.getArray()[this.y][this.x + 1].getLetter();
        String belowLeft = Main.board.getArray()[this.y + 1][this.x - 1].getLetter();
        String belowRight = Main.board.getArray()[this.y + 1][this.x + 1].getLetter();

        if (below.equals("X") && this.isFalling) {
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.killPlayer();
        } else if ((below.equals("B") || below.equals("f") || below.equals("F")) && this.isFalling) {
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            Main.board.explodeDiamond(this.x, this.y + 1);
        } else if (below.equals("P")) {
            return 2;
        } else if (below.equals("@") || below.equals("W") || below.equals("*")) {
            if (left.equals("P") && belowLeft.equals("P")) {
                return 1;
            }
            if (right.equals("P") && belowRight.equals("P")) {
                return 3;
            }
        } else if (below.equals("M")) {
            return 4;
        }
        return 0;
    }

    public void fall(int dir) {
        this.isFalling = false;
        if (dir == 2){
            Main.board.swap(this.x, this.y, this.x, this.y + 1);
            this.y++;
            this.isFalling = true;

        } else if (dir == 4) {
            MagicWall mw = (MagicWall) Main.board.get(this.x, this.y + 1);
            mw.setContains("*");
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.isFalling = true;

        } else if (dir == 1) {
            Main.board.swap(this.x, this.y,this.x - 1, this.y);
            this.x--;

        } else if (dir == 3) {
            Main.board.swap(this.x, this.y,this.x + 1, this.y);
            this.x++;
        }
    }

    public void killPlayer() {
        Main.board.swap(this.x, this.y, this.x, this.y + 1);
        Main.board.replace(this.x, this.y, new Path(this.x, this.y));
        this.y++;
        Main.player.kill();
    }

    @Override
    public void update() {
        this.fall(this.canFall());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
    public boolean getChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
