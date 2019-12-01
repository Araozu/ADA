package lab5;

public class Main {

    public static void main(String[] args) {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        BinaryHeap<Integer> bh = new MinBinaryHeap<>(arr);
        System.out.println(bh);
        bh.sort();
        System.out.println(bh);
    }

}
