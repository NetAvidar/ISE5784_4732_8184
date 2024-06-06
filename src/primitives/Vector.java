package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
       this(new Double3(x,y,z));
    }

    public Vector(Double3 xyz) {
        super(xyz);
     //   if (xyz.equals(Double3.ZERO))
     //       throw new IllegalArgumentException("creating Vecor(0,0,0) is not valid");
    }

    public Vector(Point p) {
        Double3 xyz = p.getXyz();
        Vector(xyz);
    }



    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return xyz.d1 * xyz.d1 +
                xyz.d2 * xyz.d2 +
                xyz.d3 * xyz.d3;
    }

    public Vector add(Vector other) {
        return new Vector(xyz.add(other.xyz));
    }

    @Override
    public Point add(Double3 vector) {
        return super.add(vector);
    }

    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar)); //need to check if the scale is zero - throw  Illegal exeption
    }

    private boolean isZero(double scalar) { //need to check
        return (scalar ==0);
    }

    public double dotProduct(Vector other) {
        return xyz.d1 * other.xyz.d1 +
                xyz.d2 * other.xyz.d2 +
                xyz.d3 * other.xyz.d3;
    }

    public Vector crossProduct(Vector other) {
        double x = xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2;
        double y = xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3;
        double z = xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1;
        return new Vector(x, y, z);
    }

    public Vector normalize() {
        double length = length();
        if (length == 0) {
            throw new ArithmeticException("Cannot normalize a zero vector.");
        }
        return scale(1 / length);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Vector{" + "xyz=" + xyz + '}';
    }


}
