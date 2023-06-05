package de.fhkl.gatav.ut.doodlejumper.util;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D() {
        x=0;
        y=0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D(Vector2D vector) {
        x = vector.x;
        y= vector.y;
    }
    public double magnitude() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }

    public Vector2D normalize() {
        double magnitude = magnitude();
        this.x /= magnitude;
        this.y /= magnitude;
        return this;
    }

    public Vector2D add(Vector2D vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    public Vector2D subtract(Vector2D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    public static Vector2D add(Vector2D vector1,Vector2D vector2) {
        return new Vector2D(vector1.add(vector2));
    }

    public static Vector2D subtract(Vector2D vector1,Vector2D vector2) {
        return new Vector2D(vector1.subtract(vector2));
    }

    public Vector2D multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public static Vector2D multiply(Vector2D vector, double scalar) {
        return new Vector2D(vector.multiply(scalar));
    }



    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}