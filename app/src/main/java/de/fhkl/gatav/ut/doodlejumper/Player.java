package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Player extends Rectangle {
    private static final double SPEED_PIXELS_PER_SECOND = 600.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public Player(Context context, Vector2D position, double width, double height){
        super(position, width, height, ContextCompat.getColor(context, R.color.magenta));
    }

    public void update() {
        moveDown();
        calculateNewTopLeftAndBottomRight();
    }

    public void moveDown() {
        position.add(0,MAX_SPEED);
    }
}
