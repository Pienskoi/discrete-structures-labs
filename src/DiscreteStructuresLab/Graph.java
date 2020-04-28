package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    private int size = 70;
    private boolean directed;
    private HashMap<Integer, Circle> vertices = new HashMap<>();
    private HashMap<List<Integer>, JComponent> edges = new HashMap<>(),
            treeEdges = new HashMap<>();
    private int[][] matrix;
    private Dimension d = new Dimension(1000, 1000);

    public Graph(int[][] matrix, boolean directed) {
        this.matrix = matrix;
        this.directed = directed;
        this.getVertices();
        this.getEdges();
    }

    public void draw(JFrame frame) {
        for (Circle vertex : vertices.values()) {
            frame.add(vertex);
        }
        for (JComponent edge : edges.values()) {
            frame.add(edge);
        }
    }
    public void getEdges() {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            if (directed) {
                for (int j = 0; j < n; j++) {
                    List<Integer> key = new ArrayList<>();
                    key.add(i);
                    key.add(j);
                    if (i == j && matrix[i][j] == 1) {
                        Circle vertex = vertices.get(i + 1);
                        SelfArrow arrow = new SelfArrow(vertex.x, vertex.y, size, directed, d);
                        edges.put(key, arrow);
                    }
                    if (i != j && matrix[i][j] == 1) {
                        Circle vertex1 = vertices.get(i + 1);
                        Circle vertex2 = vertices.get(j + 1);
                        int dx = vertex2.x - vertex1.x;
                        int dy = vertex2.y - vertex1.y;
                        double angle = Math.atan2(dy, dx);
                        int x1 = (int) (vertex1.x + (Math.cos(angle) * size / 2));
                        int y1 = (int) (vertex1.y + (Math.sin(angle) * size / 2));
                        int x2 = (int) (vertex2.x - (Math.cos(angle) * size / 2));
                        int y2 = (int) (vertex2.y - (Math.sin(angle) * size / 2));
                        int k = intersect(x1, y1, x2, y2);
                        if (matrix[i][j] == matrix[j][i]) {
                            double angle2 = angle + Math.PI / 10;
                            x1 = (int) (vertex1.x + (Math.cos(angle2) * size / 2));
                            y1 = (int) (vertex1.y + (Math.sin(angle2) * size / 2));
                            CurvedArrow arrow = new CurvedArrow(x1, y1, x2, y2, k, directed, d);
                            edges.put(key, arrow);
                        } else {
                            if (k > 0) {
                                CurvedArrow arrow = new CurvedArrow(x1, y1, x2, y2, k, directed, d);
                                edges.put(key, arrow);
                            } else {
                                Arrow arrow = new Arrow(x1, y1, x2, y2, directed, d);
                                edges.put(key, arrow);
                            }
                        }
                    }
                }
            } else {
                for (int l = i; l < n; l++) {
                    List<Integer> key = new ArrayList<>();
                    key.add(i);
                    key.add(l);
                    if (i == l && matrix[i][l] == 1) {
                        Circle vertex = vertices.get(i + 1);
                        SelfArrow arrow = new SelfArrow(vertex.x, vertex.y, size, directed, d);
                        edges.put(key, arrow);
                    }
                    if (i != l && matrix[i][l] == 1) {
                        Circle vertex1 = vertices.get(i + 1);
                        Circle vertex2 = vertices.get(l + 1);
                        int dx = vertex2.x - vertex1.x;
                        int dy = vertex2.y - vertex1.y;
                        double angle = Math.atan2(dy, dx);
                        int x1 = (int) (vertex1.x + (Math.cos(angle) * size / 2));
                        int y1 = (int) (vertex1.y + (Math.sin(angle) * size / 2));
                        int x2 = (int) (vertex2.x - (Math.cos(angle) * size / 2));
                        int y2 = (int) (vertex2.y - (Math.sin(angle) * size / 2));
                        int k = intersect(x1, y1, x2, y2);
                        if (k > 0) {
                            CurvedArrow arrow = new CurvedArrow(x1, y1, x2, y2, k, directed, d);
                            edges.put(key, arrow);
                        } else {
                            Arrow arrow = new Arrow(x1, y1, x2, y2, directed, d);
                            edges.put(key, arrow);
                        }
                    }
                }
            }
        }
    }
    public int intersect(double x1, double y1, double x2, double y2) {
        int counter = 0;
        for (Circle vertex : vertices.values()) {
            int xc = vertex.x;
            int yc = vertex.y;
            if (xc <= Math.max(x1, x2) && xc >= Math.min(x1, x2) &&
                    yc <= Math.max(y1, y2) && yc >= Math.min(y1, y2))
                counter++;
        }
        return counter;
    }
    public void getVertices() {
        int n = matrix.length;
        int v = n / 4 + 1;
        int h = n / 2 - v;
        int r = n % 4;
        if (h > -1) {
            for (int i = 0; i < v; i++) {
                double vi;
                if (v <= 1) vi = d.height - 200;
                else vi = (d.height - 200) / (v - 1.0);
                int rx = d.width - 100;
                int lx = 100;
                int y = (int) (100 + (vi * i));
                int rn = h + i + 1;
                int ln = n - i;
                Circle vertexRight = new Circle(rx, y, size, d, rn);
                vertices.put(rn, vertexRight);
                Circle vertexLeft = new Circle(lx, y, size, d, ln);
                vertices.put(ln, vertexLeft);
            }
        }
        for (int j = 0; j < h; j++) {
            int hti = (d.width - 200) / (h + 1);
            int hbi = hti;
            if (r % 2 != 0) {
                hbi = (d.width - 200) / (h + 2);
            }
            int tx = hti * (j + 1) + 100;
            int bx = hbi * (j + 1) + 100;
            int ty = 100;
            int by = d.height - 100;
            int tn = j + 1;
            int bn = n - v - j;
            Circle vertexTop = new Circle(tx, ty, size, d, tn);
            Circle vertexBottom = new Circle(bx, by, size, d, bn);
            vertices.put(tn, vertexTop);
            vertices.put(bn, vertexBottom);
        }
        if (r % 2 != 0) {
            int ax = ((d.width - 200) / (h + 2)) * (h + 1) + 100;
            int ay = d.height - 100;
            if (h < 0) ay = 100;
            int an = n - v - h;
            Circle vertexAdditional = new Circle(ax, ay, size, d, an);
            vertices.put(an, vertexAdditional);
        }
    }
    public void changeVertexColor(int v, Color color) {
        Circle vertex = vertices.get(v);
        vertex.changeColor(color);
    }
    public void changeVertexNumber(int v, int n) {
        Circle vertex = vertices.get(v);
        vertex.changeNumber(n);
    }
    public void addEdgeToTree(int v1, int v2) {
        List<Integer> key = new ArrayList<>();
        if (!directed && v1 > v2) {
            key.add(v2 - 1);
            key.add(v1 - 1);
        } else {
            key.add(v1 - 1);
            key.add(v2 - 1);
        }
        JComponent edge = edges.get(key);
        treeEdges.put(key, edge);
        if (edge.getClass().getName().equals("DiscreteStructuresLab.Arrow")) {
            Arrow arrow = (Arrow) edge;
            arrow.changeColor(Color.BLUE);
        }
        if (edge.getClass().getName().equals("DiscreteStructuresLab.SelfArrow")) {
            SelfArrow arrow = (SelfArrow) edge;
            arrow.changeColor(Color.BLUE);
        }
        if (edge.getClass().getName().equals("DiscreteStructuresLab.CurvedArrow")) {
            CurvedArrow arrow = (CurvedArrow) edge;
            arrow.changeColor(Color.BLUE);
        }
    }
    public void drawTree(JFrame frame) {
        for (Circle vertex : vertices.values()) {
            frame.add(vertex);
        }
        for (JComponent edge : treeEdges.values()) {
            frame.add(edge);
        }
    }
    public void addWeightToEdges(int[][] weightMatrix) {
        int n = weightMatrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (weightMatrix[i][j] != 0) {
                    List<Integer> key = new ArrayList<>();
                    key.add(i);
                    key.add(j);
                    JComponent edge = edges.get(key);
                    if (edge.getClass().getName().equals("DiscreteStructuresLab.Arrow")) {
                        Arrow arrow = (Arrow) edge;
                        arrow.addWeight(weightMatrix[i][j]);
                    }
                    if (edge.getClass().getName().equals("DiscreteStructuresLab.SelfArrow")) {
                        SelfArrow arrow = (SelfArrow) edge;
                        arrow.addWeight(weightMatrix[i][j]);
                    }
                    if (edge.getClass().getName().equals("DiscreteStructuresLab.CurvedArrow")) {
                        CurvedArrow arrow = (CurvedArrow) edge;
                        arrow.addWeight(weightMatrix[i][j]);
                    }
                }
            }
        }
    }
    public void changeEdgeColor(int v1, int v2, Color color) {
        List<Integer> key = new ArrayList<>();
        if (!directed && v1 > v2) {
            key.add(v2 - 1);
            key.add(v1 - 1);
        } else {
            key.add(v1 - 1);
            key.add(v2 - 1);
        }
        JComponent edge = edges.get(key);
        if (edge.getClass().getName().equals("DiscreteStructuresLab.Arrow")) {
            Arrow arrow = (Arrow) edge;
            arrow.changeColor(color);
        }
        if (edge.getClass().getName().equals("DiscreteStructuresLab.SelfArrow")) {
            SelfArrow arrow = (SelfArrow) edge;
            arrow.changeColor(color);
        }
        if (edge.getClass().getName().equals("DiscreteStructuresLab.CurvedArrow")) {
            CurvedArrow arrow = (CurvedArrow) edge;
            arrow.changeColor(color);
        }
    }
}
