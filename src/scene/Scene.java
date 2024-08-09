package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import renderer.SimpleRayTracer;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String name;
    public Color background = new Color(0,0,0);
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries() ;
    public List<LightSource> lights = new LinkedList<>();

    private boolean softShadow = false;
    private int numOfRaysAtBeam = 1;
    private int radiusOfTargetArea = 0;



    //constractors
    public Scene(String s){
        this.name =s;
    }

    //setter
    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public Scene setSoftShadow(boolean softShadow) {
        this.softShadow = softShadow;
        return this;
    }

    public Scene setNumOfRaysAtBeam(int numOfRaysAtBeam) {
        this.numOfRaysAtBeam = numOfRaysAtBeam;
        return this;
    }

    public Scene setRadiusOfTargetArea(int radiusOfTargetArea) {
        this.radiusOfTargetArea = radiusOfTargetArea;
        return this;
    }

    public boolean isSoftShadow() {
        return softShadow;
    }
}