package fastproportion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Canvas extends JPanel {

    private final Scene scene;
    private final List<Proportion> proportions;
    private final ButtonManager buttons;
    private final ModeSwitchAnimator modeAnimator;

    private ProportionMode currentMode = ProportionMode.CONTAIN;
    private ProportionMode targetMode = ProportionMode.CONTAIN;

    public Canvas(Scene scene, List<Proportion> proportions) {
        this.scene = scene;
        this.proportions = proportions;

        this.modeAnimator = new ModeSwitchAnimator(proportions, this::repaint);
        this.buttons = new ButtonManager(scene, proportions, () -> modeAnimator.init(currentMode));
        this.modeAnimator.init(currentMode);  // sofort richtigen Zustand setzen

        setBackground(Color.BLACK);
        setFocusable(true);

        initKeyboard();
        initMouse();
    }

    private void initKeyboard() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                ProportionMode newMode = switch (e.getKeyChar()) {
                    case '1' -> ProportionMode.FIT_HORIZONTAL;
                    case '2' -> ProportionMode.FIT_VERTICAL;
                    case '3' -> ProportionMode.CONTAIN;
                    case '4' -> ProportionMode.COVER;
                    default -> null;
                };

                if (newMode != null && newMode != targetMode) {
                    currentMode = targetMode;
                    targetMode = newMode;
                    modeAnimator.animate(currentMode, targetMode);
                }
            }
        });
    }

    private void initMouse() {
        MouseAdapter m = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                buttons.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                buttons.mouseReleased();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (buttons.mouseDragged(e))
                    repaint();
            }
        };

        addMouseListener(m);
        addMouseMotionListener(m);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Proportion p : proportions) {

            p.x = (int) scene.x;
            p.y = (int) scene.y;

            new Frame().paint(g2, p.x, p.y, p.width, p.height);

            Clipping.push(g2);
            Clipping.clip(g2, p);
            Content.paint(g2, p.animX, p.animY, p.animW, p.animH);
            Clipping.pop(g2);

            buttons.paint(g2, p);
        }

        g2.dispose();
    }
}

