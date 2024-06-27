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
                new Plane(new Point(-2,0,0),new Point(-2,3,5),new Point(-2,0,10)),
                new Tube(1,new Ray(new Point(1,0,0),new Vector(0,0,1))),
                new Cylinder(1.5,new Ray(new Point(-2,0,0),new Vector(0,0,1)),3),
                new Triangle(new Point(8,88,0),new Point(1,2,0),new Point(5,5,0)),
                new Polygon(new Point(-9,0,-1),new Point(-5,0,-1),new Point(-5,0,1),new Point(-9,0,1)));
        List<Point> result = ge.findIntersections(new Ray(new Point(0,0,0),new Vector(1,0,1)));
        assertEquals(2, result.size(), "Wrong number of points");


        // =============== Boundary Values Tests =======================
        //TC01: An empty body collection
        Geometries ge1 = new Geometries();
        List<Point> result1 = ge1.findIntersections(new Ray(new Point(0,0,0),new Vector(1,0,1)));//
        assertEquals(null, result1, "Wrong number of points");


        //TC02: No intersections with any geometry
        Geometries ge2 = new Geometries(
                new Sphere(1,new Point(-3,0,0)),
                new Plane(new Point(-2,0,0),new Point(-2,0,10),new Point(-2,3,5)),
                new Tube(1,new Ray(new Point(-5,0,0),new Vector(0,0,1))),
                new Cylinder(1.5,new Ray(new Point(-2,0,0),new Vector(0,0,1)),3),
                new Triangle(new Point(8,88,0),new Point(1,2,0),new Point(5,5,0)),
                new Polygon(new Point(-9,0,-1),new Point(-5,0,-1),new Point(-5,0,1),new Point(-9,0,1)));
        List<Point> result2 = ge2.findIntersections(new Ray(new Point(0,0,0),new Vector(1,0,1)));
        assertEquals(null, result2, "Wrong number of points");


        //TC03: All the geometries intersect
        Geometries ge3 = new Geometries(
                new Sphere(1,new Point(1,0,0)),//
                new Plane(new Point(4,0,10),new Point(4,3,10),new Point(4,0,0)),//
                new Tube(1,new Ray(new Point(1,0,0),new Vector(0,0,1))),//
                new Cylinder(1.5,new Ray(new Point(0,0,0),new Vector(0,0,1)),30),//
                new Triangle(new Point(2,-3,0),new Point(2,0,16),new Point(2,2,0)),//
                new Polygon(new Point(4,-2,-3),new Point(4,2,3),new Point(4,2,37),new Point(4,-2,37)));
        List<Point> result3 = ge.findIntersections(new Ray(new Point(0,0,0),new Vector(1,0,1)));
        assertEquals(2, result3.size(), "Wrong number of points");

    }
}