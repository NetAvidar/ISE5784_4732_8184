package renderer;

import primitives.*;

import java.lang.module.ModuleDescriptor;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a camera in a 3D scene.
 * Responsible for constructing rays through pixels on the view plane and rendering images.
 */
public class Camera implements Cloneable {

    private Point location = null;

    // Direction vectors of the camera
    private Vector Vup;
    private Vector Vto;
    private Vector Vright;

    // View plane dimensions and distance
    double height = 0.0;
    double width = 0.0;
    double distance = 0.0;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    private int radiusOfTargetArea = 0;

    // Setters & Getters

    /**
     * Gets the camera location.
     *
     * @return the location point
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Gets the up direction vector.
     *
     * @return the Vup vector
     */
    public Vector getVup() {
        return Vup;
    }

    /**
     * Gets the "to" direction vector.
     *
     * @return the Vto vector
     */
    public Vector getVto() {
        return Vto;
    }

    /**
     * Gets the distance to the view plane.
     *
     * @return the distance value
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width value
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the view plane.
     *
     * @return the height value
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the right direction vector.
     *
     * @return the Vright vector
     */
    public Vector getVright() {
        return Vright;
    }

    /**
     * Gets the image writer.
     *
     * @return the ImageWriter object
     */
    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    /**
     * Sets the up direction vector.
     *
     * @param vup the up vector to set
     */
    public void setVup(Vector vup) {
        Vup = vup;
    }

    /**
     * Sets the "to" direction vector.
     *
     * @param vto the to vector to set
     */
    public void setVto(Vector vto) {
        Vto = vto;
    }

    /**
     * Sets the width of the view plane.
     *
     * @param width the width value to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Sets the height of the view plane.
     *
     * @param height the height value to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Sets the right direction vector.
     *
     * @param vright the right vector to set
     */
    public void setVright(Vector vright) {
        Vright = vright;
    }

    /**
     * Sets the camera location.
     *
     * @param location the point location to set
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * Sets the distance to the view plane.
     *
     * @param distance the distance value to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Sets the radius of the target area.
     *
     * @param radiusOfTargetArea the radius value to set
     * @return the Camera object for chaining
     */
    public Camera setRadiusOfTargetArea(int radiusOfTargetArea) {
        this.radiusOfTargetArea = radiusOfTargetArea;
        return this;
    }

    // Constructor

    /**
     * Private constructor for the Camera class.
     */
    private Camera() {
        Vright = null;
        Vto = null;
        Vup = null;
        location = new Point(0, 0, 0);
        this.height = 0.0;
        this.width = 0.0;
        this.distance = 0.0;
        // imageWriter and rayTracer are to be set via Builder
    }

    // Functions

    /**
     * Constructs a ray through a specific pixel on the view plane.
     *
     * @param nX number of pixels in the x-axis
     * @param nY number of pixels in the y-axis
     * @param j  pixel's column index
     * @param i  pixel's row index
     * @return the constructed Ray object
     */
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

    /**
     * Retrieves the Builder object for constructing a Camera instance.
     *
     * @return the Builder instance
     */
    public static Builder getBuilder() {
        Builder b = new Builder();
        return b;
    }

    /**
     * Renders the image by casting rays through each pixel and writing the color.
     *
     * @return the Camera object for chaining
     */
    public Camera renderImage() {
        // throw new UnsupportedOperationException("This operation is not supported - throw from renderImage"); todo: they have asked this function will be void but in the tests there is a use in the return value of this function.
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                castRay(nX, nY, j, i);
            }
        }
        return this;
    }

    /**
     * Casts a ray through a specific pixel and writes its color.
     *
     * @param nX  number of pixels in the x-axis
     * @param nY  number of pixels in the y-axis
     * @param col pixel's column index
     * @param row pixel's row index
     */
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = constructRay(nX, nY, col, row);
        Color pixelColor = this.rayTracer.traceRay(ray);
        imageWriter.writePixel(col, row, pixelColor);
    }

    /**
     * Prints a grid over the image with a specified interval and color.
     *
     * @param interval the spacing between grid lines
     * @param color    the color of the grid lines
     * @return the Camera object for chaining
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
            }
        }
        return this; // todo: they have asked this function will be void but in the tests there is a use in the return value of this function.
    }

    /**
     * Writes the final image to a file.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    // Inner Builder Class

    /**
     * Builder class for constructing Camera instances.
     */
    public static class Builder {
        final private Camera camera;

        // Constructors

        /**
         * Default constructor initializing a new Camera.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Constructor initializing with an existing Camera instance.
         *
         * @param camera the Camera instance to initialize with
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        // Builder Methods

        /**
         * Sets the camera location.
         *
         * @param location the point location to set
         * @return the Builder instance for chaining
         */
        public Builder setLocation(Point location) {
            // if(####){
            //     throw new IllegalArgumentException("######","######",####);
            // }
            camera.location = location;
            return this;
        }

        /**
         * Sets the direction vectors (Vup and Vto) of the camera.
         *
         * @param vup the up vector
         * @param vto the to vector
         * @return the Builder instance for chaining
         * @throws IllegalArgumentException if vup and vto are not orthogonal
         */
        public Builder setDirection(Vector vup, Vector vto) {
            if (!isZero(vto.dotProduct(vup))) {
                throw new IllegalArgumentException("vectors up and to are not orthogonal");
            }
            camera.Vup = vup.normalize();
            camera.Vto = vto.normalize();
            return this;

        }

        /**
         * Sets the size of the view plane.
         *
         * @param height the height of the view plane
         * @param width  the width of the view plane
         * @return the Builder instance for chaining
         * @throws IllegalArgumentException if width or height are not positive
         */
        public Builder setVpSize(double height, double width) {
            if (alignZero(width) <= 0 || alignZero(height) <= 0)
                throw new IllegalArgumentException("View Plane size values must be positive");
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance the distance value to set
         * @return the Builder instance for chaining
         */
        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        /**
         * Sets the ImageWriter for the camera.
         *
         * @param im the ImageWriter instance to set
         * @return the Builder instance for chaining
         */
        public Builder setImageWriter(ImageWriter im) {
            camera.imageWriter = im;
            return this;
        }

        /**
         * Sets the RayTracer for the camera.
         *
         * @param rt the RayTracerBase instance to set
         * @return the Builder instance for chaining
         */
        public Builder setRayTracer(RayTracerBase rt) {
            camera.rayTracer = rt;
            return this;
        }

        // Build Method

        /**
         * Builds and returns the Camera instance.
         *
         * @return the constructed Camera object
         * @throws MissingResourceException if any required data is missing
         */
        public Camera build() {
            // i deleted the distance check because there is not roll about it (can be negative)

            String Scamera = "Camera ";
            // normalize
            camera.Vto.normalize();
            camera.Vup.normalize();

            if (alignZero(camera.height) <= 0) {
                throw new MissingResourceException("height data is missing ", Scamera, "height");
            }

            if (alignZero(camera.width) <= 0) {
                throw new MissingResourceException("width data is missing ", Scamera, "width");
            }
            if (camera.Vto == null) {
                throw new MissingResourceException("Vector to data is missing ", Scamera, "Vto");
            }
            if (camera.Vup == null) {
                throw new MissingResourceException("Vector up data is missing ", Scamera, "Vup");
            }

            camera.Vright = camera.Vto.crossProduct(camera.Vup);

            camera.Vright.normalize();
            if (camera.Vright == null) {
                throw new MissingResourceException("Vector right data is missing ", Scamera, "Vright");
            }
            if (camera.location == null) {
                throw new MissingResourceException("Point location data is missing ", Scamera, "location");
            }
            if (camera.rayTracer == null) {
                throw new MissingResourceException("rayTracer data is missing ", Scamera, "rayTracer");
            }
            if (camera.imageWriter == null) {
                throw new MissingResourceException("imageWriter data is missing ", Scamera, "imageWriter");
            }
            Point pC = camera.location.add(camera.Vto.scale(camera.distance));
            if (pC == null)
                throw new MissingResourceException("distance to view plane", Scamera, "distance");

            return camera;
            // try {
            //     return (Camera) camera.clone();
            // }
            // catch (CloneNotSupportedException e){
            //     return null;
            // }

        } // end of build

    } // end of Builder

} // end of Camera
