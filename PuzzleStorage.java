package com.company;

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
            return cursor < end;
        }

        public Long next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            long result = 0;
            while (result == 0) {
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
        boolean wasEmpty = puzzles[index] == 0;

        puzzles[index] = puzzle;
        predecessors[index] = predecessor;

        if (wasEmpty)
            numberOfElements++;

        return wasEmpty;
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

    private int index(long puzzle) {
        int seen0 = 0;
        int seen1 = 0;
        int seen2 = 0;
        int seen3 = 0;
        int seen4 = 0;
        int seen5 = 0;
        int seen6 = 0;
        int seen7 = 0;

        int product = 1;
        int index = 0;
        for (int i = 1; i < 9; i++) {
            int val = (int)((puzzle << (64 - i * 4)) >>> 60);

            switch (val) {
                case 0:
                    seen0 = 1;
                    break;
                case 1:
                    seen1 = 1;
                    val -= seen0;
                    break;
                case 2:
                    seen2 = 1;
                    val -= seen0 + seen1;
                    break;
                case 3:
                    seen3 = 1;
                    val -= seen0 + seen1 + seen2;
                    break;
                case 4:
                    seen4 = 1;
                    val -= seen0 + seen1 + seen2 + seen3;
                    break;
                case 5:
                    seen5 = 1;
                    val -= seen0 + seen1 + seen2 + seen3 + seen4;
                    break;
                case 6:
                    seen6 = 1;
                    val -= seen0 + seen1 + seen2 + seen3 + seen4 + seen5;
                    break;
                case 7:
                    seen7 = 1;
                    val -= seen0 + seen1 + seen2 + seen3 + seen4 + seen5 + seen6;
                    break;
                case 8:
                    val -= seen0 + seen1 + seen2 + seen3 + seen4 + seen5 + seen6 + seen7;
                    break;
            }

            product *= (10 - i);
            index += val * (maxNumPuzzles / product);
        }

        return index;
    }

    public int size() {
        return numberOfElements;
    }

    public Iterator<Long> iterator() {
        return new PuzzleStorageIterator(this);
    }
}
