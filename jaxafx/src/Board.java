import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {

    private Tile[][] array;

    // Loaded images
    private Image playerFrontImage;
    private Image dirtImage;
    private Image boulderImage;
    private Image normalWallImage;
    private Image diamondImage;
    private Image titaniumWallImage;

    public Board (String fn) {
        this.array = make(fn);

        // Load images. Note we use png images with a transparent background.
        playerFrontImage = new Image("PLAYER_FRONT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        dirtImage = new Image("DIRT.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        boulderImage = new Image("BOULDER.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        normalWallImage = new Image("NORMAL_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        diamondImage = new Image("DIAMOND.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);
        titaniumWallImage = new Image("TITANIUM_WALL.png", Main.GRID_CELL_WIDTH, Main.GRID_CELL_HEIGHT, false, false);

    }

    public Tile[][] make(String fn) {
        Tile[][] a = new Tile[Main.GRID_HEIGHT][Main.GRID_WIDTH];
        try {
            int row = 0;
            File f = new File(fn);
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                for (int col = 0; col < Main.GRID_WIDTH; col++) {
                    a[row][col] = new Tile(row, col, data.substring(col, col + 1));
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
        this.array[x][y] = t;
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
                }
            }
        }

        // Draw player at current location
        //gc.drawImage(playerFrontImage, playerX * Main.GRID_CELL_WIDTH, playerY * Main.GRID_CELL_HEIGHT);
    }

}
