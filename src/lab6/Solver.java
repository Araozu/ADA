package lab6;

import java.util.Stack;


public class Solver {
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
