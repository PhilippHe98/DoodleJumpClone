package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Projectile extends Rectangle {
    private double speed = 50;
    public Projectile(Context context, Vector2D startPosition, Vector2D direction, double width, double height) {
        //Wähle hier width = height sodass ein Quadrat entsteht
        super(startPosition, width, height, ContextCompat.getColor(context, R.color.projektil));
        direction.y = - Math.abs(direction.y);

        velocity = new Vector2D(direction.x,direction.y).multiply(speed);
    }

    @Override
    public void update() {
        position.add(velocity);
        calculateNewTopLeftAndBottomRight();
    }

    public void draw(Canvas canvas) {
            // der radius ist die breite des Rechtecks durch 2 (Rechteck soll hier ein Quadrat sein
            float radius = (float) (width/2);
            canvas.drawCircle((float)position.x,(float)position.y,(float)radius, paint);
    }

    @Override
    public boolean isColliding(Rectangle other) {
        return false;
    }
}
