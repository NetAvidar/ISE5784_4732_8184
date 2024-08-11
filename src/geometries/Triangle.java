package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Triangle class represents a triangle in 3D space.
 * It extends the Polygon class.
 */
public class Triangle extends Polygon {

    /**
     * Constructor to initialize a Triangle with given vertices.
     *
     * @param vertices Array of points representing the vertices of the triangle.
     *                 The triangle must have exactly three vertices.
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

    /**
     * Finds the intersection points between the triangle and a given ray.
     *
     * @param ray The ray that is being checked for intersections with the triangle.
     * @return A list of GeoPoint objects where the ray intersects the triangle,
     * or null if there are no intersections.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Find intersection points between the ray and the plane in which the triangle lies
        List<GeoPoint> l = this.plane.findGeoIntersectionsHelper(ray);
        Vector v = ray.getDirection();

        // If there are no intersections with the plane, return null
        if (l == null) {
            return null;
        } else {
            // Get the vertices of the triangle
            Point p0 = this.vertices.getFirst();
            Point p1 = this.vertices.get(1);
            Point p2 = this.vertices.getLast();

            // Calculate vectors from the ray's origin to the triangle's vertices
            Vector v1 = p0.subtract(ray.getHead());
            Vector v2 = p1.subtract(ray.getHead());
            Vector v3 = p2.subtract(ray.getHead());

            // Calculate the normal vectors for each edge of the triangle
            Vector n1 = v1.crossProduct(v2);
            Vector n2 = v2.crossProduct(v3);
            Vector n3 = v3.crossProduct(v1);

            // Calculate the dot products between the ray's direction and the normal vectors
            var a = v.dotProduct(n1);
            var b = v.dotProduct(n2);
            var c = v.dotProduct(n3);

            // Check if the intersection point is inside the triangle
            if ((a > 0 && b > 0 && c > 0) || (a < 0 && b < 0 && c < 0)) {
                // If the point is inside, adjust the geometry reference and return the intersection point
                GeoPoint planeInter = l.getFirst();
                List<GeoPoint> ans = new ArrayList<>();
                planeInter.geometry = this;
                ans.add(planeInter);
                return ans;
            } else {
                // If the point is outside, return null
                return null;
            }
        }
    }
}
