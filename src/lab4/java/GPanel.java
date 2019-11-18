package lab4.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GPanel extends JPanel {

    private int[] elems;
    int stepTime;

    private Rectangle2D[] squares;
    private boolean inicializado = false;
    private double heightRatio = this.getHeight() / 60.0;
    private double widthRatio  = this.getWidth()  / 50.0;

    public GPanel(int[] elems, int stepTime) {
        this.elems = elems;
        this.stepTime = stepTime;
        this.squares = new Rectangle2D[elems.length];
    }

    public void swap(int i, int j) {
        inicializado = true;
        double minH = squares[i].getMinX();
        squares[i].setRect(squares[j].getMinX(), squares[i].getMinY(), 30, squares[i].getHeight());
        squares[j].setRect(minH, squares[j].getMinY(), 30, squares[j].getHeight());

        //change position of shapes in array
        Rectangle2D temp = squares[i];
        squares[i] = squares[j];
        squares[j] = temp;
        this.repaint();

    }

    void sortAsync(SortAlgorithm algo) {
        Timer t = null;
        final Timer finalT = t;
        t = new Timer(stepTime, e -> {
            if (!algo.isSorted()) {
                algo.step(this::swap);
            } else {
                System.out.println("Stopping timer from ${Thread.currentThread().name}");

                finalT.stop();
            }
        });
        t.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D ga = (Graphics2D) g;

        heightRatio = this.getHeight() / 60.0;
        widthRatio  = (double) this.getWidth()  / elems.length;

        if (!inicializado) {

            for (int i = 0; i < elems.length; i++) {
                double elemHeight = elems[i] * heightRatio;
                squares[i] = new Rectangle2D.Double(
                        i * widthRatio,
                        this.getHeight() - elemHeight,
                        widthRatio,
                        elemHeight
                );
                ga.draw(squares[i]);
                ga.setColor(Color.BLACK);
                ga.fill(squares[i]);
            }
        }


    }


}
