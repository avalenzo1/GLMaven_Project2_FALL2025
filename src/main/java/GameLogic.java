import java.util.List;
import java.util.Set;

public class GameLogic {
    Set<Integer> selectedNumbers;
    int spotsCount;
    int drawingsCount;
    int currentDrawing;
    List<Set<Integer>> allDrawings;

    boolean validateSelection(int number) {

        return false;
    }

    Set<Integer> generateRandomNumbers(int count) {

        return Set.of();
    }

    Set<Integer> performDrawing() {

        return Set.of();
    }

    int calculateMatches(Set<Integer> drawnNumbers) {

        return 0;
    }

    void resetGameState() {

    }

    boolean isGameComplete() {

        return false;
    }

    boolean canSelectMoreNumbers() {

        return false;
    }


}
