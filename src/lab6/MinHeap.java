package lab6;

import java.util.ArrayList;

public class MinHeap<T extends Comparable<T>> {

    private final ArrayList<T> arr;

    public MinHeap(ArrayList<T> arr) {
        this.arr = arr;
    }

    public MinHeap() {
        this(new ArrayList<>());
    }

    private void swap(int pos1, int pos2) {
        T buffer = arr.get(pos1);
        arr.set(pos1, arr.get(pos2));
        arr.set(pos2, buffer);
    }

    void heapify() {

        int lastPos = arr.size();
        int firstParent = (lastPos % 2 == 0)? (lastPos - 2) / 2: (lastPos - 1) / 2;

        for (int i = firstParent; i >= 0; i--) {
            siftDown(i, arr.size());
        }

    }

    private boolean compare(T a, T b) {
        return a.compareTo(b) < 0;
    }

    private void siftDown(int pos, int maxPos) {
        int largestPos = pos;
        int posLeft = pos * 2 + 1;
        int posRight = pos * 2 + 2;

        if (posLeft < maxPos && compare(arr.get(posLeft), arr.get(largestPos))) {
            largestPos = posLeft;
        }

        if (posRight < maxPos && compare(arr.get(posRight), arr.get(largestPos))) {
            largestPos = posRight;
        }


        if (largestPos != pos) {
            swap(pos, largestPos);

            siftDown(largestPos, maxPos);
        }

    }

    private void siftUp(int pos) {
        if (pos > 0) {

            int parentPos = (pos % 2 == 0)? (pos - 2) / 2: (pos - 1) / 2;
            if (compare(arr.get(parentPos), arr.get(pos))) {
                swap(parentPos, pos);
                siftUp(parentPos);
            }

        }
    }

    void sort() {
        this.heapify();
        for (int max = arr.size(); max > 0; --max) {
            swap(0, max - 1);
            this.siftDown(0, max - 1);
        }
    }

    public T removeHead() {
        T result = arr.get(0);
        arr.remove(0);

        if (arr.size() > 0) {
            swap(0, arr.size() - 1);
            this.siftDown(0, arr.size() - 1);
        }

        return result;
    }

    void insert(T elem) {
        arr.add(elem);
        heapify();
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
