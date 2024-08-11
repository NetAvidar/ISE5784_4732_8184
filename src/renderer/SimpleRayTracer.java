package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import primitives.Color;

import java.util.List;
import static primitives.Util.alignZero;


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
     * Constructs a RayTracerBasic object with the specified scene.
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
     * @return the color of the closest intersection Geopoint or the background color if there are no intersections
     */
    //*****************************************************************************************
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor2(closestPoint, ray);
    }


    //*****************************************************************************************

    //good
    private Color calcColor1(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        if(level == 1)
            return color;
       color =  color.add(calcGlobalEffects(intersection, ray, level, k));
       return color;
    }

    //good
    private Color calcColor2(GeoPoint geopoint, Ray ray) {
        return calcColor1(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    //good
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        //there is no effect on the color
        if (nv == 0)
            return color;
        Double3 ktr ;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            // check if sign(nl) == sign(nv)
            if (nl * nv > 0){

                if(scene.isSoftShadow()) {
                   ktr =  softShadow(gp,lightSource,n);
                }
                else {
                     ktr = transparency(gp,lightSource,l,n);
                }
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    //good
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        double max = -r.dotProduct(v);
        if (alignZero(max) > 0)
            return material.kS.scale(Math.pow(max, material.Shininess));
        return Double3.ZERO;
    }

    //good
    private Double3 calcDiffusive(Material material, double nl) {
        return (material.kD.scale(Math.abs(nl)));
    }

    //
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, k, material.kT));
    }

    //good
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null ) {
            return scene.background.scale(kx);
        }
        if ((gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection()))==0) {
            return Color.BLACK;
        }
        return calcColor1(gp, ray, level - 1, kkx).scale(kx);
    }

    //good
    private Ray constructRefractedRay(Point geoPoint, Vector v, Vector n) {
        return new Ray(geoPoint, n, v);
    }

    //good
    private Ray constructReflectedRay(Point geoPoint, Vector v, Vector n) {
        // The formula is: r = v - 2 * (v.n) * n
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn)).normalize();
        return new Ray(geoPoint, n, r);
    }

    //good
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

    //good
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPointIntersections = scene.geometries.findGeoIntersections(ray);

        // Return the closest intersection point using the ray's findClosestGeoPoint method
        return ray.findClosestGeoPoint(geoPointIntersections);
    }

    //gp intresction
    //ray is ray from loction camera to gp

    private Double3 softShadow(GeoPoint gp, LightSource lightSource, Vector n) {
        //calling to function tat cuaclte the loction of the target area ,squre
        //calling to function that get the target area and return list of GeoPoint of all the GeoPoint we cuaclte i random algoritem in the target area
        //for each point in our squer we create a ray from the ray.head to it and call with this ray to transperncy function
        //we sum all the Double3 values that return from each calling (for each ray in the beam) and than divide by the amount of rays we cast from each pixel
        //^thats what we return

        //List <GeoPoint> geoPointInTheTargetArea = getCircle(...)
        Double3 sumTrascprency = Double3.ZERO;
        List <Vector> lst = lightSource.getListL(gp.point,scene.getNumOfRaysAtBeam());
        for (Vector lAroundLight: lst) {
            sumTrascprency = sumTrascprency.add(transparency(gp,lightSource,lAroundLight,n));
        }
        return  sumTrascprency.reduce(lst.size());

    }

}




//
//package renderer;
//
//import lighting.LightSource;
//import primitives.*;
//import scene.Scene;
//import geometries.Intersectable.GeoPoint;
//import primitives.Color;
//
//import java.util.LinkedList;
//import java.util.List;
//import static primitives.Util.alignZero;
//
//
//public class SimpleRayTracer extends RayTracerBase {
//
//    /**
//     * A small value used for shadow ray calculations to prevent self-shadowing artifacts.
//     */
//    private static final double DELTA = 0.1;
//    /**
//     * The maximum level of color calculation recursion.
//     */
//    private static final int MAX_CALC_COLOR_LEVEL = 10;
//
//    /**
//     * The minimum value of the reflection/refraction coefficient for color calculation termination.
//     */
//    private static final double MIN_CALC_COLOR_K = 0.001;
//
//    /**
//     * The initial reflection/refraction coefficient for color calculation.
//     */
//    private static final Double3 INITIAL_K = Double3.ONE;
//
//
//    /**
//     * Constructs a RayTracerBasic object with the specified scene.
//     *
//     * @param scene the scene to be rendered
//     */
//    public SimpleRayTracer(Scene scene) {
//        super(scene);
//    }
//
//    /**
//     * Traces a ray and calculates the color of the closest intersection point on objects in the scene.
//     *
//     * @param ray the ray to be traced
//     * @return the color of the closest intersection Geopoint or the background color if there are no intersections
//     */
//    //*****************************************************************************************
//    @Override
//    public Color traceRay(Ray ray) {
//        GeoPoint closestPoint = findClosestIntersection(ray);
//        return closestPoint == null ? scene.background
//                : calcColor2(closestPoint, ray);
//    }
//
//    public Color traceRays(List<Ray> rays) {
//        //calculate the all colors of the rays
//        Color sumColor = new Color(0, 0, 0);//initialize the sumColor with default values
//        for (Ray ray : rays) {
//            sumColor = sumColor.add(traceRay(ray));//add the color of all the rays
//        }
//        //return the average color
//        return sumColor.reduce(rays.size());
//    }
//    //*****************************************************************************************
//
//    //good
//    private Color calcColor1(GeoPoint intersection, Ray ray, int level, Double3 k) {
//        Color color = calcLocalEffects(intersection, ray, k);
//        return level == 1 ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
//    }
//
//    //good
//    private Color calcColor2(GeoPoint geopoint, Ray ray) {
//        return calcColor1(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
//    }
//
//    //good
//    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
//        Color color = gp.geometry.getEmission();
//        Vector v = ray.getDirection();
//        Vector n = gp.geometry.getNormal(gp.point);
//        double nv = alignZero(n.dotProduct(v));
//        //there is no effect on the color
//        if (nv == 0)
//            return color;
//        Material material = gp.geometry.getMaterial();
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(gp.point);
//            double nl = alignZero(n.dotProduct(l));
//            // check if sign(nl) == sign(nv)
//            if (nl * nv > 0){  // &&(unshaded(gp,l,n))) {
//                //if yes, there is effect on the color
//                Double3 ktr = transparency(gp,lightSource, l, n);
//                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
//                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
//                    color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
//                }
//            }
//        }
//        return color;
//    }
//
//    //good
//    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
//        Vector r = l.subtract(n.scale(2 * nl));
//        double max = -r.dotProduct(v);
//        if (alignZero(max) > 0)
//            return material.kS.scale(Math.pow(max, material.Shininess));
//        return Double3.ZERO;
//    }
//
//    //good
//    private Double3 calcDiffusive(Material material, double nl) {
//        return (material.kD.scale(Math.abs(nl)));
//    }
//
//    //
//    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
//        Color color = Color.BLACK;
//        Vector v = ray.getDirection();
//        Vector n = gp.geometry.getNormal(gp.point);
//        Material material = gp.geometry.getMaterial();
//        return calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, k, material.kR)
//                .add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, k, material.kT));
//    }
//
//    //good
//    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
//        Double3 kkx = k.product(kx);
//        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
//            return Color.BLACK;
//        }
//        GeoPoint gp = findClosestIntersection(ray);
//        if (gp == null) {
//            return scene.background.scale(kx);
//        }
//        if ((gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection()))==0) {
//            return Color.BLACK;
//        }
//        return calcColor1(gp, ray, level - 1, kkx).scale(kx);
//    }
//
//    //good
//    private Ray constructRefractedRay(Point geoPoint, Vector v, Vector n) {
//        return new Ray(geoPoint, n, v);
//    }
//
//    //good
//    private Ray constructReflectedRay(Point geoPoint, Vector v, Vector n) {
//        // The formula is: r = v - 2 * (v.n) * n
//        double vn = v.dotProduct(n);
//
//        if (vn == 0) {
//            return null;
//        }
//
//        Vector r = v.subtract(n.scale(2 * vn)).normalize();
//        return new Ray(geoPoint, n, r);
//    }
//
//    //good
//    private Double3 transparency(GeoPoint geoPoint,LightSource light, Vector l, Vector n) {
//        Vector shadowRay = l.scale(-1d); // from point to light source
//        Ray lightRay = new Ray(geoPoint.point, n, shadowRay);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//
//        if (intersections == null) {
//            return Double3.ONE;
//        }
//
//        Double3 ktr = Double3.ONE;
//        double pointLightD = light.getDistance(geoPoint.point);
//
//        for (GeoPoint intresectGP : intersections) {
//            if (geoPoint.point.distance(intresectGP.point) < pointLightD){
//                ktr = ktr.product(intresectGP.geometry.getMaterial().kT);
//                if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
//                    return Double3.ZERO;
//                }
//            }
//        }
//        return ktr;
//    }
//
//    //good
//    private GeoPoint findClosestIntersection(Ray ray) {
//        List<GeoPoint> geoPointIntersections = scene.geometries.findGeoIntersections(ray);
//
//        // Return the closest intersection point using the ray's findClosestGeoPoint method
//        return ray.findClosestGeoPoint(geoPointIntersections);
//    }
//
//    //gp intresction
//    //ray is ray from loction camera to gp
//
//    private Double3 softShadow(GeoPoint gp,Ray ray,LightSource light,int numOfRatsAtBeam) {
//        //calling to function tat cuaclte the loction of the target area ,squre
//        //calling to function that get the target area and return list of GeoPoint of all the GeoPoint we cuaclte i random algoritem in the target area
//        //for each point in our squer we create a ray from the ray.head to it and call with this ray to transperncy function
//        //we sum all the Double3 values that return from each calling (for each ray in the beam) and than divide by the amount of rays we cast from each pixel
//        //^thats what we return
//
////        Double3 sumTrascprency=Double3.ZERO;
////
////        for (GeoPoint pointInTargetArea : geoPointInTheTargetArea) {
////            r=new Ray(ray.getHead(),pointInTargetArea.point.subtract(ray.getHead()));
////            sumTrascprency.add(this.transparency(pointInTargetArea,
////                    light,
////                    light.getL(pointInTargetArea.point),
////                    pointInTargetArea.geometry.getNormal(pointInTargetArea.point)));
////
////        }
////        return sumTrascprency.reduce(numOfRatsAtBeam);
////    }
//        return null;
//    }
//
//
//
//    //*****************************************************************************************
//    //double pointLightD = light.getDistance(gp.point); //not revers?
//    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
//        Point point = gp.point.add(epsVector);
//        Ray lightRay = new Ray(point, lightDirection);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//        if (intersections == null)
//            return true;
//        double pointLightD = light.getDistance(gp.point); //todo: not revers?
//        for(GeoPoint intresectGP: intersections)
//        {
//            if (gp.point.distance(intresectGP.point) < pointLightD)
//                return false;
//        }
//        return true;
//    }
//
//    private boolean unshaded(GeoPoint gp, Vector l, Vector n) {
//        Vector lightDirection = l.scale(-1d); // from point to light source
//        Ray lightRay = new Ray(gp.point, lightDirection, n);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//        return intersections == null;
//    }
////*****************************************************************************************
//
//}