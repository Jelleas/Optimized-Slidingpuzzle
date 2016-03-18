import java.util.Arrays;

public class Puzzle {
    private final long representation;

    public Puzzle(int[] values) {
        representation = getRepresentation(values);
    }

    private Puzzle(long puzzle) {
        representation = puzzle;
    }

    public Puzzle moveUp() {
        int zeroLoc = zeroLocation();
        return zeroLoc < 7 ? swap(zeroLoc, zeroLoc + 3) : null;
    }

    public Puzzle moveDown() {
        int zeroLoc = zeroLocation();
        return zeroLoc > 3 ? swap(zeroLoc, zeroLoc - 3) : null;
    }

    public Puzzle moveLeft() {
        int zeroLoc = zeroLocation();
        return (zeroLoc - 1) % 3 > 0 ? swap(zeroLoc, zeroLoc - 1) : null;
    }

    public Puzzle moveRight() {
        int zeroLoc = zeroLocation();
        return (zeroLoc - 1) % 3 < 2 ? swap(zeroLoc, zeroLoc + 1) : null;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Puzzle && ((Puzzle) o).representation == representation;
    }

    // thanks to: http://www.geekviewpoint.com/java/numbers/permutation_index
    @Override
    public int hashCode() {
        int hash = 0;
        int position = 2;
        int factor = 1;
        for (int p = 9 - 1; p > 0; p--) {
            int piece = piece(p);
            int successors = 0;
            for (int q = p + 1; q < 10; q++) {
                if (piece > piece(q)) {
                    successors++;
                }
            }
            hash += (successors * factor);
            factor *= position;
            position++;
        }
        return hash;
    }

    @Override
    public String toString() {
        String repr = "";

        for (int i = 9; i > 1; i--) {
            repr += piece(i) + ", ";
        }

        return repr + piece(1);
    }

    private int piece(int index) {
        return (int)((representation << (64 - (index) * 4)) >>> 60);
    }

    private int zeroLocation() {
        return (int)(representation >>> 36);
    }

    private Puzzle swap(int zeroLoc, int otherLoc) {
        // find the value in the puzzle to be swapped
        long val = piece(otherLoc);

        // swap the value
        long newPuzzle = representation - (val << ((otherLoc - 1) * 4)) + (val << (zeroLoc - 1) * 4);

        // place the new location of the zero in the puzzle, and return
        return new Puzzle(((newPuzzle << 28) >>> 28) + (((long)otherLoc) << 36));
    }

    private long getRepresentation(int[] values) {
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
