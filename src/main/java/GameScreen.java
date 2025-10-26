import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameScreen {
    Scene scene;
    VBox root;
    MenuBar menuBar;
    HBox controlPanel;
    GridPane betCard;
    VBox spotsSelection;
    VBox drawingsSelection;
    VBox resultsDisplay;
    NumberButton[][] numberButtons;
    Set<Integer> selectedNumbers;

    int numDrawings;
    int numSpots;
    int currentDrawing;
    GameLogic gameLogic;

    GameScreen() {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setMinHeight(400);
        root.setStyle("-fx-background-color: #D4AF37;");

        controlPanel = new HBox();
        controlPanel.setAlignment(Pos.CENTER);
        selectedNumbers = new HashSet<>();

        resultsDisplay = new VBox();

        setupMenu();
        setupDrawingsSelection();
        setupSpotsSelection();

        root.getChildren().add(drawingsSelection);
    }

    Scene createGameScene() {
        return new Scene(root, 500, 400);
    }

    void setupMenu() {
        menuBar = new MenuBar();

        Menu menu = new Menu("Menu");

        MenuItem quitGame = new MenuItem("Quit");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem invertColors = new MenuItem("Invert Colors");

        menu.getItems().addAll(quitGame, newGame, invertColors);
        root.setAlignment(Pos.BASELINE_CENTER);
        menuBar.getMenus().addAll(menu);
    }

    void initializeBetCard() {
        gameLogic = new GameLogic(numDrawings, numSpots);
        numberButtons = new NumberButton[8][10];
        betCard = new GridPane();
        betCard.setAlignment(Pos.CENTER);
        betCard.setHgap(5);
        betCard.setVgap(5);

        Button autoPickButton = new Button("Auto Pick");

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 8; col++) {
                int number = row * 8 + col + 1;

                NumberButton numberButton = new NumberButton(number);
                Button button = numberButton.getButton();

                int finalCol = col;
                int finalRow = row;

                button.setOnAction(event -> {
                    handleNumberSelection(number, finalCol, finalRow);
                });

                betCard.add(button, col, row);
                numberButtons[col][row] = numberButton;
            }
        }

        autoPickButton.setOnAction(event -> {
            autoPickNumbers();
        });

        controlPanel.getChildren().addAll(autoPickButton, betCard);
    }

    void setupSpotsSelection() {
        spotsSelection = new VBox();
        spotsSelection.setAlignment(Pos.CENTER);
        spotsSelection.setPadding(new Insets(5));
        spotsSelection.setSpacing(5);

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
                root.getChildren().clear();
                root.getChildren().add(menuBar);
                root.getChildren().add(controlPanel);
            });

            hBox.getChildren().add(spotButton);
        }

        spotsSelection.getChildren().addAll(spotsText, hBox);
    }

    void setupDrawingsSelection() {
        drawingsSelection = new VBox();
        drawingsSelection.setAlignment(Pos.CENTER);
        drawingsSelection.setPadding(new Insets(5));
        drawingsSelection.setSpacing(5);

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
                root.getChildren().clear();
                root.getChildren().add(spotsSelection);
            });

            hBox.getChildren().add(drawingBtn);
        }

        drawingsSelection.getChildren().addAll(drawingsText, hBox);

    }

    void handleNumberSelection(int number, int col, int row) {
        NumberButton numberButton = numberButtons[col][row];

        if (gameLogic.validateSelection(number)) {
            selectedNumbers.add(number);
            numberButton.setSelected(true);
        }

        // game round finished
        if (gameLogic.isGameComplete()) {
            performDrawing();
        }
    }

    void autoPickNumbers() {
        Set<Integer> randomNumbers = gameLogic.generateRandomNumbers(numSpots);

        System.out.println("Random Numbers: " + randomNumbers);

        for (int randomNumber : randomNumbers) {
            Pair<Integer, Integer> pos = getPosition(randomNumber);

            handleNumberSelection(randomNumber, pos.getKey(), pos.getValue());
        }
    }

    Pair<Integer, Integer> getPosition(int number) {


        Integer col = (number - 1) % 8;
        Integer row = (number - 1) / 8;

        return new Pair<>(col, row);
    }

    void performDrawing() {
        Set<Integer> drawing = gameLogic.performDrawing();

        int matches = gameLogic.calculateMatches(drawing);

        showDrawingResults(drawing, matches, 0.0);
    }

    void showDrawingResults(Set<Integer> drawnNumbers, int matches, double winAmount) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText("Results for drawing #" + gameLogic.currentDrawing);
        alert.setContentText("The winning numbers are: " + drawnNumbers.toString() + "\n" + "You succesfully matched " + matches + " numbers.\n" + "You won " + winAmount + " points.");

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
            root.getChildren().add(new Text("Thank you for playing!"));
        }

    }

    void invertColors() {
        root.setStyle("-fx-background-color: #2B50C8;");
    }
}
