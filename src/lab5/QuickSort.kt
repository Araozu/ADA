package lab5

import lab4.SortAlgorithm

class QuickSort(override val elems: Array<Int>) : SortAlgorithm {

    private fun quicksort(low: Int, high: Int) {
        var i = low
        var j = high
        // Get the pivot element from the middle of the list
        val pivot = this.elems[low + (high - low) / 2]
        // Divide into two lists
        while (i <= j) { // If the current value from the left list is smaller than the pivot
            // element then get the next element from the left list
            while (this.elems[i] < pivot) {
                i++
            }
            // If the current value from the right list is larger than the pivot
            // element then get the next element from the right list
            while (this.elems[j] > pivot) {
                j--
            }
            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller than the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j)
                i++
                j--
            }
        }
        // Recursion
        if (low < j) quicksort(low, j)
        if (i < high) quicksort(i, high)
    }

    private fun exchange(i: Int, j: Int) {
        val temp = this.elems[i]
        this.elems[i] = this.elems[j]
        this.elems[j] = temp
    }

    private fun test(): Boolean {
        var res = true
        var buffer = elems[0]
        for (i in 1 until elems.size) {
            val elem = elems[i]
            if (elems[i] < buffer) {
                res = false
                break
            }
            buffer = elem
        }
        return res
    }

    override fun run(doRepaint: (Int) -> Unit) {
        quicksort(0, elems.size - 1);
        println(test())
    }

}
