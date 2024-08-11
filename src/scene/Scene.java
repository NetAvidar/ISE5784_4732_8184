package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import renderer.SimpleRayTracer;

import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a 3D scene containing various elements such as geometries,
 * lights, background color, and ambient lighting. It also supports the configuration of
 * soft shadows and the number of rays used in beam tracing.
 */
public class Scene {
    /** The name of the scene */
    public String name;

    /** The background color of the scene, default is black (0,0,0) */
    public Color background = new Color(0, 0, 0);

    /** The ambient light of the scene, default is no ambient light */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /** The collection of geometries in the scene */
    public Geometries geometries = new Geometries();

    /** The list of light sources in the scene */
    public List<LightSource> lights = new LinkedList<>();

    /** Flag indicating whether soft shadows are enabled, default is false */
    private boolean softShadow = false;

    /** The number of rays in a beam for soft shadows, default is 1 */
    private int numOfRaysAtBeam = 1;

    /**
     * Constructor to create a scene with the specified name.
     *
     * @param s The name of the scene.
     */
    public Scene(String s) {
        this.name = s;
    }

    /**
     * Sets the name of the scene.
     *
     * @param name The new name of the scene.
     * @return The current scene object for chaining.
     */
    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background The new background color.
     * @return The current scene object for chaining.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The new ambient light.
     * @return The current scene object for chaining.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     *
     * @param geometries The new collection of geometries.
     * @return The current scene object for chaining.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the light sources in the scene.
     *
     * @param lights The new list of light sources.
     * @return The current scene object for chaining.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Enables or disables soft shadows in the scene.
     *
     * @param softShadow True to enable soft shadows, false to disable.
     * @return The current scene object for chaining.
     */
    public Scene setSoftShadow(boolean softShadow) {
        this.softShadow = softShadow;
        return this;
    }

    /**
     * Sets the number of rays in a beam for soft shadows.
     *
     * @param numOfRaysAtBeam The new number of rays in a beam.
     * @return The current scene object for chaining.
     */
    public Scene setNumOfRaysAtBeam(int numOfRaysAtBeam) {
        this.numOfRaysAtBeam = numOfRaysAtBeam;
        return this;
    }

    /**
     * Checks if soft shadows are enabled.
     *
     * @return True if soft shadows are enabled, false otherwise.
     */
    public boolean isSoftShadow() {
        return softShadow;
    }

    /**
     * Gets the number of rays in a beam used for soft shadows.
     *
     * @return The number of rays in a beam.
     */
    public int getNumOfRaysAtBeam() {
        return numOfRaysAtBeam;
    }
}
