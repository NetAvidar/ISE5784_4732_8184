package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = new ArrayList<>();

        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        Point pa = axis.getHead();
        Vector va = axis.getDirection();

        Vector deltaP = p0.subtract(pa);

        Vector vCrossVa = v.crossProduct(va);
        Vector deltaPCrossVa = deltaP.crossProduct(va);

        double a = vCrossVa.dotProduct(vCrossVa);
        double b = 2 * vCrossVa.dotProduct(deltaPCrossVa);
        double c = deltaPCrossVa.dotProduct(deltaPCrossVa) - (radius * radius) * va.dotProduct(va);

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return intersections; // No intersections
        }

        double sqrtDiscriminant = Math.sqrt(discriminant);

        double t1 = (-b + sqrtDiscriminant) / (2 * a);
        double t2 = (-b - sqrtDiscriminant) / (2 * a);

        if (t1 >= 0) {
            intersections.add(p0.add(v.scale(t1)));
        }
        if (t2 >= 0) {
            intersections.add(p0.add(v.scale(t2)));
        }

        return intersections;
    }


}


