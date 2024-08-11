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
    @Test
    public void ourPicture() {
        Scene scene = new Scene("Test scene")//
                .setBackground(new Color(100, 200, 300));

        scene.geometries.add(
                new Plane(new Point(0, -600, 0), new Point(1, -600, 0), new Point(0, -600, 1)) //white
                        .setEmission(new Color(230, 230, 230)).setMaterial(new Material().setKd(0.3).setKs(0.8).setShininess(30)),
                new Plane(new Point(-300, -600, -100), new Point(-290, -600, -600), new Point(-300, -500, -100)) //pink
                        .setEmission(new Color(PINK)).setMaterial(new Material().setKd(0.3).setKs(0.8).setShininess(30)),

                new Triangle (new Point (-150,-160,0),new Point (230,-160,0),new Point(40,-160,300))
                        .setEmission(new Color(123, 63,0)).setMaterial(new Material().setKr(1).setKd(1).setKd(0.2).setShininess(10)),
                new Triangle (new Point (-150,-160,0),new Point (230,-160,0),new Point(40,-400,110))
                        .setEmission(new Color(123, 63,0)).setMaterial(new Material().setKd(0.3).setKs(0.8).setShininess(30)),
                new Triangle (new Point (-150,-160,0),new Point (40,-400,110),new Point(40,-160,300))
                        .setEmission(new Color(123, 63,0)).setMaterial(new Material().setKd(0.3).setKs(0.8).setShininess(30)),
                new Triangle (new Point (40,-400,110),new Point (230,-160,0),new Point(40,-160,300))
                        .setEmission(new Color(123, 63,0)).setMaterial(new Material().setKd(0.3).setKs(0.8).setShininess(30)),

                //first floor-
                new Sphere(60,new Point(-80,-100,0)).setEmission(new Color(0,30,200))  .setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//blue
                new Sphere(60,new Point(160,-100,0)).setEmission(new Color(300,0,200)) .setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//pink
                new Sphere(60,new Point(40,-100,0)).setEmission(new Color(200,0,30)) .setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//red

                new Sphere(60,new Point(-20,-100,110)).setEmission(new Color(200,200,200)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)), //gray
                new Sphere(60,new Point(100,-100,110)).setEmission(new Color(100,12,200)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//purple

                new Sphere(60,new Point(40,-100,220)).setEmission(new Color(0,160,0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//green

                //second floar-
                new Sphere(60,new Point(-20,0,55)).setEmission(new Color(255,165,0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//yellow
                new Sphere(60,new Point(100,0,55)).setEmission(new Color(255,170,180)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//pink
                new Sphere(60,new Point(40,0,165)).setEmission(new Color(255,100,0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(60)),//orange

                //third floar-
                new Sphere(60,new Point(40,100,110)).setEmission(new Color(255,30,0)).setMaterial(new Material().setKd(0.4).setKs(0.8).setShininess(40))//red
        );

        scene.geometries.add(new Sphere(10, new Point(30,-190,50)).
                setEmission(new Color(0,30,200))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)));

        //**************************************

        scene.lights.add(new DirectionalLight(new Color(50,50,50),new Vector(0.5,0.5,-0.5))); // from the top


//        scene.lights.add(new SpotLight(new Color(400, 240, 0),new Point(100,1000,500),new Vector(-30,-30,0),20).setKl(1E-5).setKq(1.5E-7));
//
//
//        scene.lights.add(new SpotLight(new Color(400, 240, 0),new Point(40,50,200),new Vector(0,-1,0),20)
//                        .setKl(1E-5).setKq(1.5E-7));
//
//        scene.lights.add(new SpotLight(new Color(200, 200, 200),new Point(-150,200,200),new Vector(-40,-40,-20),13)
//                        .setKl(1E-5).setKq(1.5E-7));

        scene.lights.add(new SpotLight(new Color(255, 255, 255),new Point(-300,300,400),new Vector(40,-40,-20),13)
                .setKl(1E-5).setKq(1.5E-7));

        scene.lights.add(new SpotLight(new Color(255, 255, 255),new Point(-20,0,250),new Vector(0,0,-1),13)
                .setKl(1E-5).setKq(1.5E-7));

        scene.lights.add(new PointLight(new Color(180,180,180),new Point(-300,-300,250),20));





        //**************************************

        scene.setSoftShadow(true).setNumOfRaysAtBeam(289);

        // Updated Camera setup
        Camera.Builder camera = Camera.getBuilder()
                .setLocation(new Point(0, 0, 1000))  // Position the camera further back
                .setDirection(new Vector(0, 1, 0).normalize(), new Vector(0, 0, -1))  // Pointing towards the scene with correct up vector
                .setVpDistance(220)  // Adjusted to capture a larger view
                .setVpSize(200, 200);  // Increased size for a broader view

        ImageWriter imagewriter=new ImageWriter("Minip1 with soft shadow",500,500);
        camera.setImageWriter(imagewriter)
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();


    }
}
