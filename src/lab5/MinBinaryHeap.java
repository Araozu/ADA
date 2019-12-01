package lab5;

public class MinBinaryHeap<T extends Comparable<T>> extends BinaryHeap<T> {

    public MinBinaryHeap(T[] arr) {
        super(arr);
    }

    @Override
    boolean compare(T a, T b) {
        return a.compareTo(b) < 0;
    }

}
