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

class CylinderTests {
     /*** Test method for{} .*/

     @Test
     public void testConstructor() {
// ============ Equivalence Partitions Tests ==============
          assertDoesNotThrow(()-> new Cylinder(5,new Ray(new Point(3,4,5),new Vector(7,5,6)),9),
                  "Failed constructing a correct Tube");

     }


     @Test
     public void testGetNormal() {
// ============ Equivalence Partitions Tests ==============
          //1
          Cylinder c1=  new Cylinder(5,new Ray(new Point(0,0,0),new Vector(0,0,1)),9);
          assertDoesNotThrow(()->c1.getNormal(new Point(5,0,0)),"on the side of the cylinder");
          //2
          Cylinder c2=  new Cylinder(5,new Ray(new Point(0,0,0),new Vector(0,0,1)),9);
          assertDoesNotThrow(()->c2.getNormal(new Point(1,0,9)),"on the top base of the cylinder");

          //3
          Cylinder c3=  new Cylinder(5,new Ray(new Point(0,0,0),new Vector(0,0,1)),9);
          assertDoesNotThrow(()->c3.getNormal(new Point(1,0,0)),"on the lower base of the cylinder");
// =============== Boundary Values Tests ==================
          //1
          Cylinder c4=  new Cylinder(5,new Ray(new Point(0,0,0),new Vector(0,0,1)),9);
          assertDoesNotThrow(()->c4.getNormal(new Point(0,0,9)),"in the center of the top base in the cylinder");

          //2
          Cylinder c5=  new Cylinder(5,new Ray(new Point(0,0,0),new Vector(0,0,1)),9);
          assertDoesNotThrow(()->c5.getNormal(new Point(0,0,0)),"in the center of the lower base in the cylinder");

     }
}