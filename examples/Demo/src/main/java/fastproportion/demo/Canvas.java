package fastproportion.demo;
import fastproportion.*;

import fastui.InteractionManager;
import fastui.behaviour.BehaviourDragMove;
import fastui.component.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {

    private final Scene scene;
    private final List<AnimatedLayout> layouts;
    private final ModeSwitchAnimator modeAnimator;
    
    private final List<Component> uiElements = new ArrayList<>();
    private final InteractionManager interactionManager;

    private ProportionMode currentMode = ProportionMode.CONTAIN;
    private ProportionMode targetMode = ProportionMode.CONTAIN;

    private final List<ProportionView> proportionViews = new ArrayList<>();

    private java.awt.image.BufferedImage createCircle(int size, Color color) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        g.dispose();
        return img;
    }

    public Canvas(Scene scene, List<AnimatedLayout> layouts) {
        this.scene = scene;
        this.layouts = layouts;

        this.modeAnimator = new ModeSwitchAnimator(layouts, this::repaint);

        java.awt.image.BufferedImage imgBase = createCircle(12, new Color(100, 100, 100, 200));
        java.awt.image.BufferedImage imgHover = createCircle(12, new Color(150, 150, 150, 255));
        java.awt.image.BufferedImage imgPressed = createCircle(12, new Color(255, 255, 255, 255));

        for (AnimatedLayout layout : layouts) {
            layout.p.x = scene.x;
            layout.p.y = scene.y;
            
            layout.moveBtn = new fastui.component.Image(imgBase);
            layout.moveBtn.addBehavior(new fastui.behaviour.BehaviorButton3x3(imgBase, imgHover, imgPressed));
            layout.moveBtn.addBehavior(new BehaviourDragMove((dx, dy) -> {
                scene.x += dx;
                scene.y += dy;
                modeAnimator.init(targetMode);
            }));
            
            layout.resizeBtn = new fastui.component.Image(imgBase);
            layout.resizeBtn.addBehavior(new fastui.behaviour.BehaviorButton3x3(imgBase, imgHover, imgPressed));
            layout.resizeBtn.addBehavior(new BehaviourDragMove((dx, dy) -> {
                layout.p.width = Math.max(50, layout.p.width + dx);
                layout.p.height = Math.max(50, layout.p.height + dy);
                modeAnimator.init(targetMode);
            }));
            
            ProportionView view = new ProportionView(layout, scene);
            proportionViews.add(view);
            
            uiElements.add(view);
            uiElements.add(layout.moveBtn);
            uiElements.add(layout.resizeBtn);
        }
        
        this.interactionManager = new InteractionManager(this, uiElements);
        this.modeAnimator.init(currentMode);

        setBackground(Color.BLACK);
        setFocusable(true);

        initKeyboard();
        initMouse();
    }

    private void initKeyboard() {
        KeyAdapter k = interactionManager.getKeyAdapter();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                k.keyPressed(e);
                
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
            @Override
            public void keyTyped(KeyEvent e) { k.keyTyped(e); }
            @Override
            public void keyReleased(KeyEvent e) { k.keyReleased(e); }
        });
    }

    private void initMouse() {
        MouseAdapter m = interactionManager.getMouseAdapter();
        addMouseListener(m);
        addMouseMotionListener(m);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (AnimatedLayout layout : layouts) {
            // Note: Bounds syncing for Frame and Content is now done entirely inside ProportionView.onRender()
        }
        
        for (Component c : uiElements) {
            c.render(g2);
        }

        g2.dispose();
    }
}
