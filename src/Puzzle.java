import java.util.Arrays;

public class Puzzle {
    public final long representation;

    public Puzzle(long puzzle) {
        representation = puzzle;
    }

    public Puzzle(int[] values) {
        representation = getRepresentation(values);
    }

    public int piece(int index) {
        return (int)((representation << (64 - (index) * 4)) >>> 60);
    }

    public void print() {
        String repr = "";

        for (int i = 9; i >= 1; i--) {
            repr += ((representation << (64 - i * 4)) >>> 60) + ", ";
        }

        System.out.println(repr);
    }

    public int zeroLocation() {
        return (int)(representation >>> 36);
    }

    public Puzzle swap(int otherLoc) {
        return swap(zeroLocation(), otherLoc);
    }

    public Puzzle swap(int zeroLoc, int otherLoc) {
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
