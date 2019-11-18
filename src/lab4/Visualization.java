package lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Visualization {

    public static void main(String[] args) {
        new Move();
    }

}

class Move extends JFrame {
    private static int[] elems = {4, 8, 7, 10, 2, 6, 7, 8, 10, 20, 19, 7, 10, 8, 6, 9, 4, 1, 4, 2, 6, 7, 8, 4, 11, 2, 12};
    private int n = 20;
    private int m = 630;
    private static Rectangle2D[] squares = new Rectangle2D[elems.length];
    private static GPanel panel;
    private static boolean swapShape = false;
    private static int countPaint = 0;


    public Move() {
        super("Move shape");
        this.setLayout(new FlowLayout());
        this.setSize(900, 850);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new GPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setBackground(Color.white);
        panel.setPreferredSize(new Dimension(880, 770));

        JButton swapButton = new JButton("Swap");
        swapButton.addActionListener(e -> bubbleSort(elems, elems.length));
        add(swapButton);
        add(panel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public static void swap(int i, int j) {
        System.out.println("Intercambiando " + i + " por " + j);
        swapShape = true;
        double minH = squares[i].getMinX();
        squares[i].setRect(squares[j].getMinX(), squares[i].getMinY(), 30, squares[i].getHeight());
        squares[j].setRect(minH, squares[j].getMinY(), 30, squares[j].getHeight());

        //change position of shapes in array
        Rectangle2D temp = squares[i];
        squares[i] = squares[j];
        squares[j] = temp;
        panel.repaint();

    }

    static void bubbleSortStep(int[] arr) {

    }

    public static void bubbleSort(int[] arr, int n) {

        // bubble up largest element
        for (int i = 0; i < n; i++) {
            boolean swap = false;
            for (int j = 1; j < n; j++) {
                if (arr[j - 1] > arr[j]) {
                    swap(j, j - 1);
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    swap = true;
                }
                try {
                    Thread.sleep(10L);
                } catch (Exception e) {
                    System.err.println(":c");
                }
            }

            if (!swap) {
                System.out.println("Break :c");
                break;
            }

        }

        for (int elem : elems) {
            System.out.print(elem + " ");
        }

    }


    public class GPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D ga = (Graphics2D) g;
            if (swapShape) {
                for (int i = 0; i < squares.length; i++) {
                    if (i % 2 != 0) {
                        ga.setColor(Color.red);
                    } else {
                        ga.setColor(Color.black);
                    }
                    ga.fill(squares[i]);
                    ga.draw(squares[i]);
                }
                countPaint++;
                System.out.println("\nrepainted: " + countPaint + " times");
            } else {
                squares[0] = new Rectangle2D.Double(n, m, 30, elems[0] * 30);
                ga.setColor(Color.black);
                ga.fill(squares[0]);
                ga.draw(squares[0]);
                n += 30;

                for (int i = 1; i < elems.length; i++) {
                    m += (elems[i - 1] * 30) - (elems[i] * 30);
                    squares[i] = new Rectangle2D.Double(n, m, 30, elems[i] * 30);
                    n += 30;
                    ga.draw(squares[i]);
                    if (i % 2 != 0) {
                        ga.setColor(Color.red);
                    } else {
                        ga.setColor(Color.black);
                    }
                    ga.fill(squares[i]);
                }
            }

            n = 20;
            m = 630;

        }

    }


}
