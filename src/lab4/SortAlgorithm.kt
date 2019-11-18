package lab4

interface SortAlgorithm {

    val elems: Array<Int>

    fun step(swap: (Int, Int) -> Unit)

    fun isSorted(): Boolean

}
