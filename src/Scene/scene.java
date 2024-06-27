package Scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class scene {
    public String name;
    public Color background = new Color(0,0,0);
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries =new Geometries() ;


    //constractors
    scene(String s){
        this.name =s;
    }

    //setter
    public scene setName(String name) {
        this.name = name;
        return this;
    }

    public scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }


}
