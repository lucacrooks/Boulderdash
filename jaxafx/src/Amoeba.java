import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

public class Amoeba extends Tile {
    private int maxCap;
    private String letter;
    private Image image;
    private ArrayList<Amoeba> amoebas;
    private ArrayList<Tile> validTiles;
    private boolean locked;
    private int elapsed;

    public Amoeba(int x, int y, int maxCap, int elapsed) {
        super(x,y);
        this.maxCap = maxCap;
        this.letter = "A";
        this.image = new Image("AMOEBA.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.amoebas = new ArrayList<>();
        this.validTiles = new ArrayList<>();
        this.locked = false;
        this.elapsed = elapsed;
    }

    public void makeAmoebasArray() {
        this.amoebas.clear();
        for (int row = Main.GRID_HEIGHT - 1; row >= 0; row--) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (Main.board.get(row, col).getLetter().equals("A")) {
                    this.amoebas.add((Amoeba) Main.board.get(row, col));
                }
            }
        }
    }

    public void makeValidTiles() {
        this.validTiles.clear();
        for (Amoeba amoeba : amoebas) {
            int ax = amoeba.getX();
            int ay = amoeba.getY();

            if (isInBounds(ax, ay - 1) && isPathOrDirt(ax, ay - 1)){
                validTiles.add(Main.board.get(ax, ay - 1));
            } else if (isInBounds(ax - 1, ay) && isPathOrDirt(ax - 1, ay)) {
                validTiles.add(Main.board.get(ax - 1, ay));
            } else if (isInBounds(ax, ay + 1) && isPathOrDirt(ax, ay + 1)){
                validTiles.add(Main.board.get(ax, ay + 1));
            } else if (isInBounds(ax + 1, ay) && isPathOrDirt(ax + 1, ay)) {
                validTiles.add(Main.board.get(ax + 1, ay));
            }
        }
    }

    public void spread(){
        if(!isMaxCapReached()) {
            if (validTiles.isEmpty()) {
                for (Amoeba amoeba : amoebas) {
                    Main.board.replace(amoeba.x, amoeba.y, new Diamond(amoeba.x, amoeba.y));
                }
            } else {
                Random ran = new Random();
                int amoebaIndex = ran.nextInt(validTiles.size());
                Tile randomTile = validTiles.get(amoebaIndex);
                int rtx = randomTile.getX();
                int rty = randomTile.getY();

                Main.board.replace(rtx, rty, new Amoeba(rtx, rty, this.maxCap, this.elapsed));
                Amoeba a = (Amoeba) Main.board.get(rtx, rty);
                a.setLocked(true);

                this.setAllLocked(true);
            }
        }
        else {
            for (Amoeba amoeba : amoebas) {
                Main.board.replace(amoeba.x, amoeba.y, new Boulder(amoeba.x, amoeba.y));
            }
        }
    }

    public boolean isMaxCapReached() {
        if (amoebas.size() == maxCap){
            return true;
        } else {
            return false;
        }
    }

    public boolean isInBounds(int x, int y) {
        if (x >= 0 && x < Main.GRID_WIDTH && y >= 0 && y < Main.GRID_HEIGHT) {
            return true;
        }
        return false;
    }

    public boolean isPathOrDirt(int x, int y) {
        return Main.board.getTileLetter(x, y).equals("P") || Main.board.getTileLetter(x, y).equals("D");
    }

    public void setAllLocked(boolean l) {
        for (int row = Main.GRID_HEIGHT - 1; row >= 0; row--) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (Main.board.get(row, col).getLetter().equals("A")) {
                    Amoeba a = (Amoeba) Main.board.get(row, col);
                    a.setLocked(l);
                }
            }
        }
    }

    public void setLocked(boolean l) {
        this.locked = l;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public String getLetter() {
        return this.letter;
    }

    @Override
    public void update() {
        this.elapsed++;
        if (this.elapsed % 1 == 0) {
            this.makeAmoebasArray();
            this.makeValidTiles();
            if (!this.locked) {
                this.spread();
            }
        } else {
            this.setAllLocked(true);
        }
    }
}