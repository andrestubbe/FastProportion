package fastproportion.demo;

import fastproportion.*;

import fastanimation.FastAnimation;
import fastanimation.AnimationEngine.HeartbeatMode;
import fasttween.FastTween;
import fasttween.Ease;

import java.util.List;

public class ModeSwitchAnimator {

    private final List<Proportion> proportions;
    private final Runnable repaintCallback;

    public ModeSwitchAnimator(List<Proportion> proportions, Runnable repaintCallback) {
        this.proportions = proportions;
        this.repaintCallback = repaintCallback;

        FastAnimation.setHeartbeatMode(HeartbeatMode.JAVA);
    }

    public void init(ProportionMode mode) {
        updateProgress(1f, mode, mode);
    }

    public void animate(ProportionMode from, ProportionMode to) {
        updateProgress(0f, from, to);

        FastAnimation.parallel(
            FastTween.to(0f, 1f, 300)
                .ease(Ease.LINEAR)
                .onUpdate(t -> updateProgress(t, from, to))
        ).start();
    }

    private void updateProgress(float t, ProportionMode from, ProportionMode to) {
        for (Proportion p : proportions) {
            // CURRENT
            float[] c = p.compute(from);
            float cx = c[0], cy = c[1], cw = c[2], ch = c[3];

            // TARGET
            float[] tg = p.compute(to);
            float tx = tg[0], ty = tg[1], tw = tg[2], th = tg[3];

            // LINEAR INTERPOLATION
            p.animX = cx + (tx - cx) * t;
            p.animY = cy + (ty - cy) * t;
            p.animW = cw + (tw - cw) * t;
            p.animH = ch + (th - ch) * t;
        }

        repaintCallback.run();
    }
}

