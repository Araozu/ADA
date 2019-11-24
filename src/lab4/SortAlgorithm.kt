package lab4

interface SortAlgorithm {

    val elems: Array<Int>

    fun step(doRepaint: () -> Unit)

    fun isSorted(): Boolean

}
