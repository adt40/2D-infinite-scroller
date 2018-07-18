package main.java.com.util;

import java.util.Objects;

public class Vector {
    private Double x;
    private Double y;

    public Vector(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Integer x, Integer y) {
        this.x = (double) x;
        this.y = (double) y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Vector add(Vector other) {
        return new Vector(x + other.getX(), y + other.getY());
    }

    public Vector sub(Vector other) {
        return new Vector(x - other.getX(), y - other.getY());
    }

    public Double dotProduct(Vector other) {
        return x * other.getX() + y * other.getY();
    }

    public Vector multiplyByScalar(Double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector multiplyByScalar(Integer scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector roundedDown() {
        return new Vector((double) x.intValue(), (double) y.intValue());
    }

    public Double distanceTo(Vector other) {
        return Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
    }

    public Double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector unit() {
        return multiplyByScalar(magnitude());
    }

    public static Vector getVectorFromDirectionInt(int directionValue) {
        switch (directionValue) {
            case 0:
                //up (w)
                return new Vector(0, -1);
            case 1:
                //down (s)
                return new Vector(0, 1);
            case 2:
                //left (a)
                return new Vector(-1, 0);
            case 3:
                //right (d)
                return new Vector(1, 0);
            default:
                return new Vector(0, 0);
        }
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(x, vector.x) &&
                Objects.equals(y, vector.y);
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }
}
