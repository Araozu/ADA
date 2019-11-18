package lab2;

import java.io.FileWriter;
import java.io.IOException;

public class BubbleSortDataWriter {

    private String getData(int iteration) {
        long startTime = System.currentTimeMillis();
        boolean result = new BubleSort(iteration).run();
        long endTime = System.currentTimeMillis();
        System.out.println("(" + (endTime - startTime) + "ms)");

        return iteration + ", " + startTime + ", " + endTime + ", " +
                (endTime - startTime) + ", " + result + "\n";
    }

    public void writeData(int iterations, int multiplier, int sum) throws IOException {
        FileWriter fout = null;
        try {
            String writeData;

            fout = new FileWriter("./data.csv");
            fout.write("number_of_elements, start_time, end_time, duration, result\n");
            for (int i = 1; i <= iterations; ++i) {
                writeData = getData(i * multiplier + sum);
                fout.write(writeData);
            }

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
