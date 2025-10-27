import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameScreen {
    JavaFXTemplate app;
    Scene scene;
    VBox root;
    MenuBar menuBar;
    HBox controlPanel;
    GridPane betCard;
    VBox spotsSelectionLayout;
    VBox drawingsSelectionLayout;
    VBox gameLayout;
    VBox gameOverLayout;
    NumberButton[][] numberButtons;
    Button submitButton;
    VBox buttonContainer;
    Set<Integer> selectedNumbers;

    static boolean isInverted = false;

    int numDrawings;
    int numSpots;

    int totalWinAmount = 0;
    int winAmount = 0;

    int[][] points = {
            {0, 2}, // 1 spot
            {0, 0, 1, 5, 75}, // 4 spot
            {0, 0, 0, 0, 2, 12, 50, 750, 10000}, // 8 spot
            {5, 0, 0, 0, 0, 2, 15, 40, 450, 4250, 100000} // 10 spot
    };

    GameLogic gameLogic;


    GameScreen(JavaFXTemplate app) {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setMinHeight(400);
        root.setStyle("-fx-background-color: #D4AF37;");

        scene = new Scene(root, 500, 400);

        setupMenuComponent();
        setupDrawingsSelectionLayout();
        setupSpotsSelectionLayout();
        setupGameLayout();
        setupGameOverLayout();

        setLayout(drawingsSelectionLayout);

        this.app = app;
    }



    Scene createGameScene() {
        return scene;
    }

    void setLayout(Pane layout) {
        root.getChildren().clear();
        root.getChildren().add(menuBar);
        root.getChildren().add(layout);
    }

    void setupMenuComponent() {
        menuBar = new MenuBar();

        Menu menu = new Menu("Menu");

        MenuItem invertColors = new MenuItem("Invert Colors");
        MenuItem back = new MenuItem("Back");
        MenuItem rules = new MenuItem("Rules");
        MenuItem odds = new  MenuItem("Odds");

        invertColors.setOnAction(e -> {
            invertColors();
        });



        menu.getItems().addAll(invertColors);
        menu.getItems().add(rules);
        menu.getItems().addAll(odds);
        menu.getItems().addAll(back);
        root.setAlignment(Pos.BASELINE_CENTER);
        menuBar.getMenus().addAll(menu);
    }

    void initializeBetCard() {
        selectedNumbers = new HashSet<>();
        gameLogic = new GameLogic(numDrawings, numSpots);
        numberButtons = new NumberButton[8][10];
        betCard = new GridPane();
        betCard.setAlignment(Pos.CENTER);
        betCard.setHgap(5);
        betCard.setVgap(5);
        buttonContainer = new VBox();
        buttonContainer.setSpacing(10);

        Button autoPickButton = new Button("Auto Pick");
        submitButton = new Button("Submit");
        submitButton.setDisable(true);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 8; col++) {
                int number = row * 8 + col + 1;

                NumberButton numberButton = new NumberButton(number);
                Button button = numberButton.getButton();

                button.setOnAction(event -> {
                    handleNumberSelection(number);
                });

                betCard.add(button, col, row);
                numberButtons[col][row] = numberButton;
            }
        }

        submitButton.setOnAction(event -> {
            // game round finished
            if (gameLogic.isGameComplete()) {
                performDrawing();
            }
        });

        autoPickButton.setOnAction(event -> {
            autoPickNumbers();
        });

        buttonContainer.getChildren().addAll(autoPickButton, submitButton);

        controlPanel.getChildren().addAll(betCard, buttonContainer);
    }

    void clearAllNumbers() {
        while (!selectedNumbers.isEmpty()) {
            handleNumberSelection(selectedNumbers.iterator().next());
        }
    }

    void setupSpotsSelectionLayout() {
        spotsSelectionLayout = new VBox();
        spotsSelectionLayout.setAlignment(Pos.CENTER);
        spotsSelectionLayout.setPadding(new Insets(5));
        spotsSelectionLayout.setSpacing(5);

        Text spotsText = new Text("How Many Spots Do You Want To Play?");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);

        int[] spots = {1, 4, 8, 10};

        for (int i = 0; i < 4; i++) {
            int spot = spots[i];

            Button spotButton = new Button();
            spotButton.setText(String.valueOf(spot));
            spotButton.setOnAction(e -> {
                numSpots = spot;
                initializeBetCard();
                setLayout(gameLayout);
            });

            hBox.getChildren().add(spotButton);
        }

        spotsSelectionLayout.getChildren().addAll(spotsText, hBox);
    }

    void setupDrawingsSelectionLayout() {
        drawingsSelectionLayout = new VBox();
        drawingsSelectionLayout.setAlignment(Pos.CENTER);
        drawingsSelectionLayout.setPadding(new Insets(5));
        drawingsSelectionLayout.setSpacing(5);

        Text drawingsText = new Text("How Many Drawings Do You Want To Pick?");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);

        for (int i = 0; i < 4; i++) {
            int drawing = i + 1;

            Button drawingBtn = new Button();
            drawingBtn.setText(String.valueOf(drawing));
            drawingBtn.setOnAction(e -> {
                numDrawings = drawing;
                setLayout(spotsSelectionLayout);
            });

            hBox.getChildren().add(drawingBtn);
        }

        drawingsSelectionLayout.getChildren().addAll(drawingsText, hBox);

    }

    void setupGameLayout() {
        gameLayout = new VBox();
        controlPanel = new HBox();
        controlPanel.setAlignment(Pos.TOP_CENTER);
        controlPanel.setSpacing(20);
        controlPanel.setPadding(new Insets(10));

        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.getChildren().addAll(controlPanel);
    }

    void handleNumberSelection(int number) {
        Pair<Integer, Integer> cell = getPosition(number);

        NumberButton numberButton = numberButtons[cell.getKey()][cell.getValue()];

        if (!numberButton.isSelected()) {
            if (gameLogic.validateSelection(number)) {
                selectedNumbers.add(number);
                numberButton.setSelected(true);
            }
        } else {
            if (gameLogic.validateDeselection(number)) {
                selectedNumbers.remove(number);
                numberButton.setSelected(false);
            }
        }

        if (gameLogic.isGameComplete()) {
            submitButton.setDisable(false);
        }
    }

    void autoPickNumbers() {
        clearAllNumbers();

        Set<Integer> randomNumbers = gameLogic.generateRandomNumbers(numSpots);

        System.out.println("Random Numbers: " + randomNumbers);

        for (int randomNumber : randomNumbers) {
            handleNumberSelection(randomNumber);
        }
    }

    void performDrawing() {
        Set<Integer> drawing = gameLogic.performDrawing();
        int matches = gameLogic.calculateMatches(drawing);

        int pointsIndex = -1;

        if (numSpots == 1) {
            pointsIndex = 0;
        } else if (numSpots == 4) {
            pointsIndex = 1;
        } else if (numSpots == 8) {
            pointsIndex = 2;
        } else if (numSpots == 10) {
            pointsIndex = 3;
        }

        winAmount = points[pointsIndex][matches];

        buttonContainer.setDisable(true);

        Iterator<Integer> iterator = drawing.iterator();

        KeyFrame highlightCell = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (iterator.hasNext()) {
                    Integer number = iterator.next();
                    Pair<Integer, Integer> cell = getPosition(number);

                    NumberButton numberButton = numberButtons[cell.getKey()][cell.getValue()];
                    numberButton.setHighlighted(true);
                } else {
                    Platform.runLater(() -> showDrawingResults(drawing, matches, winAmount));
                }
            }
        });

        Timeline timeline = new Timeline(highlightCell);

        timeline.setCycleCount(drawing.size() + 1);
        timeline.play();
    }

    void showDrawingResults(Set<Integer> drawnNumbers, int matches, double winAmount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText("Results for drawing #" + gameLogic.currentDrawing);
        alert.setContentText("The winning numbers are: " + drawnNumbers.toString() + "\n" + "You succesfully matched " + matches + " numbers.\n" + "You won $" + winAmount + " this round and $" + (winAmount + totalWinAmount) + " total.");

        alert.setX(100);
        alert.setY(100);
        alert.showAndWait();

        resetGame();
    }

    void resetGame() {
        gameLogic.resetGameState();
        buttonContainer.setDisable(false);
        totalWinAmount += winAmount;
        winAmount = 0;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 8; col++) {
                NumberButton numberButton = numberButtons[col][row];
                numberButton.reset();
            }
        }

        if (gameLogic.isGameComplete()) {
            root.getChildren().clear();
            root.setAlignment(Pos.CENTER);
            root.getChildren().add(gameOverLayout);
        }

        submitButton.setDisable(true);
    }

    void setupGameOverLayout() {

        Text thankYouText = new Text("Thank you for playing!");
        Button startOverButton = new Button("New Game");
        Button quitGameButton = new Button("Quit");

        startOverButton.setOnAction(event -> {
            app.startGame();
        });

        quitGameButton.setOnAction(event -> {
            app.close();
        });

        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(startOverButton, quitGameButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(20);
        buttonContainer.setPadding(new Insets(20));



        gameOverLayout = new VBox();
        gameOverLayout.getChildren().addAll(thankYouText, buttonContainer);
        gameOverLayout.setAlignment(Pos.CENTER);
    }

    void invertColors() {
        isInverted = !isInverted;

        if (isInverted) {
            root.setStyle("-fx-background-color: #2B50C8;");
        } else {
            root.setStyle("-fx-background-color: #D4AF37;");
        }
    }

    Pair<Integer, Integer> getPosition(int number) {
        Integer col = (number - 1) % 8;
        Integer row = (number - 1) / 8;

        return new Pair<>(col, row);
    }
}
