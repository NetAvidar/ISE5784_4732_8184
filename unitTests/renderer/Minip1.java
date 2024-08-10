
package renderer;
import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;
import static java.awt.Color.WHITE;


public class Minip1 {

    @Test
    public void houseScene() {
        Scene houseScene = new Scene("House Scene");

//**********************************************************************************************************************************************
        // Materials
        Material wallMaterial = new Material().setKd(0.5).setKs(0.3).setShininess(30);
        Material roofMaterial = new Material().setKd(0.7).setKs(0.1).setShininess(10);
        Material doorMaterial = new Material().setKd(0.3).setKs(0.2).setShininess(20);

//**********************************************************************************************************************************************
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
//**********************************************************************************************************************************************
        Plane plane = new Plane(new Point(10, 20, 30), new Point(-15, 5, 300), new Point(0, -30, 300));
        plane.setEmission(new Color(200, 0, 255));
//**********************************************************************************************************************************************
        houseScene.geometries.add(wall1, wall2, wall3, wall4, plane);
//**********************************************************************************************************************************************

        houseScene.lights.add(new SpotLight(new Color(WHITE), new Point(100, 100, 100), new Vector(-1, -1, -1), 9)
                .setKl(0.0004).setKq(0.0002));

        houseScene.setBackground(new Color(WHITE));
//**********************************************************************************************************************************************

        Camera.Builder camera = Camera.getBuilder()
                .setDirection(new Vector(0, 1, 0), // vUp (pointing upward in the Z direction)
                        new Vector(0, 0, 1)) // vTo (pointing towards the house)
                .setLocation(new Point(0, 0, -50)) // New camera position
                .setVpDistance(10)
                .setVpSize(300, 300)
                .setRayTracer(new SimpleRayTracer(houseScene));

        ImageWriter imageWriter = new ImageWriter("house_scene", 500, 500);
        camera.setImageWriter(imageWriter);

        camera.build()
                .renderImage()
                .writeToImage();
//**********************************************************************************************************************************************


//**********************************************************************************************************************************************


    }

}