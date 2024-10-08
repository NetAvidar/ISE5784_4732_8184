package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable{

    private final LinkedList<Intersectable> lst = new LinkedList<>();


    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            lst.add(geometry);
        }
    }

    public Geometries() {
    }

    public Geometries(Intersectable... geometries){
        this.add(geometries);
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new LinkedList<>();
        for (Intersectable gp : lst) {
            List<GeoPoint> tempIntersections = gp.findGeoIntersectionsHelper(ray);
            if (tempIntersections != null){// && !tempIntersections.isEmpty()) {
                intersections.addAll(tempIntersections);
            }
        }
        return intersections.isEmpty() ? null : intersections;
    }


}
