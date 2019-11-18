package lab4.java;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Panel extends JFrame {

    private int stepTime;
    private int numElems;
    private int[] elems;

    private int[] crearArr(int n) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = ((int) (Math.random() * 58)) + 2;
        }
        return result;
    }

    public Panel(int stepTime, int numElems) {
        super("Visualization");
        this.stepTime = stepTime;
        this.numElems = numElems;
        this.elems = crearArr(numElems);

        this.setLayout(new GridLayout(0, 2, 20, 20));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);


        GPanel panel1 = new GPanel(elems, stepTime);
        panel1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel1.setBackground(Color.WHITE);

        GPanel panel2 = new GPanel(elems, stepTime);
        panel2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel2.setBackground(Color.WHITE);

        GPanel panel3 = new GPanel(elems, stepTime);
        panel3.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel3.setBackground(Color.WHITE);

        JButton startButton = new JButton("Run");
        startButton.addActionListener(e -> {
            panel1.sortAsync(new InsertionSort(elems));
            panel2.sortAsync(new BubbleSort(elems));
            panel3.sortAsync(new SelectionSort(elems));
        });

        add(panel1);
        add(panel2);
        add(panel3);
        add(startButton);

    }



}
