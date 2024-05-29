package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

    @Test
    public void testGetNormal() {
        Triangle a = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        assertDoesNotThrow(() -> a.getNormal(new Point(0, 0, 1)), "Failed constructing a correct triangle");
    }
}