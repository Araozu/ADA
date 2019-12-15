package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

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
