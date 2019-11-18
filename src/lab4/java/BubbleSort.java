package lab4.java;

public class BubbleSort implements SortAlgorithm {

    private int[] elems;
    private int posLoop1;
    private int posLoop2 = 1;

    public BubbleSort(int[] elems) {
        this.elems = elems;
        this.posLoop1 = elems.length - 1;

    }

    @Override
    public void step(Swap s) {
        if (elems[posLoop2 - 1] > elems[posLoop2]) {

            s.swap(posLoop2, posLoop2 - 1);
            int temp = elems[posLoop2 - 1];
            elems[posLoop2 - 1] = elems[posLoop2];
            elems[posLoop2] = temp;

        }

        if (posLoop2 == posLoop1) {
            posLoop1--;
            posLoop2 = 1;
        } else {
            posLoop2++;
        }
    }

    @Override
    public boolean isSorted() {
        return posLoop1 == 0;
    }

}
