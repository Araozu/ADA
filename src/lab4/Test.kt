import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.GridLayout
import java.awt.geom.Rectangle2D
import javax.swing.*
import java.util.Random


fun main(args: Array<String>) {

    val elems = try {
        Integer.parseInt(args[0])
    } catch (e: Exception) {
        50
    }

    val stepTime = try {
        Integer.parseInt(args[1])
    } catch (e: Exception) {
        50
    }

    println("Step time is $stepTime, there are $elems elements.")
    Panel(stepTime, elems)
}

interface SortAlgorithm {

    val elems: Array<Int>

    fun run(doRepaint: (Int) -> Unit)

}

class BubbleSort(override val elems: Array<Int>) : SortAlgorithm {

    override fun run(doRepaint: (Int) -> Unit) {

        val n: Int = elems.size

        for (i in 0 until n) {

            for (j in 1 until n - i) {

                if (elems[j - 1] > elems[j]) {
                    val temp = elems[j - 1]
                    elems[j - 1] = elems[j]
                    elems[j] = temp
                }
                doRepaint(j)

            }

        }

        // Elimina la linea roja que queda al final.
        doRepaint(-1)

    }

}

class GPanel(private val elems: Array<Int>, private val stepTime: Int) : JPanel() {

    private var heightRatio = this.height / elems.size.toDouble()
    private var widthRatio = this.width / elems.size.toDouble()
    private var actualPos = 0

    fun doRepaint(actualPos: Int) {
        this.actualPos = actualPos
        finaliseUpdate(stepTime.toLong())
    }

    private fun finaliseUpdate(millisecondDelay: Long) {
        repaint()
        try {
            Thread.sleep(millisecondDelay)
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    private fun drawLines(ga: Graphics2D) {

        heightRatio = this.height / elems.size.toDouble()
        widthRatio = this.width / elems.size.toDouble()

        for (i in elems.indices) {
            val currentValue = elems[i]
            val elemHeight = currentValue * heightRatio
            val actualSquare = Rectangle2D.Double(
                    i * widthRatio,
                    this.height - elemHeight,
                    widthRatio,
                    elemHeight
            )
            ga.draw(actualSquare)
            ga.color = if (this.actualPos == i) Color.RED else Color.BLACK
            ga.fill(actualSquare)
        }

    }

    override fun paintComponent(graphic: Graphics?) {
        super.paintComponent(graphic)

        if (graphic == null) {
            println("Graphic null")
            return
        }

        val graphic2d = graphic.create() as Graphics2D

        drawLines(graphic2d)
        graphic2d.dispose()

    }

}

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

class MergeSort(override val elems: Array<Int>) : SortAlgorithm {

    private var doRepaint: (Int) -> Unit = {}

    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    fun merge(arr: Array<Int>, l: Int, m: Int, r: Int) {
        // Find sizes of two subarrays to be merged
        val n1 = m - l + 1
        val n2 = r - m

        /* Create temp arrays */
        val L = IntArray(n1)
        val R = IntArray(n2)

        /*Copy data to temp arrays*/
        for (i in 0 until n1) {
            L[i] = arr[l + i]
            doRepaint(l + i)
        }

        for (j in 0 until n2) {
            R[j] = arr[m + 1 + j]
            doRepaint(m + 1 + j)
        }

        /* Merge the temp arrays */ // Initial indexes of first and second subarrays
        var i = 0
        var j = 0
        // Initial index of merged subarry array
        var k = l
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i]
                i++
            } else {
                arr[k] = R[j]
                j++
            }
            doRepaint(k)
            k++
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i]
            doRepaint(l + i)
            i++
            k++
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j]
            doRepaint(m + 1 + j)
            j++
            k++
        }

    }

    // Main function that sorts arr[l..r] using
    // merge()
    fun sort(arr: Array<Int>, l: Int, r: Int) {

        if (l < r) { // Find the middle point
            val m = (l + r) / 2

            // Sort first and second halves
            sort(arr, l, m)
            sort(arr, m + 1, r)

            // Merge the sorted halves
            merge(arr, l, m, r)

        }

    }

    override fun run(doRepaint: (Int) -> Unit) {
        this.doRepaint = doRepaint
        sort(elems, 0, elems.size - 1)

        // Elimina la linea roja que queda al final.
        doRepaint(-1)
    }

}

class Panel(stepTime: Int, numElems: Int) : JFrame("Visualization") {

    private val elems = {
        val elems = (Array(numElems) { it + 1 }) // { (Math.random() * 58).toInt() + 2}
        shuffleArray(elems)
        elems
    }()

    init {
        this.layout = GridLayout(0, 2, 20, 20)
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.isResizable = false
        this.extendedState = MAXIMIZED_BOTH
        this.isVisible = true

        val panel1Elems = elems.copyOf()
        val panel1 = GPanel(panel1Elems, stepTime)
        panel1.border = BorderFactory.createLineBorder(Color.GRAY)
        panel1.background = Color.WHITE

        val panel2Elems = elems.copyOf()
        val panel2 = GPanel(panel2Elems, stepTime)
        panel2.border = BorderFactory.createLineBorder(Color.GRAY)
        panel2.background = Color.WHITE

        val panel3Elems = elems.copyOf()
        val panel3 = GPanel(panel3Elems, stepTime)
        panel3.border = BorderFactory.createLineBorder(Color.GRAY)
        panel3.background = Color.WHITE

        val panel4Elems = elems.copyOf()
        val panel4 = GPanel(panel4Elems, stepTime)
        panel4.border = BorderFactory.createLineBorder(Color.GRAY)
        panel4.background = Color.WHITE

        val panel5Elems = elems.copyOf()
        val panel5 = GPanel(panel5Elems, stepTime)
        panel4.border = BorderFactory.createLineBorder(Color.GRAY)
        panel4.background = Color.WHITE

        val startButton = JButton("Run")
        startButton.addActionListener {

            val t1 = Thread {
                InsertionSort(panel1Elems).run(panel1::doRepaint)
            }
            t1.start()

            val t2 = Thread {
                BubbleSort(panel2Elems).run(panel2::doRepaint)
            }
            t2.start()

            val t3 = Thread {
                SelectionSort(panel3Elems).run(panel3::doRepaint)
            }
            t3.start()

            val t4 = Thread {
                MergeSort(panel4Elems).run(panel4::doRepaint)
            }
            t4.start()

            val t5 = Thread {
                HeapSort(panel5Elems).run(panel5::doRepaint)
            }
            t5.start()

        }

        add(panel1)
        add(panel2)
        add(panel3)
        add(panel4)
        add(panel5)
        add(startButton)

    }

    private fun shuffleArray(elems: Array<Int>) {
        val rn = Random()
        for (i in elems.indices) {
            swap(elems, rn.nextInt(elems.size - 1), i)
        }
    }

    private fun swap(elems: Array<Int>, pos1: Int, pos2: Int) {
        val temp = elems[pos1]
        elems[pos1] = elems[pos2]
        elems[pos2] = temp
    }


}

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

        // Elimina la linea roja que queda al final.
        doRepaint(-1)

    }

}

