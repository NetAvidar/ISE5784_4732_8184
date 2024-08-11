
package renderer;

//import geometries.Geometry;
import geometries.*;
//import renderer.*;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

public class IntegrationTests {

    //**************************************************************
    int result1=0;
    int result2=0;
    int result3=0;
    int result4=0;
    int result5=0;

    Scene s=new Scene("s");
    Camera c=new Camera.Builder()
            .setDirection(new Vector(0,1,0),new Vector(0,0,-1))
            .setVpSize(6,6)
            .setVpDistance(1)
            .setRayTracer(new SimpleRayTracer(s))
            .setImageWriter(new ImageWriter("check",3,3))
            .build();
    //**************************************************************

    int func(Camera c, Intersectable intersectable) {//get camera and triangle and return num of intresction from every ray in the view plane
        List<Point> allIntersections = new LinkedList<>();
        int nX = c.getImageWriter().getNx();
        int nY = c.getImageWriter().getNy();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                Ray r1 = c.constructRay(nX, nY, j, i); //return the ray that in the center of the pixel
//                r1.getDirection().normalize();         //normalize the direction of the ray
//                Ray r2 =new Ray(r1.getDirection(),c.getVto()) ; //couclute the ray that we send from the view plane
// Ray r2 =new Ray(r1.getDirection().add(r1.getHead().getXyz()),c.getVto()) ;
                List<Point> l = intersectable.findIntersections(r1);
                if (l != null) {
                    allIntersections.addAll(l);
                }
            }
        }
        return allIntersections.size();
    }

    //**************************************************************

    @Test
    public void IntegrationSphere(){

        ///////////////////////////view plane 3*3/////////////////////////////

        //case1 - camera outside the sphere & view plane bigger
        c.setLocation(new Point(0,0,0));
        Sphere sphere1= new Sphere(1,new Point(0,0,-3));
        result1 += func(c,sphere1);
        assertEquals(2, result1, "Wrong number of points");

//        //case2 - camera outside the sphere & view plane smaller
//        c.setLocation(new Point(0,0,0));
//        Sphere sphere2= new Sphere(20,new Point(0,0,-30));
//        result2 += func(c,sphere2);
//        assertEquals(18, result2, "Wrong number of points");


//        //case3 - camera outside the sphere & view plane bigger
//        c.setLocation(new Point(0,0,0.5));
//        Sphere sphere3= new Sphere(2,new Point(0,0,-2));
//        result3 += func(c,sphere3);
//        assertEquals(10, result3, "Wrong number of points");


        //case4 - camera inside the sphere & view plane smaller
        c.setLocation(new Point(1,1,-0.5));
        Sphere sphere4= new Sphere(4,new Point(1,1,-1));
        result4 += func(c,sphere4);
        assertEquals(9, result4, "Wrong number of points");


        //case5 - camera outside the sphere & sphere behind the camera
        c.setLocation(new Point(0,0,-0.5));
        Sphere sphere5= new Sphere(0.5,new Point(0,0,-1));
        result5 += func(c,sphere5);
        assertEquals(0, result5, "Wrong number of points");


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
        Plane plane1= new Plane(new Point(2,3,-5),new Point (4,7,-5),new Point(0,0,-5));
        result1 += func(c,plane1);
        assertEquals(9, result1, "Wrong number of points");


//        //case2 - plane intrection view plane once and has 9 point
//        c.setLocation(new Point(0,0,0));
//        Plane plane2= new Plane(new Point(0,0,-5),new Vector(0,1,-2));
//        result2 += func(c,plane2);
//        assertEquals(9, result2, "Wrong number of points");


        //case3 -plane intressction view plane once and has 6 point
        c.setLocation(new Point(0,0,0));
        //Plane plane3= new Plane(new Point(0,3,1), new Point(1,3,1),new Point(1,0,1));
        Plane plane3= new Plane(new Point(0,0,-2.5), new Point(1,-0.5,-3),new Point(-1,2,-0.5));
        result3 += func(c,plane3);
        assertEquals(6, result3, "Wrong number of points");

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
        result1 += func(c,triangle1);
        assertEquals(1, result1, "Wrong number of points");


        //case2 -triangle infront of the camera and "taller" that view plane
        c.setLocation(new Point(0,0,0));
        Triangle triangle2= new Triangle(new Point(0,20,-2),new Point (1,-1,-2),new Point(-1,-1,-2));
        result2 += func(c,triangle2);
        assertEquals(2, result2, "Wrong number of points");

    }
}




