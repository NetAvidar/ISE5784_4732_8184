package lighting;

import primitives.Color;

abstract class Light {
    protected Color intensity;

    //constructor
    public Light(Color intensity) {
        this.intensity = intensity;
    }

    //getter
    public Color getIntensity() {
        return intensity;
    }


}
