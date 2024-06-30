package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    public String name;
    public Color background = new Color(0,0,0);
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries =new Geometries() ;


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

}