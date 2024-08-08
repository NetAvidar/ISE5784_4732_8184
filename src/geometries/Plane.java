package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


public class Plane extends Geometry {
    private final Point q;
    private final Vector normal;

    private final double DELTA = 0.000001;

    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    public Plane(Point p0, Point p1, Point p2) {
        if (p0 == null || p1 == null || p2 == null)
            throw new IllegalArgumentException("A plane cannot have null points");

        if (p0.equals(p1) || p1.equals(p2) || p2.equals(p0))
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
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    public Point getQ() {
        return q;
    }



    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Vector normal = this.getNormal();
        Point q = this.getQ();

        double x = normal.dotProduct(q.subtract(p0));
        //couse ray start on the plane
        if (isZero(x)) {
            return null;
        }
        double nv = normal.dotProduct(v);
        //couse ray parpllel to the plane
        if (isZero(nv)) {
            return null;
        }

        double t = alignZero(x / nv);

        if (t <= 0) {
            return null;
        }
        if (t > 0) {
            Point p=p0.add(v.scale(t));
            GeoPoint gp=new GeoPoint(this,p);
            final var l = List.of(gp);
            return l;
        }
        return null;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
}
