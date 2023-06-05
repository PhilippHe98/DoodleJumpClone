package de.fhkl.gatav.ut.doodlejumper;

import android.graphics.Canvas;
import android.graphics.Paint;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;


abstract public class GameObject {

    protected Vector2D position;
    protected Vector2D velocity;

    public GameObject(Vector2D position) {
        this.position = position;
    }

    public static double distanceBetweenGameObjects(GameObject obj1, GameObject obj2) {
        return obj1.position.subtract(obj2.position).magnitude();
    }
    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocityX(Vector2D velocity) {
        this.velocity = velocity;
    }

    abstract void update();

    abstract void draw(Canvas canvas);
}