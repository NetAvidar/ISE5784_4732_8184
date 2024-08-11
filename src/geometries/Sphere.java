package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static primitives.Util.alignZero;

/**
 * Sphere class represents a sphere in 3D space, defined by a center point and radius.
 */
public class Sphere extends RadialGeometry {

    // The center point of the sphere
    private final Point center;

    /**
     * Constructor for creating a sphere.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return The center point.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Finds the intersection points (if any) of a ray with the sphere.
     * This method helps to calculate the geometric intersections with the sphere.
     *
     * @param ray The ray to check for intersections with the sphere.
     * @return A list of GeoPoints representing the intersection points. If no intersections are found, returns null.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new ArrayList<>();
        Point p0 = ray.getHead();                    // The origin point of the ray
        Point o = this.center;                       // The center of the sphere
        Vector v = ray.getDirection().normalize();   // The normalized direction vector of the ray
        Vector u = o.subtract(p0);                   // Vector from ray origin to sphere center
        double tm = alignZero(u.dotProduct(v));      // Projection of u onto ray direction
        double dSquared = u.lengthSquared() - tm * tm; // Perpendicular distance squared from the ray to the sphere center
        double d = alignZero(Math.pow(dSquared, 0.5)); // Perpendicular distance (handled for precision)

        // If the perpendicular distance is greater than or equal to the radius, no intersection occurs
        if (d >= this.radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(this.radius * this.radius - dSquared)); // Distance from the closest point to intersection points
        double t1 = alignZero(tm - th); // First intersection distance along the ray
        double t2 = alignZero(tm + th); // Second intersection distance along the ray

        // No intersection if both distances are negative (intersection points are behind the ray origin)
        if (t1 < 0 && t2 < 0) {
            return intersections;
        }

        // Add the first intersection point if it exists in front of the ray origin
        if (t1 > 0) {
            GeoPoint p1 = new GeoPoint(this, ray.getHead().add(ray.getDirection().scale(t1)));
            intersections.add(p1);
        }

        // Add the second intersection point if it exists in front of the ray origin and is distinct from the first
        if (t2 > 0 && t1 != t2) {
            GeoPoint p2 = new GeoPoint(this, ray.getHead().add(ray.getDirection().scale(t2)));
            intersections.add(p2);
        }

        // If no valid intersections were added, return null
        if (intersections.isEmpty()) {
            return null;
        }

        return intersections;
    }

    /**
     * Calculates the normal vector to the sphere at a given point.
     *
     * @param point The point on the sphere's surface where the normal is to be calculated.
     * @return The normalized normal vector at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}
