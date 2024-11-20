public class Boulder {
    private int x;
    private int y;
    private boolean isFalling;

    public Boulder(int x, int y, boolean isFalling) {
        this.x = x;
        this.y = y;
        this.isFalling = false;
    }

    public void fall(Tile[][] level) {
        Tile currentTile = level[x][y];
        Tile below = level[x][y + 1];
        if(below.getLetter().equals("P")) {
            isFalling = true;
            currentTile.setL("P");
            below.setL("@");
        } else if (isFalling && (below.getLetter().equals("X") || below.getLetter().equals("B") ||
                below.getLetter().equals("f") || below.getLetter().equals("F"))) {
            currentTile.setL("P");
            below.setL("P");
        }
        isFalling = false;
    }

    public void push(){

    }
    public void roll(Tile[][] level) {
        Tile below = level[x][y + 1];
        Tile right = level[x + 1][y];
        Tile left = level[x - 1][y];
        Tile belowRight = level[x + 1][y + 1];
        Tile belowLeft = level[x - 1][y + 1];

        if (below.getLetter().equals("W") || below.getLetter().equals("*") || below.getLetter().equals("@")) {
            if (right.getLetter().equals("P") && belowRight.getLetter().equals("P")) {
                right.setL("@");
                fall(level);
            } else if (left.getLetter().equals("P") && belowLeft.getLetter().equals("P")) {
                left.setL("@");
                fall(level);
            }
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
