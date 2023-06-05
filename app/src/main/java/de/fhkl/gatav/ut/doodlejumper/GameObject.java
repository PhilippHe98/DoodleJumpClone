package de.fhkl.gatav.ut.doodlejumper;

import android.graphics.Canvas;

abstract public class GameObject {

    protected double posX;
    protected double posY;
    protected double velocityX;
    protected double getVelocityY;


    public GameObject(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        this.posY = posY;
    }

    public GameObject() {
    }

    public static double distanceBetweenGameObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj1.posX - obj2.posX,2)
                + Math.pow(obj1.posX - obj2.posX,2)
        );
    }
    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getGetVelocityY() {
        return getVelocityY;
    }

    public void setGetVelocityY(double getVelocityY) {
        this.getVelocityY = getVelocityY;
    }

    abstract void update();

    abstract void draw(Canvas canvas);
}