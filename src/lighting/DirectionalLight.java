package lighting;

import primitives.*;
import java.util.List;

/**
 * Represents a directional light source.
 * Directional light sources have a constant direction and do not have a specific position.
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * Constructs a DirectionalLight with the given intensity and direction.
     *
     * @param intensity the color intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    /**
     * Returns the intensity of the light at a given point.
     * For directional lights, the intensity is constant regardless of the point.
     *
     * @param p the point at which the intensity is queried (not used in this implementation)
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    /**
     * Returns the normalized direction vector of the light.
     *
     * @param p the point at which the direction is queried (not used in this implementation)
     * @return the normalized direction vector of the light
     */
    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    /**
     * Returns the distance from the light to a given point.
     * For directional lights, the distance is considered infinite as the light does not have a specific position.
     *
     * @param point the point at which the distance is queried (not used in this implementation)
     * @return the distance from the light to the point (positive infinity)
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a list containing the direction vector of the light.
     * This method is used to accommodate interfaces that expect a list of vectors.
     *
     * @param p the point at which the direction is queried (not used in this implementation)
     * @param num the number of vectors to return (not used in this implementation)
     * @return a list containing the direction vector of the light
     */
    @Override
    public List<Vector> getListL(Point p, int num) {
        return List.of(getL(p));
    }
}
