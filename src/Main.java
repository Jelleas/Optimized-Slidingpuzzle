import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        long puzzle = getPuzzle(new int[]{8, 7, 6, 5, 4, 3, 2, 1, 0});

        System.out.println("Starting with: ");
        print(puzzle);

        int nTrials = 100;
        System.out.println("Running " + nTrials + " trials, exploring the entire state space each time");

        PuzzleStorage puzzleStorage = null;
        ArrayList<Long> timings = new ArrayList<>();

        for (int i = 0; i < nTrials; i++) {
            long startTime = System.currentTimeMillis();
            puzzleStorage = Solver.solve(puzzle);
            long endTime = System.currentTimeMillis();

            timings.add(endTime - startTime);
        }

        ArrayList<Long> maxDepthPuzzles = puzzleStorage.getMaxDepthPuzzles();

        System.out.println("Found " + puzzleStorage.size() + " puzzles");
        System.out.println("Found the following " + maxDepthPuzzles.size() + " puzzles at max depth: ");
        for (Long p : maxDepthPuzzles)
            print(p);
        System.out.println("Fastest trial in " + Collections.min(timings) + " ms.");
    }

    public static void print(long puzzle) {
        String repr = "";

        for (int i = 9; i >= 1; i--) {
            repr += ((puzzle << (64 - i * 4)) >>> 60) + ", ";
        }

        System.out.println(repr);
    }

    public static long getPuzzle(int[] values) {
        if (values.length != 9)
            throw new IllegalArgumentException("Array `values` must be of length 9.");

        int[] sortedValues = values.clone();
        Arrays.sort(sortedValues);
        int[] correctValues = {0,1,2,3,4,5,6,7,8};

        for (int i = 0; i < correctValues.length; i++)
            if (sortedValues[i] != correctValues[i])
                throw new IllegalArgumentException(
                        "Array `values` must contain no duplicates and only values of 0 to 8.");

        long zeroPosition = 0;

        for (int i = 0; i < values.length; i++)
            if (values[i] == 0) {
                zeroPosition = (9 - i);
                break;
            }

        long puzzle = zeroPosition << 36;

        for (int i = 0; i < 9; i++) {
            puzzle += ((long)values[i]) << ((8 - i) * 4);
        }

        return puzzle;
    }
}


