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

	// player instance
	public static Player player = new Player(2, 2);
	// board creation from file
	public static Board board = new Board("src/EnemyTest.txt");

	// The width and height (in pixels) of each cell that makes up the game.
	public static final int GRID_CELL_WIDTH = 50;
	public static final int GRID_CELL_HEIGHT = 50;
	
	// The width of the grid in number of cells.
	public static final int GRID_WIDTH = 15;
	public static final int GRID_HEIGHT = 15;

	// The dimensions of the canvas
	public static final int CANVAS_WIDTH = GRID_WIDTH * GRID_CELL_WIDTH;
	public static final int CANVAS_HEIGHT = GRID_HEIGHT * GRID_CELL_HEIGHT;

	// The dimensions of the window
	public static final int WINDOW_WIDTH = CANVAS_WIDTH + 100;
	public static final int WINDOW_HEIGHT = CANVAS_HEIGHT + 100;

	// tick speed
	public static final int TICK_SPEED = 200;
	// amoeba capacity
	public static final int MAX_AMOEBA_CAP = 1000;
	
	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	public Canvas canvas;
	
	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline;
	
	/**
	 * Setup the new application.
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {

		// Build the GUI
		Pane root = buildGUI();

		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		// Register an event handler for key presses.
		// This causes the processKeyEvent method to be called each time a key is pressed.
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
				
		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method every half a second.
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICK_SPEED), event -> tick()));
		 // Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		// We start the timeline.
		tickTimeline.play();

		// Display the scene on the stage
		board.draw(canvas);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Process a key event due to a key being pressed, e.g., to move the player.
	 * @param event The key event that was pressed.
	 */
	public void processKeyEvent(KeyEvent event) {
		// We change the behaviour depending on the actual key that was pressed.
		String direction = "";
		switch (event.getCode()) {			
		    case RIGHT:
	        	direction = "right";
	        	break;
			case LEFT:
				direction = "left";
				break;
			case UP:
				direction = "up";
				break;
			case DOWN:
				direction = "down";
				break;
			default:
	        	// Do nothing for all other keys.
	        	break;
		}

		// calls update on player -> checks if move is valid, moves (or not), then digs its square if it is dirt.
		player.update(direction);

		// Redraw game as the player may have moved.
		board.draw(canvas);
		
		// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
		event.consume();
	}
	
	/**
	 * This method is called periodically by the tick timeline
	 * and would for, example move, perform logic in the game,
	 * this might cause the bad guys to move (by e.g., looping
	 * over them all and calling their own tick method). 
	 */
	public void tick() {
		for (int row = Main.GRID_HEIGHT - 1; row >= 0; row--) {
			for (int col = 0; col < Main.GRID_WIDTH; col++) {
				String l = Main.board.getTileLetter(col, row);
				Tile obj = Main.board.get(col, row);
				if (l.equals("@") || l.equals("*") || l.equals("M") || l.equals("f") || l.equals("B") || l.equals("F")) {
					if (!obj.getChecked()) {
						Main.board.get(col, row).update();
						obj.setChecked(true);
					}
				} else {
					Main.board.get(col, row).update();
				}
			}
		}

		for (int row = 0; row < Main.GRID_HEIGHT; row++) {
			for (int col = 0; col < Main.GRID_WIDTH; col++) {
				String l = Main.board.getTileLetter(col, row);
				Tile obj = Main.board.get(col, row);
				obj.setY(row);
				obj.setX(col);
				if (l.equals("@") || l.equals("*") || l.equals("M") || l.equals("f") || l.equals("B") || l.equals("F")) {
					obj.setChecked(false);
				}
			}
		}

		board.unlockAmoebas();
		// We then redraw the whole canvas.
		board.draw(canvas);
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
		});

		// Tick Timeline buttons
		Button startTickTimelineButton = new Button("Start Ticks");
		Button stopTickTimelineButton = new Button("Stop Ticks");
		// We add both buttons at the same time to the timeline (we could have done this in two steps).
		toolbar.getChildren().addAll(startTickTimelineButton, stopTickTimelineButton);
		// Stop button is disabled by default
		stopTickTimelineButton.setDisable(true);



		// Finally, return the border pane we built up.
        return root;
	}
	        	
	public static void main(String[] args) {
		launch(args);
	}
}