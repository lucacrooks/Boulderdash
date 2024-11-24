import javafx.scene.image.Image;
public class Fly extends Enemy {
    private int x;
    private int y;
    private String enemyType;
    private Image image;
    private boolean isAlive;
    private String letter;

    public Fly(String enemyType, int x, int y) {
        super(enemyType, x, y);
        this.isAlive = true;
        if (enemyType.equals("B")) {
            this.image = new Image("BUTTERFLY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (enemyType.equals("f")) {
            this.image = new Image("FIREFLY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        }
        this.letter = enemyType;
    }

    public void Move() {
        if (!isStuck() && isAlive) {
            if (Main.board.getTileLetter(this.x, this.y - 1).equals("P") && !Main.board.getTileLetter(this.x + 1, this.y).equals("P")) {
                Main.board.swap(this.x, this.y, this.x, this.y - 1);
            } else if (Main.board.getTileLetter(this.x - 1, this.y).equals("P") && !Main.board.getTileLetter(this.x, this.y - 1).equals("P")) {
                Main.board.swap(this.x, this.y, this.x - 1, this.y);
            } else if (Main.board.getTileLetter(this.x, this.y + 1).equals("P") && !Main.board.getTileLetter(this.x - 1, this.y).equals("P")) {
                Main.board.swap(this.x, this.y, this.x, this.y + 1);
            } else if (Main.board.getTileLetter(this.x + 1, this.y).equals("P") && !Main.board.getTileLetter(this.x, this.y + 1).equals("P")) {
                Main.board.swap(this.x, this.y, this.x + 1, this.y);
            } else {
                Main.board.swap(this.x, this.y, this.x + 1, this.y);
            }
        }

    }

    private boolean isStuck() {
        return !Main.board.getTileLetter(this.x + 1, this.y).equals("P")
                && !Main.board.getTileLetter(this.x - 1, this.y).equals("P")
                && !Main.board.getTileLetter(this.x, this.y + 1).equals("P")
                && !Main.board.getTileLetter(this.x, this.y - 1).equals("P");
    }

    public void die() {
        explode(enemyType, this.x, this.y);
        this.isAlive = false;
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
    public void update(String dir) {
        this.Move();
        checkPlayer(this.x, this.y);
    }
}
