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
                .setBackground(new Color(20,20,20));


        scene.geometries.add(
                 new Plane(new Point(0, -200, 0), new Point(1, -200, 0), new Point(0, -200, 1))
                        .setEmission(new Color(240, 220, 200)),
                new Plane(new Point(0, -180, 0), new Point(1, -180, 0), new Point(0, -180, 1))
                        .setEmission(new Color(100, 200, 300)).setMaterial(new Material().setKd(0.3).setKs(0.8).setShininess(30)),

                //first floar-
                new Sphere(60,new Point(-80,-100,0)).setEmission(new Color(0,30,200))  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),//blue
                new Sphere(60,new Point(160,-100,0)).setEmission(new Color(300,0,200)) .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(30)),//pink
                new Sphere(60,new Point(40,-100,0)).setEmission(new Color(200,0,30)) .setMaterial(new Material().setKd(0.5).setKs(0.8).setShininess(30)),//red

                new Sphere(60,new Point(-20,-100,60)).setEmission(new Color(200,200,200)).setMaterial(new Material().setKd(0.5).setKs(0.8).setShininess(30)), //gray
                new Sphere(60,new Point(100,-100,60)).setEmission(new Color(100,12,200)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),//purple

                new Sphere(60,new Point(40,-100,120)).setEmission(new Color(0,160,0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//green

                //second floar-
                new Sphere(60,new Point(-20,0,30)).setEmission(new Color(255,165,0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//yellow
                new Sphere(60,new Point(100,0,30)).setEmission(new Color(255,170,180)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//pink
                new Sphere(60,new Point(40,0,90)).setEmission(new Color(255,100,0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//orange

                //third floar-
                new Sphere(60,new Point(40,100,60)).setEmission(new Color(255,30,0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60))//red
        );

        scene.geometries.add(new Sphere(10, new Point(30,-190,50)).
                setEmission(new Color(0,30,200))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));
        //

        scene.lights.add(
                new DirectionalLight(new Color(200,300,50),new Vector(1,1,-1))); // from the top
        scene.lights.add(new SpotLight(new Color(400, 240, 0),new Point(100,1000,500),new Vector(-30,-30,0),20).setKl(1E-5).setKq(1.5E-7));

        scene.lights.add(
                new SpotLight(new Color(400, 240, 0),new Point(40,50,200),new Vector(0,-1,0),40)
                .setKl(1E-5).setKq(1.5E-7));

        scene.lights.add(
                new SpotLight(new Color(200, 200, 200),new Point(-150,200,200),new Vector(-40,-40,-20),30)
                        .setKl(1E-5).setKq(1.5E-7));

        // Updated Camera setup
        Camera.Builder camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 1000))  // Position the camera further back
                .setDirection(new Vector(0, 1, 0).normalize(), new Vector(0, 0, -1))  // Pointing towards the scene with correct up vector
                .setVpDistance(300)  // Adjusted to capture a larger view
                .setVpSize(200, 200);  // Increased size for a broader view


        scene.setSoftShadow(true);

        ImageWriter imagewriter=new ImageWriter("Spheres4",500,500);
        camera.setImageWriter(imagewriter)
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();


    }
}