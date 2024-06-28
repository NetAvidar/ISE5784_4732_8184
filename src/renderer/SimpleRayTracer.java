package renderer;

import primitives.Color;
import primitives.*;
import scene.Scene;
import java.util.List;

public class SimpleRayTracer extends RayTracerBase{

    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    public Color traceRay (Ray ray){
        List<Point> lst = scene.geometries.findIntersections(ray);
        if(lst==null) {
            return scene.ambientLight.getIntesity();
        }

        Point p =ray.findClosestPoint(lst);
        if(p!=null)
            return calcColor(p);
        return null;
    }

    private Color calcColor(Point p) {
        return scene.ambientLight.getIntesity();
    }


}
