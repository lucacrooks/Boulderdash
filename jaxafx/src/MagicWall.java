import javafx.scene.image.Image;

public class MagicWall extends Tile{
    private String letter;
    private Image image;
    private String contains;
    private boolean checked;

    public MagicWall(int x, int y) {
        super(x, y);
        this.letter = "M";
        this.image = new Image("MAGIC_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.contains = "";
        this.checked = false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    public String getContains () {
        return this.contains;
    }

    public void setContains (String c) {
        this.contains = c;
    }

    public void transform () {

        if (this.contains.equals("@")) {
            this.contains = "*";
        } else if (this.contains.equals("*")) {
            this.contains = "@";
        }

        String l = Main.board.getTileLetter(this.x, this.y + 1);
        boolean enemyBelow = l.equals("f") || l.equals("B") || l.equals("F");

        if (l.equals("P") || enemyBelow) {

            if (this.contains.equals("*")) {
                Main.board.replace(this.x, this.y + 1, new Diamond(this.x, this.y + 1));
                Diamond d = (Diamond) Main.board.get(this.x, this.y + 1);
                d.setChecked(true);
            } else if (this.contains.equals("@")) {
                Main.board.replace(this.x, this.y + 1, new Boulder(this.x, this.y + 1));
                Boulder b = (Boulder) Main.board.get(this.x, this.y + 1);
                b.setChecked(true);
            }
        }
    }

    public void reset () {
        this.contains = "";
    }

    @Override
    public void update() {
        this.transform();
        this.reset();
    }

    @Override
    public Image getImage () {
        return this.image;
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
