import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.GridLayout
import java.awt.geom.Rectangle2D
import javax.swing.*


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

    fun step(swap: (Int, Int) -> Unit)

    fun isSorted(): Boolean

}

class BubbleSort(override val elems: Array<Int>) : SortAlgorithm {

    private var posLoop1 = elems.size - 1
    private var posLoop2 = 1

    override fun step(swap: (Int, Int) -> Unit) {
        assert(posLoop1 != 0)

        if (elems[posLoop2 - 1] > elems[posLoop2]) {

            swap(posLoop2, posLoop2 - 1)
            val temp = elems[posLoop2 - 1]
            elems[posLoop2 - 1] = elems[posLoop2]
            elems[posLoop2] = temp

        }

        if (posLoop2 == posLoop1) {
            posLoop1--
            posLoop2 = 1
        } else {
            posLoop2++
        }

    }

    override fun isSorted() = posLoop1 == 0

}

class InsertionSort(override val elems: Array<Int>) : SortAlgorithm {

    var actualIter = 1
    var posActual = 1

    override fun step(swap: (Int, Int) -> Unit) {

        if (elems[posActual - 1] > elems[posActual]) {

            swap(posActual, posActual - 1)
            val temp = elems[posActual - 1]
            elems[posActual - 1] = elems[posActual]
            elems[posActual] = temp

            if (posActual == 1) {
                actualIter++
                posActual = actualIter
            } else {
                posActual--
            }

        } else {
            actualIter++
            posActual = actualIter
        }

    }

    override fun isSorted() = actualIter == elems.size

}

class SelectionSort(override val elems: Array<Int>) : SortAlgorithm {

    private var actualIter = 0
    private var actualPos = actualIter + 1
    private var minimumPos = actualIter

    override fun step(swap: (Int, Int) -> Unit) {

        if (elems[actualPos] < elems[minimumPos]) {
            minimumPos = actualPos
        }

        if (actualPos == elems.size - 1) {
            if (actualIter != minimumPos) {
                swap(actualIter, minimumPos);
                val temp = elems[minimumPos]
                elems[minimumPos] = elems[actualIter]
                elems[actualIter] = temp
            }

            actualIter++
            actualPos = actualIter + 1
            minimumPos = actualIter
        } else {
            actualPos++
        }

    }

    override fun isSorted() = actualIter == elems.size - 1

}

class GPanel(private val elems: Array<Int>, val stepTime: Int) : JPanel() {

    private val squares = Array<Rectangle2D?>(elems.size) { null }
    private var inicializado = false
    private var heightRatio = this.height / 60.0
    private var widthRatio  = this.width  / 50.0

    private fun swap(pos1: Int, pos2: Int) {
        val rectangle1 = squares[pos1] ?: return
        val rectangle2 = squares[pos2] ?: return

        val minH = rectangle1.minX
        rectangle1.setRect(rectangle2.minX, rectangle1.minY, widthRatio, rectangle1.height)
        rectangle2.setRect(minH, rectangle2.minY, widthRatio, rectangle2.height)

        //change position of shapes in array
        val temp = squares[pos1]
        squares[pos1] = squares[pos2]
        squares[pos2] = temp
        this.repaint()

    }

    fun sortAsync(algo: SortAlgorithm) {

        var t: Timer? = null
        t = Timer(stepTime) {
            if (!algo.isSorted()) {
                algo.step(::swap)
            } else {
                println("Stopping timer from ${Thread.currentThread().name}")
                t?.stop()
            }
        }
        t.start()

    }

    private fun inicializar(ga: Graphics2D) {

        heightRatio = this.height / 60.0
        widthRatio  = this.width  / elems.size.toDouble()

        for (i in elems.indices) {
            val elemHeight = elems[i] * heightRatio
            squares[i] = Rectangle2D.Double(
                    i * widthRatio,
                    this.height - elemHeight,
                    widthRatio,
                    elemHeight
            )
            ga.draw(squares[i])
            ga.color = Color.BLACK
            ga.fill(squares[i])
        }

    }

    override fun paintComponent(graphic: Graphics?) {
        super.paintComponent(graphic)

        if (graphic == null) {
            println("Graphic null")
            return
        }

        val graphic2d = graphic as Graphics2D

        if (inicializado) {
            println("Dunno what goes here, and anyway this is never called...")
        } else {
            inicializar(graphic2d)
        }

    }

}

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

        val startButton = JButton("Run")
        startButton.addActionListener {

            panel1.sortAsync(InsertionSort(panel1Elems))
            panel2.sortAsync(BubbleSort(panel2Elems))
            panel3.sortAsync(SelectionSort(panel3Elems))

        }

        add(panel1)
        add(panel2)
        add(panel3)
        add(startButton)

    }

}

