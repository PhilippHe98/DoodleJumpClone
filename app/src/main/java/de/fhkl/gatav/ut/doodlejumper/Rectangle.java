package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

//Abstrakte Klasse
public abstract class Rectangle extends GameObject{

    private Vector2D topLeft; // topleft.x ist left, topleft.y ist top
    private Vector2D bottomRight; // bottomright.x ist right, bottomright.y ist bottom
    protected Vector2D centerPoint;
    protected double width;
    protected double height;
    protected Paint paint;

    public Rectangle(Context context, Vector2D position, double width, double height) {
        super(position);
        centerPoint = position;
        topLeft = new Vector2D(centerPoint.x - width/2, centerPoint.y -height/2);
        bottomRight = new Vector2D(centerPoint.x +width/2, centerPoint.y + height/2);
        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
    }

    @Override
    void draw(Canvas canvas) {
        canvas.drawRect((float)topLeft.x, (float) topLeft.y, (float) bottomRight.x,(float) bottomRight.y, paint);
    }
}