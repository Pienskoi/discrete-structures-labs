package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Graph {
    private int size = 70;
    private boolean directed;
    private HashMap<Number, Circle> vertices = new HashMap<>();
    private int[][] matrix;

    public Graph(int[][] matrix, boolean directed) {
        this.matrix = matrix;
        this.directed = directed;
    }

    public void drawEdges(JFrame frame) {
        Dimension d = frame.getPreferredSize();
        int n = matrix.length;
        if (directed) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j && matrix[i][j] == 1) {
                        Circle vertex = vertices.get(i + 1);
                        SelfArrow arrow = new SelfArrow(vertex.x, vertex.y, size, directed, d);
                        frame.add(arrow);
                    }
                    if (i != j && matrix[i][j] == 1) {
                        Circle vertex2 = vertices.get(i + 1);
                        Circle vertex1 = vertices.get(j + 1);
                        int dx = vertex2.x - vertex1.x;
                        int dy = vertex2.y - vertex1.y;
                        double angle = Math.atan2(dy, dx);
                        int x1 = (int) (vertex1.x + (Math.cos(angle) * size / 2));
                        int y1 = (int) (vertex1.y + (Math.sin(angle) * size / 2));
                        int x2 = (int) (vertex2.x - (Math.cos(angle) * size / 2));
                        int y2 = (int) (vertex2.y - (Math.sin(angle) * size / 2));
                        if (matrix[i][j] == matrix[j][i]) {
                            double angle2 = angle + Math.PI/10;
                            x1 = (int) (vertex1.x + (Math.cos(angle2) * size / 2));
                            y1 = (int) (vertex1.y + (Math.sin(angle2) * size / 2));
                            x2 = (int) (vertex2.x - (Math.cos(angle) * size / 2));
                            y2 = (int) (vertex2.y - (Math.sin(angle) * size / 2));
                            CurvedArrow arrow = new CurvedArrow(x1, y1, x2, y2, directed, d);
                            frame.add(arrow);
                        } else {
                            if (intersect(x1, y1, x2, y2)) {
                                CurvedArrow arrow = new CurvedArrow(x1, y1, x2, y2, directed, d);
                                frame.add(arrow);
                            } else {
                                Arrow arrow = new Arrow(x1, y1, x2, y2, directed, d);
                                frame.add(arrow);
                            }
                        }
                    }
                }
            }
        } else {
            for (int k = 0; k < n; k++) {
                for (int l = k; l < n; l++) {
                    if (k == l && matrix[k][l] == 1) {
                        Circle vertex = vertices.get(k + 1);
                        SelfArrow arrow = new SelfArrow(vertex.x, vertex.y, size, directed, d);
                        frame.add(arrow);
                    }
                    if (k != l && matrix[k][l] == 1) {
                        Circle vertex2 = vertices.get(k + 1);
                        Circle vertex1 = vertices.get(l + 1);
                        int dx = vertex2.x - vertex1.x;
                        int dy = vertex2.y - vertex1.y;
                        double angle = Math.atan2(dy, dx);
                        int x1 = (int) (vertex1.x + (Math.cos(angle) * size / 2));
                        int y1 = (int) (vertex1.y + (Math.sin(angle) * size / 2));
                        int x2 = (int) (vertex2.x - (Math.cos(angle) * size / 2));
                        int y2 = (int) (vertex2.y - (Math.sin(angle) * size / 2));
                        if (intersect(x1, y1, x2, y2)) {
                            CurvedArrow arrow = new CurvedArrow(x1, y1, x2, y2, directed, d);
                            frame.add(arrow);
                        } else {
                            Arrow arrow = new Arrow(x1, y1, x2, y2, directed, d);
                            frame.add(arrow);
                        }
                    }
                }
            }
        }
    }

    public void drawVertices(JFrame frame) {
        Dimension d = frame.getPreferredSize();
        int n = matrix.length;
        int v = n / 4 + 1;
        int h = n / 2 - v;
        int r = n % 4;
        for (int i = 0; i < v; i++) {
            int vi = (d.height - 200) / (v - 1);
            int rx = d.width - 100;
            int lx = 100;
            int y = 100 + (vi * i);
            int rn = h + i + 1;
            int ln = n - i;
            Circle vertexRight = new Circle(rx, y, size, d, rn);
            Circle vertexLeft = new Circle(lx, y, size, d, ln);
            vertices.put(rn, vertexRight);
            vertices.put(ln, vertexLeft);
            frame.add(vertexRight);
            frame.add(vertexLeft);
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
            frame.add(vertexTop);
            frame.add(vertexBottom);
            if (r % 2 != 0) {
                bx = hbi * (h + 1) + 100;
                bn = n - v - h;
                Circle vertexAdditional = new Circle(bx, by, size, d, bn);
                vertices.put(bn, vertexAdditional);
                frame.add(vertexAdditional);
            }
        }
    }
    public boolean intersect(int x1, int y1, int x2, int y2) {
        for (Circle vertex : vertices.values()) {
            int xc = vertex.x;
            int yc = vertex.y;
            if (xc <= Math.max(x1, x2) && xc >= Math.min(x1, x2) &&
                    yc <= Math.max(y1, y2) && yc >= Math.min(y1, y2))
                return true;
        }
        return false;
    }
}
