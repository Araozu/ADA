package lab4

class BubbleSort(override val elems: Array<Int>) : SortAlgorithm {

    override fun run(doRepaint: (Int) -> Unit) {

        val n: Int = elems.size

        for (i in 0 until n) {

            for (j in 1 until n - i) {

                if (elems[j - 1] > elems[j]) {
                    val temp = elems[j - 1]
                    elems[j - 1] = elems[j]
                    elems[j] = temp
                }
                doRepaint(j)

            }

        }

        // Elimina la linea roja que queda al final.
        doRepaint(-1)

    }

}
