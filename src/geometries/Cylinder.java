package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The Cylinder class represents a cylinder in 3D space, defined by its radius, height,
 * and an axis represented by a Ray.
 * It extends the Tube class and includes functionality for calculating intersections with the caps.
 */
public class Cylinder extends Tube {

    /** The height of the cylinder */
    private final double height;

    /**
     * Constructs a Cylinder with the specified radius, axis, and height.
     *
     * @param radius the radius of the cylinder
     * @param ax     the axis of the cylinder represented as a Ray
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, Ray ax, double height) {
        super(radius, ax);
        this.height = height;
    }

    /**
     * Returns the normal vector to the cylinder at a given point.
     *
     * @param point the point on the cylinder's surface
     * @return the normal vector at the specified point
     */
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    /**
     * Finds the intersection points between the cylinder and a given ray, considering
     * the bounds of the cylinder's height and the caps.
     *
     * @param ray the ray to intersect with the cylinder
     * @return a list of GeoPoints where the ray intersects the cylinder
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new ArrayList<>();

        // Use the Tube's findIntersections method to get the potential intersection points
        List<GeoPoint> tubeIntersections = super.findGeoIntersectionsHelper(ray);

        // Cylinder base points
        Point p0 = axis.getHead();
        Point p1 = p0.add(axis.getDirection().scale(height));

        for (GeoPoint tubeInterPoint : tubeIntersections) {
            // Check if the tubeInterPoint point is within the bounds of the cylinder
            Vector v0ToP = tubeInterPoint.point.subtract(p0);
            Vector v1ToP = tubeInterPoint.point.subtract(p1);

            double dotProduct0 = v0ToP.dotProduct(axis.getDirection());
            double dotProduct1 = v1ToP.dotProduct(axis.getDirection());

            if (dotProduct0 >= 0 && dotProduct0 <= height && dotProduct1 <= 0 && dotProduct1 >= -height) {
                intersections.add(tubeInterPoint);
            }
        }

        // Check for intersections with the caps of the cylinder
        intersections.addAll(findCapIntersectionsHelper(ray, p0));
        intersections.addAll(findCapIntersectionsHelper(ray, p1));

        return intersections;
    }

    /**
     * Helper method to find the intersection points between the ray and a cylinder cap.
     *
     * @param ray       the ray to intersect with the cap
     * @param capCenter the center of the cap
     * @return a list of GeoPoints where the ray intersects the cap
     */
    private List<GeoPoint> findCapIntersectionsHelper(Ray ray, Point capCenter) {
        List<GeoPoint> capIntersections = new ArrayList<>();

        Vector capNormal = axis.getDirection();
        double denom = capNormal.dotProduct(ray.getDirection());
        if (Math.abs(denom) > 1e-6) { // Ensure the ray is not parallel to the cap
            Vector p0ToCap = capCenter.subtract(ray.getHead());
            double t = p0ToCap.dotProduct(capNormal) / denom;
            if (t >= 0) {
                Point intersection = ray.getHead().add(ray.getDirection().scale(t));
                if (intersection.distance(capCenter) <= radius) {
                    capIntersections.add(new GeoPoint(this, intersection));
                }
            }
        }

        return capIntersections;
    }
}
