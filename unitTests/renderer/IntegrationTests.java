package renderer;

import geometries.Geometry;
import geometries.*;
import renderer.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import primitives.*;
import scene.Scene;

import java.util.List;

public class IntegrationTests {

    //**************************************************************
    int result=0;
    int result1=0;
    int result2=0;
    int result3=0;
    int result4=0;
    int result5=0;

    Scene s=new Scene("s");
    Camera c=new Camera.Builder()
            .setDirection(new Vector(0,1,0),new Vector(0,0,1))
            .setVpSize(6,6)
            .setVpDistance(1)
            .setRayTracer(new SimpleRayTracer(s))                                 //todo:why do we need this line?
            .setImageWriter(new ImageWriter("check",300,300)) //todo:why do we need this line?
            .build();
    //**************************************************************
/*
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
    }*/
    int func(Camera c, Intersectable obj) {//get camera and triangle and return num of intresction from every ray in the view plane
        List<Point> l;
        Ray r;
        for (int i = 50; i < 300; i+=100) { // 300 because we want to builde and paint ray for pixels
            for (int j = 50; j < 300; j+=100) { // 300 because we want to builde and paint ray for pixels
                r = c.constructRay(300, 300, j, i); //todo: it not really nx ny, its not pixel here // and why i cant rech nx ny throw camera
                l = obj.findIntersections(r);
                result += l.size(); //todo: i did that everybody return emptylist and not null , but if we do return null then we need if here
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
        Sphere sphere1= new Sphere(1,new Point(0,0,3));
        result1 += func(c,sphere1);
        assertEquals(2, result1, "Wrong number of points");

        result=0;

        //case2 - camera outside the sphere & view plane smaller
        c.setLocation(new Point(0,0,0));
        Sphere sphere2= new Sphere(20,new Point(0,0,30));
        result2 += func(c,sphere2);
        assertEquals(18, result2, "Wrong number of points");

        result=0;

        //case3 - camera outside the sphere & view plane bigger
        c.setLocation(new Point(0,0,0.5));
        Sphere sphere3= new Sphere(2,new Point(0,0,2));
        result3 += func(c,sphere3);
        assertEquals(10, result3, "Wrong number of points");

        result=0;

        //case4 - camera inside the sphere & view plane smaller
        c.setLocation(new Point(1,1,0.5));
        Sphere sphere4= new Sphere(4,new Point(1,1,1));
        result4 += func(c,sphere4);
        assertEquals(9, result4, "Wrong number of points");

        result=0;

        //case5 - camera outside the sphere & sphere behind the camera
        c.setLocation(new Point(0,0,0.5));
        Sphere sphere5= new Sphere(0.5,new Point(0,0,1));
        result5 += func(c,sphere5);
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
        c.setLocation(new Point(0,0,0));
        Plane plane1= new Plane(new Point(2,3,5),new Point (4,7,5),new Point(0,0,5));
        result1 += func(c,plane1);
        assertEquals(9, result1, "Wrong number of points");

        result=0;

        //case2 - plane intrection view plane once and has 9 point
        c.setLocation(new Point(0,0,0));
        //Plane plane2= new Plane(new Point(0,7,0),new Point(1,7,1), new Point(-1,7,-1));
        Plane plane2= new Plane(new Point(0,0,8),new Point(1,2,6), new Point(-1,3,5));
        result2 += func(c,plane2);
        assertEquals(9, result2, "Wrong number of points");

        result=0;

        //case3 -plane intressction view plane once and has 6 point
        c.setLocation(new Point(0,0,0));
        //Plane plane3= new Plane(new Point(0,3,1), new Point(1,3,1),new Point(1,0,1));
        Plane plane3= new Plane(new Point(0,0,2.5), new Point(1,-0.5,3),new Point(-1,2,0.5));
        result3 += func(c,plane3);
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
        Triangle triangle1= new Triangle(new Point(0,1,2),new Point (1,-1,2),new Point(-1,-1,2));
        result1 += func(c,triangle1);
        assertEquals(1, result1, "Wrong number of points");

        result=0;

        //case2 -triangle infront of the camera and "taller" that view plane
        c.setLocation(new Point(0,0,0));
        Triangle triangle2= new Triangle(new Point(0,20,2),new Point (1,-1,2),new Point(-1,-1,2));
        result2 += func(c,triangle2);
        assertEquals(2, result2, "Wrong number of points");

    }
}
