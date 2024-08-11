package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import primitives.Color;

import java.util.List;
import static primitives.Util.alignZero;

/**
 * SimpleRayTracer is a basic implementation of ray tracing, used to render a scene by tracing rays
 * from the camera and calculating the color of the closest intersection points on the objects in the scene.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * A small value used for shadow ray calculations to prevent self-shadowing artifacts.
     */
    private static final double DELTA = 0.1;

    /**
     * The maximum level of color calculation recursion.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum value of the reflection/refraction coefficient for color calculation termination.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * The initial reflection/refraction coefficient for color calculation.
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructs a SimpleRayTracer object with the specified scene.
     *
     * @param scene the scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and calculates the color of the closest intersection point on objects in the scene.
     *
     * @param ray the ray to be traced
     * @return the color of the closest intersection GeoPoint or the background color if there are no intersections
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor2(closestPoint, ray);
    }

    /**
     * Calculates the color at an intersection point considering local effects such as diffuse and specular reflection.
     *
     * @param intersection the intersection point
     * @param ray          the ray that hit the intersection point
     * @param level        the current recursion level for global effects calculation
     * @param k            the cumulative reflection/refraction coefficient
     * @return the calculated color at the intersection point
     */
    private Color calcColor1(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        if (level == 1)
            return color;
        color = color.add(calcGlobalEffects(intersection, ray, level, k));
        return color;
    }

    /**
     * Calculates the color at an intersection point considering both local and ambient effects.
     *
     * @param geopoint the intersection GeoPoint
     * @param ray      the ray that hit the intersection point
     * @return the calculated color at the intersection point including ambient light
     */
    private Color calcColor2(GeoPoint geopoint, Ray ray) {
        return calcColor1(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the local effects (diffuse and specular reflection) of a given point.
     *
     * @param gp the intersection GeoPoint
     * @param ray the ray that hit the intersection point
     * @param k the cumulative reflection/refraction coefficient
     * @return the color resulting from the local effects at the intersection point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;

        Double3 ktr;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // Check if light is on the same side as the camera
                if (scene.isSoftShadow()) {
                    ktr = softShadow(gp, lightSource, n);
                } else {
                    ktr = transparency(gp, lightSource, l, n);
                }
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the specular reflection component of the lighting model.
     *
     * @param material the material properties of the intersected geometry
     * @param n        the normal vector at the intersection point
     * @param l        the direction vector from the intersection point to the light source
     * @param nl       the dot product of the normal vector and the light direction vector
     * @param v        the direction vector from the intersection point to the viewer
     * @return the calculated specular reflection coefficient
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        double max = -r.dotProduct(v);
        if (alignZero(max) > 0)
            return material.kS.scale(Math.pow(max, material.Shininess));
        return Double3.ZERO;
    }

    /**
     * Calculates the diffusive reflection component of the lighting model.
     *
     * @param material the material properties of the intersected geometry
     * @param nl       the dot product of the normal vector and the light direction vector
     * @return the calculated diffusive reflection coefficient
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the global effects (reflection and refraction) of a given point.
     *
     * @param gp    the intersection GeoPoint
     * @param ray   the ray that hit the intersection point
     * @param level the current recursion level for global effects calculation
     * @param k     the cumulative reflection/refraction coefficient
     * @return the color resulting from the global effects at the intersection point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, k, material.kT));
    }

    /**
     * Recursively calculates the global effects of reflection or refraction.
     *
     * @param ray   the reflected or refracted ray
     * @param level the current recursion level for global effects calculation
     * @param k     the cumulative reflection/refraction coefficient
     * @param kx    the reflection or refraction coefficient of the material
     * @return the color resulting from the global effect at the intersection point
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) {
            return scene.background.scale(kx);
        }
        if ((gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection())) == 0) {
            return Color.BLACK;
        }
        return calcColor1(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Constructs a refracted ray from the intersection point.
     *
     * @param geoPoint the intersection point
     * @param v        the direction vector of the incoming ray
     * @param n        the normal vector at the intersection point
     * @return the constructed refracted ray
     */
    private Ray constructRefractedRay(Point geoPoint, Vector v, Vector n) {
        return new Ray(geoPoint, n, v);
    }

    /**
     * Constructs a reflected ray from the intersection point.
     *
     * @param geoPoint the intersection point
     * @param v        the direction vector of the incoming ray
     * @param n        the normal vector at the intersection point
     * @return the constructed reflected ray
     */
    private Ray constructReflectedRay(Point geoPoint, Vector v, Vector n) {
        // The formula is: r = v - 2 * (v.n) * n
        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn)).normalize();
        return new Ray(geoPoint, n, r);
    }

/**
 * Calculates the transparency coefficient considering objects between the light source and the intersection point.
 *
 * @param geoPoint the intersection point
 * @param light    the light source
 * @param l        the direction vector from the intersection point to the light source
 * @param n        the normal vector
 * */



    private Double3 transparency(GeoPoint geoPoint,LightSource light, Vector l, Vector n) {
        Vector lightDirection  = l.scale(-1d); // from point to light source
        Ray lightRay = new Ray(geoPoint.point,n,lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null ) {
            return Double3.ONE;
        }

        Double3 ktr = Double3.ONE;
        double pointLightD = light.getDistance(geoPoint.point);

        for (GeoPoint intresectGP : intersections) {
            if (geoPoint.point.distance(intresectGP.point) < pointLightD){
                ktr = ktr.product(intresectGP.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }
        return ktr;
    }


    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPointIntersections = scene.geometries.findGeoIntersections(ray);

        // Return the closest intersection point using the ray's findClosestGeoPoint method
        return ray.findClosestGeoPoint(geoPointIntersections);
    }

    //gp intresction
    //ray is ray from loction camera to gp

    private Double3 softShadow(GeoPoint gp, LightSource lightSource, Vector n) {
        Double3 sumTrascprency = Double3.ZERO;
        List <Vector> lst = lightSource.getListL(gp.point,scene.getNumOfRaysAtBeam());
        for (Vector lAroundLight: lst) {
            sumTrascprency = sumTrascprency.add(transparency(gp,lightSource,lAroundLight,n));
        }
        return  sumTrascprency.reduce(lst.size());

    }

}

