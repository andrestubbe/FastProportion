package fastproportion;

import fastanimation.FastAnimation;
import fastanimation.AnimationEngine.HeartbeatMode;
import fasttween.FastTween;
import fasttween.Ease;

import java.util.List;

public class ModeSwitchAnimator {

    private static final long T0 = System.nanoTime();
    private static long ms() { return (System.nanoTime() - T0) / 1_000_000L; }

    private final List<Proportion> proportions;
    private final Runnable repaintCallback;

    public ModeSwitchAnimator(List<Proportion> proportions, Runnable repaintCallback) {
        System.out.println("[" + ms() + "ms] ANIMATION START");
        this.proportions = proportions;
        this.repaintCallback = repaintCallback;

        // WICHTIG: Kein Delay mehr
        FastAnimation.setHeartbeatMode(HeartbeatMode.JAVA);
    }

    public void init(ProportionMode mode) {
        updateProgress(1f, mode, mode);
    }

    public void animate(ProportionMode from, ProportionMode to) {

        // WICHTIG: Sofort Frame 0 anzeigen â†’ kein Delay
        updateProgress(0f, from, to);

        FastAnimation.parallel(
            FastTween.to(0f, 1f, 300)
                .ease(Ease.LINEAR)
                .onUpdate(t -> updateProgress(t, from, to))
        ).start();
    }

    private void updateProgress(float t, ProportionMode from, ProportionMode to) {
        System.out.println("[" + ms() + "ms] UPDATE " + t);

        for (Proportion p : proportions) {

            // CURRENT
            p.compute(from);
            double cx = p.scaledX;
            double cy = p.scaledY;
            double cw = p.scaledWidth;
            double ch = p.scaledHeight;

            // TARGET
            p.compute(to);
            double tx = p.scaledX;
            double ty = p.scaledY;
            double tw = p.scaledWidth;
            double th = p.scaledHeight;

            // LINEAR INTERPOLATION (1:1 wie vorher)
            p.animX = cx + (tx - cx) * t;
            p.animY = cy + (ty - cy) * t;
            p.animW = cw + (tw - cw) * t;
            p.animH = ch + (th - ch) * t;
        }

        repaintCallback.run();
    }
}

