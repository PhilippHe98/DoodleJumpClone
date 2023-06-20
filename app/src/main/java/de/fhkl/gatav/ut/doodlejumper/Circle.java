package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public abstract class Circle extends GameObject{
    protected double radius;
    protected Paint paint;
    public Circle(Vector2D position, double radius, int color) {
        super(position);
        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    void draw(Canvas canvas) {
        canvas.drawCircle((float)position.x,(float)position.y,(float)radius, paint);
    }
}
