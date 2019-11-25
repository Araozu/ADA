package lab4

class HeapSort(override val elems: Array<Int>): SortAlgorithm {

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private fun heapify(doRepaint: (Int) -> Unit, n: Int, i: Int) {
        var largest = i // Initialize largest as root
        val l = 2 * i + 1 // left = 2*i + 1
        val r = 2 * i + 2 // right = 2*i + 2

        // If left child is larger than root
        if (l < n && elems[l] > elems[largest]) {
            doRepaint(l)
            largest = l
        }

        // If right child is larger than largest so far
        if (r < n && elems[r] > elems[largest]) {
            doRepaint(r)
            largest = r
        }

        // If largest is not root
        if (largest != i) {

            val swap = elems[i]
            elems[i] = elems[largest]
            elems[largest] = swap
            doRepaint(i)

            // Recursively heapify the affected sub-tree
            heapify(doRepaint, n, largest)
        }

    }

    override fun run(doRepaint: (Int) -> Unit) {
        val n = elems.size

        // Build heap (rearrange array)
        for (i in n / 2 - 1 downTo 0) {
            heapify(doRepaint, n, i)
        }

        // One by one extract an element from heap
        for (i in n - 1 downTo 0) {
            // Move current root to end
            val temp = elems[0]
            elems[0] = elems[i]
            elems[i] = temp
            doRepaint(i)

            // call max heapify on the reduced heap
            heapify(doRepaint, i, 0)

        }

        // Elimina la linea roja que queda al final.
        doRepaint(-1)
    }
}