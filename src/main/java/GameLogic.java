import java.util.*;

public class GameLogic {
    Set<Integer> selectedNumbers;
    int spotsCount;
    int drawingsCount;
    int currentDrawing;
    List<Set<Integer>> allDrawings;

    GameLogic(int numDrawings, int numSpots) {
        selectedNumbers = new HashSet<>();
        allDrawings = new ArrayList<>();
        spotsCount = numSpots;
        drawingsCount = numDrawings;
        currentDrawing = 0;
    }

    boolean validateSelection(int number) {
        if (!canSelectMoreNumbers()) {
            System.out.println("You can't select more numbers!");
            return false;
        }

        if (selectedNumbers.contains(number)) {
            System.out.println("You already selected this number!");
            return false;
        }

        selectedNumbers.add(number);

        System.out.println("Selected number: " + number);

        return true;
    }

    Set<Integer> generateRandomNumbers(int count) {
        Set<Integer> randomNumbers = new HashSet<>();
        Random r = new Random();

        while (randomNumbers.size() < count) {
            int number = r.nextInt(80) + 1;

            randomNumbers.add(number);
        }

        return randomNumbers;
    }

    Set<Integer> performDrawing() {
        Set<Integer> drawing = generateRandomNumbers(20);

        allDrawings.add(drawing);

        return drawing;
    }

    int calculateMatches(Set<Integer> drawnNumbers) {
        Set<Integer> matchingNumbers = new HashSet<>(drawnNumbers);
        matchingNumbers.retainAll(selectedNumbers);

        return matchingNumbers.size();
    }

    void resetGameState() {
        selectedNumbers.clear();
        currentDrawing++;
    }

    boolean isGameComplete() {
        return selectedNumbers.size() >= spotsCount || currentDrawing >= drawingsCount;
    }

    boolean canSelectMoreNumbers() {
        System.out.println("You can't select more numbers!");
        return selectedNumbers.size() < spotsCount;
    }


}
