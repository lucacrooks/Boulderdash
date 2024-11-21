public class Diamond {
    private int x;
    private int y;
    private boolean isFalling;

    public Diamond(int x, int y) {
        this.x = x;
        this.y = y;
        this.isFalling = false;
    }

    public int canFall() {
        String below = Main.board.getArray()[this.y + 1][this.x].getLetter();
        String left = Main.board.getArray()[this.y][this.x - 1].getLetter();
        String right = Main.board.getArray()[this.y][this.x + 1].getLetter();
        String belowLeft = Main.board.getArray()[this.y + 1][this.x - 1].getLetter();
        String belowRight = Main.board.getArray()[this.y + 1][this.x + 1].getLetter();

        if (below.equals("X") && this.isFalling) {
            this.killPlayer();
        }

        if (below.equals("P")) {
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
        if (dir == 2){
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
            this.y++;
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "*"));
            this.isFalling = true;

        } else if (dir == 1) {
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
            this.x--;
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "*"));

        } else if (dir == 3) {
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
            this.x++;
            Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "*"));
        }
    }

    public void killPlayer() {
        Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
        this.y++;
        Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "*"));
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
