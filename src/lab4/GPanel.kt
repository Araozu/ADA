package lab4

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import javax.swing.JPanel

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
