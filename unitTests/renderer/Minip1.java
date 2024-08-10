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
*/


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

        // Camera settings - New position and direction to ensure all geometries are visible
        Camera.Builder camera = Camera.getBuilder()
                .setDirection(new Vector(0, 0, 1), // vUp (pointing upward in the Z direction)
                        new Vector(-1, -1, -1).normalize()) // vTo (pointing towards the house)
                .setLocation(new Point(150, 150, 100)) // New camera position
                .setVpDistance(500)
                .setVpSize(300, 300)
                .setRayTracer(new SimpleRayTracer(houseScene));

        // Image writer and ray tracer
        ImageWriter imageWriter = new ImageWriter("house_scene_ortho_vectors", 500, 500);
        camera.setImageWriter(imageWriter);

        camera.build()
                .renderImage()
                .writeToImage();
    }
}
