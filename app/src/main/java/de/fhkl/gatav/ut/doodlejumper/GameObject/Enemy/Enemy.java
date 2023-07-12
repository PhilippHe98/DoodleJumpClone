package de.fhkl.gatav.ut.doodlejumper.GameObject.Enemy;

import android.content.Context;
import android.graphics.Canvas;

import de.fhkl.gatav.ut.doodlejumper.EventListener;
import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Player;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Rectangle;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public abstract class Enemy extends Rectangle implements EventListener {

    protected static final double DEFAULT_VERTICAL_SPEED = 1000.0 / GameLoop.MAX_UPS;
    protected static final double TRAMPOLIN_VERTICAL_SPEED = 1700 / GameLoop.MAX_UPS;
    protected double vertical_speed;
    protected static boolean moveDown = false;

    protected Sprite enemySprite;

    protected Player player;

    public Enemy(Vector2D position, double width, double height, int color, Sprite sprite) {
        super(position, width, height, color);
        this.enemySprite = sprite;
    }

    @Override
    public void draw(Canvas canvas) {
        enemySprite.draw(canvas, topLeft, bottomRight);
        canvas.drawRect((float)topLeft.x, (float) topLeft.y, (float) bottomRight.x,(float) bottomRight.y, paint);
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
                default:
                    vertical_speed = DEFAULT_VERTICAL_SPEED;
            }
        }
    }

}
