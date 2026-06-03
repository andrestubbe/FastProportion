package fastproportion;

public final class Proportion {

    public int x;
    public int y;
    public int width;
    public int height;
    public int contentWidth;
    public int contentHeight;

    public double scaledX;
    public double scaledY;
    public double scaledWidth;
    public double scaledHeight;

    public double animX, animY, animW, animH;

    public Proportion(int width, int height, int contentWidth, int contentHeight) {
        this.width = width;
        this.height = height;
        this.contentWidth = contentWidth;
        this.contentHeight = contentHeight;
    }

    public void compute(ProportionMode mode) {

        double scale;

        switch (mode) {
            case FIT_HORIZONTAL -> scale = (double) width / contentWidth;
            case FIT_VERTICAL -> scale = (double) height / contentHeight;
            case CONTAIN -> // contain
                    scale = Math.min(
                            (double) width / contentWidth,
                            (double) height / contentHeight
                    );
            case COVER -> // cover
                    scale = Math.max(
                            (double) width / contentWidth,
                            (double) height / contentHeight
                    );
            default -> scale = 1.0;
        }

        scaledWidth = contentWidth * scale;
        scaledHeight = contentHeight * scale;

        // Zentrierung
        scaledX = x + (width - scaledWidth) * 0.5;
        scaledY = y + (height - scaledHeight) * 0.5;
    }
}

