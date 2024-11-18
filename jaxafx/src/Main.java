import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Sample application that demonstrates the use of JavaFX Canvas for a Game.
 * This class is intentionally not structured very well. This is just a starting point to show
 * how to draw an image on a canvas, respond to arrow key presses, use a tick method that is
 * called periodically, and use drag and drop.
 * 
 * Do not build the whole application in one file. This file should probably remain very small.
 *
 * @author Liam O'Reilly
 */
public class Main extends Application {

	// The width and height (in pixels) of each cell that makes up the game.
	private static final int GRID_CELL_WIDTH = 30;
	private static final int GRID_CELL_HEIGHT = 30;
	
	// The width of the grid in number of cells.
	private static final int GRID_WIDTH = 40;
	private static final int GRID_HEIGHT = 22;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = GRID_WIDTH * GRID_CELL_WIDTH;
	private static final int CANVAS_HEIGHT = GRID_HEIGHT * GRID_CELL_HEIGHT;

	// The dimensions of the window
	private static final int WINDOW_WIDTH = CANVAS_WIDTH + 100;
	private static final int WINDOW_HEIGHT = CANVAS_HEIGHT + 100;
	
	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	private Canvas canvas;
		
	// Loaded images
	private Image playerFrontImage;
	private Image dirtImage;
	private Image boulderImage;
	private Image normalWallImage;
	private Image diamondImage;
	private Image titaniumWallImage;
	
	// X and Y coordinate of player on the grid.
	private int playerX = 3;
	private int playerY = 2;
	
	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline;

	// level array creation
	public String[][] level = ReadLevelFile("src/LEVEL1.txt");
	
	/**
	 * Setup the new application.
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {
		// Load images. Note we use png images with a transparent background.
		playerFrontImage = new Image("PLAYER_FRONT.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, false, false);
		dirtImage = new Image("DIRT.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, false, false);
		boulderImage = new Image("BOULDER.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, false, false);
		normalWallImage = new Image("NORMAL_WALL.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, false, false);
		diamondImage = new Image("DIAMOND.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, false, false);
		titaniumWallImage = new Image("TITANIUM_WALL.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, false, false);

		// Build the GUI 
		Pane root = buildGUI();
		
		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		// Register an event handler for key presses.
		// This causes the processKeyEvent method to be called each time a key is pressed.
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
				
		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method every half a second.
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick()));
		 // Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		// We start the timeline upon a button press.
		
		// Display the scene on the stage
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Reads the level file then takes the data and arranges it into a 2d array.
	 * @param fn name of the file to read from.
	 * @author Luca Crooks.
	 */
	public String[][] ReadLevelFile (String fn) {
		String[][] a = new String[GRID_HEIGHT][GRID_WIDTH];
		try {
			int row = 0;
			File f = new File(fn);
			Scanner reader = new Scanner(f);
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				for (int col = 0; col < GRID_WIDTH; col++) {
					a[row][col] = data.substring(col, col + 1);
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

	/**
	 * Process a key event due to a key being pressed, e.g., to move the player.
	 * @param event The key event that was pressed.
	 */
	public void processKeyEvent(KeyEvent event) {
		// We change the behaviour depending on the actual key that was pressed.
		switch (event.getCode()) {			
		    case RIGHT:
	        	playerX++;
	        	break;
			case LEFT:
				playerX--;
				break;
			case UP:
				playerY--;
				break;
			case DOWN:
				playerY++;
				break;
			default:
	        	// Do nothing for all other keys.
	        	break;
		}
		
		// Redraw game as the player may have moved.
		drawGame();
		
		// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
		event.consume();
	}
	
	/**
	 * Draw the game on the canvas.
	 */
	public void drawGame() {
		// Get the Graphic Context of the canvas. This is what we draw on.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		// Clear canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Set the background to gray.
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Draw row of dirt images
		// We multiply by the cell width and height to turn a coordinate in our grid into a pixel coordinate.
		// We draw the row at y value 2.
		for (int row = 0; row < GRID_HEIGHT; row++) {
			for (int col = 0; col < GRID_WIDTH; col++) {
				if (level[row][col].equals("D")) {
					gc.drawImage(dirtImage, col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
				} else if (level[row][col].equals("@")) {
					gc.drawImage(boulderImage, col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
				} else if (level[row][col].equals("X")) {
					gc.drawImage(playerFrontImage, col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
				} else if (level[row][col].equals("*")) {
					gc.drawImage(diamondImage, col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
				} else if (level[row][col].equals("W")) {
					gc.drawImage(normalWallImage, col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
				} else if (level[row][col].equals("T")) {
				gc.drawImage(titaniumWallImage, col * GRID_CELL_WIDTH, row * GRID_CELL_HEIGHT);
				}
			}
		}
		
		// Draw player at current location
		gc.drawImage(playerFrontImage, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);
	}
	
	/**
	 * Reset the player's location and move them back to (0,0). 
	 */
	public void resetPlayerLocation() { 
		playerX = 3;
		playerY = 2;
		drawGame();
	}
	
	/**
	 * This method is called periodically by the tick timeline
	 * and would for, example move, perform logic in the game,
	 * this might cause the bad guys to move (by e.g., looping
	 * over them all and calling their own tick method). 
	 */
	public void tick() {
		// We then redraw the whole canvas.
		drawGame();
	}
	
	/**
	 * Create the GUI.
	 * @return The panel that contains the created GUI.
	 */
	private Pane buildGUI() {
		// Create top-level panel that will hold all GUI nodes.
		BorderPane root = new BorderPane();
				
		// Create the canvas that we will draw on.
		// We store this as a gloabl variable so other methods can access it.
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.setCenter(canvas);
		
		// Create a toolbar with some nice padding and spacing
		HBox toolbar = new HBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(10, 10, 10, 10)); 
		root.setTop(toolbar);
		
		// Create the toolbar content
		
		// Reset Player Location Button
		Button resetPlayerLocationButton = new Button("Reset Player");
		toolbar.getChildren().add(resetPlayerLocationButton);

		// Setup the behaviour of the button.
		resetPlayerLocationButton.setOnAction(e -> {
			// We keep this method short and use a method for the bulk of the work.
			resetPlayerLocation();
		});
		
		// Tick Timeline buttons
		Button startTickTimelineButton = new Button("Start Ticks");
		Button stopTickTimelineButton = new Button("Stop Ticks");
		// We add both buttons at the same time to the timeline (we could have done this in two steps).
		toolbar.getChildren().addAll(startTickTimelineButton, stopTickTimelineButton);
		// Stop button is disabled by default
		stopTickTimelineButton.setDisable(true);

		// Setup the behaviour of the buttons.
		startTickTimelineButton.setOnAction(e -> {
			// Start the tick timeline and enable/disable buttons as appropriate.
			startTickTimelineButton.setDisable(true);
			tickTimeline.play();
			stopTickTimelineButton.setDisable(false);
		});

		stopTickTimelineButton.setOnAction(e -> {
			// Stop the tick timeline and enable/disable buttons as appropriate.
			stopTickTimelineButton.setDisable(true);
			tickTimeline.stop();
			startTickTimelineButton.setDisable(false);
		});

		// Finally, return the border pane we built up.
        return root;
	}
	        	
	public static void main(String[] args) {
		launch(args);
	}
}