/*package renderer;
import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.WHITE;


public class Minip1 {
    @Test
    public void houseScene() {


         Scene houseScene = new Scene("House Scene");
        // scene.setBackground(new Color(135, 206, 235));// Sky blue background

        // Materials
        Material wallMaterial = new Material().setKd(0.5).setKs(0.3).setShininess(30);
        Material roofMaterial = new Material().setKd(0.7).setKs(0.1).setShininess(10);
        Material doorMaterial = new Material().setKd(0.3).setKs(0.2).setShininess(20);

        // House base (as a box made of 4 walls)
        Geometry wall1 = new Polygon(new Point(-50, -50, -20), new Point(50, -50, -20), new Point(50, -50, 0), new Point(-50, -50, 0))
                .setEmission(new Color(169, 169, 169)) // Light gray
                .setMaterial(wallMaterial);
        Geometry wall2 = new Polygon(new Point(-50, -50, 0), new Point(-50, 50, 0), new Point(-50, 50, -20), new Point(-50, -50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);
        Geometry wall3 = new Polygon(new Point(-50, 50, 0), new Point(50, 50, 0), new Point(50, 50, -20), new Point(-50, 50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);
        Geometry wall4 = new Polygon(new Point(50, -50, 0), new Point(50, 50, 0), new Point(50, 50, -20), new Point(50, -50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);

        // Roof (as two triangles)
        Geometry roof1 = new Triangle(new Point(-55, -50, 0), new Point(55, -50, 0), new Point(0, -50, 30))
                .setEmission(new Color(139, 69, 19)) // Brown
                .setMaterial(roofMaterial);
        Geometry roof2 = new Triangle(new Point(-55, 50, 0), new Point(55, 50, 0), new Point(0, 50, 30))
                .setEmission(new Color(139, 69, 19))
                .setMaterial(roofMaterial);

        // Door (as a rectangle)
        Geometry door = new Polygon(new Point(-10, -50, -20), new Point(10, -50, -20), new Point(10, -50, -5), new Point(-10, -50, -5))
                .setEmission(new Color(160, 82, 45)) // Sienna
                .setMaterial(doorMaterial);

        // Add geometries to scene
        houseScene.geometries.add(wall1, wall2, wall3, wall4, roof1, roof2, door);

        // Light sources
        houseScene.lights.add(new SpotLight(new Color(WHITE), new Point(100, 100, 100), new Vector(-1, -1, -1),9)
                .setKl(0.0004).setKq(0.0002));





        Camera.Builder camera = Camera.getBuilder()
                .setDirection(new Vector(0, 1, 0),  // vUp - upright camera
                        new Vector(0, 0, -1)) // vTo - looking downward at the house
                .setLocation(new Point(0, 100, 100)) // Adjust the location closer to the house
                .setVpDistance(100)  // Reduce the distance to bring the house closer in view
                .setVpSize(200, 200)
                .setRayTracer(new SimpleRayTracer(houseScene))
                .setImageWriter(new ImageWriter("house_scene", 500, 500));

        camera.build()
                .renderImage()
                .writeToImage();




        // Image writer and ray tracer
        ImageWriter imageWriter = new ImageWriter("house_scene", 500, 500);
        camera.setImageWriter(imageWriter);


        camera.build()
                .renderImage() //
                .writeToImage();

    }

}



package renderer;

import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.WHITE;

public class Minip1 {
    @Test
    public void houseScene() {

        Scene houseScene = new Scene("House Scene");

        // Materials
        Material wallMaterial = new Material().setKd(0.5).setKs(0.3).setShininess(30);
        Material roofMaterial = new Material().setKd(0.7).setKs(0.1).setShininess(10);
        Material doorMaterial = new Material().setKd(0.3).setKs(0.2).setShininess(20);

        // House base (as a box made of 4 walls)
        Geometry wall1 = new Polygon(new Point(-50, -50, -20), new Point(50, -50, -20), new Point(50, -50, 0), new Point(-50, -50, 0))
                .setEmission(new Color(169, 169, 169)) // Light gray
                .setMaterial(wallMaterial);
        Geometry wall2 = new Polygon(new Point(-50, -50, 0), new Point(-50, 50, 0), new Point(-50, 50, -20), new Point(-50, -50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);
        Geometry wall3 = new Polygon(new Point(-50, 50, 0), new Point(50, 50, 0), new Point(50, 50, -20), new Point(-50, 50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);
        Geometry wall4 = new Polygon(new Point(50, -50, 0), new Point(50, 50, 0), new Point(50, 50, -20), new Point(50, -50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);

        // Roof (as two triangles)
        Geometry roof1 = new Triangle(new Point(-55, -50, 0), new Point(55, -50, 0), new Point(0, -50, 30))
                .setEmission(new Color(139, 69, 19)) // Brown
                .setMaterial(roofMaterial);
        Geometry roof2 = new Triangle(new Point(-55, 50, 0), new Point(55, 50, 0), new Point(0, 50, 30))
                .setEmission(new Color(139, 69, 19))
                .setMaterial(roofMaterial);

        // Door (as a rectangle)
        Geometry door = new Polygon(new Point(-10, -50, -20), new Point(10, -50, -20), new Point(10, -50, -5), new Point(-10, -50, -5))
                .setEmission(new Color(160, 82, 45)) // Sienna
                .setMaterial(doorMaterial);

        // Add geometries to scene
        houseScene.geometries.add(wall1, wall2, wall3, wall4, roof1, roof2, door);

        // Light sources
        houseScene.lights.add(new SpotLight(new Color(WHITE), new Point(100, 100, 100), new Vector(-1, -1, -1), 9)
                .setKl(0.0004).setKq(0.0002));

        // Camera settings: Adjusted to capture the whole house
        Camera.Builder camera = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), // vUp
                        new Vector(0, -1, 0)) // vTo
                .setLocation(new Point(0, 200, 50)) // Moved back to capture the entire house
                .setVpDistance(400) // Adjusted distance
                .setVpSize(400, 400) // Adjusted viewport size to better fit the house
                .setRayTracer(new SimpleRayTracer(houseScene));

        // Image writer and ray tracer
        ImageWriter imageWriter = new ImageWriter("house_scene", 500, 500);
        camera.setImageWriter(imageWriter);

        camera.build()
                .renderImage()
                .writeToImage();
    }
}


package renderer;

import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.WHITE;

public class Minip1 {
    @Test
    public void houseScene() {

        Scene houseScene = new Scene("House Scene");

        // Materials
        Material wallMaterial = new Material().setKd(0.5).setKs(0.3).setShininess(30);
        Material roofMaterial = new Material().setKd(0.7).setKs(0.1).setShininess(10);
        Material doorMaterial = new Material().setKd(0.3).setKs(0.2).setShininess(20);

        // House base (as a box made of 4 walls)
        Geometry wall1 = new Polygon(new Point(-50, -50, -20), new Point(50, -50, -20), new Point(50, -50, 0), new Point(-50, -50, 0))
                .setEmission(new Color(169, 169, 169)) // Light gray
                .setMaterial(wallMaterial);
        Geometry wall2 = new Polygon(new Point(-50, -50, 0), new Point(-50, 50, 0), new Point(-50, 50, -20), new Point(-50, -50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);
        Geometry wall3 = new Polygon(new Point(-50, 50, 0), new Point(50, 50, 0), new Point(50, 50, -20), new Point(-50, 50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);
        Geometry wall4 = new Polygon(new Point(50, -50, 0), new Point(50, 50, 0), new Point(50, 50, -20), new Point(50, -50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);

        // Roof (as two triangles)
        Geometry roof1 = new Triangle(new Point(-55, -50, 0), new Point(55, -50, 0), new Point(0, -50, 30))
                .setEmission(new Color(139, 69, 19)) // Brown
                .setMaterial(roofMaterial);
        Geometry roof2 = new Triangle(new Point(-55, 50, 0), new Point(55, 50, 0), new Point(0, 50, 30))
                .setEmission(new Color(139, 69, 19))
                .setMaterial(roofMaterial);

        // Door (as a rectangle)
        Geometry door = new Polygon(new Point(-10, -50, -20), new Point(10, -50, -20), new Point(10, -50, -5), new Point(-10, -50, -5))
                .setEmission(new Color(160, 82, 45)) // Sienna
                .setMaterial(doorMaterial);

        // Add geometries to scene
        houseScene.geometries.add(wall1, wall2, wall3, wall4, roof1, roof2, door);

        // Light sources
        houseScene.lights.add(new SpotLight(new Color(WHITE), new Point(100, 100, 100), new Vector(-1, -1, -1), 9)
                .setKl(0.0004).setKq(0.0002));

        // Camera settings
        Camera.Builder camera = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1),  // Pointing directly towards the z-axis, capturing height and depth
                        new Vector(0, 1, 0))          // vUp pointing upwards along the y-axis
                .setLocation(new Point(0, -150, 30))  // Positioning the camera further back and slightly elevated
                .setVpDistance(150)                   // Adjusted for a wider view
                .setVpSize(200, 200)                  // Viewport size
                .setRayTracer(new SimpleRayTracer(houseScene));

        // Image writer and ray tracer
        ImageWriter imageWriter = new ImageWriter("house_scene", 500, 500);
        camera.setImageWriter(imageWriter);

        camera.build()
                .renderImage()
                .writeToImage();
    }
}


package renderer;

import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.WHITE;

public class Minip1 {
    @Test
    public void houseScene() {

        Scene houseScene = new Scene("House Scene");

        // Materials
        Material wallMaterial = new Material().setKd(0.5).setKs(0.3).setShininess(30);
        Material roofMaterial = new Material().setKd(0.7).setKs(0.1).setShininess(10);
        Material doorMaterial = new Material().setKd(0.3).setKs(0.2).setShininess(20);

        // House base (as a box made of 4 walls)
        Geometry wall1 = new Polygon(new Point(-50, -50, -20), new Point(50, -50, -20), new Point(50, -50, 0), new Point(-50, -50, 0))
                .setEmission(new Color(169, 169, 169)) // Light gray
                .setMaterial(wallMaterial);
        Geometry wall2 = new Polygon(new Point(-50, -50, 0), new Point(-50, 50, 0), new Point(-50, 50, -20), new Point(-50, -50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);
        Geometry wall3 = new Polygon(new Point(-50, 50, 0), new Point(50, 50, 0), new Point(50, 50, -20), new Point(-50, 50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);
        Geometry wall4 = new Polygon(new Point(50, -50, 0), new Point(50, 50, 0), new Point(50, 50, -20), new Point(50, -50, -20))
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);

        // Roof (as two triangles)
        Geometry roof1 = new Triangle(new Point(-55, -50, 0), new Point(55, -50, 0), new Point(0, -50, 30))
                .setEmission(new Color(139, 69, 19)) // Brown
                .setMaterial(roofMaterial);
        Geometry roof2 = new Triangle(new Point(-55, 50, 0), new Point(55, 50, 0), new Point(0, 50, 30))
                .setEmission(new Color(139, 69, 19))
                .setMaterial(roofMaterial);

        // Door (as a rectangle)
        Geometry door = new Polygon(new Point(-10, -50, -20), new Point(10, -50, -20), new Point(10, -50, -5), new Point(-10, -50, -5))
                .setEmission(new Color(160, 82, 45)) // Sienna
                .setMaterial(doorMaterial);

        // Add geometries to scene
        houseScene.geometries.add(wall1, wall2, wall3, wall4, roof1, roof2, door);

        // Light sources
        houseScene.lights.add(new SpotLight(new Color(WHITE), new Point(100, 100, 100), new Vector(-1, -1, -1),9)
                .setKl(0.0004).setKq(0.0002));

        // Camera settings - New position and direction to ensure all geometries are visible
        Camera.Builder camera = Camera.getBuilder()
                .setDirection(new Vector(-2, 0, -1), // vUp
                        new Vector(-1, 0, -0.5)) // vTo
                .setLocation(new Point(150, 150, 100)) // New camera position
                .setVpDistance(500)
                .setVpSize(300, 300)
                .setRayTracer(new SimpleRayTracer(houseScene));

        // Image writer and ray tracer
        ImageWriter imageWriter = new ImageWriter("house_scene_new_angle", 500, 500);
        camera.setImageWriter(imageWriter);

        camera.build()
                .renderImage()
                .writeToImage();
    }
}



package renderer;

import geometries.*;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.WHITE;

public class Minip1 {
    @Test
    public void houseScene() {

        Scene houseScene = new Scene("House Scene");

        // Materials
        Material wallMaterial = new Material().setKd(0.5).setKs(0.3).setShininess(30);
        Material roofMaterial = new Material().setKd(0.7).setKs(0.1).setShininess(10);
        Material doorMaterial = new Material().setKd(0.3).setKs(0.2).setShininess(20);


        Geometry wall1 = new Polygon(
                new Point(-50, 0, 600), // Bottom-left
                new Point(50, 0, 600),  // Bottom-right
                new Point(50, 100, 600), // Top-right
                new Point(-50, 100, 600) // Top-left
        )
                .setEmission(new Color(169, 169, 169)) // Light gray
                .setMaterial(wallMaterial);

        Geometry wall2 = new Polygon(
                new Point(50, 0, 300),  // Bottom-left
                new Point(50, 100, 300), // Top-left
                new Point(50, 100, 600), // Top-right
                new Point(50, 0, 600)   // Bottom-right
        )
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);


        Geometry wall3 = new Polygon(
                new Point(-50, 0, 300),  // Bottom-left
                new Point(50, 0, 300),   // Bottom-right
                new Point(50, 100, 300), // Top-right
                new Point(-50, 100, 300) // Top-left
        )
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);

        Geometry wall4 = new Polygon(
                new Point(-50, 0, 300),  // Bottom-left
                new Point(-50, 100, 300), // Top-left
                new Point(-50, 100, 600), // Top-right
                new Point(-50, 0, 600)   // Bottom-right
        )
                .setEmission(new Color(169, 169, 169))
                .setMaterial(wallMaterial);


        Sphere sp = new Sphere(20, new Point(0,0,400));
/*        // Roof (as two triangles)
        Geometry roof1 = new Triangle(new Point(-55, -50, 0), new Point(55, -50, 0), new Point(0, -50, 30))
                .setEmission(new Color(139, 69, 19)) // Brown
                .setMaterial(roofMaterial);
        Geometry roof2 = new Triangle(new Point(-55, 50, 0), new Point(55, 50, 0), new Point(0, 50, 30))
                .setEmission(new Color(139, 69, 19))
                .setMaterial(roofMaterial);

        // Door (as a rectangle)
        Geometry door = new Polygon(new Point(-10, -50, -20), new Point(10, -50, -20), new Point(10, -50, -5), new Point(-10, -50, -5))
                .setEmission(new Color(160, 82, 45)) // Sienna
                .setMaterial(doorMaterial);
*/
        // Add geometries to scene
       // houseScene.geometries.add(wall1, wall2, wall3, wall4, roof1, roof2, door);

/*
        houseScene.geometries.add(wall1, wall2, wall3, wall4,sp);
        houseScene.lights.add(new SpotLight(new Color(WHITE), new Point(100, 100, 100), new Vector(-1, -1, -1), 9)
                .setKl(0.0004).setKq(0.0002));
        houseScene.lights.add(new PointLight(new Color(WHITE), new Point(0, 100, 500))
                .setKl(0.0001).setKq(0.00005));
        houseScene.setBackground(new Color(WHITE));

        // Camera settings - New position and direction to ensure all geometries are visible
        Camera.Builder camera = Camera.getBuilder()
                .setDirection(
                        new Vector(0, 1, 0),                 // vUp (Z direction, pointing straight up)
                        new Vector(0, 0, 1)     // vTo (pointing straight towards the house along the Y-axis)
                )
                .setLocation(new Point(0, 50, 200))  // Camera position, in front of and above the house
                .setVpDistance(40)
                .setVpSize(200, 200)
                .setRayTracer(new SimpleRayTracer(houseScene));

//        // Adjust the camera position to ensure the geometries are within the view
//        camera.setLocation(new Point(0, -200, 200))  // Camera position closer to the scene
//                .setVpDistance(100) // Reduce the distance for a closer view
//                .setVpSize(200, 200); // Adjust the viewport size if necessary

// Add more light sources or increase the current light's intensity


        // Image writer and ray tracer
        ImageWriter imageWriter = new ImageWriter("house_scene", 500, 500);
        camera.setImageWriter(imageWriter)
                .build()
                .renderImage()
                .writeToImage();


    }

}

package renderer;
import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.io.CharArrayWriter;

public class Minip1 {
    // private Scene scene = new Scene.SceneBuilder("Test scene").build();
    @Test
    public void ourPicture(){
        Scene scene = new Scene("Test scene")//
                .setBackground(new Color(51,0,0));

        Point A=new Point(-165,170,-300);
        Point B=new Point(-165,140,-300);
        Point C=new Point(-135,140,-300);
        Point D=new Point(-135,170,-300);
        Point E=new Point(-165,170,-200);
        Point F=new Point(-165,140,-200);
        Point G=new Point(-135,140,-200);
        Point H=new Point(-135,170,-200);
        Point J=new Point(-105,140,-300);
        Point K=new Point(-105,170,-300);
        Point L=new Point(-105,140,-200);
        Point M=new Point(-105,170,-200);
        Point N=new Point(-165,110,-300);
        Point O=new Point(-135,110,-300);
        Point P=new Point(-165,110,-200);
        Point Q=new Point(-135,110,-200);
        Point R=new Point(-150,90,-250);
        Point S=new Point(-90,155,-250);

        //Scene scene = new Scene.SceneBuilder("Test scene").build();//
//        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0,0,-1), new Vector(1,0,0))
//                .setVPDistance(600).setVPSize(200, 200);

        Camera.Builder camera = Camera.getBuilder().setLocation(new Point(0,0,1000))
                .setDirection(new Vector(1,0,0), new Vector(0,0,-1))
                .setVpDistance(600).setVpSize(200,200);
        scene.geometries.add(
//                new Polygon(A,B,C,D)
//                        .setEmission(new Color(200,255,0))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setKt(0).setKr(0).setShininess(20)),
//                new Polygon(E,F,G,H)
//                        .setEmission(new Color(200,255,0))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setKt(0).setKr(0).setShininess(20)),
//                new Polygon(A,D,H,E)
//                        .setEmission(new Color(200,255,0))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setKt(0).setKr(0).setShininess(20)),
//                new Polygon(G,H,D,C)
//                        .setEmission(new Color(200,255,0))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setKt(0).setKr(0).setShininess(20)),
//                new Polygon(G,F,B,C)
//                        .setEmission(new Color(200,255,0))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.1).setKt(0).setKr(0).setShininess(20)),
//                new Polygon(E,F,B,A)
//                        .setEmission(new Color(200,255,0))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0).setShininess(20)),
//                new Polygon(L,M,K,J)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(G,C,J,L)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(M,H,D,K)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(G,H,M,L)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(C,D,K,J)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(N,O,Q,P)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(Q,G,C,O)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(P,F,B,N)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(N,B,C,O)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Polygon(P,F,G,Q)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Triangle(Q,O,R)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Triangle(N,O,R)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Triangle(N,P,R)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Triangle(P,Q,R)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Triangle(M,L,S)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Triangle(M,K,S)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Triangle(K,J,S)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
//                new Triangle(L,J,K)
//                        .setEmission(new Color(51,204,255))
//                        .setMaterial(new Material().setKd(0.5).setKs(1).setKt(0.5).setShininess(20)),
                new Sphere(20,new Point(90, 130, -300)).setEmission(new Color(ORANGE))   //light
                        .setMaterial(new Material().setKd(0.5).setKs(0.8).setKt(1)),
                new Triangle(new Point(94,130,-300),new Point(300,130,-300),new Point(300,130,-250))
                        .setEmission(new Color(102,0,153)),
                new Triangle(new Point(-105,200,700),new Point(-105,5,700),new Point(-105,5,-2500))
                        .setEmission(new Color(102,0,153))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.1).setKr(0.1).setShininess(20)),
                new Plane(new Point(-1000,28,0),new Point(90,5,0),new Point(-105,33,-2034))
                        .setEmission(new Color(GRAY))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.25))
        );


        scene.lights.add(
                new PointLight(new Color(255,255,153), new Point(90, 150, -300)) //
                        .setKl(0.00001).setKq(0.000005)
        );

        scene.lights.add(
                new SpotLight(new Color(255,204,0), new Point(90,150,-300), new Vector(15, 12, -19)) //
                        .setKl(0.00001).setKq(0.000005)
        );


        ImageWriter imagewriter=new ImageWriter("ourPicture2",500,500);
        camera.setImageWriter(imagewriter)
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();

    }
}
*/

package renderer;
import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.io.CharArrayWriter;

public class Minip1 {
    // private Scene scene = new Scene.SceneBuilder("Test scene").build();
    @Test
    public void ourPicture() {
        Scene scene = new Scene("Test scene")//
                .setBackground(new Color(PINK));


        scene.geometries.add(
                 new Plane(new Point(0, -200, 0), new Point(1, -200, 0), new Point(0, -200, 1))
                        .setEmission(new Color(240, 220, 200)),
                new Plane(new Point(0, -180, 0), new Point(1, -180, 0), new Point(0, -180, 1))
                        .setEmission(new Color(100, 200, 300)).setMaterial(new Material().setKd(0.3).setKs(0.8).setShininess(30)),
                //new Plane(new Point(1,0,2),new Point(-3,0,5),new Point(0,0,-4)).setEmission(new Color(240,220,200)),
                new Sphere(15,new Point(5,-100,500)).setEmission(new Color(200,200,200)).setMaterial(new Material().setKd(0.5).setKs(0.8).setShininess(30)), //gray
                new Sphere(60,new Point(5,-110,200)).setEmission(new Color(0,30,200))  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),//blue
                new Sphere(60,new Point(150,-100,200)).setEmission(new Color(300,0,200)) .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(30)),//pink
                new Sphere(60,new Point(40,-100,200)).setEmission(new Color(200,0,30)) .setMaterial(new Material().setKd(0.5).setKs(0.8).setShininess(30)),//red
                new Sphere(12,new Point(30,-100,600)).setEmission(new Color(100,12,200)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));//purple

        scene.geometries.add(new Sphere(10, new Point(30,-190,50)).
                setEmission(new Color(0,30,200))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));
        //

        scene.lights.add(
                new DirectionalLight(new Color(200,300,50),new Vector(1,1,-1))); // from the top
        scene.lights.add(new SpotLight(new Color(400, 240, 0),new Point(100,1000,500),new Vector(-30,-30,0),20).setKl(1E-5).setKq(1.5E-7));

        scene.lights.add(
                new SpotLight(new Color(400, 240, 0),new Point(40,50,200),new Vector(0,-1,0),20)
                .setKl(1E-5).setKq(1.5E-7));

        // Updated Camera setup
        Camera.Builder camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 1000))  // Position the camera further back
                .setDirection(new Vector(0, 1, 0).normalize(), new Vector(0, 0, -1))  // Pointing towards the scene with correct up vector
                .setVpDistance(300)  // Adjusted to capture a larger view
                .setVpSize(200, 200);  // Increased size for a broader view

        ImageWriter imagewriter=new ImageWriter("Spheres4",500,500);
        camera.setImageWriter(imagewriter)
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();


    }
}