package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source.
 * This class holds the intensity of the light, which is represented by a {@link Color}.
 */
abstract class Light {

    /**
     * The color intensity of the light.
     */
    protected Color intensity;

    /**
     * Constructs a new Light object with the specified intensity.
     *
     * @param intensity the color intensity of the light
     */
    public Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return the color intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
