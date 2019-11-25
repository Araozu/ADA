package lab4

class InsertionSort(override val elems: Array<Int>) : SortAlgorithm {

    override fun run(doRepaint: (Int) -> Unit) {

        for (actualIter in 1 until elems.size) {

            var posActual = actualIter

            while (posActual > 0 && elems[posActual - 1] > elems[posActual]) {

                val temp = elems[posActual - 1]
                elems[posActual - 1] = elems[posActual]
                elems[posActual] = temp

                doRepaint(posActual - 1)
                posActual--
            }

        }

        // Elimina la linea roja que queda al final.
        doRepaint(-1)

    }

}
