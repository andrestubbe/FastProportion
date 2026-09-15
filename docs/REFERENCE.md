# FastProportion Reference Manual

`FastProportion` is the zero-dependency, aspect-ratio scaling and viewport calculation substrate of the FastJava ecosystem.

---

## 1. Core Vocabulary

* **Container**: The outer bounding box (e.g. window, panel, terminal grid, or viewport) into which content is fitted.
* **Content**: The inner source media (e.g. video frame, texture, sprite, camera stream, or UI element) with intrinsic width and height.
* **ProportionMode**: The scaling policy determining how the content is sized and positioned inside the container while preserving its aspect ratio.
* **Viewport Coordinates**: Computed 4-float bounds `[scaledX, scaledY, scaledWidth, scaledHeight]` indicating draw placement.

---

## 2. Class: `fastproportion.Proportion`

Main calculation context.

### Constructors
- `public Proportion(float width, float height, float contentWidth, float contentHeight)`  
  Initializes scaling parameters for container dimensions (`width`, `height`) and unscaled content dimensions (`contentWidth`, `contentHeight`). Top-left offsets `x` and `y` default to `0.0f`.

### Computation Methods
- `public float[] compute(ProportionMode mode)`  
  Runs the aspect-ratio scaling algorithm and allocates a new `float[4]` containing `[scaledX, scaledY, scaledWidth, scaledHeight]`.

- `public void compute(ProportionMode mode, float[] out)`  
  Zero-allocation variant. Writes `[scaledX, scaledY, scaledWidth, scaledHeight]` directly into the provided pre-allocated array of length $\ge 4$, producing **0 bytes GC allocation**.

---

## 3. Enum: `fastproportion.ProportionMode`

Available scaling strategies:

- **`CONTAIN`**: Scales content so it fits entirely within the container without cropping. Introduces letterboxing/pillarboxing if ratios differ.
- **`COVER`**: Scales content so it completely fills the container. Bleeding edges are cropped to preserve aspect ratio without empty borders.
- **`FIT_HORIZONTAL`**: Scales content such that its width matches container width exactly. Height scales proportionally (may overflow vertically).
- **`FIT_VERTICAL`**: Scales content such that its height matches container height exactly. Width scales proportionally (may overflow horizontally).

---

## 4. Mathematical Guarantees

* **Pure Float Pipeline**: All calculations run in single-precision 32-bit floating point, eliminating cast latency and CPU register thrashing.
* **Deterministic Centering**: Scaled viewports are always centered inside the container geometry (`x + (width - scaledWidth) * 0.5f`).
* **Thread-Safety**: Pure stateless reads during `compute()`, safe for multi-threaded render pipelines.

---

**Part of the FastJava Ecosystem** — *Making the JVM faster.* 🚀