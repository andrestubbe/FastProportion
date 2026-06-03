# FastProportion Reference & Vocabulary

## 1. Core Vocabulary

*   **Container**: The bounding box (e.g., a window, a UI panel, or a screen) where the content needs to be drawn.
*   **Content**: The object being scaled (e.g., an image, a video frame, or a custom UI element).
*   **Proportion Mode**: The scaling strategy that determines how the content fits into the container while preserving its aspect ratio.
*   **Viewport Coordinates**: The final calculated `[x, y, width, height]` array defining exactly where and how large the content should be drawn inside the container.

## 2. API Quick Reference

### `Proportion` (Main Math Class)
*   `new Proportion(float containerWidth, float containerHeight, float contentWidth, float contentHeight)`: Initializes the math context.
*   `compute(ProportionMode mode)`: The core pure function. It runs the scaling algorithm and returns a new `float[4]` containing `[x, y, width, height]`.
*   *(Fields)* `x`, `y`: Offset of the container itself. Defaults to `0.0f`. Adjust these before calling `compute()` if your container is not at `(0,0)`.

### `ProportionMode` (Scaling Strategies)
*   `CONTAIN`: Scales the content up or down so that it fits entirely within the container. If the aspect ratios don't match, this creates letterboxing (empty space on the sides or top/bottom).
*   `COVER`: Scales the content so that it fills the entire container. If the aspect ratios don't match, this will crop/clip the content that bleeds outside the container bounds.
*   `FIT_HORIZONTAL`: Forces the content width to match the container width, scaling the height proportionally. The content may overflow vertically.
*   `FIT_VERTICAL`: Forces the content height to match the container height, scaling the width proportionally. The content may overflow horizontally.

## 3. Math Guarantees & Contracts

*   **Pure Float Pipeline**: Every step of the math calculation uses 32-bit floats. There are no implicit `double` conversions, preventing cast performance penalties.
*   **Zero Side Effects**: `compute()` reads from the `Proportion` instance but never writes to it. You can safely call it from multiple threads as long as you aren't mutating the `Proportion` fields concurrently.
*   **Deterministic Anchoring**: By default, FastProportion always anchors the scaled content to the absolute center of the container. 

---
**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*
