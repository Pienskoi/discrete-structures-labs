package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class CurvedArrow extends JComponent {
    private int x1, y1, x2, y2;
    private boolean directed;

    public CurvedArrow(int x1, int y1, int x2, int y2, boolean directed, Dimension d) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.directed = directed;
        this.setOpaque(false);
        this.setBounds(0 , 0, d.width, d.height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));
        if (directed) {
            AffineTransform tx = new AffineTransform();
            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(0, 0);
            arrowHead.addPoint(-5, -10);
            arrowHead.addPoint(5, -10);
            tx.setToIdentity();
            int xc = (x1 + x2) / 2;
            int yc = (y1 + y2) / 2;
            double angle = Math.atan2(y2 - y1, x2 - x1);
            double anglec = Math.atan2(y2 - yc, x2 - xc);
            QuadCurve2D line = new QuadCurve2D.Double(x1, y1, xc + (Math.sin(-angle) * 80), yc + (Math.cos(angle) * 90), x2 - (Math.cos(angle) * 5), y2 - (Math.sin(angle) * 5));
            g2d.draw(line);
            tx.translate(x2 + 0.5, y2);
            tx.rotate((anglec - Math.PI / 2d));
            g2d.setTransform(tx);
            g2d.fill(arrowHead);
        }
        else {
            int xc = (x1 + x2) / 2;
            int yc = (y1 + y2) / 2;
            double angle = Math.atan2(y2 - y1, x2 - x1);
            QuadCurve2D line = new QuadCurve2D.Double(x1, y1, xc + (Math.sin(-angle) * 80), yc + (Math.cos(angle) * 90), x2, y2 );
            g2d.draw(line);
        }
    }
}