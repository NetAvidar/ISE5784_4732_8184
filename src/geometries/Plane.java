package geometries;

import primitives.*;

import java.util.List;


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

    public Point getQ() {
        return q;
    }


    @Override
    public List<Point> findIntersections(Ray ray){

        double t;
        Point p = new Point (0,0,0);
        Point p0=ray.getHead();
        Vector v=ray.getDirection();
        Vector n = this.getNormal();
        Point q = this.getQ();

        Vector a =q.subtract(p0);
        t = (n.dotProduct(a))/(n.dotProduct(v));
        if(t>0){
            p=p0.add(v.scale(t));
        }
        else{
            throw new IllegalArgumentException("t has to be bigger then zero");
        }
        final var l = List.of(p);
        return  l;
    }

    public Point getQ() {
        return q;
    }

}

