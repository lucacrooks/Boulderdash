public class Diamond {
    private int x;
    private int y;
    public Diamond(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean canFall() {
        if (Main.board.getArray()[this.y + 1][this.x].getLetter().equals("P")) {
            return true;
        }
        return false;
    }

    public void fall() {
        Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "P"));
        this.y++;
        Main.board.replace(this.x, this.y, new Tile(this.x, this.y, "*"));
    }

    public void update() {
        if (this.canFall()) {
            this.fall();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
