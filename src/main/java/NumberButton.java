import javafx.scene.control.Button;

public class NumberButton {
    private Button button;
    private int number;
    private boolean isSelected;
    private String style;

    public NumberButton(int number) {
        this.button = new Button();
        this.button.setText(String.valueOf(number));

        this.number = number;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }

    Button getButton() {
        return button;
    }

    int getNumber() {
        return number;
    }

    void reset() {
        isSelected = false;
    }


}
