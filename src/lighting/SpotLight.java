package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.max;
import static primitives.Util.alignZero;

/**
 * Represents a spotlight light source that extends from a point light source.
 * A spotlight has a direction in which it illuminates and may have an intensity
 * that depends on the angle between the light direction and the vector from the light to the point.
 */
public class SpotLight extends PointLight {

    private Vector direction;

    /**
     * Constructs a SpotLight with specified intensity, position, direction, and radius.
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light source
     * @param direction the direction in which the spotlight is focused
     * @param r         the radius of the spotlight's influence
     */
    public SpotLight(Color intensity, Point position, Vector direction, double r) {
        super(intensity, position, r);
        this.direction = direction.normalize();
    }

    /**
     * Constructs a SpotLight with specified intensity, position, and direction.
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light source
     * @param direction the direction in which the spotlight is focused
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Sets the constant attenuation factor for the spotlight.
     *
     * @param kC the constant attenuation factor
     * @return the current SpotLight instance for method chaining
     */
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    /**
     * Sets the linear attenuation factor for the spotlight.
     *
     * @param kL the linear attenuation factor
     * @return the current SpotLight instance for method chaining
     */
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor for the spotlight.
     *
     * @param kQ the quadratic attenuation factor
     * @return the current SpotLight instance for method chaining
     */
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }

    /**
     * Calculates the intensity of the light at a given point, factoring in the spotlight's direction.
     *
     * @param p the point at which to calculate the intensity
     * @return the intensity of the light at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(max(0, alignZero(direction.dotProduct(getL(p).normalize()))));
    }

    /**
     * Returns the vector from the light source to a given point.
     *
     * @param p the point to which the vector is calculated
     * @return the vector from the light source to the given point
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    /**
     * Returns the distance from the light source to a given point.
     *
     * @param point the point to which the distance is calculated
     * @return the distance from the light source to the given point
     */
    @Override
    public double getDistance(Point point) {
        return super.getDistance(point);
    }

    /**
     * Returns a list of vectors from the light source to a given point, distributed randomly.
     *
     * @param p           the point to which the vectors are calculated
     * @param numVectors  the number of vectors to generate
     * @return a list of vectors from the light source to the given point
     */
    public List<Vector> getListL(Point p, int numVectors) {
        return super.getListL(p, numVectors);
    }
}
