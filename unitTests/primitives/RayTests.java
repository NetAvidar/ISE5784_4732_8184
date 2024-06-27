package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        // ============ Equivalence Partitions Tests ==============
        Ray r=new Ray(new Point(0,0,0),new Vector(0,0,2));

        //case1-closes point in the middele of the list
        List<Point> l1=new ArrayList<>();
        Point p = new Point (0,0,1);

        l1.add(new Point (7,2,3));
        l1.add(new Point(6,4,5));
        l1.add(p);
        l1.add(new Point(8,9,10));

        assertEquals(p,r.findClosestPoint(l1),"fail- didnt return the closes point that was in the middele of the list");

        // =============== Boundary Values Tests ==================
        //case2-Empty list
        List<Point> l2=new ArrayList<>();
        assertEquals(null,r.findClosestPoint(l2),"fail- didnt return null on empty list");


        //case3-closes point in the head of the list
        List<Point> l3=new ArrayList<>();
        Point p3 = new Point (0,0,1);

        l3.add(p3);
        l3.add(new Point (7,2,3));
        l3.add(new Point(6,4,5));
        l3.add(new Point(8,9,10));

        assertEquals(p3,r.findClosestPoint(l1),"fail- didnt return the closes point that was in the head of the list");


        //case4-closes point in the end of the list
        List<Point> l4=new ArrayList<>();
        Point p4 = new Point (0,0,1);

        l4.add(new Point (7,2,3));
        l4.add(new Point(6,4,5));
        l4.add(new Point(8,9,10));
        l4.add(p4);

        assertEquals(p4,r.findClosestPoint(l1),"fail- didnt return the closes point that was in the end of the list");
    }
}