package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for primitives.Point class
 * @author Hila & Neta
 */
class PlaneTests {
    /*** Test method for{} .*/
    private final double DELTA = 0.000001;

    @Test
   public void testFindIntsersections(){
        // ============ Equivalence Partitions Tests ==============

        Plane plane = new Plane (new Point(1,1,1),new Vector(3,5,7));

        final Point p1 = new Point (2,3,4);
        final Point p2 = new Point (1,1,1);
        final Point p3 = new Point (2,2,2);
        final Point p4 = new Point (-1,-1,-1);

        final Vector v1 = new Vector(1,-1,2);
        final Vector v2 = new Vector(1,-1,1);
        final Vector v3 = new Vector(6,10,14);
        final Vector v4 = new Vector(3,6,-6);
        final Vector v5 = new Vector(5,-7,0);
        final Vector v6 = new Vector(1,0,0);
        final Vector v7 = new Vector(0,7,-5);


        final var         exp1         = List.of(new Point(-5/6,35/6,-5/3));
        final var         exp2         = List.of();
        final var         exp5         = List.of(new Point(-8.5,9.5,-1));



        //TC01: Ray crosses the plane(1 point)
        final var result1 = plane.findIntersections(new Ray(p1, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray crosses plane");

        //TC02: Ray not crosses the plane(0 point)
        final var result2 = plane.findIntersections(new Ray(p1, v2))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(0, result2.size(), "Wrong number of points");
        assertEquals(exp2, result2, "Ray not crosses plane");

        // =============== Boundary Values Tests =======================

        //Ray parallel to the plane
        //TC01: Ray parallel to the plane and included in the plane(0 points)
        final var result3 = plane.findIntersections(new Ray(p2, v3))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p2))).toList();
        assertEquals(0, result3.size(), "Wrong number of points");
        assertEquals(exp2, result3, "Ray not crosses plane");

        //TC02: Ray parallel to the plane and not included in the plane(0 point)
        final var result4 = plane.findIntersections(new Ray(p3, v4))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p3))).toList();
        assertEquals(0, result4.size(), "Wrong number of points");
        assertEquals(exp2, result4, "Ray not crosses plane");

        //Ray orthogonal to the plane
        //TC03: Ray orthogonal to the plane before the plane(1 point)
        final var result5 = plane.findIntersections(new Ray(p4, v5))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p4))).toList();
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(exp5, result5, "Ray crosses plane");

        //TC04: Ray orthogonal to the plane inside the plane(0 point)
        final var result6 = plane.findIntersections(new Ray(p2, v5))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p2))).toList();
        assertEquals(0, result6.size(), "Wrong number of points");
        assertEquals(exp2, result6, "Ray not crosses plane");


        //TC05: Ray orthogonal to the plane after the plane(0 point)
        final var result7 = plane.findIntersections(new Ray(p3, v5))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p3))).toList();
        assertEquals(0, result7.size(), "Wrong number of points");
        assertEquals(exp2, result7, "Ray not crosses plane");

        //TC06: Ray not orthogonal and not parallel to the plane and start at the plane(0 point)
        final var result8 = plane.findIntersections(new Ray(p1, v6))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(0, result8.size(), "Wrong number of points");
        assertEquals(exp2, result8, "Ray not crosses plane");

        //TC07: Ray not orthogonal or parallel to the plane and begins in the
        //same point which appears as reference point in the plane (Q) (0 point)
        final var result9 = plane.findIntersections(new Ray(p1, v7))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(0, result9.size(), "Wrong number of points");
        assertEquals(exp2, result9, "Ray not crosses plane");



    }

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Plane
                (new Point(1, 0, 1),
                        new Point(  2, 2, 2),
                        new Point(3, 3, 3),
                        "Failed constructing a correct polygon"));

    }

    public void testGetNormal() {

        // TC01: There is a simple single test here - using a quad
        Plane p = new Plane
                (new Point(1, 0, 1), new Point(  2, 2, 2), new Point(3, 3, 3),
                        "Failed constructing a correct polygon");
        // ensure there are no exceptions
        assertDoesNotThrow(() -> p.getNormal(new Point(0, 0, 1)), "");
        // generate the test result

        // =============== Boundary Values Tests =======================


        Vector result = p.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edge
    }
}