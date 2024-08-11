package geometries;

import primitives.Color;

import java.util.ArrayList;
import java.util.List;
import primitives.*;

public class Squared3D extends Intersectable {

    List<Polygon> geometryList;

    /**
     * Constructs a Squared3D object with the specified parameters.
     *
     * @param position The position of the Squared3D.
     * @param depth    The depth of the Squared3D.
     * @param width    The width of the Squared3D.
     * @param height   The height of the Squared3D.
     * @param color    The color of the Squared3D.
     */
    public Squared3D(Point position, int depth, int width, int height, Color color, double kd, double ks, int shines) {

        // Initialize the walls

        // Front wall
        Polygon front = (Polygon) new Polygon(position,
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2(), position.getXyz().getD3()),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD1(), position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1(), position.getXyz().getD1(), position.getXyz().getD3() + height))
                .setEmission(color).setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shines));

        // Back wall
        Polygon back = (Polygon) new Polygon(new Point(position.getXyz().getD1(), position.getXyz().getD1() + depth, position.getXyz().getD3()),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD1() + depth, position.getXyz().getD3()),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2() + depth, position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1(), position.getXyz().getD2() + depth, position.getXyz().getD3() + height))
                .setEmission(color).setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shines));

        // Left wall
        Polygon left = (Polygon) new Polygon(position,
                new Point(position.getXyz().getD1(), position.getXyz().getD2(), position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1(), position.getXyz().getD2() + depth, position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1(), position.getXyz().getD2() + depth, position.getXyz().getD3()))
                .setEmission(color).setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shines));

        // Right wall
        Polygon right = (Polygon) new Polygon(new Point(position.getXyz().getD1() + width, position.getXyz().getD2(), position.getXyz().getD3()),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2(), position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2() + depth, position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2() + depth, position.getXyz().getD3()))
                .setEmission(color).setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shines));

        // Bottom wall
        Polygon down = (Polygon) new Polygon(new Point(position.getXyz().getD1(), position.getXyz().getD2(), position.getXyz().getD3()),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2(), position.getXyz().getD3()),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2() + depth, position.getXyz().getD3()),
                new Point(position.getXyz().getD1(), position.getXyz().getD2() + depth, position.getXyz().getD3()))
                .setEmission(color).setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shines));

        // Top wall
        Polygon up = (Polygon) new Polygon(new Point(position.getXyz().getD1(), position.getXyz().getD2(), position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2(), position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1() + width, position.getXyz().getD2() + depth, position.getXyz().getD3() + height),
                new Point(position.getXyz().getD1(), position.getXyz().getD2() + depth, position.getXyz().getD3() + height))
                .setEmission(color).setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shines));

        geometryList = List.of(front, back, left, right, down, up);
    }

    public List<Polygon> getGeometryList() {
        return geometryList;
    }

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> allIntersetions = new ArrayList<>();
        List<GeoPoint> onePolIntersections = new ArrayList<>();

        for (Polygon pol : geometryList) {
            onePolIntersections = pol.findGeoIntersections(ray);
            if(onePolIntersections!=null) {
                for (GeoPoint gp : onePolIntersections) {
                    allIntersetions.add(gp);
                }
            }
        }
        return allIntersetions;
    }
}
