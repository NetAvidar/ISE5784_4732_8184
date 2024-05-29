package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

//todo: what from the tests is Bva and what EP ?


/**
 * Unit tests for primitives.Point class
 * @author Hila & Neta
 */
public class PointTests {

    /*** Test method for{} .*/

    @Test
    public void testAdd() {

// ============ Equivalence Partitions Tests ==============

        Point  p1         = new Point(1, 2, 3);
        Point  p2         = new Point(2, 4, 6);
        Point  p3         = new Point(2, 4, 5);

// Subtract points
        Vector v1         = new Vector(1, 2, 3);
        if (!p2.subtract(p1).equals(v1))
            out.println("ERROR: (point2 - point1) does not work correctly");
        try {
            p1.subtract(p1);
            out.println("ERROR: (point - itself) does not throw an exception");
        } catch (IllegalArgumentException ignore) {} catch (Exception ignore) {
            out.println("ERROR: (point - itself) throws wrong exception");
        }

        // distances
        if (!isZero(p1.distanceSquared(p3) - 9))
            out.println("ERROR: squared distance between points is wrong");
        if (!isZero(p3.distanceSquared(p1) - 9))
            out.println("ERROR: squared distance between points is wrong");
        if (!isZero(p1.distance(p3) - 3))
            out.println("ERROR: distance between points to itself is wrong");
        if (!isZero(p3.distance(p1) - 3))
            out.println("ERROR: distance between points to itself is wrong");

// =============== Boundary Values Tests ==================
// distances
        if (!isZero(p1.distanceSquared(p1)))
            out.println("ERROR: point squared distance to itself is not zero");
        if (!isZero(p1.distance(p1)))
            out.println("ERROR: point distance to itself is not zero");
    }
}