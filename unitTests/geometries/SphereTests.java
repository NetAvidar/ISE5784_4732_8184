package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for primitives.Point class
 * @author Hila & Neta
 */

class SphereTests {
    /*** Test method for{} .*/

    @Test
    public void testConstructor() {
// ============ Equivalence Partitions Tests ==============
        assertDoesNotThrow(() -> new Sphere(2, new Point(3,2,1),
                "Failed constructing a correct Sphere"));
    }


    @Test
    public void testGetNormal() {
// ============ Equivalence Partitions Tests ==============

        Sphere s = new Sphere(2, new Point(3,2,1),
                "Failed constructing a correct Sphere");
        assertDoesNotThrow(() -> s.getNormal(new Point(0, 0, 1)), "");

    }
}