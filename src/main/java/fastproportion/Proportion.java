package fastproportion;

public final class Proportion {

    public float x;
    public float y;
    public float width;
    public float height;
    public float contentWidth;
    public float contentHeight;

    // Animated outputs
    public float animX;
    public float animY;
    public float animW;
    public float animH;

    public Proportion(float width, float height, float contentWidth, float contentHeight) {
        this.width = width;
        this.height = height;
        this.contentWidth = contentWidth;
        this.contentHeight = contentHeight;
    }

    /**
     * @return [scaledX, scaledY, scaledWidth, scaledHeight]
     */
    public float[] compute(ProportionMode mode) {
        float scale;

        switch (mode) {
            case FIT_HORIZONTAL -> scale = width / contentWidth;
            case FIT_VERTICAL -> scale = height / contentHeight;
            case CONTAIN -> scale = Math.min(width / contentWidth, height / contentHeight);
            case COVER -> scale = Math.max(width / contentWidth, height / contentHeight);
            default -> scale = 1.0f;
        }

        float scaledWidth = contentWidth * scale;
        float scaledHeight = contentHeight * scale;

        float scaledX = x + (width - scaledWidth) * 0.5f;
        float scaledY = y + (height - scaledHeight) * 0.5f;

        return new float[]{scaledX, scaledY, scaledWidth, scaledHeight};
    }
}

