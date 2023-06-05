package de.fhkl.gatav.ut.doodlejumper.GameObject;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Circle.Circle;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Rectangle;

public abstract class GameObject {

    private double posX = 0;
    private double posY = 0;

    public GameObject(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected double getPosY() {
        return posY;
    }

    protected double getPosX() {
        return posX;
    }

    protected static double getDistanceBetweenRect(Rectangle obj1, Rectangle obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPosX() - obj1.getPosX(), 2) +
                        Math.pow(obj2.getPosY() - obj1.getPosY(), 2)
        );
    }

    protected static double getDistanceBetweenCircle(Circle obj1, Circle obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPosX() - obj1.getPosX(), 2) +
                        Math.pow(obj2.getPosY() - obj1.getPosY(), 2)
        );
    }

    protected static double getDistanceBetweenObjects(Circle obj1, Rectangle obj2) {
       return 0;
    }
}
