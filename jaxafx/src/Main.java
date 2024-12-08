import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Luca Crooks
 */
public class Main extends Application {

	// player instance
	public static Player player = new Player(1, 1, 3);

	// list of each level file src in order
	public static final String[] levels = {
			"src/L8.txt", "src/L1.txt", "src/L2.txt", "src/L3.txt",
			"src/L4.txt", "src/L5.txt", "src/L6.txt", "src/L7.txt",
			"src/l8.txt"};

	// leaderboard creation from file
	public static HighScoreTable highscores = new HighScoreTable();
	// board creation from file
	public static Board board = new Board();

	// The width and height (in pixels) of each cell that makes up the game.
	public static final int GRID_CELL_WIDTH = 50;
	public static final int GRID_CELL_HEIGHT = 50;

	// The width of the grid in number of cells.
	public static final int GRID_WIDTH = 30;
	public static final int GRID_HEIGHT = 15;

	// The dimensions of the canvas
	public static final int CANVAS_WIDTH = GRID_WIDTH * GRID_CELL_WIDTH;
	public static final int CANVAS_HEIGHT = GRID_HEIGHT * GRID_CELL_HEIGHT;

	// The dimensions of the window
	public static final int WINDOW_WIDTH = CANVAS_WIDTH;
	public static final int WINDOW_HEIGHT = CANVAS_HEIGHT + 50;

	// tick speed
	public static final int TICK_SPEED = 200;
	// time speed
	public static final int TIMER_SPEED = 1;
	public static final int TOTAL_TIME = 300;
	// amoeba capacity
	public static final int MAX_AMOEBA_CAP = 9;
	
	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	public Canvas canvas;
	
	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline;

	// Timer to track the level time left
	private Timeline timer;
	private int timeRemaining = TOTAL_TIME;
	private Label timerLabel;
	// Flag to check that game isn't paused
	private boolean isPaused = true;
	// Different window scenes
	private Scene game;
	private Scene startMenu;
	private Scene leaderboard;
	private Scene usernameMenu;
	private String username;
	private int previousLevel = -1;

	/**
	 * Setup the new application.
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {

		// Create a scene from the GUI
		game = game(primaryStage);
		startMenu = startMenu(primaryStage);
		leaderboard = leaderboard(primaryStage);
		usernameMenu = enterName(primaryStage);

		// Register an event handler for key presses.
		// This causes the processKeyEvent method to be called each time a key is pressed.
		game.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));

		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method every half a second.
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICK_SPEED), event -> tick()));
		// Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);

		//Create a timer to update every second
		timer = new Timeline(new KeyFrame(Duration.seconds(TIMER_SPEED), event -> updateTimer()));
		timer.setCycleCount(Timeline.INDEFINITE);

		// Display the scene on the stage
		board.draw(canvas, timeRemaining);

		primaryStage.setTitle("Boulderdash");
		primaryStage.setScene(startMenu);
		primaryStage.show();
	}

	public Scene startMenu(Stage primaryStage) {
		BorderPane root = new BorderPane();

		Label title = new Label("BOULDERDASH");
		title.setStyle("-fx-text-fill: #102c70; -fx-font-size: 100; -fx-background-image: url('MAGIC_WALL.png');");


		VBox menu = new VBox(40);
		menu.setStyle("-fx-background-image: url('DIRT.png');");
		menu.setAlignment(Pos.CENTER);
		root.setCenter(menu);

		Button startGame = new Button("Start Game");
		startGame.setStyle("-fx-background-image: url('TITANIUM_WALL.png'); -fx-font-size: 30; -fx-text-fill: #173886");

		Button highscore = new Button("Leaderboard");
		highscore.setStyle("-fx-background-image: url('TITANIUM_WALL.png'); -fx-font-size: 30; -fx-text-fill: #173886");

		menu.getChildren().addAll(title, startGame, highscore);

		startGame.setOnAction(e -> {
			primaryStage.setScene(usernameMenu);

		});

		highscore.setOnAction(e -> {
			primaryStage.setScene(leaderboard);
			highscores.displayHighScores();
		});

		return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public Scene enterName(Stage primaryStage) {
		Label name = new Label("Enter your name:");
		TextField usernameField = new TextField();
		Button submitButton = new Button("Submit");

		submitButton.setOnAction(e -> {
			username = usernameField.getText();
			primaryStage.setScene(game);
			tickTimeline.play();
			timer.play();
			isPaused = false;

		});
		VBox root = new VBox(10, name, usernameField, submitButton);

		return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public Scene game(Stage primaryStage) {
		// Build the GUI
		Pane root = buildGUI();

		return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public Scene leaderboard(Stage primaryStage){
		TableView tableView = new TableView();

		TableColumn<GamePlayer, String> player = new TableColumn<>("Player");
		player.setCellValueFactory(new PropertyValueFactory<>("name"));

		tableView.getColumns().add(player);

		VBox table = new VBox(tableView);
		table.setAlignment(Pos.TOP_CENTER);

		Button goBack = new Button("Back");
		table.getChildren().add(goBack);

		goBack.setOnAction(e -> {
			primaryStage.setScene(startMenu);
			primaryStage.show();
		});

		return new Scene(table, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void updateFile(){
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/PlayerScores.txt", true))) {
			writer.write(username + " " + (board.getLevel()+1));
			writer.newLine();

		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Process a key event due to a key being pressed, e.g., to move the player.
	 * @param event The key event that was pressed.
	 */
	public void processKeyEvent(KeyEvent event) {
		// We change the behaviour depending on the actual key that was pressed.
		if(!isPaused) {
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
			board.draw(canvas, timeRemaining);

			// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
			event.consume();
		}
	}
	
	/**
	 * This method is called periodically by the tick timeline
	 * and would for, example move, perform logic in the game,
	 * this might cause the bad guys to move (by e.g., looping
	 * over them all and calling their own tick method). 
	 */
	public void tick() {
		// updates every tile object from the bottom up
		// if it is a moving object, set it to be checked to avoid multiple moves per frame
		for (int row = GRID_HEIGHT - 1; row >= 0; row--) {
			for (int col = 0; col < GRID_WIDTH; col++) {
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

		// setup for next frame
		for (int row = 0; row < GRID_HEIGHT; row++) {
			for (int col = 0; col < GRID_WIDTH; col++) {
				String l = Main.board.getTileLetter(col, row);
				Tile obj = Main.board.get(col, row);

				// stops tiles getting fragmented when many swaps() are called each frame
				// below ensures each tiles x,y position on the board matches their attributes
				obj.setY(row);
				obj.setX(col);

				// set checked on moving objects to false, ready for next frame
				if (l.equals("@") || l.equals("*") || l.equals("M") || l.equals("f") || l.equals("B") || l.equals("F")) {
					obj.setChecked(false);
				}
			}
		}
		int currentLevel = board.getLevel();
		if(currentLevel != previousLevel){
			previousLevel = currentLevel;
			updateFile();
		}
		board.unlockAmoebas();
		// We then redraw the whole canvas.
		board.draw(canvas, timeRemaining);
	}

	/**
	 * Updates the timer
	 * @author Plabata Guha
	 */
	private void updateTimer(){
		if (!isPaused && timeRemaining >0){
			timeRemaining--;
		}
		timerLabel.setText("Time Remaining: " + timeRemaining + "s");
		if(timeRemaining == 0){
			player.setLives(0);
			player.kill();
		}
	}

	/**
	 * Resets the timer
	 * @author Plabata Guha
	 */
	private void resetTimer(){
		timeRemaining = TOTAL_TIME;
		if (timer!=null){
			timer.stop();
		}
		timer.play();
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
		Button resetPlayerLocationButton = new Button("BOULDERDASH HOLY MOLY!!!");
		toolbar.getChildren().add(resetPlayerLocationButton);

		// Setup the behaviour of the button.
		resetPlayerLocationButton.setOnAction(e -> {
		});

		// Tick Timeline buttons

		Button pauseButton = new Button("Pause");
		Button playButton = new Button("Play");
		// We add both buttons at the same time to the timeline (we could have done this in two steps).
		toolbar.getChildren().addAll(pauseButton, playButton);

		pauseButton.setOnAction(e -> {
			pauseGame();
		});

		playButton.setOnAction(e -> {
			playGame();
		});

		timerLabel = new Label("Time Remaining: " + timeRemaining + "s");
		toolbar.getChildren().add(timerLabel);

		// Finally, return the border pane we built up.
        return root;
	}

	public void pauseGame() {
		tickTimeline.pause();
		isPaused = true;
		timer.pause();
	}

	public void playGame(){
		tickTimeline.play();
		isPaused = false;
		timer.play();
	}

	public static void main(String[] args) {
		launch(args);
	}
}