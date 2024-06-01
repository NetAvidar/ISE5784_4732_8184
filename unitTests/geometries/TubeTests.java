package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Hila & Neta
 */

class TubeTests {
    /*** Test method for{} .*/

    @Test
    public void testConstructor()
    {
    // ============ Equivalence Partitions Tests ==============
        assertDoesNotThrow(()->new Tube(3, new Ray(new Point(2,2,2), new Vector(2,2,2))),
                "Failed constructing a correct Tube");
    }



    @Test
    public void testGetNormal() {
    // ============ Equivalence Partitions Tests ==============
       Tube t =  new Tube(3, new Ray(new Point(2,2,2), new Vector(2,2,2)));

        // ensure there are no exceptions
        assertDoesNotThrow(() -> t.getNormal(new Point(0, 0, 1)), "");

    // =============== Boundary Values Tests ==================

        Tube l =  new Tube(3, new Ray(new Point(0,0,0), new Vector(0,0,1)));
        assertDoesNotThrow(() -> l.getNormal(new Point(0, 1, 0)), "");

    }

}