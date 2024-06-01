package geometries;
import primitives.*;

import java.util.List;

public class Sphere extends RadialGeometry  {

    private final Point center;

    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }
    @Override
    public List<Point> findIntersections(Ray ray){
        return null;
    }
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }


}
