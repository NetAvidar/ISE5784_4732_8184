package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Triangle extends Polygon{
    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        List<Point> emptyList= List.of();
        List<Point> l =this.plane.findIntersections(ray);
        if(l.isEmpty()){
            return emptyList;
        }
        else{
            Point p0 =this.vertices.get(0);
            Point p1=this.vertices.get(1);
            Point p2=this.vertices.get(2);
            Point p3=ray.getHead();
            Vector v1 = p1.subtract(p0);
            Vector v2 = p2.subtract(p0);
            Vector v3 = p3.subtract(p0);
            Vector n1 =v1.crossProduct(v2);
            Vector n2 =v2.crossProduct(v3);
            Vector n3 =v3.crossProduct(v1);
            if(n1.dotProduct(n2)>0 && n2.dotProduct(n3)>0 && n3.dotProduct(n1)>0){
                return l;
            }
            else{
                return emptyList;
            }


        }


    }

}
