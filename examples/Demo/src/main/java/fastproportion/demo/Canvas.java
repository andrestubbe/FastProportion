package fastproportion.demo;
import fastproportion.*;

import fastui.InteractionManager;
import fastui.behaviour.BehaviourDragMove;
import fastui.component.Component;
import fastui.util.Clipping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {

    private final Scene scene;
    private final List<Proportion> proportions;
    private final ModeSwitchAnimator modeAnimator;
    
    private final List<Component> uiElements = new ArrayList<>();
    private final InteractionManager interactionManager;

    private ProportionMode currentMode = ProportionMode.CONTAIN;
    private ProportionMode targetMode = ProportionMode.CONTAIN;

    private final Frame sharedFrame = new Frame();
    private final Content sharedContent = new Content();

    public Canvas(Scene scene, List<Proportion> proportions) {
        this.scene = scene;
        this.proportions = proportions;

        this.modeAnimator = new ModeSwitchAnimator(proportions, this::repaint);
        
        for (Proportion p : proportions) {
            p.x = scene.x;
            p.y = scene.y;
            MoveHandle mh = new MoveHandle(p);
            mh.addBehavior(new BehaviourDragMove((dx, dy) -> {
                scene.x += dx;
                scene.y += dy;
                modeAnimator.init(currentMode);
            }));
            
            ResizeHandle rh = new ResizeHandle(p);
            rh.addBehavior(new BehaviourDragMove((dx, dy) -> {
                p.width = Math.max(50, p.width + dx);
                p.height = Math.max(50, p.height + dy);
                modeAnimator.init(currentMode);
            }));
            
            uiElements.add(mh);
            uiElements.add(rh);
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

        for (Proportion p : proportions) {
            p.x = scene.x;
            p.y = scene.y;

            sharedFrame.setBounds(p.x, p.y, p.width, p.height);
            sharedFrame.render(g2);

            Shape oldClip = Clipping.push(g2);
            Clipping.clip(g2, p.x, p.y, p.width, p.height);
            
            sharedContent.setBounds(p.animX, p.animY, p.animW, p.animH);
            sharedContent.render(g2);
            
            Clipping.pop(g2, oldClip);
        }
        
        for (Component c : uiElements) {
            c.render(g2);
        }

        g2.dispose();
    }
}
