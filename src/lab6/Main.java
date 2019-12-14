package lab6;

import java.util.Stack;

public class Main {

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

        // Board board1 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        // Board board2 = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}}, board1);

        //
        lab6.Board board = new lab6.Board(new int[][]{
                {13,  1, 10,  4},
                {8, 12,  6,  3},
                {15,  9,  5, 14},
                {0,  7,  2, 11},
        }, 4);
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

        lab6.Solver s = new Solver(board);

        if (s.isSolvable()) {
            Stack<Board> solution = s.solution();

            while(solution.size() > 0) {
                System.out.println(solution.pop());
            }
            System.out.println("There were " + s.numberOfMoves + " steps.");
        } else {
            System.out.println("The board is not solvable.");
        }

    }

}
