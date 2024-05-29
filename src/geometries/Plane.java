package geometries;

import primitives.*;

public class Plane implements Geometry {
    private final Point q;
    private  final Vector normal;

    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    public Plane(Point p0, Point p1, Point p2, String failedConstructingACorrectPolygon) {
        if (p0==null || p1==null || p2==null)
            throw new IllegalArgumentException("A plane cannot have null points");
        q = p0;
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);

        normal = v1.crossProduct(v2).normalize();
        if ( !(this.normal.equals(1))) {
            throw new IllegalArgumentException("normal has to be equale 1");
        }
    }

    public Vector getNormal() {
        return  normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
