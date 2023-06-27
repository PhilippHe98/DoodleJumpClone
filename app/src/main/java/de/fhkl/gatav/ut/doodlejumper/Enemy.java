package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public abstract class Enemy extends Rectangle {

    private static double SPEED = 0;
    private static final double MAX_SPEED = SPEED / GameLoop.MAX_UPS;
    private static final double UPDATES_PER_SPAWN = 300;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;

    public Enemy(Context context, Vector2D position, double width, double height, int color) {
        super(position, width, height, color);
    }

    /**
     * readyToSpawn checks if the enemy is allowed to spawn right now (limited by UPDATES_PER_SPAWN
     * on top)
     * @return
     */

    public static boolean readyToSpawn(){
        if(updatesUntilNextSpawn <= 0){
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }else {
            updatesUntilNextSpawn--;
            return false;
        }
    }
}
