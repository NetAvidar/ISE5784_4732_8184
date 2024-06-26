package renderer;

import geometries.Geometry;
import geometries.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import primitives.*;

import java.util.List;

public class IntegrationTesting {

    //**************************************************************
    int result=0;
    int result1=0;
    int result2=0;
    int result3=0;
    int result4=0;
    int result5=0;

    Camera c=new Camera.Builder()
            .setDirection(new Vector(0,1,0),new Vector(0,0,1))
            .setVpSize(6,6)
            .setVpDistance(1)
            .build();
    //**************************************************************

    int func1(Camera c, Sphere sphere) { //get camera and sphere and return num of intresction from every ray in the view plane
        List<Point> l;
        Ray r;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                r = c.constructRay(3, 3, j, i);
                l = sphere.findIntersections(r);
                result += l.size();
            }
        }
        return result;
    }
    int func2(Camera c, Plane plane){ //get camera and plane and return num of intresction from every ray in the view plane
        List<Point> l;
        Ray r;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                r = c.constructRay(3, 3, j, i);
                l = plane.findIntersections(r); // here is the only differnt from func1
                result += l.size();
            }
        }
        return result;
    }
    int func3(Camera c, Triangle triangle) {//get camera and triangle and return num of intresction from every ray in the view plane
        List<Point> l;
        Ray r;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                r = c.constructRay(3, 3, j, i);
                l = triangle.findIntersections(r); // here is the only differnt from func2
                result += l.size();
            }
        }
        return result;
    }

    //**************************************************************

    @Test
    public void IntegrationSphere(){

        ///////////////////////////view plane 3*3/////////////////////////////

        //case1 - camera outside the sphere & view plane bigger
        c.setLocation(new Point(0,0,0));
        Sphere sphere1= new Sphere(1,new Point(0,0,-3));
        result1 += func1(c,sphere1);
        assertEquals(2, result1, "Wrong number of points");

        //case2 - camera outside the sphere & view plane smaller
        c.setLocation(new Point(0,0,0.5));
        Sphere sphere2= new Sphere(2.5,new Point(0,0,-2.5));
        result2 += func1(c,sphere2);
        assertEquals(18, result2, "Wrong number of points");

        //case3 - camera outside the sphere & view plane bigger
        c.setLocation(new Point(0,0,0.5));
        Sphere sphere3= new Sphere(2,new Point(0,0,-2));
        result3 += func1(c,sphere3);
        assertEquals(10, result3, "Wrong number of points");

        //case4 - camera inside the sphere & view plane smaller
        c.setLocation(new Point(1,1,0.5));
        Sphere sphere4= new Sphere(4,new Point(1,1,1));
        result4 += func1(c,sphere4);
        assertEquals(9, result4, "Wrong number of points");

        //case5 - camera outside the sphere & sphere behind the camera
        c.setLocation(new Point(0,0,0.5));
        Sphere sphere5= new Sphere(0.5,new Point(0,0,1));
        result5 += func1(c,sphere5);
        assertEquals(0, result5, "Wrong number of points");


        result=0;
        result1=0;
        result2=0;
        result3=0;
        result4=0;
        result5=0;
    }

 //**************************************************************
    @Test
    public void IntegrationPlane(){
///////////////////////////view plane 3*3/////////////////////////////
        //case1 -plane infront of camera
        //(plane z=5)
        c.setLocation(new Point(0,0,0));
        Plane plane1= new Plane(new Point(2,3,5),new Point (4,7,5),new Point(0,0,5));
        result1 += func2(c,plane1);
        assertEquals(9, result1, "Wrong number of points");

        //case2 - plane intrection view plane once and has 9 point
        //(plane x+y+z=1)?
        c.setLocation(new Point(0,0,0));
        Plane plane2= new Plane(new Point(0,0,0),new Point(0,0,0),new Point(0,0,0));
        result2 += func2(c,plane2);
        assertEquals(9, result2, "Wrong number of points");


        //case3 -plane intrection view plane once and has 6 point
        //(plane ?)
        c.setLocation(new Point(0,0,0));
        Plane plane3= new Plane(new Point(0,0,0),new Point(0,0,0),new Point(0,0,0));
        result3 += func2(c,plane3);
        assertEquals(6, result3, "Wrong number of points");

        result=0;
        result1=0;
        result2=0;
        result3=0;
        result4=0;
        result5=0;

    }

    //**************************************************************
    @Test
    public void IntegrationTringale(){
        ///////////////////////////view plane 3*3/////////////////////////////
        //case1 -triangle infront of the camera and smaller that view plane
        c.setLocation(new Point(0,0,0));
        Triangle triangle1= new Triangle(new Point(0,1,-2),new Point (1,-1,-2),new Point(-1,-1,-2));
        result1 += func3(c,triangle1);
        assertEquals(1, result1, "Wrong number of points");

        //case2 -triangle infront of the camera and "taller" that view plane
        c.setLocation(new Point(0,0,0));
        Triangle triangle2= new Triangle(new Point(0,20,-2),new Point (1,-1,-2),new Point(-1,-1,-2));
        result2 += func3(c,triangle2);
        assertEquals(2, result2, "Wrong number of points");
    }




}
