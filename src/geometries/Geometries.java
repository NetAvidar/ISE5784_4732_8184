package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

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
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = new LinkedList<>();
        for (Intersectable geo : lst) {
            List<Point> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null){// && !tempIntersections.isEmpty()) {
                intersections.addAll(tempIntersections);
            }
        }
        return intersections.isEmpty() ? null : intersections;
    }
}
