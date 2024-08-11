package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.isZero;
import java.util.Random;

/**
 * Represents a point light source with customizable attenuation coefficients and radius.
 * This light source affects objects based on their distance from the light and attenuation parameters.
 */
public class PointLight extends Light implements LightSource {

    /** Position of the point light in the 3D space */
    final Point position;
    /** Quadratic attenuation coefficient */
    private double kQ = 0d;
    /** Linear attenuation coefficient */
    private double kL = 0d;
    /** Constant attenuation coefficient */
    private double kC = 1d;
    /** Radius within which the light is effective */
    private double radius = 0;

    /**
     * Constructs a PointLight with specified intensity, position, and radius.
     *
     * @param intensity The intensity of the light
     * @param position The position of the light in the 3D space
     * @param r The radius within which the light is effective
     */
    public PointLight(Color intensity, Point position, double r) {
        super(intensity);
        this.position = position;
        this.radius = r;
    }

    /**
     * Constructs a PointLight with specified intensity and position. The radius defaults to 0.
     *
     * @param intensity The intensity of the light
     * @param position The position of the light in the 3D space
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation coefficient.
     *
     * @param kC The constant attenuation coefficient
     * @return The current PointLight instance
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation coefficient.
     *
     * @param kL The linear attenuation coefficient
     * @return The current PointLight instance
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation coefficient.
     *
     * @param kQ The quadratic attenuation coefficient
     * @return The current PointLight instance
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Sets the radius within which the light is effective.
     *
     * @param r The radius within which the light is effective
     * @return The current PointLight instance
     */
    public PointLight setRadius(double r) {
        this.radius = r;
        return this;
    }

    /**
     * Gets the intensity of the light at a specific point, taking into account attenuation.
     *
     * @param p The point where the intensity is measured
     * @return The intensity of the light at the point
     */
    @Override
    public Color getIntensity(Point p) {
        return getIntensity().scale(1 / getDenominatorLight(p));
    }

    /**
     * Computes the denominator used in the light attenuation calculation.
     *
     * @param p The point from which the light intensity is being measured
     * @return The computed denominator value
     */
    protected double getDenominatorLight(Point p) {
        double distance = p.distance(position);
        return kC + kL * distance + kQ * distance * distance;
    }

    /**
     * Gets the position of the light.
     *
     * @return The position of the light
     */
    private Point getPosition() {
        return position;
    }

    /**
     * Calculates the distance between a given point and the light's position.
     *
     * @param point The point for which the distance is calculated
     * @return The distance from the point to the light's position
     */
    public double getDistance(Point point) {
        return point.distance(this.position);
    }

    /**
     * Computes the normalized vector from the light source to a given point.
     *
     * @param p The point to which the vector is calculated
     * @return The normalized vector from the light to the point
     */
    @Override
    public Vector getL(Point p) {
        return (p.subtract(this.getPosition())).normalize();
    }

    /**
     * Generates a list of vectors from the point to multiple scattered points around the light's position.
     * This is used to create a soft shadow effect.
     *
     * @param p The point from which the vectors are calculated
     * @param numVectors The number of vectors to generate
     * @return A list of vectors from the point to scattered positions around the light
     */
    public List<Vector> getListL(Point p, int numVectors) {
        List<Vector> vectors = new LinkedList<>();
        double stepSize = radius / Math.sqrt(numVectors); // Adjust step size based on desired number of vectors

        for (double i = -radius; i < radius; i += stepSize) {
            for (double j = -radius; j < radius; j += stepSize) {
                if (i != 0 && j != 0) {
                    Point point = position.add(new Vector(i, 0.1d, j)); //0.1 == DELTE
                    if (point.equals(position)) {
                        vectors.add(p.subtract(point).normalize());
                    } else {
                        try {
                            if (point.subtract(position).dotProduct(point.subtract(position)) <= radius * radius) { //if the point inside the circle
                                vectors.add(p.subtract(point).normalize());
                            }
                        } catch (Exception e) {
                            vectors.add(p.subtract(point).normalize());
                        }
                    }
                }
            }
        }

        vectors.add(getL(p));
        return vectors;
    }

    /** Random number generator for generating random vectors */
    private static final Random RND = new Random();

    /**
     * Generates a list of random vectors around the light's position, forming a circle.
     * Useful for creating soft shadows with varying light rays.
     *
     * @param p The point from which the vectors are calculated
     * @param r The radius of the circle around the light source
     * @param amount The number of random vectors to generate
     * @return A list of vectors from the point to random positions around the light source
     */
    public List<Vector> getLCircle(Point p, double r, int amount) {
        if (p.equals(position)) {
            return null;
        }

        List<Vector> result = new LinkedList<>();

        Vector l = getL(p); // Vector to the center of the point light
        result.add(l);

        if (amount < 2) {
            return result;
        }

        Vector vAcross;
        // If l is parallel to the z-axis, then the normal is across z on the x-axis
        if (isZero(l.getXyz().getD1()) && isZero(l.getXyz().getD2())) {
            // Switch z and x places
            vAcross = new Vector(0, 0, -1 * l.getXyz().getD3()).normalize();
        } else { // Otherwise, get the normal using x and y
            // Switch x and y places
            vAcross = new Vector(l.getXyz().getD1(), -1 * l.getXyz().getD2(), 0).normalize();
        }

        // The vector in the other direction
        Vector vForward = vAcross.crossProduct(l).normalize();

        double cosAngle, sinAngle, moveX, moveY, d;

        for (int i = 0; i < amount; i++) {
            // Random cosine of angle between (-1, 1)
            cosAngle = 2 * RND.nextDouble() - 1;

            // sin(angle) = sqrt(1 - cos^2(angle))
            sinAngle = Math.sqrt(1 - cosAngle * cosAngle);

            // d is a random distance between (-r, r)
            d = r * (2 * RND.nextDouble() - 1);

            // If d is zero, retry to avoid duplicating the center point
            if (isZero(d)) {
                i--;
                continue;
            }

            // Calculate the random point around the position
            moveX = vAcross.scale(cosAngle * d).getXyz().getD1();
            moveY = vForward.scale(sinAngle * d).getXyz().getD2();
            Point movedPoint = position.add(new Vector(moveX, moveY, 0));

            // Add the vector from point p to this moved point
            result.add(movedPoint.subtract(p));
        }

        return result;
    }
}
