package de.fhkl.gatav.ut.doodlejumper.GameObject.Platform;

import android.content.Context;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class MovingPlatform extends Platform{

    private static final double  SIDE_MOVE_SPEED = 200 / GameLoop.MAX_UPS;

    public MovingPlatform(Context context, Vector2D position, double width, double height) {
        super(position, width, height,  ContextCompat.getColor(context, R.color.moving_plattform));

        double random_index = Math.random();
        if (random_index < 0.5) random_index = -1;
        else {
            random_index = 1;
        }

        velocity = new Vector2D(random_index * SIDE_MOVE_SPEED, velocity.y);
    }

    @Override
    public void update() {
        moveSideWays();
        moveDown();
    }

    private void moveSideWays() {
        if(position.x < 0)
        velocity.add(SIDE_MOVE_SPEED, velocity.y);
        if(position.x > 1000) {
            velocity.add(-SIDE_MOVE_SPEED, velocity.y);
        }
    }
}
