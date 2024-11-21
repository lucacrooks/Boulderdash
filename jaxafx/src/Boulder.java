public class Boulder {
    private int x;
    private int y;
    private boolean isFalling;

    public Boulder(int x, int y) {
        this.x = x;
        this.y = y;
        this.isFalling = false;
    }

    public void push(String dir) {
        if (dir.equals("left")) {
            this.x--;
        } else {
            this.x++;
        }
        Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "@"));
    }

    public int canFall() {
        String below = Main.board.getArray()[this.y + 1][this.x].getLetter();
        String left = Main.board.getArray()[this.y][this.x - 1].getLetter();
        String right = Main.board.getArray()[this.y][this.x + 1].getLetter();
        String belowLeft = Main.board.getArray()[this.y + 1][this.x - 1].getLetter();
        String belowRight = Main.board.getArray()[this.y + 1][this.x + 1].getLetter();

        if (below.equals("X") && this.isFalling) {
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
            Main.board.explode(this.x, this.y);
            this.killPlayer();
        } else if (below.equals("P") || below.equals("f") || below.equals("F") || below.equals("B")) {
            return 2;
        } else if (below.equals("@") || below.equals("W") || below.equals("*")) {
            if (left.equals("P") && belowLeft.equals("P")) {
                return 1;
            }
            if (right.equals("P") && belowRight.equals("P")) {
                return 3;
            }
        }
        return 0;
    }

    public void fall(int dir) {
        this.isFalling = false;
        if (dir == 2) {
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
            this.y++;
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "@"));
            this.isFalling = true;

        } else if (dir == 1) {
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
            this.x--;
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "@"));

        } else if (dir == 3) {
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
            this.x++;
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "@"));
        }
    }

    public void killPlayer() {
        Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
        this.y++;
        Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "@"));
        Main.player.setIsAlive(false);
    }

    public void update() {
        this.fall(this.canFall());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

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
