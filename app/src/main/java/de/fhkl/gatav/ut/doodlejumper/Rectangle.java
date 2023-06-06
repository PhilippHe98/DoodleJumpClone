package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

//Abstrakte Klasse
public abstract class Rectangle extends GameObject{

    protected Vector2D topLeft; // topleft.x ist left, topleft.y ist top
    protected Vector2D bottomRight; // bottomright.x ist right, bottomright.y ist bottom
    protected double width;
    protected double height;
    protected Paint paint;

    public Rectangle(Vector2D position, double width, double height, int color) {
        super(position);
        this.width = width;
        this.height = height;

        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        calculateNewTopLeftAndBottomRight();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect((float)topLeft.x, (float) topLeft.y, (float) bottomRight.x,(float) bottomRight.y, paint);
    }

    public void calculateNewTopLeftAndBottomRight() {
        //position ist hier der Mittelpunkt des Vierecks
        this.topLeft = new Vector2D(position.x - width/2, position.y - height/2);
        this.bottomRight = new Vector2D(position.x + width/2, position.y + height/2);
    }


    public Vector2D getTopLeft() {
        return topLeft;
    }

    public Vector2D getBottomRight() {
        return bottomRight;
    }

    public void addBottomRight(Vector2D bottomRight) {
        this.bottomRight.add(bottomRight);
    }

    public void addTopLeft(Vector2D topLeft) {
        this.topLeft.add(topLeft);
    }

}
