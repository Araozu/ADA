package lab4

import java.awt.Color
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.SwingWorker

class Panel(stepTime: Int, numElems: Int) : JFrame("Visualization") {

    private val elems = Array(numElems) { it + 1 } // { (Math.random() * 58).toInt() + 2}

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
                panel1.shuffleArray()
                panel1.finaliseUpdate(1000)
                panel1.sortAsync(InsertionSort(panel1Elems))
            }
            t1.start()

            val t2 = Thread {
                panel2.shuffleArray()
                panel2.finaliseUpdate(1000)
                panel2.sortAsync(BubbleSort(panel2Elems))
            }
            t2.start()

            val t3 = Thread {
                panel3.shuffleArray()
                panel3.finaliseUpdate(1000)
                panel3.sortAsync(SelectionSort(panel3Elems))
            }
            t3.start()

            val t4 = Thread {
                panel4.shuffleArray()
                panel4.finaliseUpdate(1000)
                panel4.sortAsync(MergeSort2(panel4Elems))
            }
            t4.start()

        }

        add(panel1)
        add(panel2)
        add(panel3)
        add(panel4)
        add(startButton)

    }

}
