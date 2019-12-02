package lab6;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {
    private boolean solvable = false;
    private int moves;
    private Stack<Board> steps;

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode snode;
        private final int priority;

        public SearchNode(Board board, SearchNode predecessor) {
            this.board = board;

            if (predecessor != null) moves = predecessor.getMoves() + 1;
            else moves = 0;

            snode = predecessor;
            priority = moves + board.manhattan();
        }

        public int getMoves() {
            return moves;
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getpredecessor() {
            return snode;
        }

        public boolean hasGrandFather() {
            return snode != null && snode.snode != null;
        }

        public Board getGrandFather() {
            return snode.board;
        }

        public int getpriority() {
            return priority;
        }

        @Override
        public int compareTo(SearchNode sn) {
            if (priority > sn.getpriority()) {
                return 1;
            }
            if (priority < sn.getpriority()) {
                return -1;
            }
            if (priority == sn.getpriority()) {

                if ((this.priority - this.moves) > (sn.priority - sn.moves)) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 0;
        }

    }

    public Solver(Board initial) {
        MinHeap<SearchNode> minpq = new MinHeap<>();

        if (initial == null)
            throw new IllegalArgumentException("Null argument");

        if (!initial.hasGoalBoard())
            throw new IllegalArgumentException("This board doesn't have a goal board.");

        // A∗ algorithm
        if (initial.isGoal()) {

            solvable = true;
            steps = new Stack<>();
            steps.push(initial);

        } else {

            SearchNode actualNode = new SearchNode(initial, null);

            minpq.insert(actualNode);

            while (!actualNode.board.isGoal()) {

                actualNode = minpq.removeHead();
                ArrayList<Board> possibleMoves = actualNode.board.neighbors();

                if (actualNode.hasGrandFather()) {
                    Board grandFather = actualNode.getGrandFather();

                    for (int i = 0; i < possibleMoves.size(); i++) {
                        if (possibleMoves.get(i).equals(grandFather)) {
                            possibleMoves.remove(i);
                            break;
                        }
                    }

                }

                for (Board b: possibleMoves) {
                    minpq.insert(new SearchNode(b, actualNode));
                }

            }

            solvable = true;

            createSteps(actualNode);

        }

    }

    private void createSteps(SearchNode finalNode) {

        Stack<Board> stack = new Stack<>();

        SearchNode actualNode = finalNode;
        while (actualNode.snode != null) {
            stack.push(actualNode.board);
            actualNode = actualNode.snode;
        }
        stack.push(actualNode.board);

        this.steps = stack;

    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return moves;
    }

    public Stack<Board> solution() {
        if (steps == null) return null;
        return steps;
    }

    public static void main(String[] args) {

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

        Board board1 = new Board(new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}});
        Board board2 = new Board(new int[][]{{0, 1, 6}, {7, 3, 2}, {4, 8, 5}}, board1);

        Solver s = new Solver(board2);

        Stack<Board> solution = s.solution();

        while(solution.size() > 0) {
            System.out.println(solution.pop());
        }



    }

}
