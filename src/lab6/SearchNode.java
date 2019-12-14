package lab6;

public class SearchNode implements Comparable<SearchNode> {
    final Board board;
    final int moves;
    final SearchNode snode;
    final int priority;

    public SearchNode(Board board, SearchNode predecessor) {
        this.board = board;

        if (predecessor != null) moves = predecessor.getMoves() + 1;
        else moves = 0;

        snode = predecessor;
        priority = moves + board.getManhttan();
        // priority = moves + board.calculateHamming();
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

            return Integer.compare(this.priority - this.moves, sn.priority - sn.moves);
        }
        return 0;
    }

}

