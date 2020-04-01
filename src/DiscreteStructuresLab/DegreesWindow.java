package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class DegreesWindow extends JFrame {
    private int[][] matrix;
    private final Font FONT = new Font("FreeSans", Font.BOLD, 16);
    private Dimension d;
    private boolean directed;
    private HashMap<Integer, Circle> vertices;

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
        Graph graph = new Graph(matrix, directed);
        this.vertices = graph.getVertices();
        graph.getEdges();
        String pendant = "Висячих вершин нема";
        String isolated = "Ізольованих вершин нема";
        for (Integer i : vertices.keySet()) {
            Circle vertex = vertices.get(i);
            int sumDeg = vertex.outDegree + vertex.inDegree;
            int m = i;
            String index = "";
            while(m > 0) {
                int a = m % 10;
                String num = String.valueOf(Character.toChars(Integer.parseInt("208" + a, 16)));
                index = num + index;
                m = m / 10;
            }
            String deg = "";
            if (directed) {
                String degPlus = "δ" + "\u207A" + "(v" + index + ") = " + vertex.outDegree;
                String degMinus = "δ" + "\u207B" + "(v" + index + ") = " + vertex.inDegree;
                deg = degPlus + "   " + degMinus;

            } else {
                deg = "δ(v" + index + ") = " + sumDeg;
            }
            JLabel degLabel = new JLabel(deg);
            degLabel.setBounds(50, 50 + 20 * (i - 1), d.width, 20);
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
    }
}
