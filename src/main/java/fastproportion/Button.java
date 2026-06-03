package fastproportion;

import java.awt.*;

public abstract class Button {

    protected int x, y, size;
    public boolean active = false;

    public Button(int size) {
        this.size = size;
    }

    // Einheitliche Signatur fÃ¼r alle Buttons
    public abstract void paint(Graphics2D g2, Proportion p);

    // Einheitlicher Hit-Test
    public boolean hit(int mx, int my) {
        return mx >= x && my >= y && mx <= x + size && my <= y + size;
    }
}

