package lab4

import java.awt.Color
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame

class Panel(stepTime: Int, numElems: Int) : JFrame("Visualization") {

    private val elems = Array(numElems) { (Math.random() * 58).toInt() + 2}

    private fun bubbleSort(elems: Array<Int>, swap: (Int, Int) -> Unit) {
        println("Estoy en el thread ${Thread.currentThread().name}")
        val size = elems.size

        for (i in 0 until size) {
            var doSwap = false
            for (j in 1 until size) {
                // Thread.sleep(1000L)
                if (elems[j - 1] > elems[j]) {

                    swap(j, j - 1)
                    val temp = elems[j - 1]
                    elems[j - 1] = elems[j]
                    elems[j] = temp
                    doSwap = true

                }
            }

            if (!doSwap) {
                break
            }

        }

        for (elem in elems) {
            print("$elem ")
        }

    }

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

        val startButton = JButton("Run")
        startButton.addActionListener {

            panel1.sortAsync(InsertionSort(panel1Elems))
            panel2.sortAsync(BubbleSort(panel2Elems))
            panel3.sortAsync(SelectionSort(panel3Elems))
            // panel4.sortAsync(MergeSort2(panel4Elems))

        }

        add(panel1)
        add(panel2)
        add(panel3)
        // add(panel4)
        add(startButton)

    }

}
