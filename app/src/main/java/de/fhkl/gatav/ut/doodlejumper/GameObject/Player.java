package de.fhkl.gatav.ut.doodlejumper.GameObject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

import androidx.core.content.ContextCompat;

import org.w3c.dom.ls.LSResourceResolver;

import java.util.ArrayList;

import de.fhkl.gatav.ut.doodlejumper.Game;
import de.fhkl.gatav.ut.doodlejumper.GameLoop;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.BreakablePlatform;
import de.fhkl.gatav.ut.doodlejumper.GameObject.Platform.Platform;
import de.fhkl.gatav.ut.doodlejumper.R;
import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/**
 * Die Klasse Player ist ein GameObject, das vom Spieler gesteuert.
 */
public class Player extends Rectangle {
    private static final double SPEED_PIXELS_PER_SECOND = 1200.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SIDE_MOVE_SPEED = 300.0 / GameLoop.MAX_UPS;


    private boolean isJumping = false;
    private double gravityValue = 0.8;
    private double jumpForce = 35;

    private boolean collisionFeedback;

    MediaPlayer mediaPlayer;
    Bitmap jwNormal;

    /**
     * Standardkonstruktor
     *
     * @param context  Übergebener Context
     * @param position Position an dem der Spieler "spawnt"
     * @param width    Breite des Spieler vom Mittelpunkt aus
     * @param height   Höhe des Spielers vom Mittelpunkt aus
     */
    public Player(Context context, Vector2D position, double width, double height) {
        super(position, width, height, ContextCompat.getColor(context, R.color.magenta));
        velocity = new Vector2D(0, MAX_SPEED);
        mediaPlayer = MediaPlayer.create(context, R.raw.jump_sound_cut);
        Resources res = context.getResources();
        jwNormal = BitmapFactory.decodeResource(res, R.id.jwView);
    }

    /**
     * Update Methode die den Spieler im Laufe des GameLoops updatet
     */
    public void update() {

        // Seitenwechsel
        if (position.x < -50) position.set(1050, position.y);
        if (position.x > 1050) position.set(-50, position.y);

        if(position.y < 1300) position.set(position.x, 1300);

        //Nur für Testzwecke, Resettet den Spieler
//        if (position.y > 2100) position.set(position.x, 500);

        if (velocity.y > 0) isJumping = false;
        if (velocity.y <= 0) isJumping = true;

        // Kollisionen resetten den Jump Timer
        for (Platform platform : Game.platforms) {
            if (isColliding(this, platform)) {
                if(this.bottomRight.y > platform.topLeft.y) {
                    if (!isJumping) {
                        velocity.y = -jumpForce;
                        mediaPlayer.start();
                        // Zerstöre Plattform wenn richtiger Plattformtyp
                        if(platform.getClass().toString().contains("BreakablePlatform")) {
                            Game.addPlatformsToRemove(platform);
                        }
                    }
                }
            }
        }
        velocity.y += gravityValue;
        //Begrenzung der Geschwindigkeit
        if (velocity.y > 20) velocity.y = 20;

        // der aktuellen Position des Spielers die festgelegte Velocity dazuaddieren
        position.add(velocity);
        // berechnen der neuen Maße des Rechtecks
        calculateNewTopLeftAndBottomRight();
    }
    /**
     * Methode um den Spieler nach rechts bzw. links zu bewegen
     */
    public void moveSideways(float accelerationX) {
        velocity.set(-accelerationX * SIDE_MOVE_SPEED, velocity.y);
    }

    public boolean getCollisionFeedback() {
        return collisionFeedback;
    }
}
