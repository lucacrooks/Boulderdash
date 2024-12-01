import javafx.scene.image.Image;

public class Tile {
    protected int x;
    protected int y;

    public Tile (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public String getLetter() {
        return null;
    }

    public void update() {
    }

    public void update(String dir) {
    }

    public Image getImage() {
        return null;
    }

    public boolean getChecked() {
        return true;
    }

    public void setChecked(boolean checked) {
    }

}