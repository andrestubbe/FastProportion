package fastproportion.demo;

import fastproportion.Proportion;
import fastproportion.ProportionMode;
import fasttheme.FastTheme;
import fastui.behaviour.BehaviourDragMove;
import fastui.component.Component;
import fastui.component.ClipContainer;
import fastui.component.Image;
import fastanimation.FastAnimation;
import fastanimation.AnimationEngine.HeartbeatMode;
import fasttween.FastTween;
import fasttween.Ease;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Demo extends JFrame {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("sun.java2d.opengl.fbobject", "true");
        System.setProperty("sun.java2d.d3d", "false");
        SwingUtilities.invokeLater(Demo::new);
    }

    private final Proportion p = new Proportion(500, 500, 1000, 2134);
    private float animX, animY, animW, animH;
    
    private ProportionMode currentMode = ProportionMode.CONTAIN;
    private ProportionMode targetMode = ProportionMode.CONTAIN;
    
    private final fastui.Container root = new fastui.Container();
    private final float[] tempFrom = new float[4];
    private final float[] tempTo = new float[4];

    public Demo() {
        super("FastProportion Demo");
        
        // Setup Window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1173, 610);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.BLACK);
        
        // Setup Icon
        BufferedImage icon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gi = icon.createGraphics();
        gi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gi.setColor(Color.WHITE);
        gi.fillOval(4, 4, 56, 56);
        gi.dispose();
        this.setIconImage(icon);

        this.addNotify();
        long hwnd = FastTheme.getWindowHandle(this);
        if (hwnd != 0) {
            FastTheme.setTitleBarColor(hwnd, 0, 0, 0);
            FastTheme.setTitleBarTextColor(hwnd, 255, 255, 255);
            FastTheme.setWindowTransparency(hwnd, 224);
        }

        p.x = 336;
        p.y = 55;

        FastAnimation.setHeartbeatMode(HeartbeatMode.JAVA);
        root.setBackground(Color.BLACK);

        // 1. Frame Component
        Component frame = new Component() {
            @Override
            public void onRender(Graphics2D g) {
                g.setColor(new Color(32, 32, 32));
                g.fillRect((int)getAbsoluteX(), (int)getAbsoluteY(), (int)getWidth(), (int)getHeight());
            }
        };

        // 2. Content Component
        Component content = new Component() {
            @Override
            public void onRender(Graphics2D g) {
                int ax = (int)getAbsoluteX(), ay = (int)getAbsoluteY();
                int w = (int)getWidth(), h = (int)getHeight();
                g.setColor(Color.WHITE);
                g.fillRect(ax, ay, w, h);
                g.setColor(Color.BLACK);
                g.drawLine(ax, ay, ax + w, ay + h);
                g.drawLine(ax + w, ay, ax, ay + h);
            }
        };

        // 3. View Container (syncs bounds & clipping)
        ClipContainer clip = new ClipContainer();
        clip.add(content);
        
        Component view = new Component() {
            @Override
            public void onRender(Graphics2D g) {
                frame.setBounds(p.x, p.y, p.width, p.height);
                clip.setBounds(p.x, p.y, p.width, p.height);
                content.setBounds(animX - p.x, animY - p.y, animW, animH);
            }
            @Override
            public boolean contains(float mx, float my) {
                return true; // Let interactions pass through to children
            }
        };
        view.add(frame);
        view.add(clip);
        root.add(view);

        // 4. Handles
        BufferedImage imgBase = createCircle(12, new Color(100, 100, 100, 200));
        BufferedImage imgHover = createCircle(12, new Color(150, 150, 150, 255));
        BufferedImage imgPressed = createCircle(12, new Color(255, 255, 255, 255));

        Image moveBtn = new Image(imgBase);
        moveBtn.addBehavior(new fastui.behaviour.BehaviorButton3x3(imgBase, imgHover, imgPressed));
        moveBtn.addBehavior(new BehaviourDragMove((dx, dy) -> {
            p.x += dx;
            p.y += dy;
            switchMode(targetMode);
        }));

        Image resizeBtn = new Image(imgBase);
        resizeBtn.addBehavior(new fastui.behaviour.BehaviorButton3x3(imgBase, imgHover, imgPressed));
        resizeBtn.addBehavior(new BehaviourDragMove((dx, dy) -> {
            p.width = Math.max(50, p.width + dx);
            p.height = Math.max(50, p.height + dy);
            switchMode(targetMode);
        }));

        Component handlesLayer = new Component() {
            @Override
            public void onRender(Graphics2D g) {
                moveBtn.setBounds(p.x - 6, p.y - 6, 12, 12);
                resizeBtn.setBounds(p.x + p.width - 6, p.y + p.height - 6, 12, 12);
            }
            @Override
            public boolean contains(float mx, float my) {
                return true; // Let interactions pass through to handles
            }
        };
        handlesLayer.add(moveBtn);
        handlesLayer.add(resizeBtn);
        root.add(handlesLayer);

        // 5. Keyboard Controls
        root.addKeyListener(new KeyAdapter() {
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
                    animateMode(currentMode, targetMode);
                }
            }
        });

        switchMode(currentMode);
        setContentPane(root);
        setVisible(true);
        SwingUtilities.invokeLater(root::requestFocusInWindow);
    }

    private void switchMode(ProportionMode target) {
        updateProgress(1f, target, target);
    }

    private void animateMode(ProportionMode from, ProportionMode to) {
        updateProgress(0f, from, to);
        FastAnimation.parallel(
            FastTween.to(0f, 1f, 300)
                .ease(Ease.LINEAR)
                .onUpdate(t -> updateProgress(t, from, to))
        ).start();
    }

    private void updateProgress(float t, ProportionMode from, ProportionMode to) {
        p.compute(from, tempFrom);
        p.compute(to, tempTo);
        
        animX = tempFrom[0] + (tempTo[0] - tempFrom[0]) * t;
        animY = tempFrom[1] + (tempTo[1] - tempFrom[1]) * t;
        animW = tempFrom[2] + (tempTo[2] - tempFrom[2]) * t;
        animH = tempFrom[3] + (tempTo[3] - tempFrom[3]) * t;
        
        root.repaint();
    }

    private BufferedImage createCircle(int size, Color color) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        g.dispose();
        return img;
    }
}
