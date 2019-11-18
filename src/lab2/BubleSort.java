package lab2;

public class BubleSort implements IAlgorithm {

    private int length;

    BubleSort(int length) {
        this.length = length;
    }

    @Override
    public String description() {
        return "Bubble Sort Problem";
    }

    @Override
    public boolean run() {
        // Creates a random array
        int[] data = new int[length];
        for (int i = 0; i < length; i++) {
            data[i] = (int) (Math.random() * 10000);
        }
        System.out.print(length + "i terminado. ");

        // explosive bubble sort
        for (int i = 0; i < length - 1; i++) {
            for (int j = 1; j < length; j++) {
                if (data[j - 1] > data[j]) {
                    int temp = data[j - 1];
                    data[j - 1] = data[j];
                    data[j] = temp;
                }
            }
        }
        System.out.print("Orden terminado. ");

        // how to validate??
        for (int i = 1; i < length; i++) {
            if (data[i - 1] > data[i]) return false;
        }
        return true;
    }

}
