package de.fhkl.gatav.ut.doodlejumper;

import android.graphics.Canvas;

//Abstrakte Klasse
public abstract class Rectangle extends GameObject{

    private double topleft;
    private double topright;
    private double bottomleft;
    private double bottomright;
    protected double centerPointX;
    protected double getCenterPointY;
    protected double width;
    protected double height;

    public Rectangle(double posX, double posY) {
        super(posX, posY);
    }

    @Override
    void draw(Canvas canvas) {
        canvas.drawRect();
    }
}
