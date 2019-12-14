package lab6;

import java.util.ArrayList;

public class Board {

    private final int[][] board;
    private final int manhattan;
    private final int size;


    public Board(int[][] board, int size) {
        if (board == null) {
            throw new IllegalArgumentException("The board is null.");
        }

        this.board = board;
        this.size = size;

        this.manhattan = manhattan();
    }

    private int[] generateRandomCoordinate() {
        return new int[]{(int) (Math.random() * board.length), (int) (Math.random() * board[0].length)};
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

        int pos1X = zeroI;
        int pos1Y = zeroJ;
        int pos2X = zeroI;
        int pos2Y = zeroJ;

        /*
        if (zeroI > 0) {
            pos1X = zeroI - 1;
            pos2X = zeroI - 1;
        } else {
            pos1X = zeroI + 1;
            pos2X = zeroI + 1;
        }

        pos1Y = zeroJ;
        if (zeroJ < 0) {
            pos2Y = zeroJ + 1;
        } else {
            pos2Y = zeroJ - 1;
        }
        */

        int[] c;
        do {
            c = generateRandomCoordinate();
            pos1X = c[0];
            pos1Y = c[1];

            if (Math.random() > 0.5) {
                pos2Y = pos1Y;
                if (pos1X > 0) {
                    pos2X = pos1X - 1;
                } else {
                    pos2X = pos1X + 1;
                }
            } else {
                pos2X = pos1X;
                if (pos1Y > 0) {
                    pos2Y = pos1Y - 1;
                } else {
                    pos2Y = pos1Y + 1;
                }
            }

        } while (pos1X == zeroI && pos1Y == zeroJ && pos2X == zeroI && pos2Y == zeroJ);

        System.out.println("Twin swaps from " + pos1X + "," + pos1Y + " to " + pos2X + "," + pos2Y);

        int[][] copy = swapAndReturn(pos1X, pos1Y, pos2X, pos2Y);
        return new Board(copy, this.size);
    }


    public int[][] getBoard() {
        return board;
    }


    public int getManhttan() {
        return this.manhattan;
    }


    /*
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
    */


    /*/ sum of manhattan distances between blocks and goal
    private int calculateManhattan() {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            for (int j = 0; j < row.length; j++) {
                sum += goalBoard.movesFrom(row[j], i, j);
            }
        }

        return sum;
    }
    // */

    public int manhattan() {
        int arrayPosition;
        int tile;
        int manhattan = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tile = board[i][j];
                if (tile == 0)
                    continue;
                arrayPosition = 1 + j + (i * this.size);

                if (arrayPosition - tile == 0)
                    continue;

                double ii = Math.floor(((double) (tile - 1)) / this.size);
                double jj = (tile - 1) % this.size;
                manhattan += (Math.abs(i - ii) + Math.abs(j - jj));

            }
        }

        return manhattan;
    }

    /*
    public int calculateHamming() {
        // if (goalBoard == null) return Integer.MAX_VALUE;

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
    */


    // is this board the goal board
    public boolean isGoal() {
        int arrayPosition;
        int tile;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1)
                    continue;
                tile = board[i][j];
                arrayPosition = 1 + j + (i * this.size);
                if (tile != arrayPosition)
                    return false;
            }
        }
        return true;
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
            res.add(new Board(copy, this.size));
        }

        if (zeroI < board.length - 1) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI + 1, zeroJ);
            res.add(new Board(copy, this.size));
        }

        if (zeroJ > 0) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI, zeroJ - 1);
            res.add(new Board(copy, this.size));
        }

        if (zeroJ < board[0].length - 1) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI, zeroJ + 1);
            res.add(new Board(copy, this.size));
        }

        return res;
    }


    // does this board equaly
    @Override
    public boolean equals(Object y) {
        if (y == null) return false;
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
        StringBuilder res = new StringBuilder();
        for (int[] row : board) {
            res.append("[");
            for (int value : row) {
                res.append(" ").append(value).append(" ");
            }
            res.append("]\n");
        }

        return res.toString();
    }

}
