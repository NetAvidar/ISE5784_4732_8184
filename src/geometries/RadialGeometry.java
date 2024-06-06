    package geometries;

    import primitives.Point;
    import primitives.Ray;
    import primitives.Vector;

    import java.util.List;

    public class RadialGeometry implements Geometry {

        protected final double radius;

        public RadialGeometry(double radius) {
            this.radius = radius;
        }

        @Override
        public Vector getNormal(Point point) {
            return null;
        }


        @Override
        public List<Point> findIntersections(Ray ray){

            return List.of();
        }


    }
