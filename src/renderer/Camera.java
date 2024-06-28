package renderer;
import primitives.*;

import java.lang.module.ModuleDescriptor;
import java.util.MissingResourceException;

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

    private static ImageWriter imageWriter;
    private static RayTracerBase rayTracer;



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

    //constractor
    private Camera(){
        Vright=null;
        Vto=null;
        Vup=null;
        location = new Point(0,0,0);
        this.height=0.0;
        this.width=0.0;
        this.distance=0.0;
    }

    //functions
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pC = location.add(Vto.scale(distance));
        double rY = nX/(width); // ImageWriter.getNy();
        double rX = nY/(height); // ImageWriter.getNx();
        double yi = (i - nY / 2d) + rY + (rY / 2d);
        double xj = (j - nX / 2d) + rX + (rX / 2d);
        Point pIJ = pC;
        if (yi != 0) {
            pIJ = pIJ.add(Vup.scale(-yi));
        }
        if (xj != 0) {
            pIJ = pIJ.add(Vright.scale(xj));
        }
        Vector vIJ = pIJ.subtract(location).normalize();
        return new Ray(location,vIJ);

    }

    public static Builder getBuilder() {
        Builder b=new Builder();
        return b;
    }

    public Camera renderImage(){
        //throw new UnsupportedOperationException("This operation is not supported - throw from renderImage"); todo: they have asked this function will be void but in the tests there is a use in the return value of this function.
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                castRay(imageWriter.getNx()/width,imageWriter.getNy()/height,j,i);
            }
        }
        return this;
    }
    
    private void castRay(double rx,double ry,int j, int i){
        Ray ray =constructRay(imageWriter.getNx(), imageWriter.getNy(),j,i);
        Color clr = rayTracer.traceRay(ray);
        imageWriter.writePixel(j,i,clr);
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
            if(vup.dotProduct(vto)!=0){
                throw new IllegalArgumentException("vectors up and to are not orthogonal");
            }
            camera.Vup=vup.normalize();
            camera.Vto=vto.normalize();
            return this;

        }
        public Builder setVpSize(double height,double width){
            if(height<0){
                throw new IllegalArgumentException("The height is negative");
            }
            if(width<0){
                throw new IllegalArgumentException("The width is negative");
            }
            camera.width = width;
            camera.height = height;
            return this;
        }
        public Builder setVpDistance(double distance) {
            if(distance<0){
                throw new IllegalArgumentException("The distance is negative");
            }
            camera.distance = distance;
            return this;
        }

        public Builder setImageWriter(ImageWriter im) {
            imageWriter = im;
            return this;
        }

        public Builder setRayTracer(RayTracerBase rt) {
            rayTracer = rt;
            return this;
        }


      //  @override
        //Object clone ()

        //func build
        public Camera build(){
            String Scamera = "Camera ";
            //normalize
            camera.Vto.normalize();
            camera.Vup.normalize();

            if(camera.height==0 ){
                throw new MissingResourceException("height data is missing ",Scamera,"height");
            }
            if(camera.distance==0){
                throw new MissingResourceException("distance data is missing ",Scamera,"distance");
            }
            if(camera.width==0){
                throw new MissingResourceException("width data is missing ",Scamera,"width");
            }
            if(camera.Vto.equals(new Vector(0,0,0))){
                throw new MissingResourceException("Vector to data is missing ",Scamera,"Vto");
            }
            if(camera.Vup.equals(new Vector(0,0,0))){
                throw new MissingResourceException("Vector up data is missing ",Scamera,"Vup");
            }
            camera.Vright= camera.Vup.crossProduct(camera.Vto);
            camera.Vright.normalize();
            if(camera.Vright.equals(new Vector(0,0,0))){
               throw new MissingResourceException("Vector right data is missing ",Scamera,"Vright");
            }
            if(camera.location.equals(null)){
                throw new MissingResourceException("Point location data is missing ",Scamera,"location");
            }
            if(rayTracer.equals(null)){
                throw new MissingResourceException("rayTracer data is missing ",Scamera,"rayTracer");
            }
            if(imageWriter.equals(null)){
                throw new MissingResourceException("imageWriter data is missing ",Scamera,"imageWriter");
            }

            return camera;


            /*try {
                return (Camera) camera.clone();
            }
            catch (CloneNotSupportedException e){
                throw new RuntimeException(e);         }*/
        }//end of build

    }//end of Builder

}//end of Camera
