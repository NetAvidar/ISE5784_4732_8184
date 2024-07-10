package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

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
        //*****************************************************************************
        List<Point> intersections = new ArrayList<>();
        Point p0=ray.getHead();
        Point o = this.center;
        Vector v=ray.getDirection();

        Vector u = o.subtract(p0);
        double tm = v.dotProduct(u);
        //double dSquared = pow(u.dotProduct(u) - tm * tm,0.5); // d^2 = ||u||^2 - tm^2
        double dSquared = (u.lengthSquared() - tm*tm); // d^2 = ||u||^2 - tm^2
        double d = pow(dSquared,0.5);

        //*****************************************************************************
//        // Check if the ray is tangent to the sphere (touching at exactly one point).
//        if (Math.abs(dSquared - this.radius * this.radius) < 1E-10) { //no intrection
//            // The ray touches the sphere tangentially, so we return no intersections.
//            return intersections;
//        }
        //*****************************************************************************
        if (d >= this.radius) { //no intrection
            // If d is greater or equles than the radius, there can't be any intersections.
            return null;
        }
        //*****************************************************************************
        // Proceed with calculation since there are two intersection points
        double th = pow(this.radius * this.radius - dSquared,0.5);
        double t1 = tm + th;
        double t2 = tm - th;

        if(t1<0 && t2<0){ //no intrection
            return intersections;
        }
        if (t1 > 0) {
            Point p1 = ray.getHead().add(ray.getDirection().scale(t1));
            intersections.add(p1);
        }
        //if (t2 > 0 && t1 != t2)
        if (t2 > 0 ) { // Ensure that t1 and t2 are not the same to avoid duplicates
            Point p2 = ray.getHead().add(ray.getDirection().scale(t2));
            intersections.add(p2);
        }

        if(intersections.isEmpty()){
            return null;
        }
        return intersections;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}




