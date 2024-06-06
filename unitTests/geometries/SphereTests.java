package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Hila & Neta
 */



class SphereTests {

    private final Point  p001 = new Point(0, 0, 1);
    private final Point  p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    /*** Test method for{} .*/

    @Test
    public void testConstructor() {
// ============ Equivalence Partitions Tests ==============
        assertDoesNotThrow(() -> new Sphere(2, new Point(3,2,1)),
                "Failed constructing a correct Sphere");
    }


    @Test
    public void testGetNormal() {
// ============ Equivalence Partitions Tests ==============
        assertDoesNotThrow(() -> new Sphere(2, new Point(3,2,1)), "Failed constructing a correct Sphere");
        Sphere s = new Sphere(2, new Point(3,2,1));
        assertDoesNotThrow(() -> s.getNormal(new Point(0, 0, 1)), "");

    }
    @Test
    public void testFindIntersections() {
        Sphere            sphere      = new Sphere(1d, p100);

        final Point       gp1         = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point       gp2         = new Point(1.53484692283495, 0.844948974278318, 0);
        final var         exp         = List.of(gp1, gp2);

        final Vector      v310        = new Vector(3, 1, 0);
        final Vector      v110        = new Vector(1, 1, 0);

        final Point       p01         = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============
         //TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        Vector vv=new Vector(0,0,1);
        Point pp = new Point(1,0,0.5);
        final var ex3 = List.of(new Point(1,0,1));

        final var result3 = sphere.findIntersections(new Ray(pp,vv))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(pp))).toList();
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(ex3, result3, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        Point po = new Point(1.5,0,0);
        final var result2 = sphere.findIntersections(new Ray(po, v310))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(po))).toList();
        assertEquals(0, result2.size(), "Wrong number of points");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        Point p11 = new Point(2,0,0);
        final var result11 = sphere.findIntersections(new Ray(p11, new Vector(-0.5,0,0)))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p11))).toList();
        assertEquals(1, result11.size(), "Wrong number of points");
        final var         exp11         = List.of(new Point(0,0,0));
        assertEquals(exp11, result1, "Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)

        Point p12 = new Point(2,0,0);
        final var result12 = sphere.findIntersections(new Ray(p12, new Vector(3,0,0)))
               .stream().sorted(Comparator.comparingDouble(p -> p.distance(p11))).toList();
        assertEquals(0, result11.size(), "Wrong number of points");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Point p13 = new Point(1,1,1);
        final var result13 = sphere.findIntersections(new Ray(p13, new Vector(1,1,1)))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p13))).toList();
        assertEquals(2, result11.size(), "Wrong number of points");
        final var exp13 = List.of(new Point(2.577, 2.577, 2.577),new Point(1.423, 1.423, 1.423));
        assertEquals(exp13, result13, "Ray crosses sphere");


        // TC14: Ray starts at sphere and goes inside (1 points)
        Point p14 = new Point(2,1,0);
        final var result14 = sphere.findIntersections(new Ray(p14, new Vector(-1,-1,0)))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p14))).toList();
       assertEquals(1, result14.size(), "Wrong number of points");
     //  final var exp14 = List.of(new Point(1.293, -0.707, 0) );//todo: fix this bva test

        // TC15: Ray starts inside (1 points)
        // TC16: Ray starts at the center (1 points)
        // TC17: Ray starts at sphere and goes outside (0 points)
        // TC18: Ray starts after sphere (0 points)

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line

    }
}