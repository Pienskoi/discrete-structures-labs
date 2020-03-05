package DiscreteStructuresLab;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

public class SelfArrow extends JComponent {
    private int x, y, ang1, ang2, ang3, r = 30;
    private boolean directed;

    public SelfArrow(int x, int y, int size, boolean directed, Dimension d) {
        if (y == 100) {
            this.x = x;
            this.y = y - (size / 2);
            this.ang1 = -25;
            this.ang2 = 230;
            this.ang3 = -20;
        }
        else if (y > d.height - 135) {
            this.x = x;
            this.y = y + (size / 2);
            this.ang1 = 155;
            this.ang2 = 230;
            this.ang3 = 160;
        }
        else if (x == 100) {
            this.x = x - (size / 2);
            this.y = y;
            this.ang1 = 65;
            this.ang2 = 230;
            this.ang3 = -110;
        }
        else if (x == d.width -100) {
            this.x = x + (size / 2);
            this.y = y;
            this.ang1 = -115;
            this.ang2 = 230;
            this.ang3 = 70;
        }
        this.directed = directed;
        this.setOpaque(false);
        this.setBounds(0, 0, d.width, d.height);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));
        Arc2D arc = new Arc2D.Double();
        arc.setArcByCenter(x, y, r, ang1, ang2, Arc2D.OPEN);
        if (directed) {
            AffineTransform tx = new AffineTransform();
            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(0, 0);
            arrowHead.addPoint(-5, -10);
            arrowHead.addPoint(5, -10);
            tx.setToIdentity();
            double xa = arc.getEndPoint().getX();
            double ya = arc.getEndPoint().getY();
            tx.translate(xa + 0.5, ya);
            tx.rotate(ang3 * (Math.PI / 180));
            arc.setArcByCenter(x, y, r, ang1, ang2 - 10, Arc2D.OPEN);
            g2d.draw(arc);
            g2d.setTransform(tx);
            g2d.fill(arrowHead);
        }
        else {
            g2d.draw(arc);
        }
    }
}
