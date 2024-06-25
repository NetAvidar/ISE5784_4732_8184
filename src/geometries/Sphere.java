package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Sphere extends RadialGeometry {

    private final Point center;

    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = new ArrayList<>();
        Vector u = this.center.subtract(ray.getHead());
        double tm = u.dotProduct(ray.getDirection());
        double dSquared = u.dotProduct(u) - tm * tm; // d^2 = ||u||^2 - tm^2

        // Check if the ray is tangent to the sphere (touching at exactly one point).
        if (Math.abs(dSquared - this.radius * this.radius) < 1E-10) {
            // The ray touches the sphere tangentially, so we return no intersections.
            return intersections;  // or you could return null, depending on design choices.
        }

        if (dSquared > this.radius * this.radius) {
            // If d^2 is greater than the radius squared, there can't be any intersections.
            return null;
        }

        // Proceed with calculation since there are two intersection points
        double d = Math.sqrt(dSquared);
        double th = Math.sqrt(this.radius * this.radius - dSquared);
        double t1 = tm + th;
        double t2 = tm - th;

        // Only add points where t > 0 to ensure intersections are in the direction of the ray
        if (t1 > 0) {
            Point p1 = ray.getHead().add(ray.getDirection().scale(t1));
            intersections.add(p1);
        }
        if (t2 > 0 && t1 != t2) { // Ensure that t1 and t2 are not the same to avoid duplicates
            Point p2 = ray.getHead().add(ray.getDirection().scale(t2));
            intersections.add(p2);
        }

        return intersections;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}




