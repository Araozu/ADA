package lab6;

import java.util.Stack;

public class Solver {
    private boolean solvable;
    private Stack<Board> steps;
    public int numberOfMoves = 0;


    public Solver(Board initial) {
        MinHeap<SearchNode> minpq = new MinHeap<>();
        MinHeap<SearchNode> twinpq = new MinHeap<>();

        if (initial == null)
            throw new IllegalArgumentException("Null argument");

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

            while (!actualNode.board.isGoal() && !actualTwinNode.board.isGoal() && minpq.size() > 0 && twinpq.size() > 0) {

                actualNode = minpq.removeHead();
                actualTwinNode = twinpq.removeHead();

                // System.out.println("Actual node");
                // System.out.println(actualNode.board);
                System.out.println(actualNode.priority);


                for (Board b : actualNode.board.neighbors()) {
                    if (actualNode.hasParent()) {
                        if (!b.equals(actualNode.getParent()))
                            minpq.insert(new SearchNode(b, actualNode));
                    } else {
                        minpq.insert(new SearchNode(b, actualNode));
                    }
                }

                for (Board b : actualTwinNode.board.neighbors()) {
                    if (!b.equals(actualTwinNode.board))
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
            this.numberOfMoves++;
            stack.push(actualNode.board);
            actualNode = actualNode.snode;
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
