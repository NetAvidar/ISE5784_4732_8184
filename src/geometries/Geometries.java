package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    private final LinkedList<Object> lst = new LinkedList<>();

    public Geometries() {
    }

    public Geometries(Intersectable... geometries){
        this.add(geometries);
    }

    public void add(Intersectable... geometries){
        lst.add(geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = new LinkedList<>();
        for (Intersectable geo : lst) {
            List<Point> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null && !tempIntersections.isEmpty()) {
                intersections.addAll(tempIntersections);
            }
        }
        return intersections.isEmpty() ? null : intersections;
    }
}
