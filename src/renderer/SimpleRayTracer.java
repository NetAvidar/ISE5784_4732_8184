package renderer;
import geometries.Intersectable.GeoPoint;
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
        List<GeoPoint> lst = scene.geometries.findGeoIntersections(ray);
        if(lst==null) {
            return scene.background;
        }

        GeoPoint gp =ray.findClosestGeoPoint(lst);

        if(gp!=null)
            return calcColor(gp);
        return scene.background;
    }

    private Color calcColor(GeoPoint gp) {
        Color color =scene.ambientLight.getIntesity();
        return color.add(gp.geometry.getEmission());
    }


}
