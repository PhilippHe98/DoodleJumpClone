package de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy.Enemy;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class hoveringEnemy extends Enemy {

    private static final double PIXEL_PER_UPDATE = 1;

    private Vector2D velocity = new Vector2D(PIXEL_PER_UPDATE,0);


    public hoveringEnemy(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.white));
    }

    @Override
    public void update() {
        if(position.x < 0) moveRight();
        if(position.x > 1000) moveLeft();
        moveDown();
    }

    private void moveRight() {
        velocity.set(PIXEL_PER_UPDATE, velocity.y);
    }

    private void moveLeft() {
        velocity.set(-PIXEL_PER_UPDATE, velocity.y);
    }

}
