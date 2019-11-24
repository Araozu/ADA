package lab4

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.util.*
import javax.swing.JPanel

class GPanel(private val elems: Array<Int>, val stepTime: Int) : JPanel() {

    private var heightRatio = this.height / elems.size.toDouble()
    private var widthRatio = this.width / elems.size.toDouble()

    private fun doRepaint() {
        this.repaint()
    }

    private fun swap(pos1: Int, pos2: Int) {
        val temp = elems[pos1]
        elems[pos1] = elems[pos2]
        elems[pos2] = temp
    }

    fun finaliseUpdate(millisecondDelay: Long) {
        repaint()
        try {
            Thread.sleep(millisecondDelay)
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    fun sortAsync(algo: SortAlgorithm) {

        while (!algo.isSorted()) {
            algo.step(::doRepaint)
            finaliseUpdate(stepTime.toLong())
        }

    }

    fun shuffleArray() {
        val rn = Random()
        for (i in elems.indices) {
            swap(rn.nextInt(elems.size - 1), i)
        }
        this.repaint()
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
            ga.color = Color.BLACK
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
