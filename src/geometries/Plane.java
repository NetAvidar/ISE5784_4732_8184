package geometries;

import primitives.*;

public class Plane implements Geometry {
    private final Point q;
    private  final Vector normal;

    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    public Plane(Point p0, Point p1, Point p2) {
        q = p0;
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);

        normal = v1.crossProduct(v2).normalize();
    }

    public Vector getNormal() {
        return  normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
