public class Enemy extends Tile {
    protected String enemyType;
    protected boolean isAlive;

    public Enemy(String enemyType, int x, int y, boolean isAlive) {
        super(x, y);
        this.enemyType = enemyType;
        this.isAlive = isAlive;
    }

    protected void checkNextToPlayer() {
        if (Main.board.getTileLetter(this.x + 1, this.y).equals("X")
                || Main.board.getTileLetter(this.x - 1, this.y).equals("X")
                || Main.board.getTileLetter(this.x, this.y + 1).equals("X")
                || Main.board.getTileLetter(this.x, this.y - 1).equals("X")) {
            Main.player.kill();
        }
    }

    protected void kill() {
        this.isAlive = false;
    }

}
