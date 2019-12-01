package lab5;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import lab4.SortAlgorithm;
import lab4.java.Swap;

public abstract class BinaryHeap<T extends Comparable<T>> implements SortAlgorithm {

    private final T[] arr;

    public BinaryHeap(T[] arr) {
        this.arr = arr;
    }

    private void swap(int pos1, int pos2) {
        T buffer = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = buffer;
    }

    void heapify() {

        int lastPos = arr.length;
        int firstParent = (lastPos % 2 == 0)? (lastPos - 2) / 2: (lastPos - 1) / 2;

        for (int i = firstParent; i >= 0; i--) {
            siftDown(i, arr.length);
        }

    }

    abstract boolean compare(T a, T b);

    private void siftDown(int pos, int maxPos) {
        int largestPos = pos;
        int posLeft = pos * 2 + 1;
        int posRight = pos * 2 + 2;

        if (posLeft < maxPos && compare(arr[posLeft], arr[largestPos])) {
            largestPos = posLeft;
        }

        if (posRight < maxPos && compare(arr[posRight], arr[largestPos])) {
            largestPos = posRight;
        }


        if (largestPos != pos) {
            swap(pos, largestPos);

            siftDown(largestPos, maxPos);
        }

    }

    void sort() {
        this.heapify();
        for (int max = arr.length; max > 0; --max) {
            swap(0, max - 1);
            this.siftDown(0, max - 1);
        }
    }

    @Override
    public Integer[] getElems() {
        return new Integer[0];
    }

    @Override
    public void run(Function1<? super Integer, Unit> doRepaint) {
        sort();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("[");
        for (T i: arr) {
            res.append(i).append(", ");
        }
        res.append("]");

        return res.toString();
    }

}
