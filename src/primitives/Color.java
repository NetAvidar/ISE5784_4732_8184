package primitives;

/**
 * Wrapper class for java.jwt.Color The constructors operate with any
 * non-negative RGB values. The colors are maintained without upper limit of
 * 255. Some additional operations are added that are useful for manipulating
 * light's colors
 * @author Dan Zilberstein
 */
public class Color {

    private final Double3     rgb;
    public static final Color BLACK = new Color();  //Black color = (0,0,0)

    //constructor
    private Color() { rgb = Double3.ZERO; }
    public Color(double r, double g, double b) {
        if (r < 0 || g < 0 || b < 0) throw new IllegalArgumentException("Negative color component is illegal");
        rgb = new Double3(r, g, b);
    }
    private Color(Double3 rgb) {
        if (rgb.d1 < 0 || rgb.d2 < 0 || rgb.d3 < 0)
            throw new IllegalArgumentException("Negative color component is illegal");
        this.rgb = rgb;
    }
    public Color(java.awt.Color other) { rgb = new Double3(other.getRed(), other.getGreen(), other.getBlue()); }

    //getter
    public java.awt.Color getColor() {
        int ir = (int) rgb.d1;
        int ig = (int) rgb.d2;
        int ib = (int) rgb.d3;
        return new java.awt.Color(ir > 255 ? 255 : ir, ig > 255 ? 255 : ig, ib > 255 ? 255 : ib);
    }
    //function
    public Color add(Color... colors) {
        double rr = rgb.d1;
        double rg = rgb.d2;
        double rb = rgb.d3;
        for (Color c : colors) {
            rr += c.rgb.d1;
            rg += c.rgb.d2;
            rb += c.rgb.d3;
        }
        return new Color(rr, rg, rb);
    }
    public Color scale(Double3 k) {
        if (k.d1 < 0.0 || k.d2 < 0.0 || k.d3 < 0.0)
            throw new IllegalArgumentException("Can't scale a color by a negative number");
        return new Color(rgb.product(k));
        //return new Color(rgb.d1*k.d1,rgb.d2*k.d2,rgb.d3*k.d3);
    }
    public Color scale(double k) {
        if (k < 0.0) throw new IllegalArgumentException("Can't scale a color by a negative number");
        return new Color(rgb.scale(k));
    }
    public Color reduce(double k) {
        if (k < 1) throw new IllegalArgumentException("Can't scale a color by a by a number lower than 1");
        return new Color(rgb.reduce(k));
    }
    public Color reduce(int k) {
        if (k < 1) throw new IllegalArgumentException("Can't scale a color by a by a number lower than 1");
        return new Color(rgb.reduce(k));
    }


    @Override
    public String toString() { return "rgb:" + rgb; }
}
