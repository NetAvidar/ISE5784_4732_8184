package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static primitives.Util.alignZero;

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
    protected List <GeoPoint> findGeoIntersectionsHelper(Ray ray){
        List<GeoPoint> intersections = new ArrayList<>();
        Point p0=ray.getHead();
        Point o = this.center;
        Vector v=ray.getDirection().normalize(); //todo: that the only thing that diffrent
        Vector u = o.subtract(p0);                // Vector from ray origin to sphere center
        double tm = alignZero(u.dotProduct(v));   // Projection of u onto ray direction
        double dSquared = u.lengthSquared() - tm * tm; // Perpendicular distance squared
        double d = alignZero(Math.pow(dSquared, 0.5)); // Perpendicular distance (handled for precision)

        if (d >= this.radius) {
            return null; // No intersection if distance is greater than or equal to radius
        }

        double th = alignZero(Math.sqrt(this.radius * this.radius - dSquared)); // Distance from closest point to intersection points
        double t1 = alignZero(tm - th); // First intersection distance along the ray
        double t2 = alignZero(tm + th); // Second intersection distance along the ray

        if (t1 < 0 && t2 < 0) { // No intersection if both distances are negative
            return intersections;
        }
        if (t1 > 0) {
            GeoPoint p1 = new GeoPoint(this, ray.getHead().add(ray.getDirection().scale(t1)));
            intersections.add(p1);
        }
        if (t2 > 0 && t1!=t2) {
            GeoPoint p2 = new GeoPoint(this, ray.getHead().add(ray.getDirection().scale(t2)));
            intersections.add(p2);
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