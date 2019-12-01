package lab5

import lab4.SortAlgorithm
import java.io.FileWriter
import java.util.*

class AlgorithmTester {

    private fun createSortedArray(numElems: Int) = Array(numElems) { it + 1 }

    private fun createInversedArray(numElems: Int) = Array(numElems) { numElems - it }

    private fun createFewUniqueArrap(numElems: Int) = Array(numElems) { (Math.random() * numElems).toInt() }

    private fun createRandomArray(numElems: Int): Array<Int> {
        fun swap(elems: Array<Int>, pos1: Int, pos2: Int) {
            val temp = elems[pos1]
            elems[pos1] = elems[pos2]
            elems[pos2] = temp
        }

        fun shuffleArray(elems: Array<Int>) {
            val rn = Random()
            for (i in elems.indices) {
                swap(elems, rn.nextInt(elems.size - 1), i)
            }
        }

        val elems = createSortedArray(numElems)
        shuffleArray(elems)
        return elems
    }

    private fun getData(iteration: Int, algo: SortAlgorithm): String {

        val startTime = System.currentTimeMillis()
        algo.run {}
        val endTime = System.currentTimeMillis()

        return iteration.toString() + ", " + startTime + ", " + endTime + ", " +
                (endTime - startTime) + ", " + true + "\n"
    }


    fun testAlgorithm(numElems: Array<Int>) {

        val fout = FileWriter("./src/lab5/latex/quick_inverse.csv")
        fout.write("number_of_elements, start_time, end_time, duration, result\n")

        for (num in numElems) {

            val elems = createInversedArray(num)
            val dataToWrite = getData(num, QuickSort(elems))
            fout.write(dataToWrite)

        }

        fout.close()

    }

}
