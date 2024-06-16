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
                new Sphere(1,new Point(1,0,0)),//
                new Plane(new Point(-2,0,0),new Point(-2,0,10),new Point(-2,3,5)),//
                new Tube(1,new Ray(new Point(1,0,0),new Vector(0,0,1))),//
                new Cylinder(1.5,new Ray(new Point(-2,0,0),new Vector(1,0,0)),3),//
                new Triangle(new Point(8,88,0),new Point(1,2,0),new Point(5,5,0)),//
                new Polygon(new Point(-9,0,-1),new Point(-5,0,-1),new Point(-5,0,1),new Point(-9,0,1)));//
        List<Point> result = ge.findIntersections(new Ray(new Point(0,0,0),new Vector(1,0,1)));//
        Tube t = new Tube(1,new Ray(new Point(1,0,0),new Vector(0,0,1)));
        List<Point> help =  t.findIntersections(new Ray(new Point(0,0,0),new Vector(1,0,1)));
        assertEquals(1, help.size(), "Wrong number of points");
        Cylinder c = new Cylinder(1.5,new Ray(new Point(-2,0,0),new Vector(1,0,0)),3);
        List <Point> help2 = c.findIntersections(new Ray(new Point(0,0,0),new Vector(1,0,1)));
        assertEquals(1, help2.size(), "Wrong number of points");
        assertEquals(null, result, "Wrong number of points");



        // =============== Boundary Values Tests =======================

    }

}