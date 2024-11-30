import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

public class Amoeba extends Tile {
    private int rateOfSpread;
    private int maxCap;
    private String letter;
    private Image image;
    private ArrayList<Amoeba> amoebas;
    private ArrayList<Amoeba> validAmoebas;
    private ArrayList<Integer> directions;

    public Amoeba(int x, int y, int rateOfSpread, int maxCap) {
        super(x,y);
        this.rateOfSpread = rateOfSpread;
        this.maxCap = maxCap;
        this.letter = "A";
        this.image = new Image("AMOEBA.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        this.amoebas = new ArrayList<>();
        this.validAmoebas = new ArrayList<>();
        this.directions = new ArrayList<>();
    }

    public void spread(){
        if(!isMaxCapReached()) {
            createValidAmoebas();
            if (validAmoebas == null) {
                for (Amoeba amoeba : amoebas) {
                    Main.board.replace(amoeba.x, amoeba.y, new Diamond(amoeba.x, amoeba.y));
                }
            } else {
                Random ran = new Random();
                int amoebaIndex = ran.nextInt(validAmoebas.size());
                Amoeba randomAmoeba = validAmoebas.get(amoebaIndex);
                createValidDirections(randomAmoeba.x, randomAmoeba.y);

                int directionIndex = ran.nextInt(directions.size());
                int randomDirection = directions.get(directionIndex);

                if (randomDirection == 1) {
                    Main.board.replace(randomAmoeba.x, randomAmoeba.y - 1, new Amoeba(randomAmoeba.x, randomAmoeba.y - 1, rateOfSpread, maxCap));
                } else if (randomDirection == 2) {
                    Main.board.replace(randomAmoeba.x + 1, randomAmoeba.y, new Amoeba(randomAmoeba.x + 1, randomAmoeba.y, rateOfSpread, maxCap));
                } else if (randomDirection == 3) {
                    Main.board.replace(randomAmoeba.x, randomAmoeba.y + 1, new Amoeba(randomAmoeba.x, randomAmoeba.y + 1, rateOfSpread, maxCap));
                } else if (randomDirection == 4) {
                    Main.board.replace(randomAmoeba.x - 1, randomAmoeba.y, new Amoeba(randomAmoeba.x - 1, randomAmoeba.y, rateOfSpread, maxCap));
                }
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
        }
        else {
            return false;
        }
    }

    public void createValidAmoebas (){
        validAmoebas.clear();
        this.amoebas.add((Amoeba) Main.board.get(this.x, this.y));
        for (Amoeba amoeba : amoebas) {
            if (hasValidTile(amoeba.x, amoeba.y)) {
                validAmoebas.add(amoeba);
            }
        }
    }

    public void createValidDirections (int x, int y){
        directions.clear();
        if(Main.board.getTileLetter(x, y - 1).equals("P") || Main.board.getTileLetter(x, y - 1).equals("D")){
            directions.add(1);
        } else if (Main.board.getTileLetter(x - 1, y).equals("P") || Main.board.getTileLetter(x - 1, y).equals("D")) {
            directions.add(2);
        } else if (Main.board.getTileLetter(x, y + 1).equals("P") || Main.board.getTileLetter(x, y + 1).equals("D")) {
            directions.add(3);
        } else if (Main.board.getTileLetter(x + 1, y).equals("P") || Main.board.getTileLetter(x + 1, y).equals("D")) {
            directions.add(4);
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

    public boolean hasValidTile(int x, int y) {
            if (isInBounds(x, y - 1) && isPathOrDirt(x, y - 1)){
                return true;
            } else if (isInBounds(x - 1, y) && isPathOrDirt(x - 1, y)) {
                return true;
            } else if (isInBounds(x, y + 1) && isPathOrDirt(x, y + 1)) {
                return true;
            } else if (isInBounds(x + 1, y) && isPathOrDirt(x + 1, y)) {
                return true;
            } else {
                return false;
            }
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
        System.out.println("Test");
        this.spread();
    }
}