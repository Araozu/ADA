package lab5;

public class MaxBinaryHeap<T extends Comparable<T>> extends BinaryHeap<T> {


    public MaxBinaryHeap(T[] arr) {
        super(arr);
    }

    @Override
    boolean compare(T a, T b) {
        return a.compareTo(b) > 0;
    }

}
