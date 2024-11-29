import javafx.scene.image.Image;
import java.util.Random;

public class Frog extends Enemy {
    private Image image;
    private String letter;
    private boolean checked;
    private boolean skipFrame;
    private boolean moved;

    public Frog(String enemyType, int x, int y, boolean isAlive) {
        super(enemyType, x, y, isAlive);
        this.checked = false;
        this.image = new Image("FROG.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.letter = enemyType;
        this.skipFrame = false;
        this.moved = false;
    }

    public void checkMove(int tx, int ty) {
        if (Main.board.getTileLetter(tx, ty).equals("P")) {
            Main.board.swap(this.x, this.y, tx, ty);
            this.x = tx;
            this.y = ty;
            this.moved = true;
        }
    }

    public void move() {
        if (skipFrame) {
            skipFrame = false;
        } else if (this.isAlive && !this.skipFrame) {
            skipFrame = true;
            int px = Main.player.getX();
            int py = Main.player.getY();
            int dx = Math.abs(this.x - px);
            int dy = Math.abs(this.y - py);

            this.moved = false;
            boolean xLock = false;
            boolean yLock = false;

            if (dx >= dy) {
                if (px >= this.x) {
                    this.checkMove(this.x + 1, this.y);
                } else if (px <= this.x) {
                    this.checkMove(this.x - 1, this.y);
                }
                if (!this.moved) {
                    xLock = true;
                }
            }
            if (dy >= dx && !moved || xLock) {
                if (py > this.y) {
                    this.checkMove(this.x, this.y + 1);
                } else {
                    this.checkMove(this.x, this.y - 1);
                }
                if (!this.moved) {
                    yLock = true;
                }
            }
            if (dx >= dy && !moved || yLock) {
                if (px >= this.x) {
                    this.checkMove(this.x + 1, this.y);
                } else if (px <= this.x) {
                    this.checkMove(this.x - 1, this.y);
                }
            }
        }
    }

    public void update() {
        this.move();
        this.checkNextToPlayer();
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
