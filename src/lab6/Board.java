package lab6;

import java.util.ArrayList;

public class Board {

    private final int[][] board;
    private final Board goalBoard;
    private final int manhattan;


    public Board(int[][] board, Board goalBoard, int manhattan) {
        if (board == null) {
            throw new IllegalArgumentException("The board is null.");
        }

        this.board = board;
        this.goalBoard = goalBoard;

        if (manhattan == -1)
            this.manhattan = calculateManhattan();
        else
            this.manhattan = manhattan;
    }

    // construct a board from an nxn array of blocks
    public Board(int[][] board, Board goalBoard) {
        this(board, goalBoard, -1);
    }

    public Board(int[][] board) {
        this(board, null);
    }


    public Board twin() {
        int zeroI = 0;
        int zeroJ = 0;

        loop:
        for (; zeroI < board.length; zeroI++) {
            int[] row = board[zeroI];
            for (; zeroJ < row.length; zeroJ++) {
                if (row[zeroJ] == 0) {
                    break loop;
                }
            }
            zeroJ = 0;
        }

        int pos1X;
        int pos1Y;
        int pos2X;
        int pos2Y;

        if (zeroI > 0) {
            pos1X = zeroI - 1;
            pos2X = zeroI - 1;
        } else {
            pos1X = zeroI + 1;
            pos2X = zeroI + 1;
        }

        pos1Y = zeroJ;
        if (zeroJ > 0) {
            pos2Y = zeroJ - 1;
        } else {
            pos2Y = zeroJ + 1;
        }

        int[][] copy = swapAndReturn(pos1X, pos1Y, pos2X, pos2Y);
        return new Board(copy, goalBoard);
    }

    public int[][] getBoard() {
        return board;
    }

    public Board getGoalBoard() {
        return goalBoard;
    }

    public int getManhttan() {
        return this.manhattan;
    }


    public boolean hasGoalBoard() {
        return goalBoard != null;
    }

    private int movesFrom(int elem, int i, int j) {
        int actualI = 0;
        int actualJ = 0;

        loop:
        for (; actualI < board.length; actualI++) {
            int[] row = board[actualI];
            for (; actualJ < row.length; actualJ++) {
                if (row[actualJ] == elem) {
                    break loop;
                }
            }
            actualJ = 0;
        }

        return Math.abs(i - actualI) + Math.abs(j - actualJ);
    }

    // sum of manhattan distances between blocks and goal
    private int calculateManhattan() {
        if (goalBoard == null) return Integer.MAX_VALUE;

        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            for (int j = 0; j < row.length; j++) {
                sum += goalBoard.movesFrom(row[j], i, j);
            }
        }

        return sum;
    }

    public int calculateHamming() {
        if (goalBoard == null) return Integer.MAX_VALUE;

        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            for (int j = 0; j < row.length; j++) {
                if (goalBoard.getBoard()[i][j] != this.board[i][j])
                    sum++;
            }
        }

        return sum;
    }

    // is this board the goal board
    public boolean isGoal() {
        if (goalBoard == null) throw new NullPointerException("There is no goal board...");
        return this.equals(goalBoard);
    }

    private int[][] cloneBoard() {
        int[][] clone = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            System.arraycopy(row, 0, clone[i], 0, row.length);
        }

        return clone;
    }

    private int[][] swapAndReturn(int i1, int j1, int i2, int j2) {
        int[][] newBoard = cloneBoard();

        int buffer = newBoard[i1][j1];
        newBoard[i1][j1] = newBoard[i2][j2];
        newBoard[i2][j2] = buffer;

        return newBoard;
    }

    // all possible board generated from parent
    public ArrayList<Board> neighbors() {
        ArrayList<Board> res = new ArrayList<>();

        int zeroI = 0;
        int zeroJ = 0;

        loop:
        for (; zeroI < board.length; zeroI++) {
            int[] row = board[zeroI];
            for (; zeroJ < row.length; zeroJ++) {
                if (row[zeroJ] == 0) {
                    break loop;
                }
            }
            zeroJ = 0;
        }

        if (zeroI > 0) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI - 1, zeroJ);
            res.add(new Board(copy, goalBoard));
        }

        if (zeroI < board.length - 1) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI + 1, zeroJ);
            res.add(new Board(copy, goalBoard));
        }

        if (zeroJ > 0) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI, zeroJ - 1);
            res.add(new Board(copy, goalBoard));
        }

        if (zeroJ < board[0].length - 1) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI, zeroJ + 1);
            res.add(new Board(copy, goalBoard));
        }

        return res;
    }


    // does this board equaly
    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            throw new IllegalArgumentException("The object is not a Board.");
        }

        Board b = (Board) y;

        if (b.getBoard().length == board.length && b.getBoard()[0].length == board[0].length) {

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (b.getBoard()[i][j] != board[i][j]) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        String res = "";
        for (int[] row : board) {
            res += "[";
            for (int value : row) {
                res += " " + value + " ";
            }
            res += "]\n";
        }

        return res;
    }

}
