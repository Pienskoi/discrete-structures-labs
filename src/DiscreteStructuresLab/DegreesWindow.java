package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;

public class DegreesWindow extends JFrame {
    private int[][] matrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;
    private boolean directed;

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
                sumDeg = outDegree + inDegree;
                String degPlus = "δ" + "\u207A" + "(v" + index + ") = " + outDegree;
                String degMinus = "δ" + "\u207B" + "(v" + index + ") = " + inDegree;
                deg = degPlus + "   " + degMinus;
            } else {
                degree += outDegree;
                sumDeg = degree;
                deg = "δ(v" + index + ") = " + degree;
            }
            JLabel degLabel = new JLabel(deg);
            degLabel.setBounds(50, 50 + 20 * i, d.width, 20);
            degLabel.setFont(this.FONT);
            this.add(degLabel);
            if (sumDeg == 1) {
                pendant = "Висячі вершини: " + "δ" + index + " ";
            }
            if (sumDeg == 0) {
                isolated = "Ізольовані вершини: " + "δ" + index + " ";
            }
        }
        JLabel pendantLabel = new JLabel(pendant);
        pendantLabel.setBounds(250, 50, d.width, 20);
        pendantLabel.setFont(this.FONT);
        this.add(pendantLabel);
        JLabel isolatedLabel = new JLabel(isolated);
        isolatedLabel.setBounds(250, 70, d.width, 20);
        isolatedLabel.setFont(this.FONT);
        this.add(isolatedLabel);
        for (int i = 0; i < matrix.length; i++) {
            String mat = "";
            for (int j = 0; j < matrix[i].length; j++) {
                mat = mat.concat("  " + matrix[i][j]);
            }
            mat = mat.trim();
            JLabel matrixLabel = new JLabel(mat);
            matrixLabel.setBounds(1100, 275 + 20 * i, d.width, 20);
            matrixLabel.setFont(this.FONT);
            this.add(matrixLabel);
        }
    }
}
