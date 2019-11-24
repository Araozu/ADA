package lab4

interface SortAlgorithm {

    val elems: Array<Int>

    fun run(doRepaint: (Int) -> Unit)

    fun isSorted(): Boolean

}
