package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light {

    //private final Color intesity;
    protected Double3 Ka;
    protected Color Ia;
    public final static AmbientLight NONE = new AmbientLight(Color.BLACK,0);



    public AmbientLight(Color i, Double3 k) { super(i.scale(k));}

    public AmbientLight(Color i, double k) {
        super(i.scale(k));
    }

    //public Color getIntesity() {
        return intesity;
    }
}
