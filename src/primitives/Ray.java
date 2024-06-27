package primitives;

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

    public Point findClosestPoint(List<Point> lst)
    {
        if (lst==null)
            return null;
        Point curr = lst.getFirst();
        for(int i =0; i< lst.size();i++)
        {
            if ()
        }
    }
}
