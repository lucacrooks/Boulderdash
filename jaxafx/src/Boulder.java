import javafx.scene.image.Image;

public class Boulder extends Tile {
    private String letter;
    private Image image;
    private boolean isFalling;
    private boolean checked;

    public Boulder(int x, int y) {
        super(x, y);
        this.letter = "@";
        this.image = new Image("BOULDER.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.isFalling = false;
        this.checked = false;
    }

    public void push(String dir) {
        if (dir.equals("left")) {
            Main.board.swap(this.x, this.y, this.x - 1, this.y);
            this.x--;
            this.checked = true;
        } else {
            Main.board.swap(this.x, this.y, this.x + 1, this.y);
            this.x++;
            this.checked = true;
        }
    }

    public int canFall() {
        String below = Main.board.getTileLetter(this.x, this.y + 1);
        String left = Main.board.getTileLetter(this.x - 1, this.y);
        String right = Main.board.getTileLetter(this.x + 1, this.y);
        String belowLeft = Main.board.getTileLetter(this.x - 1, this.y + 1);
        String belowRight = Main.board.getTileLetter(this.x + 1, this.y + 1);

        if (below.equals("X") && this.isFalling) {
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.killPlayer();
        } else if (((below.equals("B") || below.equals("f") || below.equals("F"))) && this.isFalling) {
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
        if (dir == 2) {
            Main.board.swap(this.x, this.y, this.x, this.y + 1);
            this.y++;
            this.isFalling = true;

        } else if (dir == 4) {
            MagicWall mw = (MagicWall) Main.board.get(this.x, this.y + 1);
            mw.setContains("@");
            Main.board.replace(this.x, this.y, new Path(this.x, this.y));
            this.isFalling = true;

        } else if (dir == 1) {
            Main.board.swap(this.x, this.y, this.x - 1, this.y);
            this.x--;

        } else if (dir == 3) {
            Main.board.swap(this.x, this.y, this.x + 1, this.y);
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

//version 1 of boulder (adapted further to be cohesive with rest of code)
/*public class Boulder {
    private int x;
    private int y;
    private boolean isFalling;

    public Boulder(int x, int y, boolean isFalling) {
        this.x = x;
        this.y = y;
        this.isFalling = false;
    }

    public void fall(Tile[][] level) {
        Tile currentTile = level[x][y];
        Tile below = level[x][y + 1];
        if(below.getLetter().equals("P")) {
            isFalling = true;
            currentTile.setL("P");
            below.setL("@");
        } else if (isFalling && (below.getLetter().equals("X") || below.getLetter().equals("B") ||
                below.getLetter().equals("f") || below.getLetter().equals("F"))) {
            currentTile.setL("P");
            below.setL("P");
        }
        isFalling = false;
    }

    public void push(){

    }
    public void roll(Tile[][] level) {
        Tile below = level[x][y + 1];
        Tile right = level[x + 1][y];
        Tile left = level[x - 1][y];
        Tile belowRight = level[x + 1][y + 1];
        Tile belowLeft = level[x - 1][y + 1];

        if (below.getLetter().equals("W") || below.getLetter().equals("*") || below.getLetter().equals("@")) {
            if (right.getLetter().equals("P") && belowRight.getLetter().equals("P")) {
                right.setL("@");
                fall(level);
            } else if (left.getLetter().equals("P") && belowLeft.getLetter().equals("P")) {
                left.setL("@");
                fall(level);
            }
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}*/
