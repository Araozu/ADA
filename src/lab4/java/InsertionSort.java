package lab4.java;

public class InsertionSort implements SortAlgorithm {

    private int[] elems;
    int actualIter = 1;
    int posActual = 1;

    public InsertionSort(int[] elems) {
        this.elems = elems;
    }

    @Override
    public void step(Swap s) {

        if (elems[posActual - 1] > elems[posActual]) {

            s.swap(posActual, posActual - 1);
            int temp = elems[posActual - 1];
            elems[posActual - 1] = elems[posActual];
            elems[posActual] = temp;

            if (posActual == 1) {
                actualIter++;
                posActual = actualIter;
            } else {
                posActual--;
            }

        } else {
            actualIter++;
            posActual = actualIter;
        }
    }

    @Override
    public boolean isSorted() {
        return actualIter == elems.length;
    }

}
