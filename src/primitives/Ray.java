package primitives;
import geometries.Intersectable.GeoPoint;
import java.util.ArrayList;
import java.util.List;

public class Ray {
    private final Point head;
    private final Vector direction;


    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    public Point getHead() {
        return head;
    }

    public Vector getDirection() {
        return direction;
    }

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> lst){
        if (lst==null)
            return null;
        if(lst.isEmpty())
            return null;

        GeoPoint curr = lst.getFirst();
        for(GeoPoint gp:lst) {
            if (this.head.distance(gp.point)<this.getHead().distance(curr.point))
                curr = gp;
        }
        return curr;
    }


}
