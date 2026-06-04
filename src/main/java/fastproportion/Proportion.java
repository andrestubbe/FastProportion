package fastproportion;

public final class Proportion {

    public float x;
    public float y;
    public float width;
    public float height;
    public float contentWidth;
    public float contentHeight;


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

    /**
     * Zero-allocation calculation. Writes the resulting coordinates into the provided array.
     *
     * @param mode The scaling mode to use
     * @param out  A float array of at least length 4. Will be populated with [x, y, width, height]
     */
    public void compute(ProportionMode mode, float[] out) {
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

        out[0] = x + (width - scaledWidth) * 0.5f;
        out[1] = y + (height - scaledHeight) * 0.5f;
        out[2] = scaledWidth;
        out[3] = scaledHeight;
    }
}

