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

    public float[] compute(ProportionMode mode) {
        float scale = computeScale(mode);
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
        float scale = computeScale(mode);
        float scaledWidth = contentWidth * scale;
        float scaledHeight = contentHeight * scale;

        out[0] = x + (width - scaledWidth) * 0.5f;
        out[1] = y + (height - scaledHeight) * 0.5f;
        out[2] = scaledWidth;
        out[3] = scaledHeight;
    }

    private float computeScale(ProportionMode mode) {
        switch (mode) {
            case FIT_HORIZONTAL: return width / contentWidth;
            case FIT_VERTICAL: return height / contentHeight;
            case CONTAIN: return Math.min(width / contentWidth, height / contentHeight);
            case COVER: return Math.max(width / contentWidth, height / contentHeight);
            default: return 1.0f; // Compiler-required fallback
        }
    }
}

