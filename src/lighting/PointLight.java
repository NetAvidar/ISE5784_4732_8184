package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    final Point position;
    private double kQ = 0d;
    private double kL = 0d;
    private double kC = 1d;

    //constructor
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }
    //setter
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
    //getter
    @Override
    public Color getIntensity(Point p) {
        return getIntensity().reduce(getDenominatorLight(p));
    }
    protected double getDenominatorLight(Point p) {
        double distance = p.distance(position);
        return kC + kL * distance + kQ * distance * distance;
    }
    private Point getPosition() {
        return position;
    }
    public double getDistance(Point point) {
        return point.distance(this.position);
    }
    @Override
    public Vector getL(Point p) {
        return (p.subtract(this.getPosition())).normalize();
    }



}









