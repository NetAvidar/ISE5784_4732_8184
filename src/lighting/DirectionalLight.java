package lighting;
import primitives.*;
import java.util.List;

public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    //constructor
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }
    //getter
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }
    @Override
    public Vector getL (Point p)
    {
        return direction.normalize();
    }
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public List<Vector> getListL(Point p, int num) {
        return List.of( getL(p));
    }


}