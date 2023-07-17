package de.fhkl.gatav.ut.doodlejumper.GameObject.Platform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class MovingPlatform extends Platform{

    private static final double  SIDE_MOVE_SPEED = 300 / GameLoop.MAX_UPS;
    private Sprite sprite;

    public MovingPlatform(Context context, Vector2D position, double width, double height) {
        super(position, width, height,  ContextCompat.getColor(context, R.color.moving_plattform));

        SpriteSheet spriteSheet = new SpriteSheet(context, R.drawable.plattform3);
        sprite = new Sprite(spriteSheet, new Rect(0,0,4040, 756));
        // Bewegt sich zufällig links oder rechts beim Spawnen
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
        if(position.x < 0) {
            velocity.add(SIDE_MOVE_SPEED, velocity.y);
        }
        if(position.x > 1000) {
            velocity.add(-SIDE_MOVE_SPEED, velocity.y);
        }
    }
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, topLeft, bottomRight);
    }
}
