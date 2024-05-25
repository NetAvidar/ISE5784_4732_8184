package geometries;
import primitives.*;

public class Tube extends  RadialGeometry {


    protected Ray axis;


    public Tube(double radius, Ray ax) {
        super(radius);
        this.axis =ax;
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
