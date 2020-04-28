package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Arrow extends JComponent {
    private int x1, y1, x2, y2, weight = 0;
    private boolean directed;
    private Color color = Color.BLACK;

    public Arrow(int x1, int y1, int x2, int y2, boolean directed, Dimension d) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
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
        if (directed) {
            AffineTransform tx = new AffineTransform();
            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(0, 0);
            arrowHead.addPoint(-5, -10);
            arrowHead.addPoint(5, -10);
            tx.setToIdentity();
            double angle = Math.atan2(y2 - y1, x2 - x1);
            Shape line = new Line2D.Double(x1, y1, x2 - (Math.cos(angle) * 5), y2 - (Math.sin(angle) * 5));
            g2d.draw(line);
            tx.translate(x2 + 0.5, y2);
            tx.rotate((angle - Math.PI / 2d));
            g2d.setTransform(tx);
            g2d.fill(arrowHead);
        }
        else {
            Shape line = new Line2D.Double(x1, y1, x2, y2);
            g2d.draw(line);
        }
        if (weight != 0) {
            FontMetrics fm = g2d.getFontMetrics();
            int xc = (4 * x1 + 5 * x2) / 9;
            int yc = (4 * y1 + 5 * y2) / 9;
            int x = xc - fm.stringWidth(String.valueOf(weight)) / 2;
            int y = yc + fm.getAscent() - fm.getHeight() / 2;
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