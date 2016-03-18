import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PuzzleStorage implements Iterable<Puzzle>{
    private final class PuzzleStorageIterator implements Iterator<Puzzle> {
        private int cursor;
        private final int end;
        private PuzzleStorage puzzleStorage;

        public PuzzleStorageIterator(PuzzleStorage puzzleStorage) {
            this.puzzleStorage = puzzleStorage;
            cursor = 0;
            end = maxNumPuzzles;
        }

        public boolean hasNext() {
            PuzzlePredeccesorPair result = null;

            // find if next element exists, leave cursor there.
            while (result == null && cursor < end) {
                result = puzzleStorage.puzzles[cursor];
                if (result == null)
                    cursor++;
            }

            return cursor < end;
        }

        public Puzzle next() {
            Puzzle result = null;
            while (result == null) {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                result = puzzleStorage.puzzles[cursor].puzzle;
                cursor++;
            }

            return result;
        }
    }

    private class PuzzlePredeccesorPair {
        final Puzzle puzzle;
        final Puzzle predecessor;

        public PuzzlePredeccesorPair(Puzzle puzzle, Puzzle predecessor) {
            this.puzzle = puzzle;
            this.predecessor = predecessor;
        }
    }

    // There cannot exist more than 9! puzzles
    private final int maxNumPuzzles = 362880;

    private PuzzlePredeccesorPair[] puzzles;
    private int numberOfElements;

    public PuzzleStorage() {
        puzzles = new PuzzlePredeccesorPair[maxNumPuzzles];
    }

    public boolean add(Puzzle puzzle, Puzzle predecessor) {
        int index = index(puzzle);

        if (puzzles[index] != null) {
            return false;
        }

        puzzles[index] = new PuzzlePredeccesorPair(puzzle, predecessor);
        numberOfElements++;
        return true;
    }

    public Puzzle predecessor(Puzzle puzzle) {
        return puzzles[index(puzzle)].predecessor;
    }

    public boolean contains(Puzzle puzzle) {
        return puzzles[index(puzzle)] != null;
    }

    private int index(Puzzle puzzle) {
        return puzzle.hashCode();
    }

    public ArrayList<Puzzle> maxDepthPuzzles() {
        ArrayList<Puzzle> maxPuzzles = new ArrayList<>();
        long maxDepth = 0;

        for (Puzzle p : this) {
            Puzzle cursorPuzzle = p;
            int depth = 0;

            while (predecessor(cursorPuzzle) != null) {
                cursorPuzzle = predecessor(cursorPuzzle);
                depth++;
            }

            if (depth > maxDepth) {
                maxPuzzles = new ArrayList<>();
                maxDepth = depth;
            }

            if (depth == maxDepth)
                maxPuzzles.add(p);
        }

        return maxPuzzles;
    }

    public int size() {
        return numberOfElements;
    }

    public Iterator<Puzzle> iterator() {
        return new PuzzleStorageIterator(this);
    }
}
