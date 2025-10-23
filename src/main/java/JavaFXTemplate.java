import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {

    private Stage primaryStage;
    private Scene menuScene;
    private Scene rulesScene;
    private Scene oddsScene;
    private Scene gameScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Keno Game");

        // Create all the scenes
        createMenuScene();
        // createRulesScene(); // Comment these out for now
        // createOddsScene();
        // createGameScene();

        // Set initial scene
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private Button createExitButton() {

        Image exitPic = new Image(getClass().getResourceAsStream("/pictures/exitPicture.png"));
        ImageView exitView = new ImageView(exitPic);
        exitView.setFitWidth(50);
        exitView.setFitHeight(50);
        exitView.setPreserveRatio(true);
        Button exitButton = new Button();
        exitButton.setStyle("-fx-background-color: transparent;");
        exitButton.setGraphic(exitView);
        exitButton.setOnAction(event -> primaryStage.close());
        return exitButton;
    }

    private void createMenuScene() {
        ImageView imgRules = null;
        ImageView imgKenoLogoView = null;
        ImageView imgViewPlay = null;


        Image rules = new Image(getClass().getResourceAsStream("pictures/ruleButton.png"));
        imgRules = new ImageView(rules);
        imgRules.setFitWidth(50);
        imgRules.setFitHeight(50);
        imgRules.setPreserveRatio(true);



        Image imgKenoLogo = new Image(getClass().getResourceAsStream("pictures/keno_logo.png"));
        imgKenoLogoView = new ImageView(imgKenoLogo);
        imgKenoLogoView.setFitWidth(200);
        imgKenoLogoView.setPreserveRatio(true);



        Image imgPlay = new Image(getClass().getResourceAsStream("pictures/play_btn.png"));
        imgViewPlay = new ImageView(imgPlay);
        imgViewPlay.setFitWidth(300);
        imgViewPlay.setPreserveRatio(true);

        Button imgRulesButton = new Button();
        imgRulesButton.setStyle("-fx-background-color: transparent;");

        imgRulesButton.setGraphic(imgRules);

        GridPane menuScreneGridPane = new GridPane();
        menuScreneGridPane.setStyle("-fx-background-color: #D4AF37;");
        menuScreneGridPane.setAlignment(Pos.TOP_LEFT);
        menuScreneGridPane.setHgap(1);
        menuScreneGridPane.setVgap(1);

        Button playButton = new Button();
        playButton.setStyle("-fx-background-color: transparent");

        playButton.setGraphic(imgViewPlay);
        Button exitButton = createExitButton();
        menuScreneGridPane.setPrefSize(500, 400);

        // Add components to grid
        menuScreneGridPane.add(exitButton, 0, 0);
        menuScreneGridPane.add(imgRulesButton, 1, 0);

        menuScreneGridPane.add(imgKenoLogoView, 0, 1, 2, 1);

        menuScreneGridPane.add(playButton, 0, 2, 2, 1);

        // Center the content
        GridPane.setHalignment(exitButton, javafx.geometry.HPos.LEFT);
        GridPane.setHalignment(imgRulesButton, javafx.geometry.HPos.LEFT);
        GridPane.setHalignment(playButton, javafx.geometry.HPos.CENTER);
        GridPane.setHalignment(imgKenoLogoView, javafx.geometry.HPos.CENTER);


        playButton.setOnAction(event -> {
            // primaryStage.setScene(gameScene);
            System.out.println("Play button clicked - game scene would load here");
        });

        imgRulesButton.setOnAction(event -> {
            // primaryStage.setScene(rulesScene);
            System.out.println("Rules button clicked - rules scene would load here");
        });

        menuScene = new Scene(menuScreneGridPane, 500, 400);
    }

    // Placeholder methods for other scenes
    private void createRulesScene() {
        // rulesScene = new Scene(new Label(), 500, 400);
    }

    private void createOddsScene() {
        // oddsScene = new Scene(new Label(), 500, 400);
    }

    private void createGameScene() {
        // gameScene = new Scene(new Label(), 500, 400);
    }
}