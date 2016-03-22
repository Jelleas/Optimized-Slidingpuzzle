import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        benchmarkExploreStateSpace(300);
        benchmarkFindSolution(300);
    }

    public static void benchmarkExploreStateSpace(int nTrials) {
        Puzzle puzzle = new Puzzle(new int[]{8, 7, 6, 5, 4, 3, 2, 1, 0});

        System.out.println("Benchmarking state space exploration.");
        System.out.println("Starting with: " + puzzle);
        System.out.println("Running " + nTrials + " trials, exploring the entire state space each time");

        PuzzleStorage puzzleStorage = null;
        ArrayList<Long> timings = new ArrayList<>();

        for (int i = 0; i < nTrials; i++) {
            long startTime = System.currentTimeMillis();
            puzzleStorage = Solver.solve(puzzle);
            long endTime = System.currentTimeMillis();

            timings.add(endTime - startTime);
        }

        ArrayList<Puzzle> maxDepthPuzzles = puzzleStorage.maxDepthPuzzles();

        System.out.println("Found " + puzzleStorage.size() + " puzzles");
        System.out.println("Found the following " + maxDepthPuzzles.size() + " puzzles at max depth: ");
        for (Puzzle p : maxDepthPuzzles)
            System.out.println(p);
        System.out.println("Fastest trial in " + Collections.min(timings) + " ms.");
        System.out.println();
    }

    public static void benchmarkFindSolution(int nTrials) {
        Puzzle puzzle = new Puzzle(new int[] {1, 4, 2, 5, 0, 8, 3, 6, 7});
        Puzzle solution = new Puzzle(new int[] {8, 7, 6, 5, 4, 3, 2, 1, 0});

        System.out.println("Starting with: " + puzzle);
        System.out.println("Searching for: " + solution);
        System.out.println("Running " + nTrials + " trials");

        PuzzleStorage puzzleStorage = null;
        ArrayList<Long> timings = new ArrayList<>();

        for (int i = 0; i < nTrials; i++) {
            long startTime = System.currentTimeMillis();
            puzzleStorage = Solver.solveUntill(puzzle, solution);
            long endTime = System.currentTimeMillis();

            timings.add(endTime - startTime);
        }

        System.out.println("Found " + puzzleStorage.size() + " puzzles");
        System.out.println("Fastest trial in " + Collections.min(timings) + " ms.");
        System.out.println();
    }
}


