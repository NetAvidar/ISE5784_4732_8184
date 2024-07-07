package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
     //
    protected Scene scene;

     //constructor
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    //func
     public abstract Color traceRay (Ray ray);
}
