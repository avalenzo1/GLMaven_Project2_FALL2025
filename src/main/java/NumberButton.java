import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
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

    static private boolean isInverted = false;

    public NumberButton(int number) {
        this.button = new Button();
        this.button.setText(String.valueOf(number));
        this.button.setMinSize(35,25);
        this.number = number;

        setSelected(false);
        setHighlighted(false);

        handleStyle();
    }

    void handleStyle() {
        String[] stylesheet = {
                "-fx-text-fill: #D5573B;",
                "-fx-background-color: #C0C0C0;",
                "-fx-border-color: #C0C0C0;",
                "-fx-border-width: 0;",
                "-fx-border-radius: 2;"
        };

        if (isSelected) {
            stylesheet[0] = "-fx-text-fill: #C0C0C0;";
            stylesheet[1] = "-fx-background-color: #D5573B;";
        }

        if (isHighlighted) {
            stylesheet[2] = "-fx-border-color: #343434;";
            stylesheet[3] = "-fx-border-width: 1;";
        }

        if (isSelected && isHighlighted) {
            stylesheet[2] = "-fx-border-color: #FFFF00;";
            stylesheet[3] = "-fx-border-width: 2;";
        }

        String style = Arrays.toString(stylesheet).replace(",", "").replace("[", "").replace("]", "");

        System.out.println("Style for btn #" + number + ": " + style);

        button.setStyle(style);
    }

    void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;

        ScaleTransition transition = new ScaleTransition(Duration.seconds(0.2), button);

        if (isHighlighted) {
            transition.setToX(1.1);
            transition.setToY(1.1);
            transition.setInterpolator(Interpolator.EASE_BOTH);
        } else {
            transition.setToX(1);
            transition.setToY(1);
        }

        transition.play();

        handleStyle();
    }

    void setSelected(boolean selected) {


        isSelected = selected;
        handleStyle();
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
        setSelected(false);
        setHighlighted(false);
    }
}
