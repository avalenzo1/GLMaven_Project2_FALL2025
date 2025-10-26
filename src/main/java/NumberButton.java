import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

public class NumberButton {
    private Button button;
    private int number;
    private boolean isSelected;
    private String style;

    public NumberButton(int number) {
        this.button = new Button();
        this.button.setText(String.valueOf(number));
        this.button.setMinSize(30,30);
        this.button.setStyle("-fx-text-fill: #D5573B; -fx-background-color: #C0C0C0");

        this.number = number;
    }

    void setSelected(boolean selected) {
        ScaleTransition transition = new ScaleTransition(Duration.seconds(0.2), button);

        if (selected) {
            this.button.setStyle("-fx-text-fill: #C0C0C0; -fx-background-color: #D5573B");

            transition.setToX(1.1);
            transition.setToY(1.1);
            transition.setInterpolator(Interpolator.EASE_BOTH);

        } else {
            this.button.setStyle("-fx-text-fill: #D5573B; -fx-background-color: #C0C0C0");

            transition.setToX(1);
            transition.setToY(1);
        }

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
