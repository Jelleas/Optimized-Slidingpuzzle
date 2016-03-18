import java.util.LinkedList;

public class Solver {
    private PuzzleStorage puzzleStorage;
    private LinkedList<Puzzle> unvisited;

    private Solver() {
        puzzleStorage = new PuzzleStorage();
        unvisited = new LinkedList<>();
    }

    public static PuzzleStorage solve(Puzzle puzzleToSolve) {
        Solver s = new Solver();
        s.unvisited.add(puzzleToSolve);
        s.puzzleStorage.add(puzzleToSolve, null);

        while (!s.unvisited.isEmpty()) {
            s.move(s.unvisited.pollFirst());
        }

        return s.puzzleStorage;
    }

    private void move(Puzzle puzzle) {
        switch (puzzle.zeroLocation()) {
            case 1:
                add(puzzle.swap(1, 2), puzzle);
                add(puzzle.swap(1, 4), puzzle);
                break;
            case 2:
                add(puzzle.swap(2, 1), puzzle);
                add(puzzle.swap(2, 3), puzzle);
                add(puzzle.swap(2, 5), puzzle);
                break;
            case 3:
                add(puzzle.swap(3, 2), puzzle);
                add(puzzle.swap(3, 6), puzzle);
                break;
            case 4:
                add(puzzle.swap(4, 1), puzzle);
                add(puzzle.swap(4, 5), puzzle);
                add(puzzle.swap(4, 7), puzzle);
                break;
            case 5:
                add(puzzle.swap(5, 2), puzzle);
                add(puzzle.swap(5, 4), puzzle);
                add(puzzle.swap(5, 6), puzzle);
                add(puzzle.swap(5, 8), puzzle);
                break;
            case 6:
                add(puzzle.swap(6, 3), puzzle);
                add(puzzle.swap(6, 5), puzzle);
                add(puzzle.swap(6, 9), puzzle);
                break;
            case 7:
                add(puzzle.swap(7, 4), puzzle);
                add(puzzle.swap(7, 8), puzzle);
                break;
            case 8:
                add(puzzle.swap(8, 5), puzzle);
                add(puzzle.swap(8, 7), puzzle);
                add(puzzle.swap(8, 9), puzzle);
                break;
            case 9:
                add(puzzle.swap(9, 6), puzzle);
                add(puzzle.swap(9, 8), puzzle);
                break;
        }
    }

    private void add(Puzzle puzzle, Puzzle predecessor) {
        if (puzzleStorage.add(puzzle, predecessor))
            unvisited.addLast(puzzle);
    }
}
