package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

public class PointLight extends Light implements LightSource{

    final Point position;
    private double kQ = 0d;
    private double kL = 0d;
    private double kC = 1d;
    private double radius = 0d;

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

    public List<Vector> getListL(Point p) {
        List<Vector> vectors = new LinkedList();
        //grid of vectors around the light
        for (double i = -radius; i < radius; i += radius / 10) {
            for (double j = -radius; j < radius; j += radius / 10) {
                if (i != 0 && j != 0) {
                    //create a point on the grid
                    Point point = position.add(new Vector(i, 0.1d, j));
                    if (point.equals(position)) {
                        //if the point is the same as the light position,
                        // add the vector from the point to the light
                        vectors.add(p.subtract(point).normalize());
                    } else {
                        try {
                            if (point.subtract(position).dotProduct(point.subtract(position))
                                    <= radius * radius) {
                                //if the point is in the radius of the light, add the vector from the point to the light
                                vectors.add(p.subtract(point).normalize());
                            }
                        } catch (Exception e) {
                            //if the point is in the radius of the light, add the vector from the point to the light
                            vectors.add(p.subtract(point).normalize());
                        }

                    }
                }

            }
        }
        vectors.add(getL(p));
        return vectors;

    }

}









