package lab2;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BubbleSortDataWriter writer = new BubbleSortDataWriter();
        writer.writeData(4, 50000, 250000);
    }

}
