package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    private final double DELTA = 0.000001;

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

}