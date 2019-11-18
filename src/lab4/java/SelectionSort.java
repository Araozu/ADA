package lab4.java;

public class SelectionSort implements SortAlgorithm {

    private int[] elems;
    private int actualIter = 0;
    private int actualPos = actualIter + 1;
    private int minimumPos = actualIter;

    public SelectionSort(int[] elems) {
        this.elems = elems;
    }

    @Override
    public void step(Swap s) {
        if (elems[actualPos] < elems[minimumPos]) {
            minimumPos = actualPos;
        }

        if (actualPos == elems.length - 1) {
            if (actualIter != minimumPos) {
                s.swap(actualIter, minimumPos);
                int temp = elems[minimumPos];
                elems[minimumPos] = elems[actualIter];
                elems[actualIter] = temp;
            }

            actualIter++;
            actualPos = actualIter + 1;
            minimumPos = actualIter;
        } else {
            actualPos++;
        }

    }

    @Override
    public boolean isSorted() {
        return actualIter == elems.length - 1;
    }

}
