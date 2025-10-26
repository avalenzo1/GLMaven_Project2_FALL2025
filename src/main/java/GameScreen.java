import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        root.setStyle("-fx-background-color: #D4AF37;");

        controlPanel = new HBox();
        controlPanel.setAlignment(Pos.CENTER);
        selectedNumbers = new HashSet<>();
        gameLogic = new GameLogic();

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

    }

    void initializeBetCard() {
        numberButtons = new NumberButton[8][10];
        betCard = new GridPane();
        betCard.setAlignment(Pos.CENTER);
        betCard.setHgap(5);
        betCard.setVgap(5);


        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 8; col++) {
                int number = row * 8 + col + 1;

                NumberButton numberButton = new NumberButton(number);
                Button button = numberButton.getButton();

                button.setOnAction(event -> {
                    if (selectedNumbers.contains(number)) {
                        numberButton.setSelected(false);
                        selectedNumbers.remove(number);
                    } else {
                        if (selectedNumbers.size() < numSpots) {
                            numberButton.setSelected(true);
                            selectedNumbers.add(number);
                        }
                    }

                });

                betCard.add(button, col, row);
                numberButtons[col][row] = numberButton;

            }

        }


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
                root.getChildren().add(betCard);
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

    void handleNumberSelection(int number) {

    }

    void autoPickNumbers() {

    }

    void startDrawings() {


    }

    void performSingleDrawing() {

    }

    void showDrawingResults(Set<Integer> drawnNumbers, int matches, double winAmount) {

    }

    void resetGame() {

    }

    void invertColors() {

    }

    void updateUIState() {

    }

}
