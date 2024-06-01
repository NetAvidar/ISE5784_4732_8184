package geometries;
import primitives.*;
import java.util.List;
//import java.util.ArrayList;

public interface Intersectable {
     List<Point> findIntersections(Ray ray);

}
