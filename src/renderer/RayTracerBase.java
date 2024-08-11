package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class representing the base functionality for a ray tracer.
 */
public abstract class RayTracerBase {

    /**
     * The scene to be rendered.
     */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the specified scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract method to trace a ray and determine its color based on the scene.
     *
     * @param ray the ray to trace
     * @return the color resulting from tracing the ray
     */
    public abstract Color traceRay(Ray ray);
}
