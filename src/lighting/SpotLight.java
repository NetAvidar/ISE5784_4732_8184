package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.max;
import static primitives.Util.alignZero;

public class SpotLight extends PointLight{

    private Vector direction;

     //constructor
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    //setter
    public SpotLight setKc(double kC) { //todo: is that what they asked for?
        super.setKc(kC);
        return this;
    }
    public SpotLight setKl(double kL) {//todo: is that what they asked for?
        super.setKl(kL);
        return this;
    }
    public SpotLight setKq(double kQ) {//todo: is that what they asked for?
        super.setKq(kQ);
        return this;
    }
    //getter
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(max(0,alignZero(direction.dotProduct(getL(p).normalize()))));
    }
    @Override
    public Vector getL (Point p) {
        return super.getL(p);
    }
    @Override
    public double getDistance(Point point) {
        return super.getDistance(point);
    }


    public List<Vector> getListL(Point p) {
        return super.getListL(p);
    }

    }


