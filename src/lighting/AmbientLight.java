package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light {

    protected Color Ia;
    protected Double3 Ka;
    public final static AmbientLight NONE = new AmbientLight(Color.BLACK,0);
    //constructor
    public AmbientLight(Color ia, Double3 ka)
    {
        super(ia.scale(ka));
    }
    public AmbientLight(Color ia, double k)
    {
        super(ia.scale(k));
    }


}