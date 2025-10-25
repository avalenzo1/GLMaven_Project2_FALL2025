import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashSet;
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
    int spotsCount;
    int drawingsCount;
    int currentDrawing;
    GameLogic gameLogic;


    GameScreen() {
        root = new VBox();
        controlPanel = new HBox();
        controlPanel.setAlignment(Pos.CENTER);
        betCard = new GridPane();
        selectedNumbers = new HashSet<>();
        gameLogic = new GameLogic();

        resultsDisplay = new VBox();
        spotsCount  = 0;
        drawingsCount = 0;
        currentDrawing = 0;

        setupMenu();
        setupDrawingsSelection();
        setupSpotsSelection();


    }

    Scene createGameScene() {
        return new Scene(root);
    }

    void setupMenu() {
        menuBar = new MenuBar();
    }

    void initializeBetCard() {
        numberButtons = new NumberButton[8][10];

    }

    void setupSpotsSelection() {
        spotsSelection = new VBox();
    }

    void setupDrawingsSelection() {
        drawingsSelection = new VBox();
        drawingsSelection.setStyle("-fx-background-color: #D4AF37;");
        drawingsSelection.getChildren().addAll(menuBar);

    }

    void handleNumberSelection(int number) {

    }

    void autoPickNumbers() {

    }

    void startDrawings() {
        root.getChildren().add(drawingsSelection);

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
