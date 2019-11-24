package lab4

class SelectionSort(override val elems: Array<Int>) : SortAlgorithm {

    override fun run(doRepaint: (Int) -> Unit) {

        for (i in 0 until elems.size - 1) {

            var index = i

            for (j in i + 1 until elems.size) {

                if (elems[j] < elems[index]) {
                    index = j //searching for lowest index
                }
                doRepaint(j)

            }

            val smallerNumber: Int = elems[index]
            elems[index] = elems[i]
            elems[i] = smallerNumber

        }

    }

}
