import javafx.scene.image.Image;
public class Fly extends Enemy {
    private String enemyType;
    private Image image;
    private boolean isAlive;
    private String letter;
    private boolean checked;

    public Fly(String enemyType, int x, int y) {
        super(enemyType, x, y);
        this.isAlive = true;
        if (enemyType.equals("B")) {
            this.image = new Image("BUTTERFLY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (enemyType.equals("f")) {
            this.image = new Image("FIREFLY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        }
        this.letter = enemyType;
        this.checked = false;
    }

    public void move() {
        if (!isStuck() && isAlive) {
            if (Main.board.getTileLetter(this.x, this.y - 1).equals("P") && !Main.board.getTileLetter(this.x + 1, this.y).equals("P")) {
                Main.board.swap(this.x, this.y, this.x, this.y - 1);
                System.out.println("doing up");
                this.y--;
            } else if (Main.board.getTileLetter(this.x - 1, this.y).equals("P") && !Main.board.getTileLetter(this.x, this.y - 1).equals("P")) {
                Main.board.swap(this.x, this.y, this.x - 1, this.y);
                this.x--;
                System.out.println("doing left");
            } else if (Main.board.getTileLetter(this.x, this.y + 1).equals("P") && !Main.board.getTileLetter(this.x - 1, this.y).equals("P")) {
                Main.board.swap(this.x, this.y, this.x, this.y + 1);
                System.out.println("doing down");
                this.y++;
            } else if (Main.board.getTileLetter(this.x + 1, this.y).equals("P") && !Main.board.getTileLetter(this.x, this.y + 1).equals("P")) {
                Main.board.swap(this.x, this.y, this.x + 1, this.y);
                System.out.println("doing right");
                this.x++;
            } else {
                Main.board.swap(this.x, this.y, this.x + 1, this.y);
                System.out.println("doing5");
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
    public void update() {
        this.move();
        checkPlayer(this.x, this.y);
    }

    @Override
    public boolean getChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean c) {
        this.checked = c;
    }
}
