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
            priority = moves + board.getManhttan();
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

        public boolean hasParent() {
            return snode != null && snode.snode != null;
        }

        public Board getParent() {
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
        MinHeap<SearchNode> twinpq = new MinHeap<>();

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
            SearchNode actualTwinNode = new SearchNode(initial.twin(), null);

            minpq.insert(actualNode);
            twinpq.insert(actualTwinNode);

            while (!actualNode.board.isGoal() && !actualTwinNode.board.isGoal()) {

                actualNode = minpq.removeHead();
                actualTwinNode = twinpq.removeHead();

                ArrayList<Board> possibleMoves = actualNode.board.neighbors();
                ArrayList<Board> possibleTwinMoves = actualTwinNode.board.neighbors();

                if (actualNode.hasParent()) {
                    Board grandFather = actualNode.getParent();

                    for (int i = 0; i < possibleMoves.size(); i++) {
                        if (possibleMoves.get(i).equals(grandFather)) {
                            possibleMoves.remove(i);
                            break;
                        }
                    }

                }

                if (actualTwinNode.hasParent()) {
                    Board grandFather = actualTwinNode.getParent();

                    for (int i = 0; i < possibleTwinMoves.size(); i++) {
                        if (possibleTwinMoves.get(i).equals(grandFather)) {
                            possibleTwinMoves.remove(i);
                            break;
                        }
                    }

                }

                for (Board b: possibleMoves) {
                    minpq.insert(new SearchNode(b, actualNode));
                }

                for (Board b: possibleTwinMoves) {
                    twinpq.insert(new SearchNode(b, actualTwinNode));
                }

            }

            solvable = actualNode.board.isGoal();

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

        Board board1 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Board board2 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}}, board1);

        Solver s = new Solver(board2);

        if (s.solvable) {
            Stack<Board> solution = s.solution();

            while(solution.size() > 0) {
                System.out.println(solution.pop());
            }
        } else {
            System.out.println("The board is not solvable.");
        }

    }

}
