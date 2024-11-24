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

    public Board (String fn) {
        this.array = makeArray(fn);
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
            while (row < Main.GRID_HEIGHT) {
                String data = reader.nextLine();
                for (int col = 0; col < Main.GRID_WIDTH; col++) {
                    String l = data.substring(col, col + 1);
                    if (l.equals("X")) {
                        a[row][col] = new Player(col, row);
                    } else if (l.equals("*")) {
                        a[row][col] = new Diamond(col, row);
                    } else if (l.equals("@")) {
                        a[row][col] = new Boulder(col, row);
                    } else if (l.equals("M")) {
                        a[row][col] = new MagicWall(col, row);
                    } else if (l.equals("D")) {
                        a[row][col] = new Dirt(col, row);
                    } else if (l.equals("P")) {
                        a[row][col] = new Path(col, row);
                    } else if (l.equals("W")) {
                        a[row][col] = new Wall(col, row);
                    } else if (l.equals("T")) {
                        a[row][col] = new TitaniumWall(col, row);
                    } else if (l.equals("E")) {
                        a[row][col] = new Exit(col, row);
                    }
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

    public void replace(int x, int y, Tile t) {
        this.array[y][x] = t;
    }

    public void swap (int x, int y, int x2, int y2) {
        Tile temp = this.array[y][x];
        this.array[y][x] = this.array[y2][x2];
        this.array[y2][x2] = temp;
    }

    public Tile[][] getArray() {
        return this.array;
    }

    public String getTileLetter(int x, int y) {
        return this.array[y][x].getLetter();
    }

    public Tile get(int x, int y) {
        return this.array[y][x];
    }

    public void explode(int x, int y) {
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                String l = this.array[y + dy][x + dx].getLetter();
                if (!l.equals("T")) {
                    this.replace(x + dx, y + dy, new Path(x + dx, y + dy));
                }
            }
        }
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
                Image img = array[row][col].getImage();
                gc.drawImage(img, col * Main.GRID_CELL_WIDTH, row * Main.GRID_CELL_HEIGHT);
            }
        }

        // Draw player at current location
        if (Main.player.getIsAlive()) {
            gc.drawImage(Main.player.getImage(), Main.player.getX() * Main.GRID_CELL_WIDTH, Main.player.getY() * Main.GRID_CELL_HEIGHT);
        }
    }
}
