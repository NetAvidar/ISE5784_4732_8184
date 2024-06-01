package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

        // =============== Boundary Values Tests =======================

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