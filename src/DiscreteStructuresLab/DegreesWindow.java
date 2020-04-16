package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class DegreesWindow extends JFrame {
    private int[][] matrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;
    private boolean directed;
    private HashSet<Integer> inDegrees = new HashSet<>();
    private HashSet<Integer> outDegrees = new HashSet<>();

    DegreesWindow(int[][] matrix, boolean directed) {
        super("Степені вершин");
        this.matrix = matrix;
        this.d = new Dimension(500, 150 + 20 * matrix.length);
        this.directed = directed;
        this.setLayout(null);
        this.setPreferredSize(d);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationByPlatform(true);
        this.init();
    }

    public void init() {
        int n = matrix.length;
        String pendant = "Висячих вершин нема";
        String isolated = "Ізольованих вершин нема";
        for (int i = 0; i < n; i++) {
            int outDegree = 0;
            int inDegree = 0;
            int degree = 0;
            int sumDeg;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) outDegree++;
                if (matrix[j][i] == 1) inDegree++;
                if (!directed && i == j) degree++;
            }
            int m = i + 1;
            String index = "";
            while (m > 0) {
                int a = m % 10;
                String num = String.valueOf(Character.toChars(Integer.parseInt("208" + a, 16)));
                index = num + index;
                m = m / 10;
            }
            String deg = "";
            if (directed) {
                inDegrees.add(inDegree);
                outDegrees.add(outDegree);
                sumDeg = outDegree + inDegree;
                String degPlus = "δ" + "\u207A" + "(v" + index + ") = " + outDegree;
                String degMinus = "δ" + "\u207B" + "(v" + index + ") = " + inDegree;
                deg = degPlus + "   " + degMinus;
            } else {
                degree += outDegree;
                inDegrees.add(degree);
                sumDeg = degree;
                deg = "δ(v" + index + ") = " + degree;
            }
            JLabel degLabel = new JLabel(deg);
            degLabel.setBounds(50, 50 + 20 * i, d.width, 20);
            degLabel.setFont(this.FONT);
            this.add(degLabel);
            if (sumDeg == 1) {
                if (pendant.equals("Висячих вершин нема")) pendant = "Висячі вершини: " + "δ" + index + " ";
                else pendant += "δ" + index + " ";
            }
            if (sumDeg == 0) {
                if (isolated.equals("Ізольованих вершин нема")) isolated = "Ізольовані вершини: " + "δ" + index + " ";
                else isolated += "δ" + index + " ";
            }
        }
        JLabel pendantLabel = new JLabel(pendant);
        pendantLabel.setBounds(250, 70, d.width, 20);
        pendantLabel.setFont(this.FONT);
        this.add(pendantLabel);
        JLabel isolatedLabel = new JLabel(isolated);
        isolatedLabel.setBounds(250, 90, d.width, 20);
        isolatedLabel.setFont(this.FONT);
        this.add(isolatedLabel);
        String text = "Граф неоднорідний";
        if (inDegrees.size() <= 1 && outDegrees.size() <= 1) text = "Граф однорідний";
        JLabel regularLabel = new JLabel(text);
        regularLabel.setBounds(250, 50, d.width, 20);
        regularLabel.setFont(this.FONT);
        this.add(regularLabel);
    }
}
