package fastproportion;

import javax.swing.*;
import java.awt.*;

public class Window {

    private static final int WIDTH = 565;
    private static final int HEIGHT = 575;

    public static void setup(JFrame jframe) {
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setLocationRelativeTo(null);
        jframe.setBackground(Color.BLACK);
        jframe.setIconImage(Icon.createRoundIcon());
    }
}

