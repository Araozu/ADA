package lab4

class InsertionSort(override val elems: Array<Int>) : SortAlgorithm {

    var actualIter = 1
    var posActual = 1

    override fun step(doRepaint: () -> Unit) {

        if (elems[posActual - 1] > elems[posActual]) {

            doRepaint()
            val temp = elems[posActual - 1]
            elems[posActual - 1] = elems[posActual]
            elems[posActual] = temp

            if (posActual == 1) {
                actualIter++
                posActual = actualIter
            } else {
                posActual--
            }

        } else {
            actualIter++
            posActual = actualIter
        }

    }

    override fun isSorted() = actualIter == elems.size

}
