package lab4

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import javax.swing.JPanel
import javax.swing.Timer

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