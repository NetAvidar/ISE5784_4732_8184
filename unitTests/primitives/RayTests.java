package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Point class
 * @author Hila & Neta
 */

class RayTests {
    /* Test method for{} .*/

    @Test
    public void testConstructor() {

        // ============ Equivalence Partitions Tests ==============
        assertDoesNotThrow(() -> new Ray(new Point(1,2,3),
                        new Vector(4,5,6)),
                "Failed constructing a correct ray");

        // =============== Boundary Values Tests ==================




    }

    @Test
    public void testFindClosestPoint()
    {

    }
}