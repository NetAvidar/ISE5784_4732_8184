package geometries;

import primitives.*;

public class Plane implements Geometry {
    private final Point q;
    private  final Vector normal;

    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    public Plane(Point v0, Point v1, Point v2) {
        q = v0;
        normal = null;
    }

    public Vector getNormal() {
        return  normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
