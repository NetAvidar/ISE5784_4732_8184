package geometries;
import primitives.*;

public class Cylinder extends Tube{

    private final double height;

    public Cylinder(double radius, Ray ax, double height) {
        super(radius, ax);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
