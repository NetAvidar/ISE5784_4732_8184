package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


public class Plane implements Geometry {
    private final Point q;
    private  final Vector normal;

    private final double DELTA = 0.000001;

    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    public Plane(Point p0, Point p1, Point p2) {
        if (p0==null || p1==null || p2==null)
            throw new IllegalArgumentException("A plane cannot have null points");

        if(p0.equals(p1)|| p1.equals(p2)||p2.equals(p0))
            throw new IllegalArgumentException("Can't build a plane with only 2 points");

        q = p0;
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        normal = v1.crossProduct(v2).normalize();
        double len = normal.length();
        if (normal.length() == 0)
            throw new IllegalArgumentException("The points are collinear");

    }

    public Vector getNormal() {
        return  normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

//    @Override
//    public List<Point> findIntersections(Ray ray) {
//        Point p0 = ray.getHead();
//        Vector v = ray.getDirection();
//        Vector n = this.getNormal();
//        Point q0 = this.getQ();
//
//        // (q0 - p0) . n / (v . n)
//        Vector q0_p0 = q0.subtract(p0);
//        double denominator = n.dotProduct(v);
//
//        if (denominator == 0) {
//            // The ray is parallel to the plane
//            return null;
//        }
//
//        double t = n.dotProduct(q0_p0) / denominator;
//
//        if (t > 0) {
//            // Intersection point is in the positive direction of the ray
//            Point intersectionPoint = p0.add(v.scale(t));
//            return List.of(intersectionPoint);
//        } else {
//            // The intersection point is behind the ray's origin
//            return null;
//        }
//    }

    @Override
    public List<Point> findIntersections(Ray ray){
        List<Point> emptyList= List.of();
        double t;
        Point p;
        Point p0=ray.getHead();
        Vector v=ray.getDirection();
        Vector normal = this.getNormal();
        Point q = this.getQ();
        Vector a =q.subtract(p0);

        if(isZero(normal.dotProduct(a))){ //couse ray start on the plane
            return null;
        }
        if(isZero(normal.dotProduct(v))){  //couse ray start on the plane
            return null;
        }
        t = alignZero((normal.dotProduct(a))/(normal.dotProduct(v)));

        if(t>0){
            p=p0.add(v.scale(t));
            final var l = List.of(p);
            return  l;
        }
        else{
          return null;
        }
    }




    public Point getQ() {
        return q;
    }
}

