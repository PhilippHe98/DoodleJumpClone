package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.RectF;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/**
 * Die Klasse Player ist ein GameObject, das vom Spieler gesteuert.
 */
public class Player extends Rectangle {
    private static final double SPEED_PIXELS_PER_SECOND = 1200.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SIDE_MOVE_SPEED = 300.0 / GameLoop.MAX_UPS;

    private boolean isJumping = false;
    private double gravityValue = 0.6;
    private double jumpForce = 28;

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
    }

    /**
     * Update Methode die den Spieler im Laufe des GameLoops updatet
     */
    public void update() {

        // Seitenwechsel
        if (position.x < -50) position.set(1050, position.y);
        if (position.x > 1050) position.set(-50, position.y);

        if(position.y < 1000) position.set(position.x, 1000);

        //Nur für Testzwecke, Resettet den Spieler
//        if (position.y > 2100) position.set(position.x, 500);

        if (velocity.y > 0) isJumping = false;
        if (velocity.y <= 0) isJumping = true;

        // Kollisionen resetten den Jump Timer
        for (Platform platform : Game.platforms) {
         if((this.bottomRight.y > platform.topLeft.y))
            if (isColliding(this, platform)) {
                if (!isJumping) velocity.y = -jumpForce;
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
}
