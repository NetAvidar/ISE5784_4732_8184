package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Cylinder extends Tube{

    private final double height;


    public Cylinder(double radius, Ray ax, double height) {
        super(radius, ax);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = new ArrayList<>();

        // Use the Tube's findIntersections method to get the potential intersection points
        List<Point> tubeIntersections = super.findIntersections(ray);

        // Cylinder base points
        Point p0 = axis.getHead();
        Point p1 = p0.add(axis.getDirection().scale(height));

        for (Point intersection : tubeIntersections) {
            // Check if the intersection point is within the bounds of the cylinder
            Vector v0ToP = intersection.subtract(p0);
            Vector v1ToP = intersection.subtract(p1);

            double dotProduct0 = v0ToP.dotProduct(axis.getDirection());
            double dotProduct1 = v1ToP.dotProduct(axis.getDirection());

            if (dotProduct0 >= 0 && dotProduct0 <= height && dotProduct1 <= 0 && dotProduct1 >= -height) {
                intersections.add(intersection);
            }
        }

        // Check for intersections with the caps of the cylinder
        intersections.addAll(findCapIntersections(ray, p0));
        intersections.addAll(findCapIntersections(ray, p1));

        return intersections;
    }

    private List<Point> findCapIntersections(Ray ray, Point capCenter) {
        List<Point> capIntersections = new ArrayList<>();

        Vector capNormal = axis.getDirection();
        double denom = capNormal.dotProduct(ray.getDirection());
        if (Math.abs(denom) > 1e-6) { // Ensure the ray is not parallel to the cap
            Vector p0ToCap = capCenter.subtract(ray.getHead());
            double t = p0ToCap.dotProduct(capNormal) / denom;
            if (t >= 0) {
                Point intersection = ray.getHead().add(ray.getDirection().scale(t));
                if (intersection.distance(capCenter) <= radius) {
                    capIntersections.add(intersection);
                }
            }
        }

        return capIntersections;
    }


}
