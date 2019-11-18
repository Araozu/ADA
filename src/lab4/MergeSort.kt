package lab4

class MergeSort(override val elems: Array<Int>) : SortAlgorithm {

    private fun merge(mergeLeft: Int, mergeMid: Int, mergeRight: Int) {
        val n1 = mergeMid - mergeLeft + 1
        val n2 = mergeRight - mergeMid
        val leftArr = Array(if (n1 >= 0) n1 else 0) { 0 }
        val rightArr = Array(if (n2 >= 0) n2 else 0) { 0 }

        for (i in 0 until n1) {
            leftArr[i] = elems[mergeLeft + i]
        }

        for (i in 0 until n2) {
            rightArr[i] = elems[mergeMid + i + 1]
        }

        var i = 0
        var j = 0
        var k = mergeLeft

        while (i < n1 && j < n2) {
            if (leftArr[i] > rightArr[j]) {
                elems[k] = rightArr[j]
                j += 1
            } else {
                elems[k] = leftArr[i]
                i += 1
                k += 1
            }
        }

        while (i < n1) {
            elems[k] = leftArr[i]
            i += 1
            k += 1
        }

        while (j < n2) {
            elems[k] = rightArr[j]
            j += 1
            k += 1
        }

    }



    fun<T> imprArr(arr: Array<T>) {
        for (e in arr) {
            print("$e ")
        }
        println()
    }

    fun ms() {

        var currentArrSize = 1

        while (currentArrSize < elems.size - 1) {
            var left = 0

            while (left < elems.size - 1) {

                val mid = left + currentArrSize - 1
                val right =
                        if (2 * currentArrSize + left - 1 > elems.size - 1) {
                            elems.size - 1
                        } else {
                            2 * currentArrSize + left - 1
                        }

                merge(left, mid, right)
                left += currentArrSize * 2

            }

            currentArrSize *= 2

        }

    }

    private var currentArrSize = 1
    private var left = 0

    private var isMerging = false
    private var mergeLeft = 0
    private var mergeMid = 0
    private var mergeRight = 0

    fun fn() {

        if (isMerging) {

            isMerging = false

        } else {
            if (currentArrSize < elems.size - 1) {

                val mid = left + currentArrSize - 1
                val right =
                        if (2 * currentArrSize + left - 1 > elems.size - 1) {
                            elems.size - 1
                        } else {
                            2 * currentArrSize + left - 1
                        }

                // Usar indicadores de si se esta ejecutando el merge.
                // isMerging = true
                merge(left, mid, right)

                left += currentArrSize * 2

                if (left >= elems.size - 1) {
                    currentArrSize *= 2
                    left = 0
                }

            }
        }

    }

    fun mergeStep(swap: (Int, Int) -> Unit) {
        val n1 = mergeMid - mergeLeft + 1
        val n2 = mergeRight - mergeMid
        val leftArr = Array(n1) { 0 }
        val rightArr = Array(n2) { 0 }

        for (i in 0 until n1) {
            leftArr[i] = elems[mergeLeft + i]
        }

        for (i in 0 until n2) {
            rightArr[i] = elems[mergeMid + i + 1]
        }

        var i = 0
        var j = 0
        var k = mergeLeft

        while (i < n1 && j < n2) {
            if (leftArr[i] > rightArr[j]) {
                elems[k] = rightArr[j]
                j += 1
            } else {
                elems[k] = leftArr[i]
                i += 1
                k += 1
            }
        }

        while (i < n1) {
            elems[k] = leftArr[i]
            i += 1
            k += 1
        }

        while (j < n2) {
            elems[k] = rightArr[j]
            j += 1
            k += 1
        }

        isMerging = false
    }

    override fun step(swap: (Int, Int) -> Unit) {

        if (isMerging) {
            mergeStep(swap)
        } else {
            ms()
            /*
            if (currentArrSize < elems.size - 1) {

                val mid = left + currentArrSize - 1
                val right =
                    if (2 * currentArrSize + left - 1 > elems.size - 1) {
                        elems.size - 1
                    } else {
                        2 * currentArrSize + left - 1
                    }

                // Usar indicadores de si se esta ejecutando el merge.
                // isMerging = true
                merge(left, mid, right)

                left += currentArrSize * 2

                if (left >= elems.size - 1) {
                    currentArrSize *= 2
                    left = 0
                }

            }

             */
        }

    }

    override fun isSorted(): Boolean {
        return if (currentArrSize >= elems.size) {
            imprArr(elems)
            true
        } else {
            false
        }
    }

}