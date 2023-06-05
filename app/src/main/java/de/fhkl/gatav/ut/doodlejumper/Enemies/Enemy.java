package de.fhkl.gatav.ut.doodlejumper.Enemies;

import android.content.Context;
import android.graphics.Paint;

import de.fhkl.gatav.ut.doodlejumper.GameLogic.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle.Rectangle;

/**
 * Enemies are different characters posing a threat to the player by touching him. They are mostly
 * stationary/ moving from side to side while the player tries to evade them.
 * Enemy extends the Rectangle class.
 */
public abstract class Enemy extends Rectangle {

    private Paint paint;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private static double SPEED_PIXELS_PER_SEC = 0;

    private static final double MAX_SPEED = SPEED_PIXELS_PER_SEC / GameLoop.MAX_UPS;

    public Enemy(Context context, double posX, double posY, int color, Player player) {
        super(posX, posY);
        this.paint = new Paint();
        paint.setColor(color);
    }
    /**
     readyToSpawn checks if a new enemy should spawn, according to the decided number of spawn
     per minute (see SPAWNS_PER_MINUTE at the top)
     @return
     */
    public static boolean readyToSpawn() {
        if(updatesUntilNextSpawn <= 0){
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn --;
            return false;
        }
    }
}
