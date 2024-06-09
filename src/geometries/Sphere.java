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
        Point P0 = ray.getHead();
        Vector L = ray.getDirection();
        Vector L0 = P0.subtract(center);

        double a = L.dotProduct(L);
        double b = 2 * L.dotProduct(L0);
        double c = L0.dotProduct(L0) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return intersections; // No intersection
        } else if (discriminant == 0) {
            double t = -b / (2 * a);
            if (t >= 0) {
                intersections.add(P0.add(L.scale(t)));
            }
        } else {
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            if (t1 >= 0) {
                intersections.add(P0.add(L.scale(t1)));
            }
            if (t2 >= 0) {
                intersections.add(P0.add(L.scale(t2)));
            }
        }
        if (intersections.isEmpty()) {
            return null;
        }
        return intersections;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}



