import javafx.scene.image.Image;

import java.util.ArrayList;
public class Enemy extends Tile {
    protected String enemyType;
    protected int x;
    protected int y;
    public Enemy(String enemyType, int x, int y) {
        super(x, y);
        this.enemyType = enemyType;
        this.x = x;
        this.y = y;
    }
    public void explode(String enemyType, int x, int y) {
        Main.board.explode(enemyType, x, y);

    }
    protected void checkPlayer(int x, int y) {
        int playerX = Main.player.getX();
        int playerY = Main.player.getY();
        ArrayList<Integer> surroundings = getSurroundings(x, y);
        for (int i = 0; i < surroundings.size(); i = i + 2) {
            if ((playerX == surroundings.get(i)) && (playerY == surroundings.get(i+1))) {
                Main.player.kill();
            }
        }
    }
    private ArrayList<Integer> getSurroundings(int x, int y) {
        x = x-1;
        y = y-1;
        ArrayList<Integer> surroundings = new ArrayList<Integer>();
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                surroundings.add(x+i);
                surroundings.add(y+j);
            }
        }
        return surroundings;
    }
}
