package geometries;
import primitives.*;

public class Tube extends  RadialGeometry {


    protected Ray axis;


    public Tube(double radius, Ray ax) {
        super(radius);
        this.axis =ax;
    }

    @Override
    public Vector getNormal(Point p) {
        Vector v = p.subtract(axis.getHead());
        double t = v.dotProduct(axis.getDirection());
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        Vector normal = p.subtract(o).normalize();
        return normal;
    }
}


