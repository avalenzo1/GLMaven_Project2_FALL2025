import javafx.scene.control.Button;

public class NumberButton {
    private Button button;
    private int number;
    private boolean isSelected;
    private String style;

    public NumberButton(int number) {
        this.button = new Button();
        this.button.setText(String.valueOf(number));

        this.button.setStyle("-fx-color: #C0C0C0");
        this.button.setStyle("-fx-text-fill: #D5573B");

        this.number = number;
    }

    void setSelected(boolean selected) {
        if (selected) {
            this.button.setStyle("-fx-color: #D5573B");
            this.button.setStyle("-fx-text-fill: #C0C0C0");
        } else {
            this.button.setStyle("-fx-color: #C0C0C0");
            this.button.setStyle("-fx-text-fill: #D5573B");
        }

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
