import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class GameScreen {
    Scene scene;
    VBox root;
    MenuBar menuBar;
    HBox controlPanel;
    GridPane betCard;
    VBox spotsSelectionLayout;
    VBox drawingsSelectionLayout;
    VBox gameLayout;
    NumberButton[][] numberButtons;
    Button submitButton;
    Set<Integer> selectedNumbers;

    static boolean isInverted = false;

    int numDrawings;
    int numSpots;
    int currentDrawing;
    GameLogic gameLogic;

    GameScreen() {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setMinHeight(400);
        root.setStyle("-fx-background-color: #D4AF37;");

        scene = new Scene(root, 500, 400);

        setupMenuComponent();
        setupDrawingsSelectionLayout();
        setupSpotsSelectionLayout();
        setupGameLayout();

        setLayout(drawingsSelectionLayout);
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

        invertColors.setOnAction(e -> {
            invertColors();
        });

        menu.getItems().addAll(invertColors);
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

        controlPanel.getChildren().addAll(betCard, autoPickButton, submitButton);
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

        for (Integer number : drawing) {
            Pair<Integer, Integer> cell = getPosition(number);
            NumberButton numberButton = numberButtons[cell.getKey()][cell.getValue()];

            numberButton.setHighlighted(true);
        }

        showDrawingResults(drawing, matches, 0.0);
    }

    void showDrawingResults(Set<Integer> drawnNumbers, int matches, double winAmount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText("Results for drawing #" + gameLogic.currentDrawing);
        alert.setContentText("The winning numbers are: " + drawnNumbers.toString() + "\n" + "You succesfully matched " + matches + " numbers.\n" + "You won " + winAmount + " points.");

        alert.setX(100);
        alert.setY(100);
        alert.showAndWait();

        resetGame();
    }

    void resetGame() {
        gameLogic.resetGameState();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 8; col++) {
                NumberButton numberButton = numberButtons[col][row];
                numberButton.setSelected(false);
            }
        }

        if (gameLogic.isGameComplete()) {
            root.getChildren().clear();
            root.setAlignment(Pos.CENTER);
            root.getChildren().add(new Text("Thank you for playing!"));
        }

        submitButton.setDisable(true);
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
