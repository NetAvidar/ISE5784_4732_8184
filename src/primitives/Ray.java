package primitives;

import java.util.ArrayList;
import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * Class representing a ray in 3D space.
 * A ray is defined by a head (starting point) and a direction vector.
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    private static final double DELTA = 0.1;

    /**
     * Constructs a ray with a given head and direction.
     *
     * @param head The starting point of the ray.
     * @param direction The direction vector of the ray, which will be normalized.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * Constructs a ray with a point, normal vector, and direction vector.
     * The head of the ray is slightly moved in the direction of the normal
     * to avoid self-intersections (by a small DELTA value).
     *
     * @param p The starting point of the ray.
     * @param n The normal vector at point p.
     * @param dir The direction vector of the ray, which will be normalized.
     */
    public Ray(Point p, Vector n, Vector dir) {
        this.direction = dir.normalize();
        double nv = n.dotProduct(this.direction);
        Vector delta = n.scale(DELTA);
        if (nv < 0)
            delta = delta.scale(-1d);
        this.head = p.add(delta);
    }

    /**
     * Gets the head (starting point) of the ray.
     *
     * @return The head of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Gets the direction vector of the ray.
     *
     * @return The direction vector of the ray.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Finds the closest point to the ray's head from a list of points.
     *
     * @param points A list of points to search through.
     * @return The closest point to the ray's head, or null if the list is empty or null.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest GeoPoint to the ray's head from a list of GeoPoints.
     *
     * @param lst A list of GeoPoints to search through.
     * @return The closest GeoPoint to the ray's head, or null if the list is empty or null.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> lst) {
        if (lst == null)
            return null;

        GeoPoint curr = lst.getFirst();
        for (GeoPoint gp : lst) {
            if (this.head.distance(gp.point) < this.head.distance(curr.point))
                curr = gp;
        }
        return curr;
    }

    /**
     * Checks if this ray is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the object is a Ray with the same head and direction, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ray r = (Ray) obj;
        return head.equals(r.head) && direction.equals(r.direction);
    }
}
