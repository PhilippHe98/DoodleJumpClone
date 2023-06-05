package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player extends Rectangle {

    private double left;

    public void setTop(double top) {
        this.top = top;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    private double top;
    private double right;
    private double bottom;
    private Paint paint;

    public Player(Context context, double left, double top, double right, double bottom){
        super();
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);

    }
    public void draw(Canvas canvas) {
        canvas.drawRect((float)left, (float) top, (float)right, (float) bottom, paint);

    }

    public void update() {
    }

    private void setPos(double top, double bottom){
        setTop(top);
        setBottom(bottom);
    }
    public void jump() {
        setPos(top -500, bottom -500);
    }

    public void reset() {
        setPos(top +500,bottom +500);
    }
}
