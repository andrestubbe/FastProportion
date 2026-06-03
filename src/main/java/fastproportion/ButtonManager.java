package fastproportion;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class ButtonManager {

    private final Scene scene;
    private final List<Proportion> proportions;
    private final Runnable onUpdate;

    private final ButtonMove btnMove = new ButtonMove();
    private final ButtonResize btnResize = new ButtonResize();

    private Proportion activeProportion = null;

    private int startMouseX, startMouseY;
    private int startX, startY;
    private int startW, startH;

    public ButtonManager(Scene scene, List<Proportion> proportions, Runnable onUpdate) {
        this.scene = scene;
        this.proportions = proportions;
        this.onUpdate = onUpdate;
    }

    // ---------------------------------------------------------
    // HIT TEST
    // ---------------------------------------------------------
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        for (Proportion p : proportions) {

            if (btnMove.hit(mx, my, p)) {
                activeProportion = p;
                btnMove.active = true;

                startMouseX = mx;
                startMouseY = my;
                startX = p.x;
                startY = p.y;
                return;
            }

            if (btnResize.hit(mx, my, p)) {
                activeProportion = p;
                btnResize.active = true;

                startMouseX = mx;
                startMouseY = my;
                startW = p.width;
                startH = p.height;
                return;
            }
        }
    }

    public void mouseReleased() {
        btnMove.active = false;
        btnResize.active = false;
        activeProportion = null;
    }

    // ---------------------------------------------------------
    // DRAGGING
    // ---------------------------------------------------------
    public boolean mouseDragged(MouseEvent e) {
        if (activeProportion == null)
            return false;

        int mx = e.getX();
        int my = e.getY();

        Proportion p = activeProportion;

        if (btnMove.active) {
            scene.x = startX + (mx - startMouseX);
            scene.y = startY + (my - startMouseY);
            onUpdate.run();
            return true;
        }

        if (btnResize.active) {
            p.width = Math.max(50, startW + (mx - startMouseX));
            p.height = Math.max(50, startH + (my - startMouseY));
            onUpdate.run();
            return true;
        }

        return false;
    }

    // ---------------------------------------------------------
    // PAINT
    // ---------------------------------------------------------
    public void paint(Graphics2D g2, Proportion p) {
        btnMove.paint(g2, p);
        btnResize.paint(g2, p);
    }
}

