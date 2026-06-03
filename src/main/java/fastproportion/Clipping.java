package fastproportion;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Clipping {
    private static Shape oldClip;

    public static void push(Graphics2D g2) {
        oldClip = g2.getClip();
    }

    public static void pop(Graphics2D g2) {
        g2.setClip(oldClip);
    }

    public static void clip(Graphics2D g2, Proportion p) {
        g2.clip(new Rectangle2D.Double(p.x, p.y, p.width, p.height));
    }
}

