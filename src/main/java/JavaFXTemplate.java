import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class JavaFXTemplate extends Application {

    // And was here
    private Stage primaryStage;
    private Scene menuScene;
    private Scene rulesScene;
    private Scene oddsScene;
    private Scene gameScene;
    private Scene prevScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Keno Game");

        // Create all the scenes
        createMenuScene();
         createRulesScene(prevScene); // Comment these out for now
         createOddsScene();
         createGameScene();

        // Set initial scene
        primaryStage.setScene(menuScene);
        primaryStage.show();
        prevScene = menuScene;
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
            primaryStage.setScene(rulesScene);
            System.out.println("Rules button clicked - rules scene would load here");
        });
        return rulesButton;
    }


     //Creates the odds button
    private Button createOddsButton() {

        Button oddsButton = createImageButton("pictures/Dice.png", 50, 50);
        oddsButton.setOnAction(event -> {
            primaryStage.setScene(oddsScene);
            System.out.println("Odds button clicked - odds scene would load here");
        });
        return oddsButton;
    }



     //Creates the play button
    private Button createPlayButton() {
        Button playButton = createImageButton("pictures/play_btn.png", 300, 100);
        playButton.setOnAction(event -> {
            primaryStage.setScene(gameScene);
            prevScene = gameScene;
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

    private Button createBackButon(Scene scene) {
        Button playButton = createImageButton("pictures/backButton.png", 50, 50);
        playButton.setOnAction(event -> {
            primaryStage.setScene(scene);
            System.out.println("Back button Clicked");
        });
        return playButton;
    }

    private void createRulesScene(Scene prevScene) {
        Button backButton = createBackButon(menuScene);
        backButton.setAlignment(Pos.CENTER);


        // Create title
        Label title = new Label("Keno Rules");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #8B0000;");

        // Create formatted rules text
        Text rulesText = new Text();
        rulesText.setText(
                " WHAT IS KENO?\n" +
                        "Keno is a lottery-style gambling game where players select numbers and win based on how many match randomly drawn numbers.\n\n" +

                        " BETTING\n" +
                        "• Bet Card: Grid of numbers 1-80\n" +
                        "• Number of Spots: Choose 1, 4, 8, or 10 numbers\n" +
                        "• Drawings: Play for 1-4 consecutive drawings\n\n" +

                        " GAME FLOW\n" +
                        "1. Select number of spots\n" +
                        "2. Choose numbers manually or use Quick Pick\n" +
                        "3. Select number of drawings (1-4)\n" +
                        "4. Watch the drawings\n" +
                        "5. Collect winnings and play again!\n\n" +

                        " DRAWING MECHANICS\n" +
                        "• 20 numbers drawn from 1-80\n" +
                        "• No duplicates in single drawing\n" +
                        "• Sequential display with pauses\n" +
                        "• Instant win calculation after each drawing"
        );
        rulesText.setStyle("-fx-font-size: 14px; -fx-line-spacing: 8px; -fx-fill: #2F4F4F;");
        rulesText.setWrappingWidth(500);

        // Create scroll pane
        ScrollPane scrollPane = new ScrollPane(rulesText);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: rgba(255,255,255,0.8);");
        scrollPane.setPadding(new Insets(20));

        // Main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #FFD700, #DAA520);");
        mainLayout.getChildren().addAll(title, scrollPane, backButton);

        rulesScene = new Scene(mainLayout, 600, 500);
    }

    private void createOddsScene() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: #D4AF37;");

        VBox root = new VBox();

        Button backButton = createBackButon(menuScene);

        root.getChildren().addAll(backButton, scrollPane);

        Text oddsText = new Text();

        oddsText.setText("Game Odds:\n" +
                "10 spot -> 1 in 9.05\n" +
                "8 spot -> 1 in 9.77\n" +
                "4 spot -> 1 in 3.86\n" +
                "1 spot -> 1 in 4.00");

        scrollPane.setContent(oddsText);

        oddsScene = new Scene(root, 500, 400);
    }

    private void createGameScene() {
        // I'll style this later!

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #D4AF37");

        Text pickDrawing = new Text("");

        FlowPane flowPane = new FlowPane();

        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        flowPane.setPadding(new Insets(10, 10, 10, 10));

        List<CheckBox> checkBoxList = new ArrayList<>();


        for (int i = 0; i < 80; i++) {
            checkBoxList.add(new CheckBox());
            flowPane.getChildren().add(checkBoxList.get(i));
        }

        borderPane.setCenter(flowPane);
        gameScene = new Scene(borderPane, 500, 400);


    }
}