package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Projectile extends Circle {

    private double speed = 50;
    public Projectile(Context context, Vector2D startPosition, Vector2D direction, double radius) {
        super(startPosition, radius, context.getColor(R.color.projektil));

        direction.y = - Math.abs(direction.y);


        velocity = new Vector2D(direction.x,direction.y).multiply(speed);
    }

    @Override
    void update() {
        position.add(velocity);
    }
}
