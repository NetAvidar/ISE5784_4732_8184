package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Hila & Neta
 */

class TriangleTests {
    /*** Test method for{} .*/

    @Test
    public void testConstructor() {
// ============ Equivalence Partitions Tests ==============


// =============== Boundary Values Tests ==================


    }

    @Test
    public void testGetNormal() {
        Triangle a = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        assertDoesNotThrow(() -> a.getNormal(new Point(0, 0, 1)), "Failed constructing a correct triangle");
    }


    public void testFindIntsersections(){

        Triangle triangle = new Triangle (new Point(1,1,1),new Point(1,4,5),new Point(6,2,3));

        final Point p1 = new Point (0,0,0);
        final Vector v1 = new Vector(1,2,1);


        final var         exp1         = List.of(new Point(7/27,14/27,7/17));
        final var         exp3         = List.of();


        // ============ Equivalence Partitions Tests ==============

        //TC01: Ray crosses inside the triangle(1 point)
        final var result1 = triangle.findIntersections(new Ray(p1, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray ##### triangle");

        //outside the triangle
        //TC02: Ray in front of one of its edges (0 point)
        final var result2 = triangle.findIntersections(new Ray(p1, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(0, result1.size(), "Wrong number of points");
        assertEquals(exp3, result2, "Ray not crosses triangle");

        //TC03: Ray in front of one of its vertex (0 point)
        final var result3 = triangle.findIntersections(new Ray(p1, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(0, result1.size(), "Wrong number of points");
        assertEquals(exp3, result3, "Ray not crosses triangle");

        // =============== Boundary Values Tests =======================


        //TC04: ray intersects the triangle on one of its edges (0 point)
        final var result4 = triangle.findIntersections(new Ray(p1, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(0, result1.size(), "Wrong number of points");
        assertEquals(exp3, result4, "Ray not crosses triangle");


        //TC05: ray intersects the triangle on one of its vertex (0 point)
        final var result5 = triangle.findIntersections(new Ray(p1, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(0, result1.size(), "Wrong number of points");
        assertEquals(exp1, result5, "Ray not crosses triangle");


        //TC06: Ray in front of one of its vertex (0 point)
        final var result6 = triangle.findIntersections(new Ray(p1, v1))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p1))).toList();
        assertEquals(0, result1.size(), "Wrong number of points");
        assertEquals(exp3, result6, "Ray not crosses triangle");
    }


}





















