package lab6;

public class SearchNode implements Comparable<SearchNode> {
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

