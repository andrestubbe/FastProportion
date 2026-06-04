package fastproportion;

/**
 * Defines the scaling strategy used when fitting content into a container's bounds.
 * These modes determine how aspect ratios are preserved or clipped during calculation.
 */
public enum ProportionMode {
    
    /**
     * Scales the content so its width perfectly matches the container's width.
     * The height is scaled proportionally, which may cause it to overflow or underflow the container's height.
     */
    FIT_HORIZONTAL,
    
    /**
     * Scales the content so its height perfectly matches the container's height.
     * The width is scaled proportionally, which may cause it to overflow or underflow the container's width.
     */
    FIT_VERTICAL,
    
    /**
     * Scales the content as large as possible without cropping.
     * It ensures the entire content is visible within the container, preserving aspect ratio.
     * This often results in letterboxing (empty space on the sides or top/bottom).
     */
    CONTAIN,
    
    /**
     * Scales the content to fill the entire container, preserving aspect ratio.
     * It ensures no empty space remains in the container, but parts of the content
     * may be cropped (overflow) if the aspect ratios do not match exactly.
     */
    COVER
}
