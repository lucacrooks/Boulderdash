import javafx.scene.image.Image;

public class Fly extends Enemy {
    private Image image;
    private String letter;
    private boolean checked;
    private String direction;

    public Fly(String enemyType, int x, int y, boolean isAlive) {
        super(enemyType, x, y, isAlive);
        this.checked = false;
        if (enemyType.equals("B")) {
            this.image = new Image("BUTTERFLY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        } else if (enemyType.equals("f")) {
            this.image = new Image("FIREFLY.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        }
        this.letter = enemyType;
        this.checked = false;
        this.direction = "S";
    }

    public void move() {
        if (this.isAlive) {
            int ox = this.x;
            int oy = this.y;

            boolean n = Main.board.getTileLetter(this.x, this.y - 1).equals("P");
            boolean s = Main.board.getTileLetter(this.x, this.y + 1).equals("P");
            boolean e = Main.board.getTileLetter(this.x + 1, this.y).equals("P");
            boolean w = Main.board.getTileLetter(this.x - 1, this.y).equals("P");

            String[][] dirs = {
                    {"N", "W", "N", "E", "S"},
                    {"S", "E", "S", "W", "N"},
                    {"E", "N", "E", "S", "W"},
                    {"W", "S", "W", "N", "E"}
            };

            int i = 0;
            while (!dirs[i][0].equals(this.direction)) {
                i++;
            }

            boolean done = false;
            int j = 1;
            while (!done && j <= 4) {
                if (dirs[i][j].equals("N")) {
                    if (Main.board.getTileLetter(this.x, this.y - 1).equals("P")) {
                        done = true;
                        this.y--;
                        this.direction = "N";
                    }
                } else if (dirs[i][j].equals("S")) {
                     if (Main.board.getTileLetter(this.x, this.y + 1).equals("P")) {
                         done = true;
                         this.y++;
                         this.direction = "S";
                     }
                } else if (dirs[i][j].equals("E")) {
                    if (Main.board.getTileLetter(this.x + 1, this.y).equals("P")) {
                        done = true;
                        x++;
                        this.direction = "E";
                    }
                } else if (dirs[i][j].equals("W")) {
                    if (Main.board.getTileLetter(this.x - 1, this.y).equals("P")) {
                        done = true;
                        x--;
                        this.direction = "W";
                    }
                }

                Main.board.swap(this.x, this.y, ox, oy);
                j++;
            }
        }
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
        if (this.checkNextTo("X")) {
            Main.player.kill();
        }
        if (this.checkNextTo("A")) {
            this.kill();
        }
    }

    public void kill() {
        Main.board.explode(this.x, this.y);
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
