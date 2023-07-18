package de.fhkl.gatav.ut.doodlejumper.GameObject.PowerUp;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.EventListener;
import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;


/*
* Die PowerUP Klasse stellt die PowerUps und deren Fähigkeiten zur Verfügung, außerdem kümmert
* sie sich um die Platzierung der Objekte (nur auf Plattformen möglich)
* */
public abstract class PowerUp extends Rectangle implements EventListener {

    protected static final double DEFAULT_VERTICAL_SPEED = 600.0 / GameLoop.MAX_UPS;
    protected static final double TRAMPOLIN_VERTICAL_SPEED = 1400 / GameLoop.MAX_UPS;
    protected double vertical_speed;
    private static boolean moveDown;
    protected Player player;

    public PowerUp(Vector2D position, double width, double height, int color) {
        super(position, width, height, color);
    }

    protected void moveDown() {
        if(moveDown) velocity.set(velocity.x, vertical_speed);
        if(!moveDown) velocity.set(velocity.x, 0);

        position.add(velocity);
        calculateNewTopLeftAndBottomRight();
    }

    public static void setMoveDown(boolean b) {
        moveDown = b;
    }



    public void setPlayer(Player player) {
        if(this.player != null) {
            this.player.removeListener(this);
        }

        this.player = player;

        if(this.player != null) {
            this.player.registerListener(this);
        }
    }
    public void removeListener() {
        if(this.player != null) {
            this.player.removeListener(this);
        }
    }

    @Override
    public void reactToEvent() {
        if(player != null) {
            switch (player.getState()) {
                case "TRAMPOLIN":
                    vertical_speed = TRAMPOLIN_VERTICAL_SPEED;
                    break;
                case "JETPACK":
                    vertical_speed = TRAMPOLIN_VERTICAL_SPEED;
                    break;
                default:
                    vertical_speed = DEFAULT_VERTICAL_SPEED;

            }
        }
    }
}
