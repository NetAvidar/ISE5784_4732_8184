package renderer;
import primitives.*;

import java.lang.module.ModuleDescriptor;
import java.util.MissingResourceException;

public class Camera implements Cloneable{

    private Point location;

    //diraction of the camera
    private Vector Vup;
    private Vector Vto;
    private Vector Vright;

    //View plane
    double height=0.0;
    double width=0.0;
    double distance=0.0;
    //from camera


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
        //location????
        this.height=0.0;
        this.width=0.0;
        this.distance=0.0;
    }

    //functions
    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }

    public static Builder getBuilder() {
        Builder b=new Builder();
        return b;
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

        //func build
        public Camera build(){
            String Scamera = "Camera ";
            if(camera.height==0 ){
                throw new MissingResourceException("height data is missing ",Scamera,"height");
            }
            if(camera.distance==0){
                throw new MissingResourceException("distance data is missing ",Scamera,"distance");
            }
            if(camera.width==0){
                throw new MissingResourceException("width data is missing ",Scamera,"width");
            }
            if(camera.Vto.equals(0)){
                throw new MissingResourceException("Vector to data is missing ",Scamera,"Vto");
            }
            if(camera.Vup.equals(0)){
                throw new MissingResourceException("Vector up data is missing ",Scamera,"Vup");
            }
//            if(camera.Vright.equals(0)){
//                throw new MissingResourceException("Vector right data is missing ",Scamera,"Vright");
//            }
            if(camera.location.equals(0)){
                throw new MissingResourceException("Point location data is missing ",Scamera,"location");
            }

            camera.Vright= camera.Vup.crossProduct(camera.Vto);

            //normalize
            camera.Vto.normalize();
            camera.Vright.normalize();
            camera.Vup.normalize();

            try {
                return (Camera) camera.clone();
            }
            catch (CloneNotSupportedException e){
                throw new RuntimeException(e);
            }
        }

    }
}
