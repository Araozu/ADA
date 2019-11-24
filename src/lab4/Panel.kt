package lab4

import java.awt.Color
import java.awt.GridLayout
import java.util.*
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame

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

        val startButton = JButton("Run")
        startButton.addActionListener {

            val t1 = Thread {
                panel1.sortAsync(InsertionSort(panel1Elems))
            }
            t1.start()

            val t2 = Thread {
                panel2.sortAsync(BubbleSort(panel2Elems))
            }
            t2.start()

            val t3 = Thread {
                panel3.sortAsync(SelectionSort(panel3Elems))
            }
            t3.start()

            val t4 = Thread {
                panel4.sortAsync(MergeSort(panel4Elems))
            }
            t4.start()

        }

        add(panel1)
        add(panel2)
        add(panel3)
        add(panel4)
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
