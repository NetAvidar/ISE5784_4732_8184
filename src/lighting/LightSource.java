package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

public interface LightSource {

    public Color getIntensity(Point p);
    public Vector getL (Point p);
    public double getDistance(Point p);
    public List<Vector> getListL(Point p);

 //   public List <Vector> getCircle(Point p, double r, int amount);
}