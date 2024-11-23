import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {

    private Tile[][] array;
    private ArrayList<Diamond> diamondsArray;

    // Loaded images
    private Image playerFrontImage = Main.player.getImage();
    private Image dirtImage = new Image("DIRT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    private Image boulderImage = new Image("BOULDER.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    private Image normalWallImage = new Image("NORMAL_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    private Image diamondImage = new Image("DIAMOND.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    private Image titaniumWallImage = new Image("TITANIUM_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    private Image exitImage = new Image("EXIT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
    private Image magicWallImage = new Image("MAGIC_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);


    public Board (String fn) {
        this.array = makeArray(fn);
        this.diamondsArray = makeDiamondsArray();
    }

    /**
     * Reads the level file then takes the data and arranges it into a 2d array.
     * @param fn name of the file to read from.
     * @author Luca Crooks.
     */
    public Tile[][] makeArray(String fn) {
        Tile[][] a = new Tile[Main.GRID_HEIGHT][Main.GRID_WIDTH];
        try {
            int row = 0;
            File f = new File(fn);
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                for (int col = 0; col < Main.GRID_WIDTH; col++) {
                    String l = data.substring(col, col + 1);
                    a[row][col] = new Tile(row, col, l);
                }
                row++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return a;
    }

    public ArrayList<Diamond> makeDiamondsArray() {
        ArrayList<Diamond> da = new ArrayList<Diamond>();
        for (int row = 0; row < Main.GRID_HEIGHT; row++) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (this.array[row][col].getLetter().equals("*")) {
                    da.add(new Diamond(col, row));
                }
            }
        }
        return da;
    }

    public ArrayList<Boulder> makeBouldersArray() {
        ArrayList<Boulder> ba = new ArrayList<Boulder>();
        for (int row = 0; row < Main.GRID_HEIGHT; row++) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (this.array[row][col].getLetter().equals("@")) {
                    ba.add(new Boulder(col, row));
                }
            }
        }
        return ba;
    }

    public ArrayList<MagicWall> makeMagicWallsArray() {
        ArrayList<MagicWall> mwa = new ArrayList<MagicWall>();
        for (int row = 0; row < Main.GRID_HEIGHT; row++) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (this.array[row][col].getLetter().equals("M")) {
                    mwa.add(new MagicWall(col, row));
                }
            }
        }
        return mwa;
    }

    public Boulder getBoulderByPos (int x, int y) {
        for (int i = 0; i < Main.boulders.size(); i++) {
            Boulder b = Main.boulders.get(i);
            if (b.getX() == x && b.getY() == y) {
                return b;
            }
        }
        return null;
    }

    public Diamond getDiamondByPos (int x, int y) {
        for (int i = 0; i < Main.diamonds.size(); i++) {
            Diamond d = Main.diamonds.get(i);
            if (d.getX() == x && d.getY() == y) {
                return d;
            }
        }
        return null;
    }

    public MagicWall getMagicWallByPos (int x, int y) {
        for (int i = 0; i < Main.magicWalls.size(); i++) {
            MagicWall mw = Main.magicWalls.get(i);
            if (mw.getX() == x && mw.getY() == y) {
                return mw;
            }
        }
        return null;
    }

    public void replace(int x, int y, Tile t) {
        this.array[y][x] = t;
    }

    public Tile[][] getArray() {
        return this.array;
    }

    public String getTileLetter(int x, int y) {
        return this.array[y][x].getLetter();
    }

    public void explode(int x, int y) {

    }

    public void draw(Canvas canvas) {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Set the background to gray.
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // We multiply by the cell width and height to turn a coordinate in our grid into a pixel coordinate.
        for (int row = 0; row < Main.GRID_HEIGHT; row++) {
            for (int col = 0; col < Main.GRID_WIDTH; col++) {
                if (array[row][col].getLetter().equals("D")) {
                    gc.drawImage(dirtImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                } else if (array[row][col].getLetter().equals("M")) {
                    gc.drawImage(magicWallImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                } else if (array[row][col].getLetter().equals("@")) {
                    gc.drawImage(boulderImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                } else if (array[row][col].getLetter().equals("X")) {
                    gc.drawImage(playerFrontImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                } else if (array[row][col].getLetter().equals("*")) {
                    gc.drawImage(diamondImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                } else if (array[row][col].getLetter().equals("W")) {
                    gc.drawImage(normalWallImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                } else if (array[row][col].getLetter().equals("T")) {
                    gc.drawImage(titaniumWallImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                } else if (array[row][col].getLetter().equals("E")) {
                    gc.drawImage(exitImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                } else if (array[row][col].getLetter().equals("X")) {
                    gc.drawImage(playerFrontImage, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
                }
            }
        }

        // Draw player at current location
        if (Main.player.getIsAlive()) {
            gc.drawImage(playerFrontImage, Main.player.getX() * Main.GRID_CELL_WIDTH, Main.player.getY() * Main.GRID_CELL_HEIGHT);
        }
    }

}
