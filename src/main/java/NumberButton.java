import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Arrays;

public class NumberButton {
    private Button button;
    private int number;
    private boolean isSelected;
    private boolean isHighlighted;
    private static String[] stylesheet = {
            "-fx-text-fill: #D5573B;",
            "-fx-background-color: #C0C0C0;",
            "-fx-outer-border: #C0C0C0;"
    };

    static private boolean isInverted = false;

    public NumberButton(int number) {
        this.button = new Button();
        this.button.setText(String.valueOf(number));
        this.button.setMinSize(30,30);
        this.number = number;

        handleStyle();

        isSelected = false;
        isHighlighted = false;
    }

    void handleStyle() {
        String style = Arrays.toString(stylesheet).replace(",", "").replace("[", "").replace("]", "");

        System.out.println(style);

        button.setStyle(style);
    }

    void setHighlighted(boolean highlighted) {
        if (highlighted) {
            stylesheet[2] = "-fx-outer-border: yellow;";
        } else {
            stylesheet[2] = "-fx-outer-border: #C0C0C0;";
        }

        handleStyle();

        isHighlighted = highlighted;
    }

    void setSelected(boolean selected) {
        ScaleTransition transition = new ScaleTransition(Duration.seconds(0.2), button);

        if (selected) {
            stylesheet[0] = "-fx-text-fill: #C0C0C0;";
            stylesheet[1] = "-fx-background-color: #D5573B;";

            transition.setToX(1.1);
            transition.setToY(1.1);
            transition.setInterpolator(Interpolator.EASE_BOTH);

        } else {
            stylesheet[0] = "-fx-text-fill: #D5573B;";
            stylesheet[1] = "-fx-background-color: #C0C0C0;";

            transition.setToX(1);
            transition.setToY(1);
        }

        handleStyle();

        transition.play();


        isSelected = selected;
    }

    Button getButton() {
        return button;
    }

    int getNumber() {
        return number;
    }

    boolean isSelected() {
        return isSelected;
    }

    void reset() {
        isSelected = false;
    }
}
