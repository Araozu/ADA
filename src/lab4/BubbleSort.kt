package lab4

class BubbleSort(override val elems: Array<Int>) : SortAlgorithm {

    private var posLoop1 = elems.size - 1
    private var posLoop2 = 1

    override fun step(swap: (Int, Int) -> Unit) {
        assert(posLoop1 != 0)

        if (elems[posLoop2 - 1] > elems[posLoop2]) {

            swap(posLoop2, posLoop2 - 1)
            val temp = elems[posLoop2 - 1]
            elems[posLoop2 - 1] = elems[posLoop2]
            elems[posLoop2] = temp

        }

        if (posLoop2 == posLoop1) {
            posLoop1--
            posLoop2 = 1
        } else {
            posLoop2++
        }

    }

    override fun isSorted() = posLoop1 == 0

}
