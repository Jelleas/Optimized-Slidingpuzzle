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
            Puzzle result = null;

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

                result = puzzleStorage.puzzles[cursor];
                cursor++;
            }

            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // There cannot exist more than 9! puzzles
    private final int maxNumPuzzles = 362880;

    private Puzzle[] puzzles;
    private Puzzle[] predecessors;
    private int numberOfElements;

    public PuzzleStorage() {
        puzzles = new Puzzle[maxNumPuzzles];
        predecessors = new Puzzle[maxNumPuzzles];
    }

    public boolean add(Puzzle puzzle, Puzzle predecessor) {
        int index = index(puzzle);

        if (puzzles[index] != null) {
            return false;
        }

        puzzles[index] = puzzle;
        predecessors[index] = predecessor;
        numberOfElements++;
        return true;
    }

    public Puzzle get(Puzzle puzzle) {
        return puzzles[index(puzzle)];
    }

    public Puzzle getPredecessor(Puzzle puzzle) {
        return predecessors[index(puzzle)];
    }

    public boolean contains(Puzzle puzzle) {
        return puzzles[index(puzzle)] != null;
    }

    // thanks to: http://www.geekviewpoint.com/java/numbers/permutation_index
    private int index(Puzzle puzzle) {
        int index = 0;
        int position = 2;
        int factor = 1;
        for (int p = 9 - 1; p > 0; p--) {
            int piece = puzzle.piece(p);
            int successors = 0;
            for (int q = p + 1; q < 10; q++) {
                if (piece > puzzle.piece(q)) {
                    successors++;
                }
            }
            index += (successors * factor);
            factor *= position;
            position++;
        }
        return index;
    }

    public ArrayList<Puzzle> getMaxDepthPuzzles() {
        ArrayList<Puzzle> maxPuzzles = new ArrayList<>();
        long maxDepth = 0;

        for (Puzzle p : this) {
            Puzzle cursorPuzzle = p;
            int depth = 0;

            while (this.getPredecessor(cursorPuzzle) != null) {
                cursorPuzzle = this.getPredecessor(cursorPuzzle);
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
