package de.fhkl.gatav.ut.doodlejumper.util;
/*
Eine externe Klasse die x,y Werte speichert sodass man mit ihnen arbeiten kann.
 */
public class Vector2D {
    /*
    X Position des Objekts auf dem Bildschrim des Smartphones
     */
    public double x;
    /*
    Y Position des Objekts auf dem Bildschrim des Smartphones
     */
    public double y;

    /*
    Standardkonstruktor der die Werte x und y initialisiert
     */
    public Vector2D() {
        this.x=0;
        this.y=0;
    }
    /*
    Konstruktor der mit den übergebenen Werten die Werte x und y initialisiert
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /*
    Konstruktor der die Werte x und y mit den Werten eines übergebenen Vectors initialisiert
     */
    public Vector2D(Vector2D vector) {
        this.x = vector.x;
        this.y= vector.y;
    }
    /*
    Magnitude gibt die Länge des Vectors zurück mithilfe der Betragsfunktion zurück
     */
    public double magnitude() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }
    /*
    Setzt die Werte von x und y auf die übergebenen Werte x und y
    */
    public void set(double x,double y) {
        this.x = x;
        this.y = y;
    }
    /*
    Normalsiert den Vector, heißt die Länge des Vectors ist 1
     */
    public Vector2D normalize() {
        double magnitude = magnitude();
        this.x /= magnitude;
        this.y /= magnitude;
        return this;
    }
    /*
    Addiert die übergebenen Werte x und y zu den Werten von x und y des Vectors der die
    Funktion aufruft
     */
    public Vector2D add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }
    /*
    Addiert die Werte vom Vector, der die Funktion aufruft, mit den Werten vom übergebenen Vector
     */
    public Vector2D add(Vector2D vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }
    /*
    Subtrahiert den übergebenen Vector vom Vector der die Funktion aufruft
     */

    public Vector2D subtract(Vector2D vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }
    /*
    Addiert zwei Vectoren miteinander mithilfe der obigen add Funktion und gibt
    einen neuen Vector zurück
     */

    public static Vector2D add(Vector2D vector1,Vector2D vector2) {
        return new Vector2D(vector1.add(vector2));
    }
    /*
    Subtrahiert zwei Vectoren voneinander mithilfe der subtract Funktion von oben und gibt einen
    neuen Vector zurück
     */

    public static Vector2D subtract(Vector2D vector1,Vector2D vector2) {
        return new Vector2D(vector1.subtract(vector2));
    }

    /**
     * Multipliziert die x und y Werte eines Vecotrs mit einem Skalar
     * @param scalar
     * @return Vector
     */
    public Vector2D multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    /**
     * Multipliziert einen Vector mit einem Skalar
     * @param vector
     * @param scalar
     * @return Vector
     */
    public static Vector2D multiply(Vector2D vector, double scalar) {
        return new Vector2D(vector.multiply(scalar));
    }

    /**
     * Ausgabe des Vectors mithilfe der überschriebene ToString Methode
     * @return String
     */

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}