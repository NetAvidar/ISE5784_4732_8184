package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.*;


import java.util.ArrayList;
import java.util.List;

public class Triangle extends Polygon{
    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        List<GeoPoint> l =this.plane.findGeoIntersectionsHelper(ray);
        Vector v = ray.getDirection();

        if(l==null){
            return null;
        }
        else{
            Point p0 =this.vertices.getFirst();
            Point p1=this.vertices.get(1);
            Point p2=this.vertices.getLast();

            Vector v1 = p0.subtract(ray.getHead());
            Vector v2 = p1.subtract(ray.getHead());
            Vector v3 = p2.subtract(ray.getHead());

            Vector n1 =v1.crossProduct(v2);
            Vector n2 =v2.crossProduct(v3);
            Vector n3 =v3.crossProduct(v1);

            var a = v.dotProduct(n1);
            var b = v.dotProduct(n2);
            var c = v.dotProduct(n3);

            if((a>0 && b>0 && c>0) || (a<0 && b<0 && c<0)){
                GeoPoint planeInter = l.getFirst();
                List<GeoPoint> ans = new ArrayList<>();
                planeInter.geometry = this;
                ans.add(planeInter);
                return ans;
            }
            else{
                return null;
            }
        }
    }
}