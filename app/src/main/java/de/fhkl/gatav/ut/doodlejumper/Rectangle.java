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

    public Rectangle() {
        super();
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

    // Erzeugt ein Objekt vom Typ Rect (Nicht zu verwechseln mit Rectangle), das dann zur Kollisionsdetektion benutzt werden kann,
    // da es die Methode intersect() besitzt die das bestimmen kann,
    public RectF getBounds() {
        return new RectF((float) topLeft.x, (float) topLeft.y, (float) bottomRight.x, (float) bottomRight.y);
    }

    public static boolean isColliding(Rectangle r1, Rectangle r2) {
        RectF rectF1 = r1.getBounds();
        RectF rectF2 = r2.getBounds();

        return rectF1.intersect(rectF2);
    }
}
