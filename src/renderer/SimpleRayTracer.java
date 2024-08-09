//package renderer;
//
//import geometries.Intersectable;
//import geometries.Intersectable.GeoPoint;
//import lighting.LightSource;
//import primitives.Color;
//import primitives.*;
//import scene.Scene;
//import java.util.List;
//
//
//import static primitives.Util.alignZero;
//
//
//public class SimpleRayTracer extends RayTracerBase {
//
//
//    private static final double DELTA = 0.02;
//    private static final int MAX_CALC_COLOR_LEVEL = 10;   //todo: maybe need to change in the futre
//    private static final double MIN_CALC_COLOR_K = 0.001; //todo: maybe need to change in the futre
//
//    private static final Double3 INITIAL_K = Double3.ONE; //todo: where its apper?
//
//
//    //constructor
//    public SimpleRayTracer(Scene scene) {
//        super(scene);
//    }
//
//    //functions
//    public Color traceRay(Ray ray) {
//        List<GeoPoint> lst = scene.geometries.findGeoIntersections(ray);
//        if (lst == null) {
//            return scene.background;
//        }
//
//        GeoPoint gp = ray.findClosestGeoPoint(lst);
//        if (gp != null)
//            return calcColor(gp,ray); // calling the main function to culc //todo: should match the parmetrs
//        return scene.background;
//    }
//

//
//    private Color calcColor(GeoPoint gp, Ray ray)//The main function -not recoursive
//    {
//        return scene.ambientLight.getIntensity().
//                add(calcLocalEffects(gp,ray))
//                .add(calcGlobalEffects(gp, ray));
//        return color;
//    }
//
//    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
//        Color color = calcLocalEffects(intersection, ray, k);
//        return level == 1 ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
//    }
//
////    private Color calcColor(GeoPoint gp, Ray ray) //
////    {
////        return Color.BLACK;
////    }
//
//    private Color calcGlobalEffect(Ray ray, Double3 kx) {
//        GeoPoint gp = findClosestIntersection(ray);
//        return (gp == null ? scene.background : calcColor(gp, ray)).scale(kx);
//    }
//
//     private GeoPoint findClosestIntersection(Ray ray) {
////         List<GeoPoint> intersections = p.findGeoIntersections(ray);
////         if (intersections==null) return null;
////         GeoPoint closest = intersections.getFirst();
////         double minDistance =
////         for (intersections: GeoPoint gp) {
////
////
////         }
//     }
//
//
////    public static Point findNearestPoint(List<Point> points, Point target) {
////        if (points == null || points.isEmpty()) {
////            throw new IllegalArgumentException("The points list cannot be null or empty.");
////        }
////
////        Point nearestPoint = points.get(0);
////        double minDistance = nearestPoint.distanceTo(target);
////
////        for (Point point : points) {
////            double distance = point.distanceTo(target);
////            if (distance < minDistance) {
////                minDistance = distance;
////                nearestPoint = point;
////            }
////        }
////
////        return nearestPoint;
////    }
////        {
////            if (gp.point.distance(intresectGP.point) < pointLightD)
////                return false;
////        }
////    }
//
//
//    private Color calcGlobalEffects(GeoPoint gp, Ray ray)  // The recoursive function for the global affects
//   {
//        Vector n = gp.geometry.getNormal(gp.point);
//        Material material = gp.geometry.getMaterial();
//        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kT)
//                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kR);
//    }
//
//    private Color calcLocalEffects(GeoPoint gp, Ray ray)  //non recoursive (and the only) function for local affets
//    {
//        Vector n = gp.geometry.getNormal(gp.point).normalize();
//        Vector v = ray.getDirection().normalize();
//        double nv = alignZero(n.dotProduct(v));
//        Color color = gp.geometry.getEmission();
//        if (nv == 0) return color;
//
//        Material material = gp.geometry.getMaterial();
//
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(gp.point);
//            double nl = alignZero(n.dotProduct(l));
//            if (nl * nv > 0 &&(unshaded(gp,l,n,nl,lightSource))) { // sign(nl) == sign(nv) and not shaded
//                Color il = lightSource.getIntensity(gp.point);
//
//                color = color.add(il.scale(calcDiffusive(material, nl)), il.scale(calcSpecular(material, n, l, nl, v)));
//            }
//        }
//        return color;
//    }
//
//    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
//        Vector r = l.subtract(n.scale(2 * nl));
//        double max = -r.dotProduct(v);
//        if (alignZero(max) > 0)
//            return material.kS.scale(Math.pow(max, material.Shininess));
//        return Double3.ZERO;
//    }
//
//    private Double3 calcDiffusive(Material material, double nl) {
//        return (material.kD.scale(Math.abs(nl)));     //kd*|nl|
//               // .add(material.kS.scale(Math.pow(Math.max(0, v.dotProduct(r)*-1),material.Shininess))));
//    }
//
//
//    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
//        Color color = Color.BLACK;
//        Vector v = ray.getDirection();
//        Vector n = gp.geometry.getNormal(gp.point);
//        Material material = gp.geometry.getMaterial();
//        return calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, k, material.kR)
//                .add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, k, material.kT));
//    }
//
//    /**
//     * Calculates the color from a single global effect (reflection or refraction) at a given ray.
//     * Performs recursive calculations for the reflection or refraction ray.
//     *
//     * @param ray   the reflection or refraction ray
//     * @param level the current recursion level
//     * @param k     the reflection/refraction coefficient
//     * @param kx    the reflection/refraction color coefficient
//     * @return the calculated color from the global effect
//     */
//    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
//        Double3 kkx = k.product(kx);
//        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
//            return Color.BLACK;
//        }
//        GeoPoint gp = findClosestIntersection(ray);
//        if (gp == null) {
//            return scene.background.scale(kx);
//        }
//        if (isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDirection()))) {
//            return Color.BLACK;
//        }
//        return calcColor(gp, ray, level - 1, kkx).scale(kx);
//    }
//
//    /**
//     * Constructs a refracted ray from the given intersection point, incoming direction, and surface normal.
//     *
//     * @param geoPoint the intersection point
//     * @param v        the incoming direction
//     * @param n        the surface normal
//     * @return the constructed refracted ray
//     */
//    private Ray constructRefractedRay(Point geoPoint, Vector v, Vector n) {
//        return new Ray(geoPoint, n, v);
//    }
//
//    /**
//     * Constructs a reflected ray from the given intersection point, incoming direction, and surface normal.
//     *
//     * @param geoPoint the intersection point
//     * @param v        the incoming direction
//     * @param n        the surface normal
//     * @return the constructed reflected ray
//     */
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
//
//
//}



package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import primitives.Color;

import java.util.LinkedList;
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

    private boolean softShadow;
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
    public SimpleRayTracer setSoftShadow(boolean softShadow) {
        this.softShadow = softShadow;
        return this;
    }

    //*****************************************************************************************
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor2(closestPoint, ray);
    }

    public Color traceRays(List<Ray> rays) {
        //calculate the all colors of the rays
        Color sumColor = new Color(0, 0, 0);//initialize the sumColor with default values
        for (Ray ray : rays) {
            sumColor = sumColor.add(traceRay(ray));//add the color of all the rays
        }
        //return the average color
        return sumColor.reduce(rays.size());
    }
    //*****************************************************************************************

    //good
    private Color calcColor1(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
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
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            // check if sign(nl) == sign(nv)
            if (nl * nv > 0){  // &&(unshaded(gp,l,n))) {
                //if yes, there is effect on the color
                Double3 ktr = transparency(gp,lightSource, l, n);
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
        if (gp == null) {
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
        Vector shadowRay = l.scale(-1d); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, n, shadowRay);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null) {
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

    private Double3 softShadow(GeoPoint gp,Ray ray,LightSource light,int numOfRatsAtBeam){
        //calling to function tat cuaclte the loction of the target area ,squre
        //calling to function that get the target area and return list of GeoPoint of all the GeoPoint we cuaclte i random algoritem in the target area
        //for each point in our squer we create a ray from the ray.head to it and call with this ray to transperncy function
        //we sum all the Double3 values that return from each calling (for each ray in the beam) and than divide by the amount of rays we cast from each pixel
        //^thats what we return

        Ray r;
        Double3 sumTrascprency=Double3.ZERO;

//        for (GeoPoint pointInTargetArea : geoPointInTheTargetArea) {
//            r=new Ray(ray.getHead(),pointInTargetArea.point.subtract(ray.getHead()));
//            sumTrascprency.add(this.transparency(pointInTargetArea,
//                    light,
//                    light.getL(pointInTargetArea.point),
//                    pointInTargetArea.geometry.getNormal(pointInTargetArea.point)));
//
//        }
//        return sumTrascprency.reduce(numOfRatsAtBeam);
//        return null;
    }



//*****************************************************************************************
   //double pointLightD = light.getDistance(gp.point); //not revers?
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true;
        double pointLightD = light.getDistance(gp.point); //todo: not revers?
        for(GeoPoint intresectGP: intersections)
        {
            if (gp.point.distance(intresectGP.point) < pointLightD)
                return false;
        }
        return true;
    }

    private boolean unshaded(GeoPoint gp, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1d); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        return intersections == null;
    }
//*****************************************************************************************

}


//package renderer;
//
//import geometries.Intersectable.GeoPoint;
//import lighting.LightSource;
//import primitives.Color;
//import primitives.*;
//import scene.Scene;
//import geometries.*;
//import java.util.List;
//
//import static primitives.Util.alignZero;
//
//
//public class SimpleRayTracer extends RayTracerBase {
//
//    private static final double DELTA = 0.02;
//    private static final int MAX_CALC_COLOR_LEVEL = 10;
//    private static final double MIN_CALC_COLOR_K = 0.001;
//
//    //constructor
//    public SimpleRayTracer(Scene scene) {
//        super(scene);
//    }
//
//    //functions
//    public Color traceRay(Ray ray) {
//        List<GeoPoint> lst = scene.geometries.findGeoIntersections(ray);
//        if (lst == null) {
//            return scene.background;
//        }
//        GeoPoint gp = ray.findClosestGeoPoint(lst);
//        if (gp != null)
//            return calcColor(gp,ray);
//        return scene.background;
//    }
//
////    private Color calcColor(GeoPoint gp, Ray ray) {
////        Color Ip;
////        Material mat = gp.geometry.getMaterial();
////        Color Il = scene.ambientLight.getIntesity().add(gp.geometry.getEmission());
////        Vector n = gp.geometry.getNormal(gp.point);
////        Ip = scene.ambientLight.getIntesity().add(gp.geometry.getEmission());
////        // for (Intersectable geo : lst)
////        for(LightSource i : scene.lights)
////        {
////
////
//////            Vector l = i.getL(gp.point);
//////            Vector v = null; //todo:fix it
//////            Vector r = l.subtract(l.crossProduct(n).crossProduct(n)).scale(2);//todo:  crossProduct??;
//////            Double3 d3 =(mat.kD.scale(l.dotProduct(n))
//////                    .add(mat.kS.scale(Math.pow(Math.max(0, v.dotProduct(r)*-1),mat.Shininess))));
//////            Color It = null; //
//////            It = It.scale(i.getIntensity(gp.point));
////        }
//    // return scene.ambientLight.getIntesity().add(gp.geometry.getEmission());
////    }
//
//    private Color calcColor(GeoPoint gp, Ray ray){
//        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp,ray));
//    }
//
//    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
//        Vector n = gp.geometry.getNormal(gp.point).normalize();
//        Vector v = ray.getDirection().normalize();
//        double nv = alignZero(n.dotProduct(v));
//        Color color = gp.geometry.getEmission();
//        if (nv == 0) return color;
//
//        Material material = gp.geometry.getMaterial();
//
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(gp.point);
//            double nl = alignZero(n.dotProduct(l));
//            if (nl * nv > 0 &&(unshaded(gp, l, n,nl,lightSource))) { // sign(nl) == sign(nv)
//                Color il = lightSource.getIntensity(gp.point);
//                // color = color.add(il.scale(calcDiffusive(material, nl)) //Il  *  kd*|nl|
//                //        .add(calcSpecular(il,material, n, l, nl, v)));     //ks*(max(0,-v*r))^Shininess*Il
//
//                color = color.add(il.scale(calcDiffusive(material, nl)), il.scale(calcSpecular(material, n, l, nl, v)));
//                //color = color.add(il.scale(calcDiffusive(material, nl)), il.scale(calcSpecular(material, n, l, nl, v)));
//            }
//        }
//        return color;
//    }
////    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
////        Vector n = gp.geometry.getNormal(gp.point).normalize();
////        Vector v = ray.getDirection().normalize();
////        double nv = alignZero(n.dotProduct(v));
////        Color color = gp.geometry.getEmission();
////
////        if (nv == 0) {return color;}
////
////        Material material = gp.geometry.getMaterial();
////
////        for (LightSource lightSource : scene.lights) {
////            Vector l = lightSource.getL(gp.point);
////            double nl = alignZero(n.dotProduct(l));
////
////            if ((nl * nv > 0) && (unshaded(gp, l, n))) { // sign(nl) == sign(nv)
////                Color Il = lightSource.getIntensity(gp.point);
////                Double3 diffusive=calcDiffusive(material, nl); // Diffusive coucltion //Il  *  kd*|nl|
////                Double3 specular=calcSpecular(material, n, l, nl, v); //Specular coucltion //ks*(max(0,-v*r))^Shininess*Il
////                Double3 diffusiveSpecular=diffusive.add(specular); //add them
////                color =color.add(Il.scale(diffusiveSpecular)); //multiply by Il
////
////            }
////        }
////        return color;
////    }
//
//    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
//        Vector r = l.subtract(n.scale(2 * nl));
//        double max = -r.dotProduct(v);
//        if (alignZero(max) > 0)
//            return material.kS.scale(Math.pow(max, material.Shininess));
//        return Double3.ZERO;
//    }
//
//    private Double3 calcDiffusive(Material material, double nl) {
//        return (material.kD.scale(Math.abs(nl)));     //kd*|nl|
//        // .add(material.kS.scale(Math.pow(Math.max(0, v.dotProduct(r)*-1),material.Shininess))));
//    }
//
//    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
//        Point point = gp.point.add(epsVector);
//        Ray lightRay = new Ray(point, lightDirection);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//        if (intersections == null) return true;
//        double pointLightD = light.getDistance(gp.point);
//        for(GeoPoint intresectGP: intersections)
//        {
//            if (gp.point.distance(intresectGP.point) < pointLightD)
//                return false;
//        }
//        return true;
//    }
//
//
//
//
//}
//
//
//
////notes-
//// color = color.add(il.scale(calcDiffusive(material, nl)) //Il  *  kd*|nl|
////        .add(calcSpecular(il,material, n, l, nl, v)));     //ks*(max(0,-v*r))^Shininess*Il
////color = color.add(Il.scale(calcDiffusive(material, nl)));
////color = color.add(Il.scale(calcSpecular(material, n, l, nl, v)));
////color = color.add(Il.scale(calcDiffusive(material, nl)), Il.scale(calcSpecular(material, n, l, nl, v)));
//
//
//
//
//
//
//
//
//
//
//
//
////package renderer;
////
////import geometries.Intersectable.GeoPoint;
////import lighting.LightSource;
////import primitives.Color;
////import primitives.*;
////import scene.Scene;
////import java.util.List;
////
////import static primitives.Util.alignZero;
////
////
////public class SimpleRayTracer extends RayTracerBase {
////
////    //constructor
////    public SimpleRayTracer(Scene scene) {
////        super(scene);
////    }
////
////    //functions
////    public Color traceRay(Ray ray) {
////        List<GeoPoint> lst = scene.geometries.findGeoIntersections(ray);
////        if (lst == null) {
////            return scene.background;
////        }
////
////        GeoPoint gp = ray.findClosestGeoPoint(lst);
////        if (gp != null)
////            return calcColor(gp,ray);
////        return scene.background;
////    }
////
//////    private Color calcColor(GeoPoint gp, Ray ray) {
//////        Color Ip;
//////        Material mat = gp.geometry.getMaterial();
//////        Color Il = scene.ambientLight.getIntesity().add(gp.geometry.getEmission());
//////        Vector n = gp.geometry.getNormal(gp.point);
//////        Ip = scene.ambientLight.getIntesity().add(gp.geometry.getEmission());
//////        // for (Intersectable geo : lst)
//////        for(LightSource i : scene.lights)
//////        {
//////
//////
////////            Vector l = i.getL(gp.point);
////////            Vector v = null; //todo:fix it
////////            Vector r = l.subtract(l.crossProduct(n).crossProduct(n)).scale(2);//todo:  crossProduct??;
////////            Double3 d3 =(mat.kD.scale(l.dotProduct(n))
////////                    .add(mat.kS.scale(Math.pow(Math.max(0, v.dotProduct(r)*-1),mat.Shininess))));
////////            Color It = null; //
////////            It = It.scale(i.getIntensity(gp.point));
//////        }
////    // return scene.ambientLight.getIntesity().add(gp.geometry.getEmission());
//////    }
////
////    private Color calcColor(GeoPoint gp, Ray ray){
////        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp,ray));
////    }
////
////    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
////        Vector n = gp.geometry.getNormal(gp.point);
////        Vector v = ray.getDirection();
////        double nv = alignZero(n.dotProduct(v));
////        Color color = gp.geometry.getEmission();
////        if (nv == 0) return color;
////
////        Material material = gp.geometry.getMaterial();
////
////        for (LightSource lightSource : scene.lights) {
////            Vector l = lightSource.getL(gp.point);
////            double nl = alignZero(n.dotProduct(l));
////            if (nl * nv > 0) { // sign(nl) == sign(nv)
////                Color il = lightSource.getIntensity(gp.point);
////                // color = color.add(il.scale(calcDiffusive(material, nl)) //Il  *  kd*|nl|
////                //        .add(calcSpecular(il,material, n, l, nl, v)));     //ks*(max(0,-v*r))^Shininess*Il
////
////
////                color = color.add(il.scale(calcDiffusive(material, nl)), il.scale(calcSpecular(material, n, l, nl, v)));
////                //color = color.add(il.scale(calcDiffusive(material, nl)), il.scale(calcSpecular(material, n, l, nl, v)));
////            }
////        }
////        return color;
////    }
////
////    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
////        Vector r = l.subtract(n.scale(2 * nl));
////        double max = -r.dotProduct(v);
////        if (alignZero(max) > 0)
////            return material.kS.scale(Math.pow(max, material.Shininess));
////        return Double3.ZERO;
////    }
////
////    private Double3 calcDiffusive(Material material, double nl) {
////        return (material.kD.scale(Math.abs(nl)));     //kd*|nl|
////        // .add(material.kS.scale(Math.pow(Math.max(0, v.dotProduct(r)*-1),material.Shininess))));
////    }
////
////
////}