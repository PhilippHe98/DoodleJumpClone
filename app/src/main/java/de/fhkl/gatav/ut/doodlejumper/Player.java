package de.fhkl.gatav.ut.doodlejumper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import de.fhkl.gatav.ut.doodlejumper.util.Vector2D;

/**
 * Die Klasse Player ist ein GameObject, das vom Spieler gesteuert.
 */
public class Player extends Rectangle {
    private static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SIDE_MOVE_SPEED = 300.0/GameLoop.MAX_UPS;

    /*
    Variablen für das Springen des Players
     */
    private boolean isJumping;
    private long jumpStartTime;
    private float jumpduration;

    /**
     * Standardkonstruktor
     * @param context Übergebener Context
     * @param position Position an dem der Spieler "spawnt"
     * @param width Breite des Spieler vom Mittelpunkt aus
     * @param height Höhe des Spielers vom Mittelpunkt aus
     */
    public Player(Context context, Vector2D position, double width, double height){
        super(position, width, height, ContextCompat.getColor(context, R.color.magenta));
        velocity = new Vector2D(0,MAX_SPEED);
    }

    /**
     * Update Methode die den Spieler im Laufe des GameLoops updatet
     */
    public void update() {
        // der aktuellen Position des Spielers die festgelegte Velocity dazuaddieren
        position.add(velocity);
        // berechnen der neuen Maße des Rechtecks
        calculateNewTopLeftAndBottomRight();
        // überprüfen der Position des Spielers um das Springen zu steuern
        if(position.y < 800) moveDown();

        if(position.x < -50) position.set(1050, position.y);
        if(position.x > 1050) position.set(-50, position.y);

        //Nur für Testzwecke
        if (position.y>2100) position.set(position.x, 500);

        //Springen
        for(Rectangle rect : Game.platforms)
            if (this.isColliding(rect)) {
                System.out.println("ACHTUNG KOLLISION!!!");
                moveUp();
            }
    }

    /**
     * Methode zum starten des Springens
     */
    public void startJump() {
        if(!isJumping) {
            isJumping = true;
            jumpStartTime = System.currentTimeMillis();
        }
    }

    /**
     * Methode um den Spieler nach unten zu bewegen
     */
    public void moveDown() {
        velocity.set(0,MAX_SPEED);
    }
    /**
     * Methode um den Spieler nach oben zu bewegen
     */
    public void moveUp() {
        velocity.set(0,-MAX_SPEED);
    }
    /**
     * Methode um den Spieler nach rechts bzw. links zu bewegen
     */
    public void moveSideways(float accelerationX) {
        velocity.set(-accelerationX*SIDE_MOVE_SPEED, velocity.y);
    }
}
