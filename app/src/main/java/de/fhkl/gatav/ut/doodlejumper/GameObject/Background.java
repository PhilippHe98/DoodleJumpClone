package de.fhkl.gatav.ut.doodlejumper.GameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.EventListener;
import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.Graphics.Sprite;
import de.fhkl.gatav.ut.doodlejumper.Graphics.SpriteSheet;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

public class Background extends Rectangle implements EventListener {

    protected Player player = null;
    protected static final double DEFAULT_VERTICAL_SPEED = 1000.0 / GameLoop.MAX_UPS;
    protected static final double TRAMPOLIN_VERTICAL_SPEED = 1700 / GameLoop.MAX_UPS;
    protected static double vertical_speed = DEFAULT_VERTICAL_SPEED;
    private static boolean moveDown;
    private Sprite backgroundSprite;

    public Background(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.blue));

        SpriteSheet backgroundSpriteSheet = new SpriteSheet(context, R.drawable.background);
        backgroundSprite = new Sprite(backgroundSpriteSheet, new Rect(0,0,4688,10146));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        backgroundSprite.draw(canvas, topLeft, bottomRight);
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
                case "DEFAULT":
                    vertical_speed = DEFAULT_VERTICAL_SPEED;
                    break;
            }
        }
    }
}
