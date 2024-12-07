import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class StartMenu extends Application {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;

    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Label title = new Label("BOULDERDASH");
        title.setStyle("-fx-text-fill: #29a929; -fx-font-size: 50; -fx-background-image: url('NORMAL_WALL.png');");


        VBox menu = new VBox(40);
        menu.setStyle("-fx-background-image: url('DIRT.png');");
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        Button startGame = new Button("Start Game");
        startGame.setStyle("-fx-background-image: url('NORMAL_WALL.png'); -fx-background-size: 20;" +
                " -fx-font-size: 20; -fx-text-fill: #29a929");
        menu.getChildren().add(title);
        menu.getChildren().add(startGame);

        startGame.setOnAction(event -> {
            startMain();
        });

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Boulderdash");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startMain(){
        System.out.println("GAME");

    }
    public static void main(String[] args) {
        launch(args);
    }
}
