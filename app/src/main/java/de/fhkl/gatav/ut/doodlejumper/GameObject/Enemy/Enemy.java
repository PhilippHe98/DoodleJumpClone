package de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy;

import android.content.Context;

import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public abstract class Enemy extends Rectangle {

    private static double SPEED = 0;
    private static final double MAX_SPEED = SPEED / GameLoop.MAX_UPS;

    public Enemy(Vector2D position, double width, double height, int color) {
        super(position, width, height, color);
    }

}
