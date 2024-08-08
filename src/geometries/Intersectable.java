package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;
//import java.util.ArrayList;

public abstract class Intersectable {

     public static class GeoPoint {
          public Geometry geometry;
          public Point point;

          public GeoPoint(Geometry geo, Point p) {
               this.point = p;
               this.geometry = geo;
          }


          public Geometry getGeometry() {
               return geometry;
          }

          @Override
          public boolean equals(Object other) {
               if (!(other instanceof GeoPoint gp)) return false; //use instanceof like Dan asked
               return geometry == gp.geometry  &&
                       Objects.equals(point, gp.point);
          }

          @Override
          public String toString() {
               return "GeoPoint{" +
                       "geometry=" + geometry +
                       ", point=" + point +
                       '}';
          }
     }


     public List<Point> findIntersections(Ray ray) {
          var geoList = findGeoIntersections(ray);
          return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
     }

     public List <GeoPoint> findGeoIntersections (Ray ray){
          return this.findGeoIntersectionsHelper(ray);
     }

     protected abstract List <GeoPoint> findGeoIntersectionsHelper(Ray ray);

}