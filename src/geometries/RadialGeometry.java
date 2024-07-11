    package geometries;

    import primitives.Point;
    import primitives.Ray;
    import primitives.Vector;

    import java.util.List;

    public class RadialGeometry extends Geometry {
//////////////////////////////////////////////////////////////////////////
        protected final double radius;

 //////////////////////////////////////////////////////////////////////////
        public RadialGeometry(double radius) {
            this.radius = radius;
        }
        @Override
        public Vector getNormal(Point point) {
            return null;
        }
        public double getRadius() {
            return radius;
        }

//////////////////////////////////////////////////////////////////////////
        @Override
        protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){return List.of();};


    }
