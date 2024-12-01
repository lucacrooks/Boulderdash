public class Enemy extends Tile {
    protected String enemyType;
    protected boolean isAlive;

    public Enemy(String enemyType, int x, int y, boolean isAlive) {
        super(x, y);
        this.enemyType = enemyType;
        this.isAlive = isAlive;
    }

    protected boolean checkNextTo(String l) {
        if (Main.board.getTileLetter(this.x + 1, this.y).equals(l)
                || Main.board.getTileLetter(this.x - 1, this.y).equals(l)
                || Main.board.getTileLetter(this.x, this.y + 1).equals(l)
                || Main.board.getTileLetter(this.x, this.y - 1).equals(l)) {
            return true;
        }
        return false;
    }
}
