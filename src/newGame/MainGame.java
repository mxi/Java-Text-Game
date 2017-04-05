package newGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainGame extends Application {

    public static String CONFIG_DIR = "src/newGame/Configs/";
    public static MainGame screen;
    public static double aspectRatio = 4.0 / 3.0; // 4:3 aspect ratio
    public static int widthOfTiles = 256;
    public static int heightOfTiles = (int) (widthOfTiles / aspectRatio); // if 4:3 then height = 192
    public static int tileWidth = 5;
    public static int tileHeight = 5;
    public static int width = widthOfTiles * tileWidth; // 1280
    public static int height = heightOfTiles * tileHeight; // 960

    private Stage stage;
    private Scene scene;
    private Pane layout;
    private Canvas canvas;
    private GraphicsContext graphics;

    @Override
    public void start(Stage istage) throws Exception {
        screen = this;

        stage = istage;
        stage.setTitle("Santiago Zapata");

        layout = new Pane();

        canvas = new Canvas(width, height);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);

        graphics = canvas.getGraphicsContext2D();
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, width, height);

        layout.getChildren().add(canvas);

        scene = new Scene(layout, width, height);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getLayout() {
        return layout;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphics() {
        return graphics;
    }

    private void setHandlers() {
        // TODO: Add controls for the game later on.
        canvas.setOnKeyTyped(key -> {});
    }

    // -- Main Method

    public static void main(String[] args) {
        launch(args);
    }
}
