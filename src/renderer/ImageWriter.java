package renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import primitives.Color;

/**
 * The ImageWriter class is responsible for managing the accumulation of a pixel color matrix and producing a non-optimized JPEG image from this matrix.
 * It handles image-related parameters of the view plane, such as the pixel matrix size and resolution.
 *
 * @author Dan
 */
public class ImageWriter {
    /** Horizontal resolution of the image - number of pixels in a row */
    private int nX;
    /** Vertical resolution of the image - number of pixels in a column */
    private int nY;

    /** Directory path for the image file generation - relative to the user directory */
    private static final String FOLDER_PATH = System.getProperty("user.dir") + "/images";

    /** Image generation buffer (the matrix of the pixels) */
    private BufferedImage image;
    /** Image file name, not including the file extension '.png' */
    private String imageName;
    /** Logger for reporting I/O failures */
    private Logger logger = Logger.getLogger("ImageWriter");

    // ***************** Constructors ********************** //

    /**
     * Constructs an ImageWriter object with the specified image name and view plane parameters.
     *
     * @param imageName the name of the PNG file
     * @param nX        the number of pixels by width (horizontal resolution)
     * @param nY        the number of pixels by height (vertical resolution)
     */
    public ImageWriter(String imageName, int nX, int nY) {
        this.imageName = imageName;
        this.nX = nX;
        this.nY = nY;

        image = new BufferedImage(nX, nY, BufferedImage.TYPE_INT_RGB);
    }

    // ***************** Getters/Setters ********************** //

    /**
     * Gets the vertical resolution of the view plane (Y axis).
     *
     * @return the number of vertical pixels
     */
    public int getNy() { return nY; }

    /**
     * Gets the horizontal resolution of the view plane (X axis).
     *
     * @return the number of horizontal pixels
     */
    public int getNx() { return nX; }

    // ***************** Operations ******************** //

    /**
     * Produces a PNG file of the image according to the pixel color matrix.
     * The file is saved in the project directory.
     *
     * @throws IllegalStateException if an I/O error occurs, such as a missing directory
     */
    public void writeToImage() {
        try {
            File file = new File(FOLDER_PATH + '/' + imageName + ".png");
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O error", e);
            throw new IllegalStateException("I/O error - may be missing directory " + FOLDER_PATH, e);
        }
    }

    /**
     * Writes the color of a specific pixel into the pixel color matrix.
     *
     * @param xIndex X axis index of the pixel
     * @param yIndex Y axis index of the pixel
     * @param color  final color of the pixel
     */
    public void writePixel(int xIndex, int yIndex, Color color) {
        image.setRGB(xIndex, yIndex, color.getColor().getRGB());
    }
}
