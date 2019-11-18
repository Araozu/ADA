package lab4

class MergeSort2(override val elems: Array<Int>) : SortAlgorithm {

    fun<T> imprArr(arr: Array<T>) {
        for (e in arr) {
            print("$e ")
        }
        println()
    }

    fun merge(l: Int, m: Int, r: Int) {
        // Find sizes of two subarrays to be merged
        val n1 = m - l + 1
        val n2 = r - m

        /* Create temp arrays */
        val L = IntArray(n1)
        val R = IntArray(n2)

        /*Copy data to temp arrays*/
        for (i in 0 until n1)
            L[i] = elems[l + i]
        for (j in 0 until n2)
            R[j] = elems[m + 1 + j]


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        var i = 0
        var j = 0

        // Initial index of merged subarry array
        var k = l
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                elems[k] = L[i]
                i++
            } else {
                elems[k] = R[j]
                j++
            }
            k++
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            elems[k] = L[i]
            i++
            k++
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            elems[k] = R[j]
            j++
            k++
        }

    }

    // Main function that sorts elems[l..r] using
    // merge()
    fun sort(l: Int, r: Int) {
        if (l < r) {
            // Find the middle point
            val m = (l + r) / 2

            // Sort first and second halves
            sort(l, m)
            sort(m + 1, r)

            // Merge the sorted halves
            merge(l, m, r)
        }
    }

    var sorted = false

    override fun step(swap: (Int, Int) -> Unit) {
        sort(0, elems.size - 1)
        imprArr(elems)
        sorted = true
    }

    override fun isSorted() = sorted

}
