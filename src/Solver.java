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
        add(puzzle.moveUp(), puzzle);
        add(puzzle.moveDown(), puzzle);
        add(puzzle.moveRight(), puzzle);
        add(puzzle.moveLeft(), puzzle);
    }

    private void add(Puzzle puzzle, Puzzle predecessor) {
        if (puzzle != null && puzzleStorage.add(puzzle, predecessor))
            unvisited.addLast(puzzle);
    }
}
