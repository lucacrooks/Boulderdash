import javafx.scene.image.Image;

public class Fly extends Enemy {
    private Image image;
    private String letter;
    private boolean checked;
    private String direction;
    private boolean setup;

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
        this.direction = "N";
        this.setup = false;
    }

    public String setDirection() {
        boolean n = Main.board.getTileLetter(this.x, this.y - 1).equals("P");
        boolean s = Main.board.getTileLetter(this.x, this.y + 1).equals("P");
        boolean e = Main.board.getTileLetter(this.x + 1, this.y).equals("P");
        boolean w = Main.board.getTileLetter(this.x - 1, this.y).equals("P");

        if (!n) {
            return "E";
        } else if (!e) {
            return "S";
        } else if (!s) {
            return "W";
        } else if (!w) {
            return "N";
        }
        return "N";
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

            boolean[][] truth = {
                    {n, w, n, e, s},
                    {s, e, s, w, n},
                    {e, n, e, s, w},
                    {w, s, w, n, e}
            };

            int[][][] coords = {
                    {{0, -1}, {-1, 0}, {0, -1}, {1, 0}, {0, 1}},
                    {{0, 1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}},
                    {{1, 0}, {0, -1}, {1, 0}, {0, 1}, {-1, 0}},
                    {{-1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 0}}
            };

            int i = 0;
            while (!dirs[i][0].equals(this.direction)) {
                i++;
            }

            int j = 1;
            boolean done = false;
            while (!done && j <= 4) {
                if (truth[i][j]) {
                    done = true;
                    this.x += coords[i][j][0];
                    this.y += coords[i][j][1];
                    this.direction = dirs[i][j];
                }
                j++;
            }

            Main.board.swap(this.x, this.y, ox, oy);
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
        if (!setup) {
            setup = true;
            this.direction = setDirection();
        }
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
