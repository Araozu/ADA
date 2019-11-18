package lab3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ClosestPairDataWriter {

    private ArrayList<Coordinate> createCoordinateArrayList(int length) {
        ArrayList<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            result.add(new Coordinate((int) (Math.random() * 100), (int) (Math.random() * 100)));
        }
        return result;
    }

    private String getData(int length) {
        long startTime = System.currentTimeMillis();
        boolean result = new ClosestPairProblem(createCoordinateArrayList(length)).run();
        long endTime = System.currentTimeMillis();
        System.out.println("(" + (endTime - startTime) + "ms)");

        return length + ", " + startTime + ", " + endTime + ", " +
                (endTime - startTime) + ", " + result + "\n";
    }

    public void writeData(int length) throws IOException {
        FileWriter fout = null;
        try {
            String writeData;

            fout = new FileWriter("./data-cp.csv");
            fout.write("number_of_elements, start_time, end_time, duration, result\n");

            writeData = getData(length);
            fout.write(writeData);


        } catch (IOException e) {
            System.err.println("Error al abrir el archivo para escritura.\n" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fout != null) {
                fout.close();
            }
        }
    }

    private String getData2(int length) {
        ArrayList<Coordinate> items = createCoordinateArrayList(length);
        StringBuilder sb = new StringBuilder();
        for (Coordinate c: items) {
            sb.append(c.x).append(", ").append(c.y).append("\n");
        }

        return sb.toString();
    }

    public void writeData2(int length) throws IOException {
        FileWriter fout = null;
        try {
            String writeData;

            fout = new FileWriter("./data-cp-c.csv");
            fout.write("x-axis, y-axis\n");

            writeData = getData2(length);
            fout.write(writeData);


        } catch (IOException e) {
            System.err.println("Error al abrir el archivo para escritura.\n" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fout != null) {
                fout.close();
            }
        }
    }

}
