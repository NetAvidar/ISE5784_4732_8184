package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
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

    }

    //**************************************************************
    @Test
    public void IntegrationPlane(){
///////////////////////////view plane 3*3/////////////////////////////

        //case1 -
        c.setLocation(new Point(#,#,#));
        Plane plane1= new Plane(###)
        result1 += func2(c,plane1);
        assertEquals(9, result1, "Wrong number of points");

        //case1 -
        c.setLocation(new Point(#,#,#));
        Plane plane1= new Plane(###)
        result1 += func2(c,plane1);
        assertEquals(9, result1, "Wrong number of points");


        //case1 -
        c.setLocation(new Point(#,#,#));
        Plane plane1= new Plane(###)
        result1 += func2(c,plane1);
        assertEquals(9, result1, "Wrong number of points");


        //case1 -
        c.setLocation(new Point(#,#,#));
        Plane plane1= new Plane(###)
        result1 += func2(c,plane1);
        assertEquals(9, result1, "Wrong number of points");


        //case1 -
        c.setLocation(new Point(#,#,#));
        Plane plane1= new Plane(###)
        result1 += func2(c,plane1);
        assertEquals(9, result1, "Wrong number of points");



    }



    //**************************************************************
    @Test
    public void IntegrationTringale(){

    }

}
