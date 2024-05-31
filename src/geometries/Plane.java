package geometries;

import primitives.*;



public class Plane implements Geometry {
    private final Point q;
    private  final Vector normal;

    private final double DELTA = 0.000001;

    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    public Plane(Point p0, Point p1, Point p2, String failedConstructingACorrectPolygon) {
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
}
