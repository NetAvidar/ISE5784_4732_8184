package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {
    @Test
    public void testFindIntsersections(){
        // ============ Equivalence Partitions Tests ==============
        Geometries ge = new Geometries(
                new Sphere(2,new Point(1,1,1)),
                new Plane(new Point(3,4,5),new Vector(3,3,3)),
                new Tube(1,new Ray(new Point(2,3,1),new Vector(2,2,2))),
                new Cylinder(3,new Ray(new Point(2,5,6),new Vector(1,0,2)),5),
                new Triangle(new Point(8,88,8),new Point(1,0,3),new Point(5,5,5)),
                new Polygon(new Point(1,1,1),new Point(9,0,2),new Point(5,7,8),new Point(2,2,2)));
        List<Point> result = ge.findIntersections(new Ray(new Point(1,7,4),new Vector(2,5,6)));

        assertEquals(7, result.size(), "Wrong number of points");




        // =============== Boundary Values Tests =======================

    }

}