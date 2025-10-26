import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class GameLogicTests {
    private GameLogic gameLogic;
    private final int TEST_SPOTS = 5;

    @BeforeEach
    void init() {
        int TEST_DRAWINGS = 3;
        gameLogic = new GameLogic(TEST_DRAWINGS, TEST_SPOTS);
    }

    // Test 1: Game initialization with valid parameters
    // Test 1: Random numbers generated for user are within valid range
    @Test
    void testRandomNumbersForUserAreWithinRange() {
        Set<Integer> randomNumbers = gameLogic.generateRandomNumbers(8);
        for (int number : randomNumbers) {
            assertTrue(number >= 1 && number <= 80, "Number should be between 1 and 80");
        }
    }


    // Test 2: Random numbers for user are unique
    @Test
    void testRandomNumbersForUserAreUnique() {
        Set<Integer> randomNumbers = gameLogic.generateRandomNumbers(15);
        assertEquals(15, randomNumbers.size(), "All generated numbers should be unique");
    }


    // Test 3: Random numbers for user have correct count
    @Test
    void testRandomNumbersForUserHaveCorrectCount() {
        Set<Integer> randomNumbers = gameLogic.generateRandomNumbers(5);
        assertEquals(5, randomNumbers.size(), "Should generate exactly 5 numbers");
    }


    // Test 4: System drawing generates correct number of random numbers
    @Test
    void testSystemDrawingGeneratesCorrectCount() {
        // First select user numbers
        for (int i = 1; i <= TEST_SPOTS; i++) {
            gameLogic.validateSelection(i);
        }

        Set<Integer> systemDrawing = gameLogic.performDrawing();
        assertEquals(20, systemDrawing.size(), "System should draw 20 numbers");
    }

    // Test 5: System drawing numbers are within valid range
    @Test
    void testSystemDrawingNumbersWithinRange() {
        for (int i = 1; i <= TEST_SPOTS; i++) {
            gameLogic.validateSelection(i);
        }

        Set<Integer> systemDrawing = gameLogic.performDrawing();
        for (int number : systemDrawing) {
            assertTrue(number >= 1 && number <= 80, "System drawing numbers should be between 1 and 80, it generated" + number);
        }
    }

    // Test 6: System drawing numbers are unique
    @Test
    void testSystemDrawingNumbersAreUnique() {
        for (int i = 1; i <= TEST_SPOTS; i++) {
            gameLogic.validateSelection(i);
        }

        Set<Integer> systemDrawing = gameLogic.performDrawing();
        assertEquals(20, systemDrawing.size(), "All system drawn numbers should be unique");
    }

    // Test 7: Perfect match - all user numbers match system numbers
    @Test
    void testPerfectMatchAllNumbers() {
        // Set up identical selections
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5);
        gameLogic.selectedNumbers.addAll(userNumbers);

        Set<Integer> systemDrawing = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        int matches = gameLogic.calculateMatches(systemDrawing);

        assertEquals(5, matches, "Should match all 5 user numbers");
    }

    // Test 8: Partial match - some user numbers match system numbers
    @Test
    void testPartialMatchSomeNumbers() {
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5);
        gameLogic.selectedNumbers.addAll(userNumbers);

        Set<Integer> systemDrawing = Set.of(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39);
        int matches = gameLogic.calculateMatches(systemDrawing);

        assertEquals(3, matches, "Should match 3 user numbers: 1, 3, 5");
    }
    // Test 9: No match - no user numbers match system numbers
    @Test
    void testNoMatchNoCommonNumbers() {
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5);
        gameLogic.selectedNumbers.addAll(userNumbers);

        Set<Integer> systemDrawing = Set.of(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25);
        int matches = gameLogic.calculateMatches(systemDrawing);

        assertEquals(0, matches, "Should have no matches");
    }
    // Test 10: Single match - only one user number matches system
    @Test
    void testSingleMatchOneNumber() {
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5);
        gameLogic.selectedNumbers.addAll(userNumbers);

        Set<Integer> systemDrawing = Set.of(1, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);
        int matches = gameLogic.calculateMatches(systemDrawing);

        assertEquals(1, matches, "Should match only 1 number: 1");
    }
    // Test 11: Match calculation with empty user selection
    @Test
    void testMatchCalculationWithEmptyUserSelection() {
        Set<Integer> systemDrawing = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        int matches = gameLogic.calculateMatches(systemDrawing);

        assertEquals(0, matches, "Should have 0 matches with empty user selection");
    }

    // Test 12: User selection validation prevents duplicates
    @Test
    void testUserSelectionPreventsDuplicates() {
        assertTrue(gameLogic.validateSelection(10), "First selection should be valid");
        assertFalse(gameLogic.validateSelection(10), "Duplicate selection should be invalid");
    }

    // Test 13: User can  deselect and reselect numbers within same game
    @Test
    void testUserCanDeselectAndReselectNumbers() {
        // Select a number
        assertTrue(gameLogic.validateSelection(25), "Should be able to select number 25");
        assertTrue(gameLogic.selectedNumbers.contains(25), "Number 25 should be in selected set");

        // Deselect the number
        assertTrue(gameLogic.validateDeselection(25), "Should be able to deselect number 25");
        assertFalse(gameLogic.selectedNumbers.contains(25), "Number 25 should be removed from selected set");

        // Reselect the same number
        assertTrue(gameLogic.validateSelection(25), "Should be able to reselect number 25");
        assertTrue(gameLogic.selectedNumbers.contains(25), "Number 25 should be back in selected set");

        // Verify selection count is correct
        assertEquals(1, gameLogic.selectedNumbers.size(), "Should have exactly 1 selected number");
    }

    // Test 14: User selection dosent go past  spot limit
    @Test
    void testUserSelectionRespectsSpotLimit() {
        // Fill all available spots
        for (int i = 1; i <= TEST_SPOTS; i++) {
            assertTrue(gameLogic.validateSelection(i), "Selection " + i + " should be valid");
        }

        // Try to exceed spot limit
        assertFalse(gameLogic.validateSelection(TEST_SPOTS + 1), "Should not allow exceeding spot limit");
    }

    // Test 15: User deselection works
    @Test
    void testUserDeselection() {
        gameLogic.validateSelection(15);
        assertTrue(gameLogic.selectedNumbers.contains(15), "Number 15 should be selected");

        assertTrue(gameLogic.validateDeselection(15), "Deselection should be valid");
        assertFalse(gameLogic.selectedNumbers.contains(15), "Number 15 should be deselected");
    }

    // Test 16: User cannot deselect non-selected number
    @Test
    void testUserCannotDeselectNonSelectedNumber() {
        assertFalse(gameLogic.validateDeselection(25), "Should not allow deselecting non-selected number");
    }

    // Test 17: Current drawing increments after each system drawing
    @Test
    void testCurrentDrawingIncrements() {
        for (int i = 1; i <= TEST_SPOTS; i++) {
            gameLogic.validateSelection(i);
        }

        int initialDrawing = gameLogic.currentDrawing;
        gameLogic.performDrawing();
        gameLogic.resetGameState();
        assertEquals(initialDrawing + 1, gameLogic.currentDrawing, "Current drawing should increment, initial Drawing: " + (initialDrawing + 1) + " Should be: " + gameLogic.currentDrawing);
    }

    // Test 18: Game completion after all drawings
    @Test
    void testGameCompletionAfterAllDrawings() {
        gameLogic.currentDrawing = gameLogic.drawingsCount;
        assertTrue(gameLogic.isGameComplete(), "Game should be complete after all drawings");
    }

    // Test 19: Game not complete before all drawings
    @Test
    void testGameNotCompleteBeforeAllDrawings() {
        gameLogic.currentDrawing = gameLogic.drawingsCount - 1;
        assertFalse(gameLogic.isGameComplete(), "Game should not be complete before all drawings");
    }

    // Test 20: Can select more numbers when under limit
    @Test
    void testCanSelectMoreNumbersWhenUnderLimit() {
        gameLogic.validateSelection(1);
        gameLogic.validateSelection(2);
        assertTrue(gameLogic.canSelectMoreNumbers(), "Should allow more selections when under limit");
    }

    // Test 21: Cant select more #s when at limit
    @Test
    void testCannotSelectMoreNumbersWhenAtLimit() {
        for (int i = 1; i <= TEST_SPOTS; i++) {
            gameLogic.validateSelection(i);
        }
        assertFalse(gameLogic.canSelectMoreNumbers(), "Should not allow more selections when at limit");
    }

    // Test 22: Random number generation for different spot counts
    @Test
    void testRandomNumberGenerationForDifferentSpots() {
        int[] spotCounts = {1, 4, 8, 10};
        for (int spots : spotCounts) {
            Set<Integer> numbers = gameLogic.generateRandomNumbers(spots);
            assertEquals(spots, numbers.size(), "Should generate exactly " + spots + " numbers");
        }
    }

    // Test 23: System drawing stores results in allDrawings
    @Test
    void testSystemDrawingStoresResults() {
        for (int i = 1; i <= TEST_SPOTS; i++) {
            gameLogic.validateSelection(i);
        }

        int initialDrawingsCount = gameLogic.allDrawings.size();
        gameLogic.performDrawing();

        assertEquals(initialDrawingsCount + 1, gameLogic.allDrawings.size(), "Should store drawing in allDrawings");
    }

    // Test 24: Multiple drawings have separate results
    @Test
    void testMultipleDrawingsMaintainSeparateResults() {
        for (int i = 1; i <= TEST_SPOTS; i++) {
            gameLogic.validateSelection(i);
        }

        Set<Integer> firstDrawing = gameLogic.performDrawing();
        Set<Integer> secondDrawing = gameLogic.performDrawing();

        assertNotSame(firstDrawing, secondDrawing, "Drawings should be separate instances");
    }

    // Test 25: Match calculation with large overlap
    @Test
    void testMatchCalculationWithLargeOverlap() {
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        gameLogic.selectedNumbers.addAll(userNumbers);

        Set<Integer> systemDrawing = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        int matches = gameLogic.calculateMatches(systemDrawing);

        assertEquals(10, matches, "Should match all 10 user numbers");
    }

    // Test 26: Edge case - user selects maximum possible numbers
    @Test
    void testUserSelectsMaximumNumbers() {
        GameLogic maxGame = new GameLogic(1, 10); // 10 spots maximum
        for (int i = 1; i <= 10; i++) {
            assertTrue(maxGame.validateSelection(i), "Should allow selecting " + i);
        }
        assertEquals(10, maxGame.selectedNumbers.size(), "Should have 10 selected numbers");
    }
}