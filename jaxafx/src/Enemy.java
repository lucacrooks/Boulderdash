import javafx.scene.image.Image;

import java.util.ArrayList;
public class Enemy extends Tile {
    protected String enemyType;

    public Enemy(String enemyType, int x, int y) {
        super(x, y);
        this.enemyType = enemyType;
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
        ArrayList<Integer> surroundings = new ArrayList<Integer>();

        surroundings.add(x + 1);
        surroundings.add(y);

        surroundings.add(x - 1);
        surroundings.add(y);

        surroundings.add(x);
        surroundings.add(y + 1);

        surroundings.add(x);
        surroundings.add(y - 1);

        return surroundings;
    }
}
