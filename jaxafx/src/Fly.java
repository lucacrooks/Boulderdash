import javafx.scene.image.Image;
public class Fly extends Enemy {
    private String enemyType;
    private Image image;
    private boolean isAlive;
    private String letter;
    private boolean checked;
    private String direction;

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
        this.direction = "down";
    }

    public void move() {
        if (this.isAlive) {
            boolean front;
            boolean back;
            boolean left;
            boolean right;
            int ox = this.x;
            int oy = this.y;

            boolean f = Main.board.getTileLetter(this.x, this.y - 1).equals("P");
            boolean b = Main.board.getTileLetter(this.x, this.y + 1).equals("P");
            boolean r = Main.board.getTileLetter(this.x + 1, this.y).equals("P");
            boolean l = Main.board.getTileLetter(this.x - 1, this.y).equals("P");

            if (this.direction == "down") {
                if (r) {
                    this.x++;
                    this.direction = "right";
                } else if (b) {
                    this.y++;
                    this.direction = "down";
                } else if (l) {
                    this.x--;
                    this.direction = "left";
                } else if (f) {
                    this.y--;
                    this.direction = "up";
                }
                Main.board.swap(this.x, this.y, ox, oy);

            } else if (this.direction == "up") {
                if (l) {
                    this.x--;
                    this.direction = "left";
                } else if (f) {
                    this.y--;
                    this.direction = "up";
                } else if (r) {
                    this.x++;
                    this.direction = "right";
                } else if (b) {
                    this.y++;
                    this.direction = "down";
                }
                Main.board.swap(this.x, this.y, ox, oy);

            } else if (this.direction == "right") {
                if (f) {
                    this.y--;
                    this.direction = "up";
                } else if (r) {
                    this.x++;
                    this.direction = "right";
                } else if (b) {
                    this.y++;
                    this.direction = "down";
                } else if (l) {
                    this.x--;
                    this.direction = "left";
                }
                Main.board.swap(this.x, this.y, ox, oy);

            } else if (this.direction == "left") {
                if (b) {
                    this.y++;
                    this.direction = "down";
                } else if (l) {
                    this.x--;
                    this.direction = "left";
                } else if (f) {
                    this.y--;
                    this.direction = "up";
                } else if (r) {
                    this.x++;
                    this.direction = "right";
                }
                Main.board.swap(this.x, this.y, ox, oy);
            }
        }
    }

    public void die() {
        explode(this.x, this.y);
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
        this.checkNextToPlayer();
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
