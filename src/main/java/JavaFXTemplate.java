import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
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
        createRulesScene(); // Comment these out for now
        // createOddsScene();
        // createGameScene();

        // Set initial scene
        primaryStage.setScene(rulesScene);
        primaryStage.show();
    }
    private Button createImageButton(String imagePath, double width, double height) {

        ImageView imageView = createImageView(imagePath, width, height);

        Button button = new Button();
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        button.setGraphic(imageView);



        // Enhanced hover effect with animation
        button.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_ENTERED, e -> {
            button.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-cursor: hand;");
        });

        button.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_EXITED, e -> {
            button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        });

        return button;
    }
    private ImageView createImageView(String imagePath, double width, double height) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }



     // Creates the exit button
    private Button createExitButton() {
        Button button = createImageButton("pictures/exitPicture.png", 50, 50);
        button.setOnAction(e -> primaryStage.close());
        return button;
    }


     // Creates the rules button
    private Button createRulesButton() {
        Button rulesButton = createImageButton("pictures/ruleButton.png", 50, 50);
        rulesButton.setOnAction(event -> {
            // primaryStage.setScene(rulesScene);
            System.out.println("Rules button clicked - rules scene would load here");
        });
        return rulesButton;
    }


     //Creates the odds button
    private Button createOddsButton() {
        Button oddsButton = createImageButton("pictures/Dice.png", 50, 50);
        oddsButton.setOnAction(event -> {
            // primaryStage.setScene(oddsScene);
            System.out.println("Odds button clicked - odds scene would load here");
        });
        return oddsButton;
    }



     //Creates the play button
    private Button createPlayButton() {
        Button playButton = createImageButton("pictures/play_btn.png", 300, 100);
        playButton.setOnAction(event -> {
            // primaryStage.setScene(gameScene);
            System.out.println("Play button clicked - game scene would load here");
        });
        return playButton;
    }

    private void createMenuScene() {
        // Create buttons using the new functions
        Button exitButton = createExitButton();
        Button rulesButton = createRulesButton();
        Button oddsButton = createOddsButton();
        Button playButton = createPlayButton();

        // Create logo
        ImageView logoView = createImageView("pictures/keno_logo.png", 200, 100);

        // Set margins
        BorderPane.setMargin(exitButton, new Insets(20, 0, 0, 20));
        BorderPane.setMargin(rulesButton, new Insets(20, 0, 0, 0));
        BorderPane.setMargin(oddsButton, new Insets(20, 300, 0, 0));

        // Create center content
        VBox centerContent = new VBox(20);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.getChildren().addAll(logoView, playButton);

        // Create top container
        BorderPane topContainer = new BorderPane();
        topContainer.setLeft(exitButton);
        topContainer.setCenter(rulesButton);
        topContainer.setRight(oddsButton);

        // Create main layout
        BorderPane menuBorderPane = new BorderPane();
        menuBorderPane.setStyle("-fx-background-color: #D4AF37;");
        menuBorderPane.setPrefSize(500, 400);
        menuBorderPane.setTop(topContainer);
        menuBorderPane.setCenter(centerContent);

        menuScene = new Scene(menuBorderPane, 500, 400);
    }

    // Placeholder methods for other scenes
    private void createRulesScene() {
        // Create buttons using the new functions
        Button exitButton = createExitButton();
        Button rulesButton = createRulesButton();
        Button oddsButton = createOddsButton();





        // Set margins
        BorderPane.setMargin(exitButton, new Insets(20, 0, 0, 20));
        BorderPane.setMargin(rulesButton, new Insets(20, 0, 0, 0));
        BorderPane.setMargin(oddsButton, new Insets(20, 300, 0, 0));



        // Create top container
        BorderPane topContainer = new BorderPane();
        topContainer.setLeft(exitButton);
        topContainer.setCenter(rulesButton);
        topContainer.setRight(oddsButton);

        // Create main layout
        BorderPane menuBorderPane = new BorderPane();
        menuBorderPane.setStyle("-fx-background-color: #D4AF37;");
        menuBorderPane.setPrefSize(500, 400);
        menuBorderPane.setTop(topContainer);

        rulesScene = new Scene(menuBorderPane, 500, 400);
    }

    private void createOddsScene() {
        // oddsScene = new Scene(new Label(), 500, 400);
    }

    private void createGameScene() {
        // gameScene = new Scene(new Label(), 500, 400);
    }
}