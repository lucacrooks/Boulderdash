public class MagicWall{
    private int x;
    private int y;
    private String contains;

    public MagicWall(int x, int y) {
        this.x = x;
        this.y = y;
        this.contains = "";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getContains () {
        return this.contains;
    }

    public void setContains (String c) {
        this.contains = c;
    }

    public void remove() {
        if (this.contains.equals("@")) {
            Main.boulders.remove(Main.board.getBoulderByPos(this.x, this.y - 1));
        } else if (this.contains.equals("*")) {
            Main.diamonds.remove(Main.board.getDiamondByPos(this.x, this.y - 1));
        }
    }

    public void transform () {

        if (this.contains.equals("@")) {
            this.contains = "*";
        } else if (this.contains.equals("*")) {
            this.contains = "@";
        }

        if (Main.board.getTileLetter(this.x, this.y + 1).equals("P") && !this.contains.equals("")) {
            Main.board.replace(this.x, this.y + 1, new Tile(this.x, this.y + 1, this.contains));
            if (this.contains.equals("*")) {
                Main.diamonds.add(new Diamond(this.x, this.y + 1));
            } else if (this.contains.equals("@")) {
                Main.boulders.add(new Boulder(this.x, this.y + 1));
            }
        }
    }

    public void reset () {
        this.contains = "";
    }

    public void update() {
        this.remove();
        this.transform();
        this.reset();
    }

}
