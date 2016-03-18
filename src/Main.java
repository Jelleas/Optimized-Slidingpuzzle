import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(new int[]{8, 7, 6, 5, 4, 3, 2, 1, 0});

        System.out.println("Starting with: " + puzzle);

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

        ArrayList<Puzzle> maxDepthPuzzles = puzzleStorage.getMaxDepthPuzzles();

        System.out.println("Found " + puzzleStorage.size() + " puzzles");
        System.out.println("Found the following " + maxDepthPuzzles.size() + " puzzles at max depth: ");
        for (Puzzle p : maxDepthPuzzles)
            System.out.println(p);
        System.out.println("Fastest trial in " + Collections.min(timings) + " ms.");
    }
}


