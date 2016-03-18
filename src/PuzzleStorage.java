import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PuzzleStorage implements Iterable<Long>{
    private final class PuzzleStorageIterator implements Iterator<Long> {
        private int cursor;
        private final int end;
        private PuzzleStorage puzzleStorage;

        public PuzzleStorageIterator(PuzzleStorage puzzleStorage) {
            this.puzzleStorage = puzzleStorage;
            cursor = 0;
            end = maxNumPuzzles;
        }

        public boolean hasNext() {
            long result = 0;

            // find if next element exists, leave cursor there.
            while (result == 0 && cursor < end) {
                result = puzzleStorage.puzzles[cursor];
                if (result == 0)
                    cursor++;
            }

            return cursor < end;
        }

        public Long next() {
            long result = 0;
            while (result == 0) {
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

    private long[] puzzles;
    private long[] predecessors;
    private int numberOfElements;

    public PuzzleStorage() {
        puzzles = new long[maxNumPuzzles];
        predecessors = new long[maxNumPuzzles];
    }

    public boolean add(long puzzle, long predecessor) {
        int index = index(puzzle);

        if (puzzles[index] != 0) {
            return false;
        }

        puzzles[index] = puzzle;
        predecessors[index] = predecessor;
        numberOfElements++;
        return true;
    }

    public long get(long puzzle) {
        return puzzles[index(puzzle)];
    }

    public long getPredecessor(long puzzle) {
        return predecessors[index(puzzle)];
    }

    public boolean contains(long puzzle) {
        return puzzles[index(puzzle)] != 0;
    }

    // thanks to: http://www.geekviewpoint.com/java/numbers/permutation_index
    private int index(long puzzle) {
        int index = 0;
        int position = 2;// position 1 is paired with factor 0 and so is skipped
        int factor = 1;
        for (int p = 9 - 1; p > 0; p--) {
            int val1 = (int)((puzzle << (64 - (p) * 4)) >>> 60);
            int successors = 0;
            for (int q = p + 1; q < 10; q++) {
                int val2 = (int)((puzzle << (64 - (q) * 4)) >>> 60);
                if (val1 > val2) {
                    successors++;
                }
            }
            index += (successors * factor);
            factor *= position;
            position++;
        }
        return index;
    }

    public ArrayList<Long> getMaxDepthPuzzles() {
        ArrayList<Long> maxPuzzles = new ArrayList<>();
        long maxDepth = 0;

        for (long p : this) {
            long cursorPuzzle = p;
            int depth = 0;

            while (this.getPredecessor(cursorPuzzle) != 0) {
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

    public Iterator<Long> iterator() {
        return new PuzzleStorageIterator(this);
    }
}
