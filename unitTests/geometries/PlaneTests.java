package geometries;


import primitives.Point;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PlaneTest {

    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Plane
                (new Point(0, 0, 1),
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                "Failed constructing a correct polygon"));

    }


}