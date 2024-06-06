package primitives;

import java.util.Objects;

public class Point {
    public static final Point ZERO = new Point(0d,0d,0d);  //d main double
    protected Double3 xyz;

    public Double3 getXyz() {
        return xyz;
    }


    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z); //DRY
    }

    Point(Double3 xyz) { //package friendly
        this.xyz = xyz;
    }

    public Point add(Double3 vector) {
        return new Point(this.xyz.add(vector));
    }

    public double distanceSquared(Point other) {
        double dx = this.xyz.d1 - other.xyz.d1;
        double dy = this.xyz.d2 - other.xyz.d2;
        double dz = this.xyz.d3 - other.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(Point other) {
        return Math.sqrt(this.distanceSquared(other));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false; //use instanceof like Dan asked
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "Point{" + "xyz=" + xyz + '}';
    }

    // Getters and Setters (optional)

    public Point add(Vector vector) {
        Double3 newXYZ = this.xyz.add(vector.xyz);
        return new Point(newXYZ);
    }

    public Vector subtract(Point point) {
        Double3 newXYZ = this.xyz.subtract(point.xyz);
        return new Vector(newXYZ);
    }
}
