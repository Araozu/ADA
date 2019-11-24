package lab4

class SelectionSort(override val elems: Array<Int>) : SortAlgorithm {

    private var actualIter = 0
    private var actualPos = actualIter + 1
    private var minimumPos = actualIter

    override fun step(doRepaint: () -> Unit) {

        if (elems[actualPos] < elems[minimumPos]) {
            minimumPos = actualPos
        }

        if (actualPos == elems.size - 1) {
            if (actualIter != minimumPos) {
                doRepaint()
                val temp = elems[minimumPos]
                elems[minimumPos] = elems[actualIter]
                elems[actualIter] = temp
            }

            actualIter++
            actualPos = actualIter + 1
            minimumPos = actualIter
        } else {
            actualPos++
        }

    }

    override fun isSorted() = actualIter == elems.size - 1

}
