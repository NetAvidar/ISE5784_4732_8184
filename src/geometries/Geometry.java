package geometries;

import primitives.Material;
import primitives.Point;
import primitives.Vector;
import primitives.Color;
import java.awt.*;

public abstract class Geometry extends Intersectable{

    protected Color emission = Color.BLACK;
    private Material material =  new Material();

    public abstract Vector getNormal(Point point);

    public Color getEmission() {
        return this.emission;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public Geometry setEmission(Color emi) {
        this.emission = emi;
        return this;
    }


}