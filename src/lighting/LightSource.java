package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * Interface representing a light source in a 3D environment.
 * Provides methods to obtain the intensity of the light, the direction vector of the light,
 * the distance from the light source to a point, and a list of light direction vectors.
 */
public interface LightSource {

    /**
     * Gets the intensity of the light at a specific point.
     *
     * @param p The point at which the intensity is to be calculated.
     * @return The color representing the intensity of the light at the given point.
     */
    public Color getIntensity(Point p);

    /**
     * Gets the direction vector of the light from a specific point.
     *
     * @param p The point from which the direction vector of the light is to be calculated.
     * @return The direction vector of the light at the given point.
     */
    public Vector getL(Point p);

    /**
     * Gets the distance from the light source to a specific point.
     *
     * @param p The point at which the distance is to be measured.
     * @return The distance between the light source and the given point.
     */
    public double getDistance(Point p);

    /**
     * Gets a list of direction vectors of the light from a specific point.
     *
     * @param p The point from which the direction vectors of the light are to be calculated.
     * @param numVectors The number of direction vectors to return.
     * @return A list of direction vectors of the light at the given point.
     */
    public List<Vector> getListL(Point p, int numVectors);

    // Optionally, this method could be implemented to return a circle of direction vectors around a point
    // public List<Vector> getCircle(Point p, double r, int amount);
}
