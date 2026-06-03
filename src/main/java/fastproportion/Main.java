package fastproportion;

import fasttheme.FastTheme;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("sun.java2d.opengl.fbobject", "true");
        System.setProperty("sun.java2d.d3d", "false");

        SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        super("Gallery");
        Window.setup(this);
        addNotify();
        long hwnd = FastTheme.getWindowHandle(this);
        if (hwnd != 0) {
            FastTheme.setTitleBarColor(hwnd, 0, 0, 0);
            FastTheme.setTitleBarTextColor(hwnd, 255, 255, 255);
        }

        Scene scene = new Scene();
        scene.x = 25;
        scene.y = 25;

        List<Proportion> proportions = new ArrayList<>();
        proportions.add(new Proportion(500, 500, 1000, 2134));

        Canvas canvas = new Canvas(scene, proportions);
        setContentPane(canvas);

        setVisible(true);
        SwingUtilities.invokeLater(canvas::requestFocusInWindow);
    }
}

