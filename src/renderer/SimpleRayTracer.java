package renderer;

import primitives.Color;
import primitives.*;
import scene.Scene;
import java.util.List;

public class SimpleRayTracer extends RayTracerBase{

    //constructor
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }
    //func
    public Color traceRay (Ray ray){
        List<Point> lst = scene.geometries.findIntersections(ray);
        if(lst==null) {
            return scene.background;
        }

        Point p =ray.findClosestPoint(lst);
        if(p!=null)
            return calcColor(p);
        return scene.background;
    }
    private Color calcColor(Point p) {
        return scene.ambientLight.getIntesity();
    }


}
