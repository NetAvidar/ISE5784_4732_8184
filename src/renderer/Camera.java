package renderer;
import primitives.*;

import java.lang.module.ModuleDescriptor;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera implements Cloneable{

    private Point location = null;

    //diraction of the camera
    private Vector Vup;
    private Vector Vto;
    private Vector Vright;

    //View plane
    double height=0.0;
    double width=0.0;
    double distance=0.0;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    private int radiusOfTargetArea = 0;


    //setters & getters
    public Point getLocation() {
        return location;
    }
    public Vector getVup() {
        return Vup;
    }
    public Vector getVto() {
        return Vto;
    }
    public double getDistance() {
        return distance;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public Vector getVright() {
        return Vright;
    }
    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    public void setVup(Vector vup) {
        Vup = vup;
    }
    public void setVto(Vector vto) {
        Vto = vto;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void setVright(Vector vright) {
        Vright = vright;
    }
    public void setLocation(Point location) {
        this.location = location;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public Camera setRadiusOfTargetArea(int radiusOfTargetArea) {
        this.radiusOfTargetArea = radiusOfTargetArea;
        return this;
    }

    //constractor
    private Camera(){
        Vright=null;
        Vto=null;
        Vup=null;
        location = new Point(0,0,0);
        this.height=0.0;
        this.width=0.0;
        this.distance=0.0;
        //imageWriter=;
        //rayTracer=;
    }

    //functions
    public Ray constructRay(int nX, int nY, int j, int i) {

        Point pC = location.add(Vto.scale(distance));
        double Rx = width / nX;
        double Ry = height / nY;
        double xJ = (j - (double) (nX - 1) / 2) * Rx;
        double yI = -(i - (double) (nY - 1) / 2) * Ry;
        Vector pIJ = new Vector(pC.getXyz());
        if (xJ != 0)
            pIJ = pIJ.add(Vright.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(Vup.scale(yI));
        Vector vIJ = pIJ.subtract(location);
        return new Ray(location, vIJ);

    }
    public static Builder getBuilder() {
        Builder b=new Builder();
        return b;
    }
    public Camera renderImage(){
        //throw new UnsupportedOperationException("This operation is not supported - throw from renderImage"); todo: they have asked this function will be void but in the tests there is a use in the return value of this function.
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                castRay(nX,nY ,j,i);
            }
        }
        return this;
    }
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = constructRay(nX, nY, col, row);
        Color pixelColor = this.rayTracer.traceRay(ray);
        imageWriter.writePixel(col, row, pixelColor);
    }
    public Camera printGrid (int interval, Color color){
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if(i%interval==0 || j%interval==0)
                    imageWriter.writePixel(j,i, color);
            }
        }
        return this; //todo: they have asked this function will be void but in the tests there is a use in the return value of this function.
    }
    public void writeToImage() {
        imageWriter.writeToImage();
    }


    //class Builder
    public static class Builder{
        final private Camera camera ;

        //constractors
        public Builder() {
            this.camera = new Camera();
        }
        public Builder(Camera camera) {
            this.camera = camera;
        }

        //functions
        public Builder setLocation(Point location) {
//            if(####){
//                throw new IllegalArgumentException("######","######",####);
//            }
            camera.location = location;
            return this;
        }
        public Builder setDirection(Vector vup,Vector vto){
            if(!isZero(vto.dotProduct(vup))){
                throw new IllegalArgumentException("vectors up and to are not orthogonal");
            }
            camera.Vup=vup.normalize();
            camera.Vto=vto.normalize();
            return this;

        }
        public Builder setVpSize(double height,double width){
            if (alignZero(width) <= 0 || alignZero(height) <= 0)
                throw new IllegalArgumentException("View Plane size values must be positive");
            camera.width = width;
            camera.height = height;
            return this;
        }
        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            return this;
        }
        public Builder setImageWriter(ImageWriter im) {
            camera.imageWriter = im;
            return this;
        }
        public Builder setRayTracer(RayTracerBase rt) {
            camera.rayTracer = rt;
            return this;
        }


        //func build
        public Camera build(){
            //i deleted the distance check becuse there is not roll about it (can be negitve)

            String Scamera = "Camera ";
            //normalize
            camera.Vto.normalize();
            camera.Vup.normalize();

            if(alignZero(camera.height) <= 0){
                throw new MissingResourceException("height data is missing ",Scamera,"height");
            }

            if(alignZero(camera.width) <= 0){
                throw new MissingResourceException("width data is missing ",Scamera,"width");
            }
            if(camera.Vto==null){
                throw new MissingResourceException("Vector to data is missing ",Scamera,"Vto");
            }
            if(camera.Vup==null){
                throw new MissingResourceException("Vector up data is missing ",Scamera,"Vup");
            }

            camera.Vright= camera.Vto.crossProduct(camera.Vup);

            camera.Vright.normalize();
            if(camera.Vright==null){
               throw new MissingResourceException("Vector right data is missing ",Scamera,"Vright");
            }
            if(camera.location==null){
                throw new MissingResourceException("Point location data is missing ",Scamera,"location");
            }
            if(camera.rayTracer==null){
                throw new MissingResourceException("rayTracer data is missing ",Scamera,"rayTracer");
            }
            if(camera.imageWriter==null){
                throw new MissingResourceException("imageWriter data is missing ",Scamera,"imageWriter");
            }
            Point pC = camera.location.add(camera.Vto.scale(camera.distance));
            if (pC == null)
                throw new MissingResourceException("distance to view plane", Scamera, "distance");


            return camera;
//            try {
//                return (Camera) camera.clone();
//            }
//            catch (CloneNotSupportedException e){
//                return null;
//             }

        } //end of build

    }//end of Builder

}//end of Camera
