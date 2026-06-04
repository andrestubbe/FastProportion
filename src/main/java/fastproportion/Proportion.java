package fastproportion;

/**
 * A lightweight, zero-dependency utility for calculating aspect-ratio aware scaling.
 * <p>
 * This class isolates pure rendering math from UI components. It takes an outer container size
 * and an inner content size, and calculates exactly where and how large the content should be
 * drawn to satisfy a specific {@link ProportionMode}.
 * <p>
 * All calculations use a pure 32-bit float pipeline to ensure sub-pixel accuracy and
 * prevent integer rounding artifacts during animations. Public fields are exposed deliberately
 * for performance reasons (avoiding getter overhead and megamorphic dispatch risk on the hotpath).
 */
public final class Proportion {

    /** X coordinate of the container's top-left corner. Defaults to 0. */
    public float x;
    /** Y coordinate of the container's top-left corner. Defaults to 0. */
    public float y;
    
    /** The width of the boundary container into which the content is fitted. */
    public float width;
    /** The height of the boundary container into which the content is fitted. */
    public float height;
    
    /** The original unscaled width of the target content (e.g. an image or video). */
    public float contentWidth;
    /** The original unscaled height of the target content. */
    public float contentHeight;

    /**
     * Initializes a new Proportion math context.
     *
     * @param width         The boundary container width
     * @param height        The boundary container height
     * @param contentWidth  The original content width
     * @param contentHeight The original content height
     */
    public Proportion(float width, float height, float contentWidth, float contentHeight) {
        this.width = width;
        this.height = height;
        this.contentWidth = contentWidth;
        this.contentHeight = contentHeight;
    }

    /**
     * Standard scaling calculation allocating a new array for the results.
     * Useful for one-off calculations where garbage collection is not a concern.
     *
     * @param mode The scaling strategy to apply (e.g. CONTAIN, COVER).
     * @return A newly allocated array containing [scaledX, scaledY, scaledWidth, scaledHeight].
     */
    public float[] compute(ProportionMode mode) {
        float scale = computeScale(mode);
        float scaledWidth = contentWidth * scale;
        float scaledHeight = contentHeight * scale;
        float scaledX = x + (width - scaledWidth) * 0.5f;
        float scaledY = y + (height - scaledHeight) * 0.5f;

        return new float[]{scaledX, scaledY, scaledWidth, scaledHeight};
    }

    /**
     * Zero-allocation calculation optimized for high-frequency render loops.
     * <p>
     * Instead of allocating a new float array on every frame, this method writes the
     * resulting coordinates directly into the provided pre-allocated array.
     * This eliminates GC pressure entirely during animations and layout updates.
     *
     * @param mode The scaling mode to use (e.g. CONTAIN, COVER).
     * @param out  A pre-allocated float array of at least length 4. 
     *             Will be populated with [scaledX, scaledY, scaledWidth, scaledHeight].
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

    /**
     * Computes the raw scaling multiplier based on the requested mode.
     */
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

