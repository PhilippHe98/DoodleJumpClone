package de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy;

import android.content.Context;

import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public abstract class Enemy extends Rectangle {

    private static double SPEED = 0;
    private static final double MAX_SPEED = SPEED / GameLoop.MAX_UPS;
    protected static final double VERTICAL_SPEED = 1000.0 / GameLoop.MAX_UPS;
    protected static boolean moveDown = false;

    public Enemy(Vector2D position, double width, double height, int color) {
        super(position, width, height, color);
    }

    public void update(){
        if(moveDown) velocity.set(velocity.x, VERTICAL_SPEED);
        if(!moveDown) velocity.set(velocity.x, 0);
        position.add(velocity);
        calculateNewTopLeftAndBottomRight();
    }

    public static void setMoveDown(boolean b) {
        moveDown = b;
    }

}
