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

//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        //***************************
//        List<Point> intersections = new ArrayList<>();
//        Point p0=ray.getHead();
//        Point o = this.center;
//        Vector v=ray.getDirection();
//
//        Vector u = o.subtract(p0);
//        double tm = v.dotProduct(u);
//        double dSquared = (u.lengthSquared() - tm*tm);
//        double d = pow(dSquared,0.5);
//
//        if (d >= this.radius) {
//            return null;
//        }
//
//        double th = pow(this.radius * this.radius - dSquared,0.5);
//        double t1 = tm + th;
//        double t2 = tm - th;
//
//        if(t1<0 && t2<0){ //no intrection
//            return intersections;
//        }
//        if (t1 > 0) {
//            Point p1 = ray.getHead().add(ray.getDirection().scale(t1));
//            intersections.add(p1);
//        }
//        //if (t2 > 0 && t1 != t2)
//        if (t2 > 0 ) { // Ensure that t1 and t2 are not the same to avoid duplicates
//            Point p2 = ray.getHead().add(ray.getDirection().scale(t2));
//            intersections.add(p2);
//        }
//
//        if(intersections.isEmpty()){
//            return null;
//        }
//        return intersections;
//    }
//
//


    @Override
    protected List <GeoPoint> findGeoIntersectionsHelper(Ray ray){
        List<GeoPoint> intersections = new ArrayList<>();
        Point p0=ray.getHead();
        Point o = this.center;
        Vector v=ray.getDirection();

        Vector u = o.subtract(p0);
        double tm = v.dotProduct(u);
        double dSquared = (u.lengthSquared() - tm*tm);
        double d = pow(dSquared,0.5);

        if (d >= this.radius) {
            return null;
        }

        double th = pow(this.radius * this.radius - dSquared,0.5);
        double t1 = tm + th;
        double t2 = tm - th;

        if(t1<0 && t2<0){ //no intrection
            return intersections;
        }
        if (t1 > 0) {
            GeoPoint p1 = new GeoPoint(this,ray.getHead().add(ray.getDirection().scale(t1)));
            intersections.add(p1);
        }
        //if (t2 > 0 && t1 != t2)
        if (t2 > 0 ) { // Ensure that t1 and t2 are not the same to avoid duplicates
            GeoPoint p2 = new GeoPoint(this,ray.getHead().add(ray.getDirection().scale(t2)));
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