package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents ambient light in a scene.
 * Ambient light is a type of light that is present in the scene uniformly, affecting all objects equally
 * regardless of their position or orientation.
 */
public class AmbientLight extends Light {

    /**
     * The color of the ambient light.
     */
    protected Color Ia;

    /**
     * The attenuation factor of the ambient light.
     */
    protected Double3 Ka;

    /**
     * A constant representing no ambient light (black).
     */
    public final static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs an {@code AmbientLight} with the specified color and attenuation factor.
     *
     * @param ia the color of the ambient light
     * @param ka the attenuation factor of the ambient light, which scales the light intensity
     */
    public AmbientLight(Color ia, Double3 ka) {
        super(ia.scale(ka));
    }

    /**
     * Constructs an {@code AmbientLight} with the specified color and attenuation factor.
     *
     * @param ia the color of the ambient light
     * @param k  the attenuation factor of the ambient light, which scales the light intensity
     */
    public AmbientLight(Color ia, double k) {
        super(ia.scale(k));
    }
}
