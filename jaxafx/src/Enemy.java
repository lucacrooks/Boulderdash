import javafx.scene.image.Image;

import java.util.ArrayList;
public class Enemy extends Tile {
    protected String enemyType;

    public Enemy(String enemyType, int x, int y) {
        super(x, y);
        this.enemyType = enemyType;
    }
    public void explode(int x, int y) {
        Main.board.explode(x, y);

    }

    protected void checkNextToPlayer() {
        if (Main.board.getTileLetter(this.x + 1, this.y).equals("X")
                || Main.board.getTileLetter(this.x - 1, this.y).equals("X")
                || Main.board.getTileLetter(this.x, this.y + 1).equals("X")
                || Main.board.getTileLetter(this.x, this.y - 1).equals("X")) {
            Main.player.kill();
        }
    }
}
