package com.company;

import java.util.LinkedList;

public class Solver {
    private PuzzleStorage puzzleStorage;
    private LinkedList<Long> unvisited;

    private Solver() {
        puzzleStorage = new PuzzleStorage();
        unvisited = new LinkedList<>();
    }

    public static PuzzleStorage solve (long puzzleToSolve) {
        Solver s = new Solver();
        s.unvisited.add(puzzleToSolve);
        s.puzzleStorage.add(puzzleToSolve, 0);

        while (!s.unvisited.isEmpty()) {
            s.move(s.unvisited.pollFirst());
        }

        return s.puzzleStorage;
    }

    private void move(long puzzle) {
        int zeroLoc = (int)(puzzle >>> 36);

        switch (zeroLoc) {
            case 1:
                add(swap(puzzle, 1, 2), puzzle);
                add(swap(puzzle, 1, 4), puzzle);
                break;
            case 2:
                add(swap(puzzle, 2, 1), puzzle);
                add(swap(puzzle, 2, 3), puzzle);
                add(swap(puzzle, 2, 5), puzzle);
                break;
            case 3:
                add(swap(puzzle, 3, 2), puzzle);
                add(swap(puzzle, 3, 6), puzzle);
                break;
            case 4:
                add(swap(puzzle, 4, 1), puzzle);
                add(swap(puzzle, 4, 5), puzzle);
                add(swap(puzzle, 4, 7), puzzle);
                break;
            case 5:
                add(swap(puzzle, 5, 2), puzzle);
                add(swap(puzzle, 5, 4), puzzle);
                add(swap(puzzle, 5, 6), puzzle);
                add(swap(puzzle, 5, 8), puzzle);
                break;
            case 6:
                add(swap(puzzle, 6, 3), puzzle);
                add(swap(puzzle, 6, 5), puzzle);
                add(swap(puzzle, 6, 9), puzzle);
                break;
            case 7:
                add(swap(puzzle, 7, 4), puzzle);
                add(swap(puzzle, 7, 8), puzzle);
                break;
            case 8:
                add(swap(puzzle, 8, 5), puzzle);
                add(swap(puzzle, 8, 7), puzzle);
                add(swap(puzzle, 8, 9), puzzle);
                break;
            case 9:
                add(swap(puzzle, 9, 6), puzzle);
                add(swap(puzzle, 9, 8), puzzle);
                break;
        }
    }

    private void add(long puzzle, long predecessor) {
        if (puzzleStorage.add(puzzle, predecessor))
            unvisited.addLast(puzzle);
    }

    private long swap(long puzzle, int zeroLoc, int otherLoc) {
        // find the value in the puzzle to be swapped
        long val = ((puzzle << (64 - otherLoc * 4)) >>> 60);

        // swap the value
        long newPuzzle = (puzzle - (val << ((otherLoc - 1) * 4))) | (val << (zeroLoc - 1) * 4);

        // place the new location of the zero in the puzzle, and return
        return ((newPuzzle << 28) >>> 28) | (((long)otherLoc) << 36);
    }
}
