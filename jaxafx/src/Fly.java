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
            Boolean front;
            Boolean back;
            Boolean left;
            Boolean right;
            int ox = this.x;
            int oy = this.y;

            if (this.direction == "down") {
                front = Main.board.getTileLetter(this.x, this.y + 1).equals("P");
                back = Main.board.getTileLetter(this.x, this.y - 1).equals("P");
                right = Main.board.getTileLetter(this.x - 1, this.y).equals("P");
                left = Main.board.getTileLetter(this.x + 1, this.y).equals("P");

                if (left) {
                    this.x++;
                    this.direction = "right";
                } else if (front) {
                    this.y++;
                    this.direction = "down";
                } else if (right) {
                    this.x--;
                    this.direction = "left";
                } else if (back) {
                    this.y--;
                    this.direction = "up";
                }
                Main.board.swap(this.x, this.y, ox, oy);

            } else if (this.direction == "up") {
                front = Main.board.getTileLetter(this.x, this.y - 1).equals("P");
                back = Main.board.getTileLetter(this.x, this.y + 1).equals("P");
                right = Main.board.getTileLetter(this.x + 1, this.y).equals("P");
                left = Main.board.getTileLetter(this.x - 1, this.y).equals("P");

                if (left) {
                    this.x--;
                    this.direction = "left";
                } else if (front) {
                    this.y--;
                    this.direction = "up";
                } else if (right) {
                    this.x++;
                    this.direction = "right";
                } else if (back) {
                    this.y++;
                    this.direction = "down";
                }
                Main.board.swap(this.x, this.y, ox, oy);

            } else if (this.direction == "right") {
                front = Main.board.getTileLetter(this.x + 1, this.y).equals("P");
                back = Main.board.getTileLetter(this.x - 1, this.y).equals("P");
                right = Main.board.getTileLetter(this.x, this.y + 1).equals("P");
                left = Main.board.getTileLetter(this.x, this.y - 1).equals("P");

                if (left) {
                    this.y--;
                    this.direction = "up";
                } else if (front) {
                    this.x++;
                    this.direction = "right";
                } else if (right) {
                    this.y++;
                    this.direction = "down";
                } else if (back) {
                    this.x--;
                    this.direction = "left";
                }
                Main.board.swap(this.x, this.y, ox, oy);

            } else if (this.direction == "left") {
                front = Main.board.getTileLetter(this.x - 1, this.y).equals("P");
                back = Main.board.getTileLetter(this.x + 1, this.y).equals("P");
                right = Main.board.getTileLetter(this.x, this.y - 1).equals("P");
                left = Main.board.getTileLetter(this.x, this.y + 1).equals("P");

                if (left) {
                    this.y++;
                    this.direction = "down";
                } else if (front) {
                    this.x--;
                    this.direction = "left";
                } else if (right) {
                    this.y--;
                    this.direction = "up";
                } else if (back) {
                    this.x++;
                    this.direction = "right";
                }
                Main.board.swap(this.x, this.y, ox, oy);
            }
        }
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
