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
        this.checked = false;
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
                System.out.println("going up");
                this.y--;
            } else if (Main.board.getTileLetter(this.x - 1, this.y).equals("P") && !Main.board.getTileLetter(this.x, this.y - 1).equals("P")) {
                Main.board.swap(this.x, this.y, this.x - 1, this.y);
                System.out.println("going left");
                this.x--;
            } else if (Main.board.getTileLetter(this.x, this.y + 1).equals("P") && !Main.board.getTileLetter(this.x - 1, this.y).equals("P")) {
                Main.board.swap(this.x, this.y, this.x, this.y + 1);
                System.out.println("going down");
                this.y++;
            } else if (Main.board.getTileLetter(this.x + 1, this.y).equals("P") && !Main.board.getTileLetter(this.x, this.y + 1).equals("P")) {
                Main.board.swap(this.x, this.y, this.x + 1, this.y);
                System.out.println("going right");
                this.x++;
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
    public void update() {
        this.move();
        checkPlayer(this.x, this.y);
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
