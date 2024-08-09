package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.isZero;
import java.util.Random;

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

    public PointLight setRadius(double r) {
        radius = r;
        return this;
    }
    //getter
    @Override
    public Color getIntensity(Point p) {
        return getIntensity().scale(1/getDenominatorLight(p));
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

    // for random number of rays to create for soft shadows
    private static final Random RND = new Random();

   // @Override
    public List<Vector> getLCircle(Point p, double r, int amount) {
        if (p.equals(position)) {
            return null;
        }

        List<Vector> result = new LinkedList<>();

        Vector l = getL(p); // vector to the center of the point light
        result.add(l);

        if (amount < 2) {
            return result;
        }

        Vector vAcross;
        // if l is parallel to z axis, then the normal is across z on x-axis
        if (isZero(l.getXyz().getD1()) && isZero(l.getXyz().getD2())) {
            // switch z and x places
            vAcross = new Vector(0, 0, -1 * l.getXyz().getD3()).normalize();
        } else { // otherwise get the normal using x and y
            // switched x and y places
            vAcross = new Vector(l.getXyz().getD1(), -1 * l.getXyz().getD2(), 0).normalize();
        }

        // the vector to the other direction
        Vector vForward = vAcross.crossProduct(l).normalize();

        double cosAngle, sinAngle, moveX, moveY, d;

        for (int i = 0; i < amount; i++) {
            // Random cosine of angle between (-1, 1)
            cosAngle = 2 * RND.nextDouble() - 1;

            // sin(angle) = sqrt(1 - cos^2(angle))
            sinAngle = Math.sqrt(1 - cosAngle * cosAngle);

            // d is a random distance between (-r, r)
            d = r * (2 * RND.nextDouble() - 1);

            // If d is zero, retry to avoid duplicating the center point
            if (isZero(d)) {
                i--;
                continue;
            }

            // Calculate the random point around the position
            moveX = vAcross.scale(cosAngle * d).getXyz().getD1();
            moveY = vForward.scale(sinAngle * d).getXyz().getD2();
            Point movedPoint = position.add(new Vector(moveX, moveY, 0));

            // Add the vector from point p to this moved point
            result.add(movedPoint.subtract(p));
        }

        return result;
    }

}









