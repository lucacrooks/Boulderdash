public abstract class Tile {
    private int x;
    private int y;
    private String letter;

    public Tile (int x, int y, String letter) {
        this.x = x;
        this.y = y;
        this.letter = letter;
    }

    public abstract boolean isTraversable();
    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public String getLetter() {
        return this.letter;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setL(String letter) {
        this.letter = letter;
    }
}