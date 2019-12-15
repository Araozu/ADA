
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



class Solver {
    private boolean solvable;
    private Stack<Board> steps;
    public int numberOfMoves = 0;


    public Solver(Board initial) {
        MinPQ<SearchNode> minpq = new MinPQ<>();
        MinPQ<SearchNode> twinpq = new MinPQ<>();

        if (initial == null)
            throw new IllegalArgumentException("Null argument");

        // A∗ algorithm
        if (initial.isGoal()) {

            solvable = true;
            steps = new Stack<>();
            steps.push(initial);

        } else {

            SearchNode actualNode = new SearchNode(initial, null);
            SearchNode actualTwinNode = new SearchNode(initial.twin2(), null);

            minpq.insert(actualNode);
            twinpq.insert(actualTwinNode);

            // O(8n²)
            while (!actualNode.board.isGoal() && !actualTwinNode.board.isGoal()) {

                actualNode = minpq.delMin();
                actualTwinNode = twinpq.delMin();

                /*/
                int size = minpq.size();
                System.out.println(size +
                        " Pr (" + actualNode.priority + ") - " +
                        "Mov (" + actualNode.moves + ") - Man (" + actualNode.manhattan + ")");
                //*/

                // System.out.println(actualNode.board);

                /*/
                System.out.println("// ->" + size2 +
                        " Pr (" + actualTwinNode.priority + ") - " +
                        "Mov (" + actualTwinNode.moves + ") - Man (" + actualTwinNode.manhattan + ")");
                //*/


                // O(4n²)
                for (Board b : actualNode.board.neighbors()) {

                    //
                    if (actualNode.hasParent()) {
                        if (!b.equals(actualNode.getParent()))
                            minpq.insert(new SearchNode(b, actualNode));
                    } else {
                        minpq.insert(new SearchNode(b, actualNode));
                    }
                    // */
                }

                // O(4n²)
                for (Board b : actualTwinNode.board.neighbors()) {


                    //
                    if (actualTwinNode.hasParent()) {
                        if (!b.equals(actualTwinNode.getParent()))
                            twinpq.insert(new SearchNode(b, actualTwinNode));
                    } else {
                        twinpq.insert(new SearchNode(b, actualTwinNode));
                    }
                    //*/
                }

            }

            solvable = actualNode.board.isGoal();

            createSteps(actualNode);

        }

    }


    private void createSteps(SearchNode finalNode) {

        Stack<Board> stack = new Stack<>();

        SearchNode actualNode = finalNode;
        while (actualNode.previous != null) {
            this.numberOfMoves++;
            stack.push(actualNode.board);
            actualNode = actualNode.previous;
        }
        stack.push(actualNode.board);

        this.steps = stack;

    }


    public boolean isSolvable() {
        return solvable;
    }


    public Stack<Board> solution() {
        if (steps == null) return null;
        return steps;
    }

}

class SearchNode implements Comparable<SearchNode> {
    final Board board;
    final int moves;
    final SearchNode previous;
    final int priority;
    final int manhattan;

    public SearchNode(Board board, SearchNode predecessor) {
        this.board = board;

        if (predecessor != null) moves = predecessor.moves + 1;
        else moves = 0;

        previous = predecessor;
        manhattan = board.getManhattan();

        priority = moves + manhattan;
    }

    public boolean hasParent() {
        return previous != null;
    }

    public Board getParent() {
        return previous.board;
    }

    public int getpriority() {
        return priority;
    }

    @Override
    public int compareTo(SearchNode that) {

        if(this.priority == that.priority) return 0;
        return (this.priority > that.priority) ? 1 :  -1;

        /*/
        if (priority > sn.getpriority()) {
            return 1;
        }
        if (priority < sn.getpriority()) {
            return -1;
        }
        if (priority == sn.getpriority()) {

            return Integer.compare(this.priority - this.moves, sn.priority - sn.moves);
        }
        return 0;
        // */
    }

}

class MinPQ<E extends Comparable<E>> {
    private int capacity = 10;
    protected int size = 0;

    private Object[] items = new Object[capacity];

    private int getLeftChildIndex(int parentIndex) {
        return 2*parentIndex+1;
    }
    private int getRightChildIndex(int parentIndex) {
        return 2*parentIndex+2;
    }
    private int getParentIndex(int childIndex) {
        return (childIndex - 1)/2;
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index)<size;
    }
    private boolean hasRightChild(int index) {
        return getRightChildIndex(index)<size;
    }
    private boolean hasParent(int index) {
        return getParentIndex(index)>=0;
    }

    private E leftChild(int index) {
        return (E)items[getLeftChildIndex(index)];
    }
    private E rightChild(int index) {
        return (E)items[getRightChildIndex(index)];
    }
    private E parent(int index) {
        return (E)items[getParentIndex(index)];
    }

    private void swap(int indexOne, int indexTwo) {
        E temp = (E)items[indexOne];
        items[indexOne]=items[indexTwo];
        items[indexTwo]=temp;
    }

    private void ensureExtraCapacity() {
        if(size==capacity) {
            items = Arrays.copyOf(items, capacity*2);
            capacity*=2;
        }
    }

    public E min() {
        if(size==0) throw new IllegalStateException();
        return (E)items[0];
    }

    public E delMin() {
        if(size==0) throw new IllegalStateException();
        E item = (E)items[0];
        items[0] = items[size-1];
        size--;
        demotion();
        return item;
    }

    public void insert(E item) {
        ensureExtraCapacity();
        items[size]=item;
        size++;
        promotion();
    }


    private void promotion() {
        int index = size-1;
        while(hasParent(index) && parent(index).compareTo((E)items[index])>0) {
            swap(getParentIndex(index),index);
            index = getParentIndex(index);
        }
    }

    private void demotion() {
        int index = 0;
        while(hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && rightChild(index).compareTo(leftChild(index))<0) {
                smallerChildIndex = getRightChildIndex(index);
            }

            if(((E) (items[index])).compareTo((E)items[smallerChildIndex])<0) {
                break;
            } else {
                swap(index,smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }

}

class Board {

    private final int[][] board;
    private final int manhattan;
    private final int size;

    private int zeroI = 0;
    private int zeroJ = 0;

    // O(2n²)
    public Board(int[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("The board is null.");
        }

        this.board = board;
        this.size = board.length;

        this.findZeroPos();
        this.manhattan = manhattan();
    }

    // O(1)
    private int[] generateRandomCoordinate() {
        return new int[]{(int) (Math.random() * board.length), (int) (Math.random() * board[0].length)};
    }

    // O(n²)
    public void findZeroPos() {
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
    }


    // O(n²)
    public Board twin2() {

        int pos1X;
        int pos1Y;
        int pos2X;
        int pos2Y;

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

        int[][] copy = swapAndReturn(pos1X, pos1Y, pos2X, pos2Y);
        return new Board(copy);
    }
    // */


    public Board twin() {
        int[][] twin = cloneBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0 && board[i][j + 1] != 0 && j < (size - 1)) {
                    int swap = twin[i][j];
                    twin[i][j] = twin[i][j + 1];
                    twin[i][j + 1] = swap;
                    return new Board(twin);
                }
            }
        }
        return new Board(twin);
    }


    public int[][] getBoard() {
        return board;
    }


    public int getManhattan() {
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

    // O(n²)
    private int manhattan() {
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
    public int hamming() {
        int arrayPosition;
        int tile;
        int displaced = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tile = board[i][j];
                if (tile == 0)
                    continue;
                arrayPosition = 1 + j + (i * this.size);

                if (tile != arrayPosition)
                    displaced++;
            }
        }
        return displaced;
    }


    // O(n²)
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

    // O(n²)
    private int[][] cloneBoard() {
        int[][] clone = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            System.arraycopy(row, 0, clone[i], 0, row.length);
        }

        return clone;
    }

    // O(n²)
    private int[][] swapAndReturn(int i1, int j1, int i2, int j2) {
        int[][] newBoard = cloneBoard();

        int buffer = newBoard[i1][j1];
        newBoard[i1][j1] = newBoard[i2][j2];
        newBoard[i2][j2] = buffer;

        return newBoard;
    }

    // O(4n²)
    public ArrayList<Board> neighbors() {
        ArrayList<Board> res = new ArrayList<>();

        if (zeroI > 0) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI - 1, zeroJ);
            res.add(new Board(copy));
        }

        if (zeroI < board.length - 1) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI + 1, zeroJ);
            res.add(new Board(copy));
        }

        if (zeroJ > 0) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI, zeroJ - 1);
            res.add(new Board(copy));
        }

        if (zeroJ < board[0].length - 1) {
            int[][] copy = swapAndReturn(zeroI, zeroJ, zeroI, zeroJ + 1);
            res.add(new Board(copy));
        }

        return res;
    }


    // O(n²)
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

    // O(n²)
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

public class Test {

    private static int[][] readFromFile(String fileName) throws FileNotFoundException {

        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        int size = Integer.parseInt(sc.nextLine());

        int[][] result = new int[size][size];

        for (int i = 0; i < size; i++) {

            String line = sc.nextLine();
            String[] numbers = line.split(" ");
            int actualJ = 0;
            for (String number : numbers) {
                if (number.equals("")) continue;

                result[i][actualJ] = Integer.parseInt(number);
                actualJ++;
            }

        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {

        /*
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(8);
        nums.add(5);
        nums.add(3);
        nums.add(2);
        nums.add(7);
        nums.add(6);
        nums.add(4);

        MinHeap<Integer> mh = new MinHeap<>(nums);
        System.out.println(mh);
        mh.heapify();
        System.out.println(mh);
        mh.insert(10);
        System.out.println(mh);
        mh.insert(-1);
        System.out.println(mh);
        */

        // Board board1 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        // Board board2 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}}, board1);

        int[][] boardRaw = readFromFile("data.txt");
        /*/
        Board board = new lab6.Board(new int[][]{
                {13,  1, 10,  4},
                {8, 12,  6,  3},
                {15,  9,  5, 14},
                {0,  7,  2, 11},
        });
        // */

        /*/
        Board targetBoard = new Board(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}});
        Board board2 = new Board(new int[][]{
                {1, 0, 2},
                {7, 5, 4},
                {8, 6, 3}}, targetBoard);
        // */

        Solver s = new Solver(new Board(boardRaw));

        if (s.isSolvable()) {
            Stack<Board> solution = s.solution();

            while (solution.size() > 0) {
                System.out.println(solution.pop());
            }
            System.out.println("There were " + s.numberOfMoves + " steps.");
        } else {
            System.out.println("The board is not solvable.");
        }

    }

}
