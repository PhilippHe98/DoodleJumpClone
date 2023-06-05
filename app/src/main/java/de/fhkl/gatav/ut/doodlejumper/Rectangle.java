package de.fhkl.gatav.ut.doodlejumper;

import android.graphics.Canvas;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

//Abstrakte Klasse
public abstract class Rectangle extends GameObject{

    private Vector2D topleft;
    private Vector2D topright;
    private Vector2D bottomleft;
    private Vector2D bottomright;
    protected Vector2D centerPoint;
    protected double width;
    protected double height;

    public Rectangle(Vector2D position, double width, double height) {
        super(position);
    }

    @Override
    void draw(Canvas canvas) {
        canvas.drawRect();
    }
}
