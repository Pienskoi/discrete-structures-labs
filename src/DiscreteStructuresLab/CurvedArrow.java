package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.*;

public class CurvedArrow extends JComponent {
    private int x1, y1, x2, y2, k, weight = 0;
    private boolean directed;
    private Color color = Color.BLACK;

    public CurvedArrow(int x1, int y1, int x2, int y2, int k, boolean directed, Dimension d) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.k = k;
        this.directed = directed;
        this.setOpaque(false);
        this.setBounds(0 , 0, d.width, d.height);
    }

    public void changeColor(Color color) { this.color = color; }
    public void addWeight(int weight) { this.weight = weight; }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(this.color);
        double angle = Math.atan2(y2 - y1, x2 - x1);
        double xc = (x1 + x2) / 2.0 + (Math.sin(-angle) * ((k * 35) + 45));
        double yc = (y1 + y2) / 2.0 + (Math.cos(angle) * ((k * 35) + 45));
        QuadCurve2D left = new QuadCurve2D.Double();
        QuadCurve2D right = new QuadCurve2D.Double();
        if (directed) {
            AffineTransform tx = new AffineTransform();
            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(0, 0);
            arrowHead.addPoint(-5, -10);
            arrowHead.addPoint(5, -10);
            tx.setToIdentity();
            QuadCurve2D line = new QuadCurve2D.Double(x1, y1, xc, yc, x2 - (Math.cos(angle) * 5), y2 - (Math.sin(angle) * 5));
            g2d.draw(line);
            line.subdivide(left, right);
            Point2D centerPoint = left.getP2();
            double anglec = Math.atan2(y2 - centerPoint.getY(), x2 - centerPoint.getX());
            tx.translate(x2 + 0.5, y2);
            tx.rotate((anglec - Math.PI / 2.0));
            g2d.setTransform(tx);
            g2d.fill(arrowHead);
        }
        else {
            QuadCurve2D line = new QuadCurve2D.Double(x1, y1, xc, yc, x2, y2 );
            line.subdivide(left, right);
            g2d.draw(line);
        }
        if (weight != 0) {
            FontMetrics fm = g2d.getFontMetrics();
            left.subdivide(left, right);
            Point2D quarterPoint = left.getP2();
            int x = (int) (quarterPoint.getX() - fm.stringWidth(String.valueOf(weight)) / 2);
            int y = (int) (quarterPoint.getY() + fm.getAscent() - fm.getHeight() / 2);
            g2d.translate(x, y);
            g2d.setColor(Color.WHITE);
            FontRenderContext frc = g2d.getFontRenderContext();
            TextLayout tl = new TextLayout(String.valueOf(weight), new Font("Arial", Font.BOLD, 18), frc);
            Shape shape = tl.getOutline(null);
            g2d.setStroke(new BasicStroke(4));
            g2d.draw(shape);
            g2d.setColor(color);
            g2d.fill(shape);
        }
    }
}