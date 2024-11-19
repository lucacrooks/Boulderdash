public class Tile {
    private int x;
    private int y;
    private String letter;

    public Tile (int x, int y, String letter) {
        this.x = x;
        this.y = y;
        this.letter = letter;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getLetter() {
        return letter;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setL(String letter) {
        this.letter = letter;
    }
}