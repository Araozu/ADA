package lab3;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        /*//
        ArrayList<Coordinate> cList = new ArrayList<>();
        cList.add(new Coordinate(2, 3));
        cList.add(new Coordinate(12, 30));
        cList.add(new Coordinate(40, 50));
        cList.add(new Coordinate(5, 1));
        cList.add(new Coordinate(12, 10));
        cList.add(new Coordinate(3, 4));

        ClosestPairProblem c = new ClosestPairProblem(cList);
        double result = c.calc();
        System.out.println(result);
        // */

        ClosestPairDataWriter c = new ClosestPairDataWriter();
        c.writeData2(100000);
    }

}
